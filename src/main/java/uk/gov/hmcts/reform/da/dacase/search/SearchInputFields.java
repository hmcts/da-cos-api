package uk.gov.hmcts.reform.da.dacase.search;

import org.springframework.stereotype.Component;
import uk.gov.hmcts.ccd.sdk.api.CCDConfig;
import uk.gov.hmcts.ccd.sdk.api.ConfigBuilder;
import uk.gov.hmcts.ccd.sdk.api.SearchField;
import uk.gov.hmcts.reform.da.dacase.model.CaseData;
import uk.gov.hmcts.reform.da.dacase.model.State;
import uk.gov.hmcts.reform.da.dacase.model.UserRole;

import java.util.List;

import static java.util.List.of;
import static uk.gov.hmcts.reform.da.dacase.search.CaseFieldsConstants.APPLICANT_EMAIL;
import static uk.gov.hmcts.reform.da.dacase.search.CaseFieldsConstants.APPLICANT_FIRST_NAME;
import static uk.gov.hmcts.reform.da.dacase.search.CaseFieldsConstants.APPLICANT_LAST_NAME;
import static uk.gov.hmcts.reform.da.dacase.search.CaseFieldsConstants.APPLICANT_SOLICITOR_EMAIL;
import static uk.gov.hmcts.reform.da.dacase.search.CaseFieldsConstants.EMAIL;
import static uk.gov.hmcts.reform.da.dacase.search.CaseFieldsConstants.FIRST_NAME;
import static uk.gov.hmcts.reform.da.dacase.search.CaseFieldsConstants.LAST_NAME;
import static uk.gov.hmcts.reform.da.dacase.search.CaseFieldsConstants.RESPONDENT_FIRST_NAME;
import static uk.gov.hmcts.reform.da.dacase.search.CaseFieldsConstants.RESPONDENT_LAST_NAME;



@Component
public class SearchInputFields implements CCDConfig<CaseData, State, UserRole> {

    @Override
    public void configure(final ConfigBuilder<CaseData, State, UserRole> configBuilder) {

        final List<SearchField> searchFieldList = of(
            SearchField.builder().label(FIRST_NAME).id(
                APPLICANT_FIRST_NAME).build(),
            SearchField.builder().label(LAST_NAME).id(
                APPLICANT_LAST_NAME).build(),
            SearchField.builder().label(FIRST_NAME).id(
                RESPONDENT_FIRST_NAME).build(),
            SearchField.builder().label(LAST_NAME).id(
                RESPONDENT_LAST_NAME).build(),
            SearchField.builder().label(EMAIL).id(
                APPLICANT_EMAIL).build(),
            SearchField.builder().label(EMAIL).id(
                APPLICANT_SOLICITOR_EMAIL).build()
        );

        configBuilder.searchInputFields().caseReferenceField();
        configBuilder.searchInputFields().fields(searchFieldList);
    }
}
