package uk.gov.hmcts.reform.da.solicitor.event.page;

import uk.gov.hmcts.reform.da.common.ccd.CcdPageConfiguration;
import uk.gov.hmcts.reform.da.common.ccd.PageBuilder;
import uk.gov.hmcts.reform.da.dacase.model.CaseData;
import uk.gov.hmcts.reform.da.dacase.model.CaseInfo;

public class DaCaseName implements CcdPageConfiguration {
    @Override
    public void addTo(final PageBuilder pageBuilder) {

        pageBuilder
            .page("DaCaseName")
            .pageLabel("Solicitor Application")
            .complex(CaseData::getCaseInfo)
            .mandatoryWithLabel(
                CaseInfo::getCaseName,
                "Case Name"
            )
            .done();
    }
}
