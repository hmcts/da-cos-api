package uk.gov.hmcts.reform.da.dacase.validation;

import uk.gov.hmcts.reform.da.dacase.model.CaseData;

import java.util.List;

import static uk.gov.hmcts.reform.da.dacase.validation.ValidationUtil.flattenLists;
import static uk.gov.hmcts.reform.da.dacase.validation.ValidationUtil.validateBasicCase;

public final class ApplicationValidation {

    private ApplicationValidation() {

    }

    public static List<String> validateReadyForPayment(CaseData caseData) {
        List<String> errors = validateBasicCase(caseData);

        return errors;
    }

    public static List<String> validateIssue(CaseData caseData) {
        return flattenLists(
            validateBasicCase(caseData)
        );
    }

}
