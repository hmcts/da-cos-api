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
        addCaseInfo(tabBuilder);
    }

    private void addHeaderFields(final Tab.TabBuilder<CaseData, UserRole> tabBuilder) {
        tabBuilder
            .field("createdDate")
            .field(CaseData::getCaseInfo)
            .field(CaseData::getApplicationType);
    }

    private void addCaseInfo(final Tab.TabBuilder<CaseData, UserRole> tabBuilder) {
        tabBuilder
            .label("CaseInfo", null, "### CaseInfo name")
            .field("caseInfocaseName");
    }

}
