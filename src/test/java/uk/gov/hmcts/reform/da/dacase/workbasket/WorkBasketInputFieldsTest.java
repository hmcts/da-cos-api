package uk.gov.hmcts.reform.da.dacase.workbasket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.gov.hmcts.ccd.sdk.ConfigBuilderImpl;
import uk.gov.hmcts.reform.da.dacase.model.CaseData;
import uk.gov.hmcts.reform.da.dacase.model.State;
import uk.gov.hmcts.reform.da.dacase.model.UserRole;

import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static uk.gov.hmcts.reform.da.testutil.ConfigTestUtil.createCaseDataConfigBuilder;
import static uk.gov.hmcts.reform.da.testutil.ConfigTestUtil.getWorkBasketInputFields;

public class WorkBasketInputFieldsTest {
    private WorkBasketInputFields workBasketInputFields;

    @BeforeEach
    void setUp() {
        workBasketInputFields = new WorkBasketInputFields();
    }

    @Test
    void shouldSetWorkBasketResultFields() throws Exception {
        final ConfigBuilderImpl<CaseData, State, UserRole> configBuilder = createCaseDataConfigBuilder();

        workBasketInputFields.configure(configBuilder);

        assertThat(getWorkBasketInputFields(configBuilder).getFields())
            .extracting("id",
                        "label",
                        "listElementCode",
                        "showCondition")
            .contains(
                tuple("applicantHomeAddress",
                      "Postcode",
                      "PostCode",
                      null),
                tuple("applicantLastName",
                      "Applicant's Last Name",
                      null,
                      null)
            );
    }
}
