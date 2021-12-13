package uk.gov.hmcts.reform.da.dacase.model.access;

import com.google.common.collect.SetMultimap;
import org.junit.jupiter.api.Test;
import uk.gov.hmcts.ccd.sdk.api.HasRole;
import uk.gov.hmcts.ccd.sdk.api.Permission;

import static org.assertj.core.data.MapEntry.entry;
import static org.assertj.guava.api.Assertions.assertThat;
import static uk.gov.hmcts.ccd.sdk.api.Permission.*;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.*;

class CaseworkerCourtAdminAccessTest {

    @Test
    void shouldGrantCaseworkerCourtAdminAccess() {

        final SetMultimap<HasRole, Permission> grants = new CaseworkerCourtAdminAccess().getGrants();

        assertThat(grants)
            .hasSize(11)
            .contains(
                entry(LEGAL_ADVISOR, R),
                entry(SUPER_USER, R),
                entry(COURT_ADMIN, C),
                entry(COURT_ADMIN, R),
                entry(COURT_ADMIN, U),
                entry(CASE_WORKER, C),
                entry(CASE_WORKER, R),
                entry(CASE_WORKER, U),
                entry(SYSTEM_UPDATE, C),
                entry(SYSTEM_UPDATE, R),
                entry(SYSTEM_UPDATE, U)
            );
    }
}
