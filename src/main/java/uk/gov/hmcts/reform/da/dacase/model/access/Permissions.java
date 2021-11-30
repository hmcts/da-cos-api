package uk.gov.hmcts.reform.da.dacase.model.access;

import uk.gov.hmcts.ccd.sdk.api.Permission;

import java.util.Set;

import static uk.gov.hmcts.ccd.sdk.api.Permission.CR;
import static uk.gov.hmcts.ccd.sdk.api.Permission.CRU;
import static uk.gov.hmcts.ccd.sdk.api.Permission.CRUD;
import static uk.gov.hmcts.ccd.sdk.api.Permission.D;
import static uk.gov.hmcts.ccd.sdk.api.Permission.R;
import static uk.gov.hmcts.ccd.sdk.api.Permission.U;

public final class Permissions {

    protected static final Set<Permission> CREATE_READ_UPDATE_DELETE = CRUD;
    public static final Set<Permission> CREATE_READ_UPDATE = CRU;
    protected static final Set<Permission> CREATE_READ = CR;
    public static final Set<Permission> READ_UPDATE = Set.of(R, U);
    public static final Set<Permission> READ_UPDATE_DELETE = Set.of(R, U, D);
    public static final Set<Permission> READ = Set.of(R);

    private Permissions() {
    }
}
