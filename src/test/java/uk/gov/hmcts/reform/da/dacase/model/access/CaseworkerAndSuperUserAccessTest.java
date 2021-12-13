package uk.gov.hmcts.reform.da.dacase.model.access;

import com.google.common.collect.SetMultimap;
import org.junit.jupiter.api.Test;
import uk.gov.hmcts.ccd.sdk.api.HasRole;
import uk.gov.hmcts.ccd.sdk.api.Permission;

import static org.assertj.core.data.MapEntry.entry;
import static org.assertj.guava.api.Assertions.assertThat;
import static uk.gov.hmcts.ccd.sdk.api.Permission.*;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.*;

class CaseworkerAndSuperUserAccessTest {
    @Test
    void shouldGrantCaseworkerAndSuperUserAccess() {
        final SetMultimap<HasRole, Permission> grants = new CaseworkerAndSuperUserAccess().getGrants();

        assertThat(grants)
            .hasSize(14)
            .contains(
                entry(SOLICITOR, R),
                entry(LEGAL_ADVISOR, R),
                entry(DISTRICT_JUDGE, R),
                entry(SUPER_USER, C),
                entry(SUPER_USER, R),
                entry(SUPER_USER, U),
                entry(SUPER_USER, D),
                entry(CASE_WORKER, C),
                entry(CASE_WORKER, R),
                entry(CASE_WORKER, U),
                entry(SYSTEM_UPDATE, C),
                entry(SYSTEM_UPDATE, R),
                entry(SYSTEM_UPDATE, U),
                entry(SYSTEM_UPDATE, D)
            );
    }
}
