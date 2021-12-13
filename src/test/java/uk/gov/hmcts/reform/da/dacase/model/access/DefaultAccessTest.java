package uk.gov.hmcts.reform.da.dacase.model.access;

import com.google.common.collect.SetMultimap;
import org.junit.jupiter.api.Test;
import uk.gov.hmcts.ccd.sdk.api.HasRole;
import uk.gov.hmcts.ccd.sdk.api.Permission;

import static org.assertj.core.data.MapEntry.entry;
import static org.assertj.guava.api.Assertions.assertThat;
import static uk.gov.hmcts.ccd.sdk.api.Permission.R;
import static uk.gov.hmcts.ccd.sdk.api.Permission.C;
import static uk.gov.hmcts.ccd.sdk.api.Permission.U;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.SUPER_USER;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.LEGAL_ADVISOR;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.CASE_WORKER;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.SYSTEM_UPDATE;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.COURT_ADMIN;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.SOLICITOR;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.DISTRICT_JUDGE;


class DefaultAccessTest {
    @Test
    void shouldGrantDefaultAccess() {

        final SetMultimap<HasRole, Permission> grants = new DefaultAccess().getGrants();

        assertThat(grants)
            .hasSize(11)
            .contains(
                entry(SYSTEM_UPDATE, C),
                entry(SYSTEM_UPDATE, R),
                entry(SYSTEM_UPDATE, U),
                entry(CASE_WORKER, R),
                entry(COURT_ADMIN, R),
                entry(SOLICITOR, R),
                entry(SUPER_USER, R),
                entry(LEGAL_ADVISOR, R),
                entry(DISTRICT_JUDGE, R)
            );
    }
}
