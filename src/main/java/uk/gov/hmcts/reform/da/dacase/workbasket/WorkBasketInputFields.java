package uk.gov.hmcts.reform.da.dacase.workbasket;

import org.springframework.stereotype.Component;
import uk.gov.hmcts.ccd.sdk.api.CCDConfig;
import uk.gov.hmcts.ccd.sdk.api.ConfigBuilder;
import uk.gov.hmcts.reform.da.dacase.model.CaseData;
import uk.gov.hmcts.reform.da.dacase.model.State;
import uk.gov.hmcts.reform.da.dacase.model.UserRole;

@Component
public class WorkBasketInputFields implements CCDConfig<CaseData, State, UserRole> {

    @Override
    public void configure(final ConfigBuilder<CaseData, State, UserRole> configBuilder) {

        configBuilder
            .workBasketInputFields()
            .caseReferenceField()
            .field("applicantHomeAddress", "Postcode", "PostCode")
            .field("applicantLastName", "Applicant's Last Name");
    }
}
