package uk.gov.hmcts.reform.da.solicitor.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.ccd.sdk.api.CaseDetails;
import uk.gov.hmcts.reform.authorisation.generators.AuthTokenGenerator;
import uk.gov.hmcts.reform.da.dacase.model.CaseData;
import uk.gov.hmcts.reform.da.dacase.model.State;
import uk.gov.hmcts.reform.da.solicitor.service.task.DomesticAbuseApplicationDraft;
import uk.gov.hmcts.reform.da.solicitor.service.task.InitialiseSolicitorCreatedApplication;

import static uk.gov.hmcts.reform.da.dacase.task.CaseTaskRunner.caseTasks;

@Service
@Slf4j
public class SolicitorCreateApplicationService {

    @Autowired
    private InitialiseSolicitorCreatedApplication initialiseSolicitorCreatedApplication;

    @Autowired
    private DomesticAbuseApplicationDraft domesticAbuseApplicationDraft;

    @Autowired
    private AuthTokenGenerator authTokenGenerator;

    public CaseDetails<CaseData, State> aboutToSubmit(final CaseDetails<CaseData, State> caseDetails) {

        return caseTasks(
            initialiseSolicitorCreatedApplication,
            domesticAbuseApplicationDraft
        ).run(caseDetails);
    }
}
