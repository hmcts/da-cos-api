package uk.gov.hmcts.reform.da.dacase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.hmcts.ccd.sdk.ConfigBuilderImpl;
import uk.gov.hmcts.reform.da.common.AddSystemUpdateRole;
import uk.gov.hmcts.reform.da.dacase.model.CaseData;
import uk.gov.hmcts.reform.da.dacase.model.State;
import uk.gov.hmcts.reform.da.dacase.model.UserRole;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.gov.hmcts.ccd.sdk.api.Permission.C;
import static uk.gov.hmcts.ccd.sdk.api.Permission.R;
import static uk.gov.hmcts.ccd.sdk.api.Permission.U;
import static uk.gov.hmcts.reform.da.dacase.model.State.Draft;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.CASE_WORKER;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.LEGAL_ADVISOR;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.SOLICITOR;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.SUPER_USER;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.SYSTEM_UPDATE;
import static uk.gov.hmcts.reform.da.testutil.ConfigTestUtil.createCaseDataConfigBuilder;

@ExtendWith(MockitoExtension.class)
class DomesticAbuseTest {

    @Mock
    private AddSystemUpdateRole addSystemUpdateRole;

    @InjectMocks
    private DomesticAbuse domesticAbuse;

    @Test
    void shouldAddSystemUpdateUserAccessToDraftStateWhenEnvironmentIsAat() {
        final ConfigBuilderImpl<CaseData, State, UserRole> configBuilder = createCaseDataConfigBuilder();

        when(addSystemUpdateRole.isEnvironmentAat()).thenReturn(true);

        domesticAbuse.configure(configBuilder);

        assertThat(configBuilder.build().getCaseType()).isEqualTo("FL401");

        assertThat(configBuilder.build().getStateRolePermissions().columnMap().get(SYSTEM_UPDATE))
            .contains(entry(Draft, Set.of(C, R, U)));

        assertThat(configBuilder.build().getStateRolePermissions().columnMap().get(SOLICITOR))
            .contains(entry(Draft, Set.of(C, R, U)));

        assertThat(configBuilder.build().getStateRolePermissions().columnMap().get(SUPER_USER))
            .contains(entry(Draft, Set.of(C, R, U)));

        assertThat(configBuilder.build().getStateRolePermissions().columnMap().get(LEGAL_ADVISOR))
            .contains(entry(Draft, Set.of(R)));

        assertThat(configBuilder.build().getStateRolePermissions().columnMap().get(CASE_WORKER))
            .contains(entry(Draft, Set.of(C, R, U)));

        verify(addSystemUpdateRole).isEnvironmentAat();
    }

    @Test
    void shouldNotAddSystemUpdateUserAccessToDraftStateWhenEnvironmentIsNotAat() {
        final ConfigBuilderImpl<CaseData, State, UserRole> configBuilder = createCaseDataConfigBuilder();

        when(addSystemUpdateRole.isEnvironmentAat()).thenReturn(false);

        domesticAbuse.configure(configBuilder);

        assertThat(configBuilder.build().getCaseType()).isEqualTo("FL401");

        assertThat(configBuilder.build().getStateRolePermissions().columnMap().get(SOLICITOR))
            .contains(entry(Draft, Set.of(C, R, U)));

        assertThat(configBuilder.build().getStateRolePermissions().columnMap().get(SUPER_USER))
            .contains(entry(Draft, Set.of(C, R, U)));

        assertThat(configBuilder.build().getStateRolePermissions().columnMap().get(LEGAL_ADVISOR))
            .contains(entry(Draft, Set.of(R)));

        assertThat(configBuilder.build().getStateRolePermissions().columnMap().get(CASE_WORKER))
            .contains(entry(Draft, Set.of(C, R, U)));

        verify(addSystemUpdateRole).isEnvironmentAat();
    }
}
