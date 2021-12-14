package uk.gov.hmcts.reform.da.dacase.validation;

import org.junit.jupiter.api.Test;
import uk.gov.hmcts.reform.da.dacase.model.CaseData;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static uk.gov.hmcts.reform.da.dacase.validation.ApplicationValidation.validateIssue;

class ApplicationValidationTest {

    @Test
    void shouldValidateBasicCase() {
        CaseData caseData = new CaseData();
        List<String> errors = validateIssue(caseData);
        assertThat(errors).hasSize(4);
    }
}
