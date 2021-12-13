package uk.gov.hmcts.reform.da.dacase.tab;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static uk.gov.hmcts.reform.da.dacase.model.State.DRAFT;
import static uk.gov.hmcts.reform.da.dacase.model.State.SUBMITTED;
import static uk.gov.hmcts.reform.da.dacase.tab.TabShowCondition.andNotShowForState;

public class TabShowConditionTest {
    @Test
    void shouldOnlyShowTabIfCaseStateIsNotAnyOfGivenStates() {
        assertThat((andNotShowForState(DRAFT, SUBMITTED)))
            .isEqualTo("[STATE]!=\"FL401 application case drafted\" "
                           + "AND [STATE]!=\"FL401 application case submitted\"");
    }
}
