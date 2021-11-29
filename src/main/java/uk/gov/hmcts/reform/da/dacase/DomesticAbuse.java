package uk.gov.hmcts.reform.da.dacase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.ccd.sdk.api.CCDConfig;
import uk.gov.hmcts.ccd.sdk.api.ConfigBuilder;
import uk.gov.hmcts.reform.da.common.AddSystemUpdateRole;
import uk.gov.hmcts.reform.da.dacase.model.CaseData;
import uk.gov.hmcts.reform.da.dacase.model.State;
import uk.gov.hmcts.reform.da.dacase.model.UserRole;

import static uk.gov.hmcts.reform.da.dacase.model.State.Draft;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.CASE_WORKER;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.COURT_ADMIN;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.DISTRICT_JUDGE;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.LEGAL_ADVISOR;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.RESPONDENT_SOLICITOR;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.SOLICITOR;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.SUPER_USER;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.SYSTEM_UPDATE;
import static uk.gov.hmcts.reform.da.dacase.model.access.Permissions.CREATE_READ_UPDATE;
import static uk.gov.hmcts.reform.da.dacase.model.access.Permissions.READ;

@Component
public class DomesticAbuse implements CCDConfig<CaseData, State, UserRole> {

    public static final String CASE_TYPE = "FL401";
    public static final String JURISDICTION = "DOMESTICABUSE";

    @Autowired
    private AddSystemUpdateRole addSystemUpdateRole;

    @Override
    public void configure(final ConfigBuilder<CaseData, State, UserRole> configBuilder) {
        configBuilder.setCallbackHost(System.getenv().getOrDefault("CASE_API_URL", "http://da-cos-api:4550"));

        configBuilder.caseType(CASE_TYPE, CASE_TYPE, "FL401 Application");
        configBuilder.jurisdiction(JURISDICTION, "Family Domestic Abuse",
                                   "Family Domestic Abuse: non-molestation or occupational order");
        configBuilder.omitHistoryForRoles(SOLICITOR, RESPONDENT_SOLICITOR);

        configBuilder.grant(Draft, CREATE_READ_UPDATE, SOLICITOR);
        configBuilder.grant(Draft, CREATE_READ_UPDATE, SUPER_USER);
        configBuilder.grant(Draft, CREATE_READ_UPDATE, CASE_WORKER);
        configBuilder.grant(Draft, CREATE_READ_UPDATE, COURT_ADMIN);
        configBuilder.grant(Draft, CREATE_READ_UPDATE, SUPER_USER);

        configBuilder.grant(Draft, READ, LEGAL_ADVISOR);
        configBuilder.grant(Draft, READ, DISTRICT_JUDGE);

        if (addSystemUpdateRole.isEnvironmentAat()) {
            configBuilder.grant(Draft, CREATE_READ_UPDATE, SYSTEM_UPDATE);
        }
    }
}
