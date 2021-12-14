package uk.gov.hmcts.reform.da.dacase.model;

import org.junit.jupiter.api.Test;
import uk.gov.hmcts.ccd.sdk.type.YesOrNo;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static uk.gov.hmcts.reform.da.testutil.TestConstants.TEST_APPLICANT_EMAIL_ID;

class CaseDataTest {
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

        assertThat(caseData.getApplicant().getEmail()).isEmpty();
    }

    @Test
    void shouldReturnNullIfApplicant2EmailIsNullAndCaseInviteIsNull() {

        final CaseData caseData = CaseData.builder()
            .build();

        assertThat(caseData.getApplicant().getEmail()).isNull();
    }

    @Test
    void shouldReturnNonMolestationApplicatonType() {

        final CaseData caseData = CaseData.builder()
            .applicant(Applicant.builder()
                           .email(TEST_APPLICANT_EMAIL_ID)
                           .build())
            .applicationType(ApplicationType.NON_MOLESTATION_ORDER)
            .build();

        assertThat(caseData.getApplicationType()).isEqualTo(ApplicationType.NON_MOLESTATION_ORDER);
    }

    @Test
    void shouldReturnOccupationalApplicatonType() {

        final CaseData caseData = CaseData.builder()
            .applicant(Applicant.builder()
                           .email(TEST_APPLICANT_EMAIL_ID)
                           .build())
            .applicationType(ApplicationType.OCCUPATIONAL_ORDER)
            .build();

        assertThat(caseData.getApplicationType()).isEqualTo(ApplicationType.OCCUPATIONAL_ORDER);
    }

    @Test
    void shouldReturnApplicationPreceedingAsTrueApplicatonType() {

        final CaseData caseData = CaseData.builder()
            .application(Application.builder().applicantHasOngoingCourtProceedings(YesOrNo.YES).build())
            .build();

        assertThat(caseData.getApplication().getApplicantHasOngoingCourtProceedings().toBoolean()).isTrue();
    }

    @Test
    void shouldReturnApplicationPreceedingAsFalseApplicatonType() {

        final CaseData caseData = CaseData.builder()
            .application(Application.builder().applicantHasOngoingCourtProceedings(YesOrNo.NO).build())
            .build();

        assertThat(caseData.getApplication().getApplicantHasOngoingCourtProceedings().toBoolean()).isFalse();
    }
}
