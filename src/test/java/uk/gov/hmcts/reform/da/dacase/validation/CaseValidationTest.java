package uk.gov.hmcts.reform.da.dacase.validation;

import org.junit.jupiter.api.Test;
import uk.gov.hmcts.reform.da.dacase.model.CaseData;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static uk.gov.hmcts.reform.da.dacase.validation.ValidationUtil.notNull;
import static uk.gov.hmcts.reform.da.dacase.validation.ValidationUtil.validateApplicantBasicCase;
import static uk.gov.hmcts.reform.da.dacase.validation.ValidationUtil.validateBasicCase;

public class CaseValidationTest {
    private static final String EMPTY = " cannot be empty or null";
    private static final String IN_THE_FUTURE = " can not be in the future.";
    private static final String MORE_THAN_ONE_HUNDRED_YEARS_AGO = " can not be more than 100 years ago.";

    @Test
    public void shouldValidateBasicCase() {
        CaseData caseData = new CaseData();
        List<String> errors = validateBasicCase(caseData);
        assertThat(errors).hasSize(4);
    }

    @Test
    public void shouldValidateApplicationBasicCase() {
        CaseData caseData = new CaseData();
        List<String> errors = validateApplicantBasicCase(caseData);
        assertThat(errors).hasSize(3);
    }

    @Test
    public void shouldReturnErrorWhenStringIsNull() {
        List<String> response = notNull(null, "field");

        assertThat(response).isEqualTo(List.of("field" + EMPTY));
    }
}
