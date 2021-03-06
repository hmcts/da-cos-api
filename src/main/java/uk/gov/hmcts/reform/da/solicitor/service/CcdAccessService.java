package uk.gov.hmcts.reform.da.solicitor.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.authorisation.generators.AuthTokenGenerator;
import uk.gov.hmcts.reform.ccd.client.CaseAssignmentApi;
import uk.gov.hmcts.reform.ccd.client.CaseUserApi;
import uk.gov.hmcts.reform.ccd.client.model.CaseAssignmentUserRoleWithOrganisation;
import uk.gov.hmcts.reform.ccd.client.model.CaseAssignmentUserRolesRequest;
import uk.gov.hmcts.reform.da.idam.IdamService;
import uk.gov.hmcts.reform.idam.client.models.User;

import java.util.List;
import java.util.Set;

import static uk.gov.hmcts.reform.da.dacase.model.UserRole.APPLICANT_SOLICITOR;

@Service
@Slf4j
public class CcdAccessService {
    @Autowired
    private CaseUserApi caseUserApi;

    @Autowired
    private CaseAssignmentApi caseAssignmentApi;

    @Autowired
    private IdamService idamService;

    @Autowired
    private AuthTokenGenerator authTokenGenerator;

    public void addApplicantSolicitorRole(String solicitorIdamToken, Long caseId) {
        User solicitorUser = idamService.retrieveUser(solicitorIdamToken);
        User systemUpdateUser = idamService.retrieveSystemUpdateUserDetails();

        Set<String> caseRoles = Set.of(APPLICANT_SOLICITOR.getRole());

        String solicitorUserId = solicitorUser.getUserDetails().getId();

        log.info(
            "Adding roles {} to case Id {} and user Id {}",
            caseRoles,
            caseId,
            solicitorUserId
        );

        String idamToken = systemUpdateUser.getAuthToken();
        String s2sToken = authTokenGenerator.generate();

        caseAssignmentApi.removeCaseUserRoles(
            idamToken,
            s2sToken,
            CaseAssignmentUserRolesRequest
                .builder()
                .caseAssignmentUserRolesWithOrganisation(
                    List.of(
                        CaseAssignmentUserRoleWithOrganisation.builder()
                            .caseDataId(caseId.toString())
                            .caseRole(APPLICANT_SOLICITOR.getRole())
                            .userId(solicitorUserId)
                            .build()
                    )
                )
                .build()
        );

        caseAssignmentApi.addCaseUserRoles(
            idamToken,
            s2sToken,
            CaseAssignmentUserRolesRequest
                .builder()
                .caseAssignmentUserRolesWithOrganisation(
                    List.of(
                        CaseAssignmentUserRoleWithOrganisation.builder()
                            .caseDataId(caseId.toString())
                            .caseRole(APPLICANT_SOLICITOR.getRole())
                            .userId(solicitorUserId)
                            .build()
                    )
                )
                .build()
        );

        log.info("Successfully added the applicant's solicitor roles to case Id {} ", caseId);
    }
}
