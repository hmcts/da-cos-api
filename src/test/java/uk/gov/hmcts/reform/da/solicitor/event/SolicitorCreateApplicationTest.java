package uk.gov.hmcts.reform.da.solicitor.event;

import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.hmcts.ccd.sdk.ConfigBuilderImpl;
import uk.gov.hmcts.ccd.sdk.api.CaseDetails;
import uk.gov.hmcts.ccd.sdk.api.Event;
import uk.gov.hmcts.ccd.sdk.api.Permission;
import uk.gov.hmcts.reform.da.common.AddSystemUpdateRole;
import uk.gov.hmcts.reform.da.dacase.model.CaseData;
import uk.gov.hmcts.reform.da.dacase.model.State;
import uk.gov.hmcts.reform.da.dacase.model.UserRole;
import uk.gov.hmcts.reform.da.solicitor.service.CcdAccessService;
import uk.gov.hmcts.reform.da.solicitor.service.SolicitorCreateApplicationService;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static uk.gov.hmcts.ccd.sdk.api.Permission.C;
import static uk.gov.hmcts.ccd.sdk.api.Permission.R;
import static uk.gov.hmcts.ccd.sdk.api.Permission.U;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.CASE_WORKER;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.DISTRICT_JUDGE;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.LEGAL_ADVISOR;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.SOLICITOR;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.SUPER_USER;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.SYSTEM_UPDATE;
import static uk.gov.hmcts.reform.da.solicitor.event.SolicitorCreateApplication.SOLICITOR_CREATE;
import static uk.gov.hmcts.reform.da.testutil.ConfigTestUtil.createCaseDataConfigBuilder;
import static uk.gov.hmcts.reform.da.testutil.ConfigTestUtil.getEventsFrom;
import static uk.gov.hmcts.reform.da.testutil.TestConstants.TEST_CASE_ID;
import static uk.gov.hmcts.reform.da.testutil.TestDataHelper.LOCAL_DATE_TIME;
import static uk.gov.hmcts.reform.da.testutil.TestDataHelper.caseData;

@ExtendWith(MockitoExtension.class)
class SolicitorCreateApplicationTest {

    @Mock
    private SolicitorCreateApplicationService solicitorCreateApplicationService;

    @Mock
    private AddSystemUpdateRole addSystemUpdateRole;

    @Mock
    private CcdAccessService ccdAccessService;

    @Mock
    private HttpServletRequest httpServletRequest;

    @InjectMocks
    private SolicitorCreateApplication solicitorCreateApplication;

    @Test
    void shouldAddConfigurationToConfigBuilder() {
        final ConfigBuilderImpl<CaseData, State, UserRole> configBuilder = createCaseDataConfigBuilder();

        solicitorCreateApplication.configure(configBuilder);

        assertThat(getEventsFrom(configBuilder).values())
            .extracting(Event::getId)
            .contains(SOLICITOR_CREATE);
    }

    @Test
    void shouldSetPermissionForSolicitorAndSystemUpdateRoleWhenEnvironmentIsAat() {
        final ConfigBuilderImpl<CaseData, State, UserRole> configBuilder = createCaseDataConfigBuilder();

        when(addSystemUpdateRole.addIfConfiguredForEnvironment(anyList()))
            .thenReturn(List.of(SOLICITOR, SYSTEM_UPDATE));

        solicitorCreateApplication.configure(configBuilder);

        assertThat(getEventsFrom(configBuilder).values())
            .extracting(Event::getId)
            .contains(SOLICITOR_CREATE);

        SetMultimap<UserRole, Permission> expectedRolesAndPermissions
            = ImmutableSetMultimap.<UserRole, Permission>builder()
            .put(LEGAL_ADVISOR, R)
            .put(SOLICITOR, C)
            .put(SOLICITOR, R)
            .put(SOLICITOR, U)
            .put(DISTRICT_JUDGE, R)
            .put(SUPER_USER, R)
            .put(SUPER_USER, U)
            .put(CASE_WORKER, R)
            .put(SYSTEM_UPDATE, R)
            .put(SYSTEM_UPDATE, C)
            .put(SYSTEM_UPDATE, U)
            .put(LEGAL_ADVISOR, R)
            .build();

        assertThat(getEventsFrom(configBuilder).values())
            .extracting(Event::getGrants)
            .containsExactlyInAnyOrder(expectedRolesAndPermissions);

        verify(addSystemUpdateRole).addIfConfiguredForEnvironment(anyList());
    }

    @Test
    void shouldPopulateMissingRequirementsFieldsInCaseData() {
        final CaseData caseData = caseData();
        final CaseDetails<CaseData, State> beforeDetails = new CaseDetails<>();
        final CaseDetails<CaseData, State> details = new CaseDetails<>();
        details.setData(caseData);
        details.setId(TEST_CASE_ID);
        details.setCreatedDate(LOCAL_DATE_TIME);

        when(solicitorCreateApplicationService.aboutToSubmit(details)).thenReturn(details);

        solicitorCreateApplication.aboutToSubmit(details, beforeDetails);

        verify(solicitorCreateApplicationService).aboutToSubmit(details);
    }

    @Test
    void shouldSetApplicantSolicitorRoleWhenCaseSubmitted() {
        final long caseId = 1L;
        final String authorization = "authorization";
        final CaseDetails<CaseData, State> caseDetails = new CaseDetails<>();
        final CaseData caseData = CaseData.builder().build();
        caseDetails.setData(caseData);
        caseDetails.setId(caseId);

        when(httpServletRequest.getHeader(AUTHORIZATION)).thenReturn(authorization);

        solicitorCreateApplication.submitted(caseDetails, caseDetails);

        verify(ccdAccessService).addApplicantSolicitorRole(
            authorization,
            caseId);
    }
}
