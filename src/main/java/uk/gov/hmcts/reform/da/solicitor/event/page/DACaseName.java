package uk.gov.hmcts.reform.da.solicitor.event.page;

import uk.gov.hmcts.reform.da.common.ccd.CcdPageConfiguration;
import uk.gov.hmcts.reform.da.common.ccd.PageBuilder;
import uk.gov.hmcts.reform.da.dacase.model.CaseData;

public class DACaseName implements CcdPageConfiguration {
    @Override
    public void addTo(final PageBuilder pageBuilder) {

        pageBuilder
            .page("DACaseName")
            .pageLabel("Solicitor Application")
            .complex(CaseData::getCaseName)
            .done();
    }
}
