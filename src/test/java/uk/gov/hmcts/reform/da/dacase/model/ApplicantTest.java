package uk.gov.hmcts.reform.da.dacase.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.ccd.sdk.type.YesOrNo.NO;
import static uk.gov.hmcts.ccd.sdk.type.YesOrNo.YES;

class ApplicantTest {

    @Test
    void shouldReturnTrueIfContactDetailsAreConfidential() {

        final Applicant applicant = Applicant.builder()
            .keepContactDetailsConfidential(YES)
            .build();

        assertThat(applicant.isConfidentialContactDetails()).isTrue();
    }

    @Test
    void shouldReturnFalseIfContactDetailsAreNotConfidential() {

        final Applicant applicant = Applicant.builder()
            .keepContactDetailsConfidential(NO)
            .build();

        assertThat(applicant.isConfidentialContactDetails()).isFalse();
    }

    @Test
    void shouldReturnFalseIfContactDetailsAreSetToNull() {

        final Applicant applicant = Applicant.builder()
            .build();

        assertThat(applicant.isConfidentialContactDetails()).isFalse();
    }
}
