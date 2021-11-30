package uk.gov.hmcts.reform.da.solicitor.service.task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.hmcts.ccd.sdk.api.CaseDetails;
import uk.gov.hmcts.reform.da.dacase.model.Applicant;
import uk.gov.hmcts.reform.da.dacase.model.CaseData;
import uk.gov.hmcts.reform.da.dacase.model.State;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.reform.da.testutil.TestConstants.TEST_CASE_ID;
import static uk.gov.hmcts.reform.da.testutil.TestDataHelper.LOCAL_DATE_TIME;

@ExtendWith(MockitoExtension.class)
public class DomesticAbuseApplicationDraftTest {

    @InjectMocks
    private DomesticAbuseApplicationDraft domesticAbuseApplicationDraft;

    @Test
    void shouldCallDocAssemblyServiceAndReturnCaseDataWithMiniDraftApplicationDocument() {

        final var caseData = CaseData.builder()
            .applicant(Applicant.builder().build())
            .build();

        final CaseDetails<CaseData, State> caseDetails = new CaseDetails<>();
        caseDetails.setData(caseData);
        caseDetails.setId(TEST_CASE_ID);
        caseDetails.setCreatedDate(LOCAL_DATE_TIME);

        final Map<String, Object> templateContent = new HashMap<>();

        final var result = domesticAbuseApplicationDraft.apply(caseDetails);

        assertThat(result.getData()).isEqualTo(caseData);
    }
}
