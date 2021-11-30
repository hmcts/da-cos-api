package uk.gov.hmcts.reform.da.solicitor.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.hmcts.ccd.sdk.api.CaseDetails;
import uk.gov.hmcts.reform.authorisation.generators.AuthTokenGenerator;
import uk.gov.hmcts.reform.da.dacase.model.CaseData;
import uk.gov.hmcts.reform.da.dacase.model.State;
import uk.gov.hmcts.reform.da.solicitor.service.task.DomesticAbuseApplicationDraft;
import uk.gov.hmcts.reform.da.solicitor.service.task.InitialiseSolicitorCreatedApplication;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.gov.hmcts.reform.da.testutil.TestConstants.TEST_CASE_ID;
import static uk.gov.hmcts.reform.da.testutil.TestDataHelper.caseData;

@ExtendWith(MockitoExtension.class)
class SolicitorCreateApplicationApplicationServiceTest {

    private static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.of(2021, 04, 28, 1, 0);

    @Mock
    private InitialiseSolicitorCreatedApplication initialiseSolicitorCreatedApplication;

    @Mock
    private DomesticAbuseApplicationDraft domesticAbuseApplicationDraft;

    @Mock
    private AuthTokenGenerator authTokenGenerator;

    @InjectMocks
    private SolicitorCreateApplicationService solicitorCreateApplicationService;

    @Test
    void shouldCompleteStepsToCreateApplication() {

        final CaseData caseData = caseData();
        final CaseDetails<CaseData, State> caseDetails = new CaseDetails<>();
        caseDetails.setData(caseData);
        caseDetails.setId(TEST_CASE_ID);
        caseDetails.setCreatedDate(LOCAL_DATE_TIME);

        when(initialiseSolicitorCreatedApplication.apply(caseDetails)).thenReturn(caseDetails);
        when(domesticAbuseApplicationDraft.apply(caseDetails)).thenReturn(caseDetails);

        final CaseDetails<CaseData, State> result = solicitorCreateApplicationService.aboutToSubmit(caseDetails);

        assertThat(result.getData()).isEqualTo(caseData);

        verify(initialiseSolicitorCreatedApplication).apply(caseDetails);
        verify(domesticAbuseApplicationDraft).apply(caseDetails);
    }
}
