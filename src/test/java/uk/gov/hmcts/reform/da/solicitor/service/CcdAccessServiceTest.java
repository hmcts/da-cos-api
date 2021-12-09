package uk.gov.hmcts.reform.da.solicitor.service;

import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.hmcts.reform.authorisation.exceptions.InvalidTokenException;
import uk.gov.hmcts.reform.authorisation.generators.AuthTokenGenerator;
import uk.gov.hmcts.reform.ccd.client.CaseAssignmentApi;
import uk.gov.hmcts.reform.ccd.client.CaseUserApi;
import uk.gov.hmcts.reform.ccd.client.model.CaseAssignmentUserRolesRequest;
import uk.gov.hmcts.reform.ccd.client.model.CaseAssignmentUserRolesResponse;
import uk.gov.hmcts.reform.da.idam.IdamService;
import uk.gov.hmcts.reform.idam.client.models.User;
import uk.gov.hmcts.reform.idam.client.models.UserDetails;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static uk.gov.hmcts.reform.da.testutil.TestConstants.APP_SOL_AUTH_TOKEN;
import static uk.gov.hmcts.reform.da.testutil.TestConstants.SOLICITOR_USER_ID;
import static uk.gov.hmcts.reform.da.testutil.TestConstants.SYSTEM_UPDATE_AUTH_TOKEN;
import static uk.gov.hmcts.reform.da.testutil.TestConstants.SYSTEM_USER_USER_ID;
import static uk.gov.hmcts.reform.da.testutil.TestConstants.TEST_CASE_ID;
import static uk.gov.hmcts.reform.da.testutil.TestConstants.TEST_SERVICE_AUTH_TOKEN;
import static uk.gov.hmcts.reform.da.testutil.TestConstants.TEST_SOL_USER_EMAIL;
import static uk.gov.hmcts.reform.da.testutil.TestConstants.TEST_SYSTEM_UPDATE_USER_EMAIL;
import static uk.gov.hmcts.reform.da.testutil.TestDataHelper.feignException;

@ExtendWith(MockitoExtension.class)
class CcdAccessServiceTest {
    @InjectMocks
    private CcdAccessService ccdAccessService;

    @Mock
    private CaseUserApi caseUserApi;

    @Mock
    private CaseAssignmentApi caseAssignmentApi;

    @Mock
    private IdamService idamService;

    @Mock
    private AuthTokenGenerator authTokenGenerator;

    @Test
    void shouldNotThrowAnyExceptionWhenAddApplicant1RoleIsInvoked() {
        User solicitorUser = getIdamUser(APP_SOL_AUTH_TOKEN, SOLICITOR_USER_ID, TEST_SOL_USER_EMAIL);
        User systemUpdateUser = getIdamUser(SYSTEM_UPDATE_AUTH_TOKEN,
                                            SYSTEM_USER_USER_ID, TEST_SYSTEM_UPDATE_USER_EMAIL);

        when(idamService.retrieveUser(APP_SOL_AUTH_TOKEN))
            .thenReturn(solicitorUser);

        when(idamService.retrieveSystemUpdateUserDetails())
            .thenReturn(systemUpdateUser);

        when(authTokenGenerator.generate())
            .thenReturn(TEST_SERVICE_AUTH_TOKEN);

        when(caseAssignmentApi.removeCaseUserRoles(
                eq(SYSTEM_UPDATE_AUTH_TOKEN),
                eq(TEST_SERVICE_AUTH_TOKEN),
                any(CaseAssignmentUserRolesRequest.class)
            )).thenReturn(CaseAssignmentUserRolesResponse.builder().build());

        when(caseAssignmentApi.addCaseUserRoles(
                eq(SYSTEM_UPDATE_AUTH_TOKEN),
                eq(TEST_SERVICE_AUTH_TOKEN),
                any(CaseAssignmentUserRolesRequest.class)
            )).thenReturn(CaseAssignmentUserRolesResponse.builder().build());

        assertThatCode(() -> ccdAccessService.addApplicantSolicitorRole(APP_SOL_AUTH_TOKEN, TEST_CASE_ID))
            .doesNotThrowAnyException();

        verify(idamService).retrieveUser(APP_SOL_AUTH_TOKEN);
        verify(idamService).retrieveSystemUpdateUserDetails();
        verify(authTokenGenerator).generate();
        verify(caseAssignmentApi)
            .removeCaseUserRoles(
                eq(SYSTEM_UPDATE_AUTH_TOKEN),
                eq(TEST_SERVICE_AUTH_TOKEN),
                any(CaseAssignmentUserRolesRequest.class)
            );

        verify(caseAssignmentApi)
            .addCaseUserRoles(
                eq(SYSTEM_UPDATE_AUTH_TOKEN),
                eq(TEST_SERVICE_AUTH_TOKEN),
                any(CaseAssignmentUserRolesRequest.class)
            );

        verifyNoMoreInteractions(idamService, authTokenGenerator, caseUserApi, caseAssignmentApi);
    }

