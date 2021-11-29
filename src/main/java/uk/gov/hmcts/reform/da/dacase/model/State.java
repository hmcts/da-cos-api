package uk.gov.hmcts.reform.da.dacase.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uk.gov.hmcts.ccd.sdk.api.CCD;

@RequiredArgsConstructor
@Getter
public enum State {
    @CCD(
        name = "Draft",
        label = "# **${[CASE_REFERENCE]}** ${applicantLastName} **&** ${respondentLastName}\n### **${[STATE]}**\n"
    )
    Draft("FL401 application case drafted"),

    @CCD(
        name = "Submitted",
        label = "# **${[CASE_REFERENCE]}** ${applicantLastName} **&** ${respondentLastName}\n### **${[STATE]}**\n"
    )
    Submitted("FL401 application case submitted");

    private final String name;

}

