package uk.gov.hmcts.reform.da.common.ccd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmcts.befta.dse.ccd.CcdEnvironment;
import uk.gov.hmcts.befta.dse.ccd.CcdRoleConfig;
import uk.gov.hmcts.befta.dse.ccd.DataLoaderToDefinitionStore;
import uk.gov.hmcts.reform.da.dacase.DomesticAbuse;

import java.util.List;
import java.util.Locale;

public class HighLevelDataSetupApp extends DataLoaderToDefinitionStore {

    private static final Logger logger = LoggerFactory.getLogger(HighLevelDataSetupApp.class);

    public static final String PUBLIC = "PUBLIC";
    private static final CcdRoleConfig[] CCD_ROLES_NEEDED_FOR_DA = {
        new CcdRoleConfig("caseworker-domesticabuse", PUBLIC),
        new CcdRoleConfig("caseworker-domesticabuse-courtadmin", PUBLIC),
        new CcdRoleConfig("caseworker-domesticabuse-caseworker", PUBLIC),
        new CcdRoleConfig("caseworker-domesticabuse-superuser", PUBLIC),
        new CcdRoleConfig("caseworker-domesticabuse-la", PUBLIC),
        new CcdRoleConfig("caseworker-domesticabuse-judge", PUBLIC),
        new CcdRoleConfig("caseworker-domesticabuse-solicitor", PUBLIC),
        new CcdRoleConfig("caseworker-domesticabuse-systemupdate", PUBLIC)

    };

    private final CcdEnvironment environment;

    public HighLevelDataSetupApp(CcdEnvironment dataSetupEnvironment) {
        super(dataSetupEnvironment);
        environment = dataSetupEnvironment;
    }

    public static void main(String[] args) throws Throwable {
        if (CcdEnvironment.valueOf(args[0].toUpperCase(Locale.UK)).equals(CcdEnvironment.PROD)) {
            return;
        }
        main(HighLevelDataSetupApp.class, args);
    }

    @Override
    protected boolean shouldTolerateDataSetupFailure() {
        return true;
    }

    @Override
    public void addCcdRoles() {
        for (CcdRoleConfig roleConfig : CCD_ROLES_NEEDED_FOR_DA) {
            try {
                logger.info("\n\nAdding CCD Role {}.", roleConfig);
                addCcdRole(roleConfig);
                logger.info("\n\nAdded CCD Role {}.", roleConfig);
            } catch (Exception e) {
                logger.error("\n\nCouldn't add CCD Role {} - Exception: {}.\n\n", roleConfig, e);
                if (!shouldTolerateDataSetupFailure()) {
                    throw e;
                }
            }
        }
    }

    @Override
    protected List<String> getAllDefinitionFilesToLoadAt(String definitionsPath) {
        String environmentName = environment.name().toLowerCase(Locale.UK);
        return List.of(
            "build/ccd-config/ccd-" + DomesticAbuse.CASE_TYPE + "-" + environmentName + ".xlsx"
        );
    }
}
