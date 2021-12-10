package uk.gov.hmcts.reform.da.dacase.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import uk.gov.hmcts.ccd.sdk.api.HasRole;

@AllArgsConstructor
@Getter
public enum UserRole implements HasRole {

    CASE_WORKER("caseworker-da-caseworker", "CRU"),
    COURT_ADMIN("caseworker-da-courtadmin", "CRU"),
    LEGAL_ADVISOR("caseworker-da-la", "CRU"),
    DISTRICT_JUDGE("caseworker-da-judge", "CRU"),
    SUPER_USER("caseworker-da-superuser", "CRU"),
    SYSTEM_UPDATE("caseworker-da-systemupdate", "CRU"),
    SOLICITOR("caseworker-da-solicitor", "CRU"),
    CREATOR("[CREATOR]", "CRU"),
    APPLICANT_SOLICITOR("[APPLICANTSOLICITOR]", "CRU"),
    RESPONDENT_SOLICITOR("[RESPONDENTSOLICITOR]", "CRU");

    @JsonValue
    private final String role;
    private final String caseTypePermissions;

}
