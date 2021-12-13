package uk.gov.hmcts.reform.da.dacase.model.access;

import com.google.common.collect.SetMultimap;
import org.junit.jupiter.api.Test;
import uk.gov.hmcts.ccd.sdk.api.HasRole;
import uk.gov.hmcts.ccd.sdk.api.Permission;

import static org.assertj.core.data.MapEntry.entry;
import static org.assertj.guava.api.Assertions.assertThat;
import static uk.gov.hmcts.ccd.sdk.api.Permission.C;
import static uk.gov.hmcts.ccd.sdk.api.Permission.D;
import static uk.gov.hmcts.ccd.sdk.api.Permission.R;
import static uk.gov.hmcts.ccd.sdk.api.Permission.U;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.*;


class CaseWorkerAccessTest {
    @Test
    void shouldGrantCaseworkerAccess() {
        final SetMultimap<HasRole, Permission> grants = new CaseworkerAccess().getGrants();

        assertThat(grants)
            .hasSize(17)
            .contains(
                entry(SOLICITOR, R),
                entry(SUPER_USER, C),
                entry(SUPER_USER, R),
                entry(SUPER_USER, U),
                entry(CASE_WORKER, C),
                entry(CASE_WORKER, R),
                entry(CASE_WORKER, U),
                entry(LEGAL_ADVISOR, C),
                entry(LEGAL_ADVISOR, R),
                entry(LEGAL_ADVISOR, U),
                entry(DISTRICT_JUDGE, C),
                entry(DISTRICT_JUDGE, R),
                entry(DISTRICT_JUDGE, U),
                entry(SYSTEM_UPDATE, C),
                entry(SYSTEM_UPDATE, R),
                entry(SYSTEM_UPDATE, U),
                entry(SYSTEM_UPDATE, D)
            );
    }
}
