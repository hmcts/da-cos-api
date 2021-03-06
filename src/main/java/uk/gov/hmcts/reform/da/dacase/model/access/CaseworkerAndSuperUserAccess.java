package uk.gov.hmcts.reform.da.dacase.model.access;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import uk.gov.hmcts.ccd.sdk.api.HasAccessControl;
import uk.gov.hmcts.ccd.sdk.api.HasRole;
import uk.gov.hmcts.ccd.sdk.api.Permission;

import static uk.gov.hmcts.reform.da.dacase.model.UserRole.CASE_WORKER;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.DISTRICT_JUDGE;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.LEGAL_ADVISOR;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.SOLICITOR;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.SUPER_USER;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.SYSTEM_UPDATE;

public class CaseworkerAndSuperUserAccess implements HasAccessControl {

    @Override
    public SetMultimap<HasRole, Permission> getGrants() {
        SetMultimap<HasRole, Permission> grants = HashMultimap.create();
        grants.putAll(SOLICITOR, Permissions.READ);
        grants.putAll(LEGAL_ADVISOR, Permissions.READ);
        grants.putAll(DISTRICT_JUDGE, Permissions.READ);

        grants.putAll(SUPER_USER, Permissions.CREATE_READ_UPDATE_DELETE);

        grants.putAll(CASE_WORKER, Permissions.CREATE_READ_UPDATE);
        grants.putAll(SYSTEM_UPDATE, Permissions.CREATE_READ_UPDATE);
        return grants;
    }
}
