package uk.gov.hmcts.reform.da.solicitor.service.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.ccd.sdk.api.CaseDetails;
import uk.gov.hmcts.reform.da.dacase.model.CaseData;
import uk.gov.hmcts.reform.da.dacase.model.State;
import uk.gov.hmcts.reform.da.dacase.task.CaseTask;

@Component
@Slf4j
public class DomesticAbuseApplicationDraft implements CaseTask {

    @Override
    public CaseDetails<CaseData, State> apply(final CaseDetails<CaseData, State> caseDetails) {

        final Long caseId = caseDetails.getId();

        log.info("Executing handler for generating draft domestic application for case id {} ", caseId);

        return caseDetails;
    }
}
