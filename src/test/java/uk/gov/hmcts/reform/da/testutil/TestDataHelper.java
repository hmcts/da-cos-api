package uk.gov.hmcts.reform.da.testutil;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import feign.FeignException;
import feign.Request;
import feign.Response;
import uk.gov.hmcts.ccd.sdk.type.AddressGlobalUK;
import uk.gov.hmcts.reform.ccd.client.model.CallbackRequest;
import uk.gov.hmcts.reform.ccd.client.model.CaseDetails;
import uk.gov.hmcts.reform.da.dacase.model.Applicant;
import uk.gov.hmcts.reform.da.dacase.model.Application;
import uk.gov.hmcts.reform.da.dacase.model.ApplicationType;
import uk.gov.hmcts.reform.da.dacase.model.CaseData;
import uk.gov.hmcts.reform.da.dacase.model.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static feign.Request.HttpMethod.GET;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static uk.gov.hmcts.ccd.sdk.type.YesOrNo.NO;
import static uk.gov.hmcts.ccd.sdk.type.YesOrNo.YES;
import static uk.gov.hmcts.reform.da.dacase.DomesticAbuse.CASE_TYPE;
import static uk.gov.hmcts.reform.da.dacase.model.Gender.FEMALE;
import static uk.gov.hmcts.reform.da.dacase.model.Gender.MALE;
import static uk.gov.hmcts.reform.da.testutil.TestConstants.TEST_CASE_ID;
import static uk.gov.hmcts.reform.da.testutil.TestConstants.TEST_FIRST_NAME;
import static uk.gov.hmcts.reform.da.testutil.TestConstants.TEST_LAST_NAME;
import static uk.gov.hmcts.reform.da.testutil.TestConstants.TEST_MIDDLE_NAME;
import static uk.gov.hmcts.reform.da.testutil.TestConstants.TEST_USER_EMAIL;

public class TestDataHelper {


    public static final LocalDate LOCAL_DATE = LocalDate.of(2021, 4, 28);
    public static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.of(2021, 4, 28, 1, 0);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().findAndRegisterModules();
    private static final TypeReference<HashMap<String, Object>> TYPE_REFERENCE = new TypeReference<>() {
    };

    private TestDataHelper() {

    }

    public static Applicant getApplicant() {
        return getApplicant(FEMALE);
    }

    public static Applicant getApplicant(Gender gender) {
        return Applicant.builder()
            .firstName(TEST_FIRST_NAME)
            .middleName(TEST_MIDDLE_NAME)
            .lastName(TEST_LAST_NAME)
            .email(TEST_USER_EMAIL)
            .gender(gender)
            .keepContactDetailsConfidential(NO)
            .build();
    }

    public static Applicant getApplicantWithAddress() {
        return Applicant.builder()
            .firstName(TEST_FIRST_NAME)
            .middleName(TEST_MIDDLE_NAME)
            .lastName(TEST_LAST_NAME)
            .email(TEST_USER_EMAIL)
            .gender(MALE)
            .homeAddress(AddressGlobalUK.builder()
                             .addressLine1("line 1")
                             .postTown("town")
                             .postCode("postcode")
                             .build())
            .build();
    }

    public static Applicant getInvalidApplicant() {
        return Applicant.builder()
            .firstName(TEST_FIRST_NAME)
            .middleName(TEST_MIDDLE_NAME)
            .lastName(TEST_LAST_NAME)
            .email(TEST_USER_EMAIL)
            .build();
    }

    public static CaseData caseData() {
        return CaseData.builder()
            .applicant(getApplicant())
            .build();
    }

    public static CaseData invalidCaseData() {
        return CaseData.builder()
            .applicant(getInvalidApplicant())
            .build();
    }

    public static CaseData validApplicantCaseData() {

        var applicant = getApplicant();
        applicant.setKeepContactDetailsConfidential(YES);

        var application = Application.builder().build();

        return CaseData
            .builder()
            .applicationType(ApplicationType.NON_MOLESTATION_ORDER)
            .applicant(applicant)
            .application(application)
            .build();
    }

    public static CallbackRequest callbackRequest(CaseData caseData) {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());

        return CallbackRequest
            .builder()
            .caseDetails(
                CaseDetails
                    .builder()
                    .data(OBJECT_MAPPER.convertValue(caseData, TYPE_REFERENCE))
                    .id(TEST_CASE_ID)
                    .createdDate(LOCAL_DATE_TIME)
                    .build()
            )
            .build();
    }

    public static CallbackRequest callbackRequest(final CaseData caseData,
                                                  final String eventId) {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());

        return CallbackRequest
            .builder()
            .eventId(eventId)
            .caseDetailsBefore(caseDetailsBefore(caseData))
            .caseDetails(
                CaseDetails
                    .builder()
                    .data(OBJECT_MAPPER.convertValue(caseData, TYPE_REFERENCE))
                    .id(TEST_CASE_ID)
                    .createdDate(LOCAL_DATE_TIME)
                    .caseTypeId(CASE_TYPE)
                    .build()
            )
            .build();
    }

    public static CallbackRequest callbackRequest(final CaseData caseData, String eventId, String state) {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        return CallbackRequest
            .builder()
            .eventId(eventId)
            .caseDetailsBefore(
                caseDetailsBefore(caseData))
            .caseDetails(
                CaseDetails
                    .builder()
                    .data(OBJECT_MAPPER.convertValue(caseData, TYPE_REFERENCE))
                    .state(state)
                    .id(TEST_CASE_ID)
                    .caseTypeId(CASE_TYPE)
                    .build()
            )
            .build();
    }

    public static FeignException feignException(int status, String reason) {
        byte[] emptyBody = {};
        Request request = Request.create(GET, EMPTY, Map.of(), emptyBody, UTF_8, null);

        return FeignException.errorStatus(
            "idamRequestFailed",
            Response.builder()
                .request(request)
                .status(status)
                .headers(Collections.emptyMap())
                .reason(reason)
                .build()
        );
    }

    private static CaseDetails caseDetailsBefore(CaseData caseData) {
        return CaseDetails
            .builder()
            .data(OBJECT_MAPPER.convertValue(caseData, TYPE_REFERENCE))
            .id(TEST_CASE_ID)
            .caseTypeId(CASE_TYPE)
            .build();
    }
}
