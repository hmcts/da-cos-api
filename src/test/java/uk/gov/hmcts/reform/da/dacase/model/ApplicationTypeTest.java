package uk.gov.hmcts.reform.da.dacase.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ApplicationTypeTest {
    @Test
    void shouldReturnTrueIfNonMolestationOrderType() {
        assertThat(ApplicationType.NON_MOLESTATION_ORDER.isNonMolestationOrder()).isTrue();
    }

    @Test
    void shouldReturnTrueIfOccupationalOrder() {
        assertThat(ApplicationType.OCCUPATIONAL_ORDER.isOccupationalOrder()).isTrue();
    }
}
