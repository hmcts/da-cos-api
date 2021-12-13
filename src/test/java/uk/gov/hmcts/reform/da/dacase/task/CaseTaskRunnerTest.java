package uk.gov.hmcts.reform.da.dacase.task;

import org.junit.jupiter.api.Test;

import uk.gov.hmcts.ccd.sdk.api.CaseDetails;
import uk.gov.hmcts.reform.da.dacase.model.Applicant;
import uk.gov.hmcts.reform.da.dacase.model.CaseData;
import uk.gov.hmcts.reform.da.dacase.model.State;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static uk.gov.hmcts.reform.da.dacase.task.CaseTaskRunner.caseTasks;

public class CaseTaskRunnerTest {

    @Test
    void shouldReturnReducedFunctionWrappedInCaseTaskRunnerAndBeAppliedToCaseDetails() {

        final CaseData caseData = CaseData.builder().build();
        final CaseDetails<CaseData, State> caseDetails = new CaseDetails<>();
        caseDetails.setData(caseData);

        final CaseDetails<CaseData, State> result = caseTasks(
            cd -> {
                cd.getData().setApplicant(Applicant.builder().firstName("first name").build());
                return cd;
            },
            new TestCaseTask()
        ).run(caseDetails);

        assertThat(result.getData().getApplicant())
            .extracting(Applicant::getFirstName, Applicant::getLastName)
            .contains("first name", "last name");
    }

    public static class TestCaseTask implements CaseTask {

        @Override
        public CaseDetails<CaseData, State> apply(CaseDetails<CaseData, State> caseDetails) {
            caseDetails.getData().getApplicant().setLastName("last name");
            return caseDetails;
        }
    }
}
