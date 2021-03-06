package uk.gov.hmcts.reform.da.solicitor.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.ccd.sdk.api.CCDConfig;
import uk.gov.hmcts.ccd.sdk.api.CaseDetails;
import uk.gov.hmcts.ccd.sdk.api.ConfigBuilder;
import uk.gov.hmcts.ccd.sdk.api.callback.AboutToStartOrSubmitResponse;
import uk.gov.hmcts.reform.ccd.client.model.SubmittedCallbackResponse;
import uk.gov.hmcts.reform.da.common.AddSystemUpdateRole;
import uk.gov.hmcts.reform.da.common.ccd.CcdPageConfiguration;
import uk.gov.hmcts.reform.da.common.ccd.PageBuilder;
import uk.gov.hmcts.reform.da.dacase.model.CaseData;
import uk.gov.hmcts.reform.da.dacase.model.State;
import uk.gov.hmcts.reform.da.dacase.model.UserRole;
import uk.gov.hmcts.reform.da.solicitor.event.page.DaCaseName;
import uk.gov.hmcts.reform.da.solicitor.service.CcdAccessService;
import uk.gov.hmcts.reform.da.solicitor.service.SolicitorCreateApplicationService;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import static java.util.Arrays.asList;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static uk.gov.hmcts.reform.da.dacase.model.State.Draft;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.CASE_WORKER;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.DISTRICT_JUDGE;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.LEGAL_ADVISOR;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.SOLICITOR;
import static uk.gov.hmcts.reform.da.dacase.model.UserRole.SUPER_USER;
import static uk.gov.hmcts.reform.da.dacase.model.access.Permissions.CREATE_READ_UPDATE;
import static uk.gov.hmcts.reform.da.dacase.model.access.Permissions.READ;
import static uk.gov.hmcts.reform.da.dacase.model.access.Permissions.READ_UPDATE;

@Slf4j
@Component
public class SolicitorCreateApplication implements CCDConfig<CaseData, State, UserRole> {

    public static final String SOLICITOR_CREATE = "solicitor-create-application";

    @Autowired
    private SolicitorCreateApplicationService solicitorCreateApplicationService;

    @Autowired
    private AddSystemUpdateRole addSystemUpdateRole;

    @Autowired
    private CcdAccessService ccdAccessService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public void configure(final ConfigBuilder<CaseData, State, UserRole> configBuilder) {
        final PageBuilder pageBuilder = addEventConfig(configBuilder);

        final List<CcdPageConfiguration> pages = asList(
            new DaCaseName()
        );

        pages.forEach(page -> page.addTo(pageBuilder));
    }

    public AboutToStartOrSubmitResponse<CaseData, State> aboutToSubmit(CaseDetails<CaseData, State> details,
                                                                       CaseDetails<CaseData, State> beforeDetails) {
        log.info("Solicitor create application about to submit callback invoked");

        final CaseDetails<CaseData, State> result = solicitorCreateApplicationService.aboutToSubmit(details);

        return AboutToStartOrSubmitResponse.<CaseData, State>builder()
            .data(result.getData())
            .build();
    }

    private PageBuilder addEventConfig(
        final ConfigBuilder<CaseData, State, UserRole> configBuilder) {

        var defaultRoles = new ArrayList<UserRole>();
        defaultRoles.add(SOLICITOR);

        var updatedRoles = addSystemUpdateRole.addIfConfiguredForEnvironment(defaultRoles);

        return new PageBuilder(configBuilder
                                   .event(SOLICITOR_CREATE)
                                   .initialState(Draft)
                                   .name("Apply for a domestic abuse")
                                   .description("Apply for a domestic abuse")
                                   .showSummary()
                                   .endButtonLabel("Save Application")
                                   .aboutToSubmitCallback(this::aboutToSubmit)
                                   .submittedCallback(this::submitted)
                                   .explicitGrants()
                                   .grant(CREATE_READ_UPDATE, updatedRoles.toArray(UserRole[]::new))
                                   .grant(READ_UPDATE, SUPER_USER)
                                   .grant(READ, CASE_WORKER, LEGAL_ADVISOR, DISTRICT_JUDGE));
    }

    public SubmittedCallbackResponse submitted(CaseDetails<CaseData, State> details,
                                               CaseDetails<CaseData, State> before) {
        ccdAccessService.addApplicantSolicitorRole(
            httpServletRequest.getHeader(AUTHORIZATION),
            details.getId()
        );

        return SubmittedCallbackResponse.builder().build();
    }
}
