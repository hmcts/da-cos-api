package uk.gov.hmcts.reform.da.solicitor.service.task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.hmcts.ccd.sdk.api.CaseDetails;
import uk.gov.hmcts.reform.da.dacase.model.CaseData;
import uk.gov.hmcts.reform.da.dacase.model.State;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.reform.da.testutil.TestDataHelper.LOCAL_DATE;
import static uk.gov.hmcts.reform.da.testutil.TestDataHelper.LOCAL_DATE_TIME;
import static uk.gov.hmcts.reform.da.testutil.TestDataHelper.caseData;

@ExtendWith(MockitoExtension.class)
class InitialiseSolicitorCreatedApplicationTest {

    @InjectMocks
    private InitialiseSolicitorCreatedApplication initialiseSolicitorCreatedApplication;

    @Test
    void shouldSetApplicationCreatedDateToCaseDetailsCreatedDateAndApplicant1SolicitorRepresentedToTrue() {

        final CaseData caseData = caseData();

        final CaseDetails<CaseData, State> caseDetails = new CaseDetails<>();
        caseDetails.setCreatedDate(LOCAL_DATE_TIME);
        caseDetails.setData(caseData);

        final CaseDetails<CaseData, State> result = initialiseSolicitorCreatedApplication.apply(caseDetails);

        assertThat(result.getData().getApplication().getCreatedDate()).isEqualTo(LOCAL_DATE);
    }
}
