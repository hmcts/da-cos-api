package uk.gov.hmcts.reform.da.common.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.hmcts.reform.da.common.AddSystemUpdateRole;
import uk.gov.hmcts.reform.da.dacase.model.UserRole;

import java.util.List;

import static com.github.stefanbirkner.systemlambda.SystemLambda.withEnvironmentVariable;
import static org.assertj.core.api.Assertions.assertThat;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.SOLICITOR;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.SYSTEM_UPDATE;

@ExtendWith(MockitoExtension.class)
class AddSystemUpdateRoleTest {

    @InjectMocks
    private AddSystemUpdateRole addSystemUpdateRole;

    @Test
    void shouldAddSystemUpdateRoleWhenEnvironmentIsAat() throws Exception {
        List<UserRole> actualRoles =
            withEnvironmentVariable("ENVIRONMENT", "aat")
                .execute(() -> addSystemUpdateRole.addIfConfiguredForEnvironment(List.of(SOLICITOR))
                );

        assertThat(actualRoles).containsExactlyInAnyOrder(SOLICITOR, SYSTEM_UPDATE);
    }

    @Test
    void shouldReturnTrueWhenEnvironmentIsAat() throws Exception {
        boolean isEnvironmentAat =
            withEnvironmentVariable("ENVIRONMENT", "aat")
                .execute(() -> addSystemUpdateRole.isEnvironmentAat()
                );

        assertThat(isEnvironmentAat).isTrue();
    }
}
