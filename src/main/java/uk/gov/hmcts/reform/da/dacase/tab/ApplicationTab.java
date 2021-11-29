package uk.gov.hmcts.reform.da.dacase.tab;

import org.springframework.stereotype.Component;
import uk.gov.hmcts.ccd.sdk.api.CCDConfig;
import uk.gov.hmcts.ccd.sdk.api.ConfigBuilder;
import uk.gov.hmcts.ccd.sdk.api.Tab;
import uk.gov.hmcts.reform.da.dacase.model.CaseData;
import uk.gov.hmcts.reform.da.dacase.model.State;
import uk.gov.hmcts.reform.da.dacase.model.UserRole;

@Component
public class ApplicationTab implements CCDConfig<CaseData, State, UserRole> {

    @Override
    public void configure(final ConfigBuilder<CaseData, State, UserRole> configBuilder) {
        final Tab.TabBuilder<CaseData, UserRole> tabBuilder = configBuilder.tab("applicationDetails", "Application");

        addHeaderFields(tabBuilder);
        addApplicant(tabBuilder);
    }

    private void addHeaderFields(final Tab.TabBuilder<CaseData, UserRole> tabBuilder) {
        tabBuilder
            .field("createdDate")
            .field("dateSubmitted")
            .field("issueDate")
            .field("dueDate")
            .field(CaseData::getApplicationType);
    }

    private void addApplicant(final Tab.TabBuilder<CaseData, UserRole> tabBuilder) {
        tabBuilder
            .label("LabelApplicant-Heading", null, "### The applicant")
            .field("applicantFirstName")
            .field("applicantMiddleName")
            .field("applicantLastName")
            .field("applicantGender")
            .label(
                "LabelApplicantDetailsAreConfidential-Heading",
                "applicantKeepContactDetailsConfidential=\"Yes\"",
                "#### The applicant's contact details are confidential"
            )
            .field("applicantPhoneNumber", "applicantKeepContactDetailsConfidential=\"No\"")
            .field("applicantEmail", "applicantKeepContactDetailsConfidential=\"No\"")
            .field("applicantHomeAddress", "applicantKeepContactDetailsConfidential=\"No\"")
            .field("applicantCorrespondenceAddress", "applicantKeepContactDetailsConfidential=\"No\"");
    }

}
