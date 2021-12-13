package uk.gov.hmcts.reform.da.dacase.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static uk.gov.hmcts.reform.da.testutil.TestConstants.TEST_APPLICANT_EMAIL_ID;

public class CaseDataTest {
    @Test
    void shouldReturnApplicant2EmailIfApplicant2EmailIsSet() {

        final CaseData caseData = CaseData.builder()
            .applicant(Applicant.builder()
                            .email(TEST_APPLICANT_EMAIL_ID)
                            .build())
            .build();

        assertThat(caseData.getApplicant().getEmail()).isEqualTo(TEST_APPLICANT_EMAIL_ID);
    }

    @Test
    void shouldReturnApplicant2InviteEmailIfApplicant2EmailIsNullAndApplicant2InviteEmailAddressIsSet() {

        final CaseData caseData = CaseData.builder()
            .applicant(Applicant.builder()
                            .email("")
                            .build())
            .build();

        assertThat(caseData.getApplicant().getEmail()).isEqualTo("");
    }

    @Test
    void shouldReturnNullIfApplicant2EmailIsNullAndCaseInviteIsNull() {

        final CaseData caseData = CaseData.builder()
            .build();

        assertThat(caseData.getApplicant().getEmail()).isNull();
    }
}