    @Test
    void shouldThrowFeignUnauthorizedExceptionWhenRetrievalOfSolicitorUserFails() {
        doThrow(feignException(401, "Failed to retrieve Idam user"))
            .when(idamService).retrieveUser(APP_SOL_AUTH_TOKEN);

        assertThatThrownBy(() -> ccdAccessService.addApplicantSolicitorRole(APP_SOL_AUTH_TOKEN, TEST_CASE_ID))
            .isExactlyInstanceOf(FeignException.Unauthorized.class)
            .hasMessageContaining("Failed to retrieve Idam user");
    }

    @Test
    void shouldThrowFeignUnauthorizedExceptionWhenRetrievalOfCaseworkerTokenFails() {
        User solicitorUser = getIdamUser(APP_SOL_AUTH_TOKEN, SOLICITOR_USER_ID, TEST_SOL_USER_EMAIL);

        when(idamService.retrieveUser(APP_SOL_AUTH_TOKEN))
            .thenReturn(solicitorUser);

        doThrow(feignException(401, "Failed to retrieve Idam user"))
            .when(idamService).retrieveSystemUpdateUserDetails();

        assertThatThrownBy(() -> ccdAccessService.addApplicantSolicitorRole(APP_SOL_AUTH_TOKEN, TEST_CASE_ID))
            .isExactlyInstanceOf(FeignException.Unauthorized.class)
            .hasMessageContaining("Failed to retrieve Idam user");

        verify(idamService).retrieveUser(APP_SOL_AUTH_TOKEN);

        verifyNoMoreInteractions(idamService);
    }

    @Test
    void shouldThrowInvalidTokenExceptionWhenServiceAuthTokenGenerationFails() {
        User solicitorUser = getIdamUser(APP_SOL_AUTH_TOKEN, SOLICITOR_USER_ID, TEST_SOL_USER_EMAIL);
        User systemUpdateUser = getIdamUser(SYSTEM_UPDATE_AUTH_TOKEN,
                                            SYSTEM_USER_USER_ID, TEST_SYSTEM_UPDATE_USER_EMAIL);

        when(idamService.retrieveUser(APP_SOL_AUTH_TOKEN))
            .thenReturn(solicitorUser);

        when(idamService.retrieveSystemUpdateUserDetails())
            .thenReturn(systemUpdateUser);

        doThrow(new InvalidTokenException("s2s secret is invalid"))
            .when(authTokenGenerator).generate();

        assertThatThrownBy(() -> ccdAccessService.addApplicantSolicitorRole(APP_SOL_AUTH_TOKEN, TEST_CASE_ID))
            .isExactlyInstanceOf(InvalidTokenException.class)
            .hasMessageContaining("s2s secret is invalid");

        verify(idamService).retrieveUser(APP_SOL_AUTH_TOKEN);
        verify(idamService).retrieveSystemUpdateUserDetails();

        verifyNoMoreInteractions(idamService);
    }

    @Test
    void shouldThrowFeignUnProcessableEntityExceptionWhenCcdClientThrowsException() {
        User solicitorUser = getIdamUser(APP_SOL_AUTH_TOKEN, SOLICITOR_USER_ID, TEST_SOL_USER_EMAIL);
        User systemUpdateUser = getIdamUser(SYSTEM_UPDATE_AUTH_TOKEN,
                                            SYSTEM_USER_USER_ID, TEST_SYSTEM_UPDATE_USER_EMAIL);

        when(idamService.retrieveUser(APP_SOL_AUTH_TOKEN))
            .thenReturn(solicitorUser);

        when(idamService.retrieveSystemUpdateUserDetails())
            .thenReturn(systemUpdateUser);

        when(authTokenGenerator.generate())
            .thenReturn(TEST_SERVICE_AUTH_TOKEN);

        when(caseAssignmentApi.removeCaseUserRoles(
            eq(SYSTEM_UPDATE_AUTH_TOKEN),
            eq(TEST_SERVICE_AUTH_TOKEN),
            any(CaseAssignmentUserRolesRequest.class)
        )).thenThrow(feignException(422, "Case roles not valid"));

        assertThatThrownBy(() -> ccdAccessService.addApplicantSolicitorRole(APP_SOL_AUTH_TOKEN, TEST_CASE_ID))
            .isExactlyInstanceOf(FeignException.UnprocessableEntity.class)
            .hasMessageContaining("Case roles not valid");

        verify(idamService).retrieveUser(APP_SOL_AUTH_TOKEN);
        verify(idamService).retrieveSystemUpdateUserDetails();
        verify(authTokenGenerator).generate();

        verifyNoMoreInteractions(idamService, authTokenGenerator);
    }

    private User getIdamUser(String authToken, String userId, String email) {
        return new User(
            authToken,
            UserDetails.builder().id(userId).email(email).build()
        );
    }
}
