package uk.gov.hmcts.reform.da.dacase.model;

import org.junit.jupiter.api.Test;
import uk.gov.hmcts.ccd.sdk.type.YesOrNo;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class ApplicationTest {

    @Test
    void shouldReturnTrueIfApplicantHasOngoingCourtProceedings() {
        final var application = Application.builder()
            .applicantHasOngoingCourtProceedings(YesOrNo.YES)
            .createdDate(LocalDate.now())
            .build();

        assertThat(application.getApplicantHasOngoingCourtProceedings().toBoolean()).isTrue();
    }

    @Test
    void shouldReturnFalseIfApplicantHasOngoingCourtProceedings() {
        final var application = Application.builder()
            .applicantHasOngoingCourtProceedings(YesOrNo.NO)
            .createdDate(LocalDate.now())
            .build();

        assertThat(application.getApplicantHasOngoingCourtProceedings().toBoolean()).isFalse();
    }

    @Test
    void shouldReturnCreatedDateResponseIfDateOfCreationIsSet() {

        final LocalDate createdDate = LocalDate.of(2021, 12, 31);
        final var application = Application.builder()
            .createdDate(createdDate)
            .build();

        assertThat(application.getCreatedDate()).isEqualTo(createdDate);
    }

    @Test
    void shouldReturnCreatedDateIfDateOfCreationIsNotSet() {

        final var application = Application.builder()
            .build();

        assertThat(application.getCreatedDate()).isNull();
    }
}
