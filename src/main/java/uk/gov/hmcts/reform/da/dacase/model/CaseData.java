package uk.gov.hmcts.reform.da.dacase.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uk.gov.hmcts.ccd.sdk.api.CCD;
import uk.gov.hmcts.reform.da.dacase.model.access.DefaultAccess;

import static uk.gov.hmcts.ccd.sdk.type.FieldType.MultiSelectList;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CaseData {

    @JsonUnwrapped(prefix = "caseInfo")
    @Builder.Default
    @CCD(access = {DefaultAccess.class})
    private CaseInfo caseInfo = new CaseInfo();

    @CCD(
        label = "Application type",
        access = {DefaultAccess.class},
        typeOverride = MultiSelectList,
        typeParameterOverride = "ApplicationType"
    )
    private ApplicationType applicationType;

    @JsonUnwrapped(prefix = "applicant")
    @Builder.Default
    @CCD(access = {DefaultAccess.class})
    private Applicant applicant = new Applicant();

    @JsonUnwrapped()
    @Builder.Default
    private Application application = new Application();
}
