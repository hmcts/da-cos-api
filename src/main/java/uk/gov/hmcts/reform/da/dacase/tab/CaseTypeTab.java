package uk.gov.hmcts.reform.da.dacase.tab;

import org.springframework.stereotype.Component;
import uk.gov.hmcts.ccd.sdk.api.CCDConfig;
import uk.gov.hmcts.ccd.sdk.api.ConfigBuilder;
import uk.gov.hmcts.reform.da.dacase.model.CaseData;
import uk.gov.hmcts.reform.da.dacase.model.State;
import uk.gov.hmcts.reform.da.dacase.model.UserRole;

import static uk.gov.hmcts.reform.da.dacase.model.UserRole.CASE_WORKER;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.DISTRICT_JUDGE;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.LEGAL_ADVISOR;

@Component
public class CaseTypeTab implements CCDConfig<CaseData, State, UserRole> {

    @Override
    public void configure(final ConfigBuilder<CaseData, State, UserRole> configBuilder) {
        buildStateTab(configBuilder);
        buildConfidentialTab(configBuilder);
    }

    private void buildStateTab(ConfigBuilder<CaseData, State, UserRole> configBuilder) {
        configBuilder.tab("state", "State")
            .forRoles(UserRole.RESPONDENT_SOLICITOR)
            .label("LabelState", null, "#### Case State:  ${[STATE]}");
    }

    private void buildConfidentialTab(ConfigBuilder<CaseData, State, UserRole> configBuilder) {
        configBuilder.tab("Confidential", "Confidential Address")
            .forRoles(CASE_WORKER, LEGAL_ADVISOR, DISTRICT_JUDGE)
            .showCondition("applicantKeepContactDetailsConfidential=\"keep\"")
            .field("applicantCorrespondenceAddress")
            .field("applicantPhoneNumber")
            .field("applicantEmail")
            .field("applicantHomeAddress");
    }
}
