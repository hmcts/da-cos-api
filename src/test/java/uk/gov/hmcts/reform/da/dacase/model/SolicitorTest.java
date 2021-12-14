package uk.gov.hmcts.reform.da.dacase.model;

import org.junit.jupiter.api.Test;
import uk.gov.hmcts.ccd.sdk.type.YesOrNo;

import static org.assertj.core.api.Assertions.assertThat;

class SolicitorTest {
    @Test
    void shouldReturnTrueIfApplicantHasOngoingCourtProceedings() {
        final var application = Solicitor.builder()
            .name("test")
            .email("test@test.com")
            .phone("1234567890")
            .reference("testRef")
            .address("testAddress")
            .agreeToReceiveEmails(YesOrNo.YES)
            .build();

        assertThat(application.getAgreeToReceiveEmails().toBoolean()).isTrue();
        assertThat(application.getName()).isEqualTo("test");
        assertThat(application.getAddress()).isEqualTo("testAddress");
        assertThat(application.getPhone()).isEqualTo("1234567890");
        assertThat(application.getReference()).isEqualTo("testRef");
    }
}
