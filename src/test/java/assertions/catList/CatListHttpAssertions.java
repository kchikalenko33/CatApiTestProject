package assertions.catList;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.experimental.UtilityClass;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@UtilityClass
public class CatListHttpAssertions {
    @Step("Статус-код ответа должен быть 200")
    public static void assertOk(Response response) {
        assertEquals(HttpStatus.SC_OK, response.getStatusCode(), "Статус-код ответа должен быть 200");
    }

    @Step("Content-Type ответа должен быть JSON")
    public static void assertContentTypeJson(Response response) {
        response.then().contentType(ContentType.JSON);
    }

    @Step("Время ответа должно быть меньше {time} мс")
    public static void assertResponseTime(Response response, Long time) {
        response.then().time(Matchers.lessThan(time));
    }

    @Step("Статус-код ответа должен быть 207")
    public static void assertMultiStatus(Response response) {
        assertEquals(HttpStatus.SC_MULTI_STATUS, response.getStatusCode(), "Статус-код ответа должен быть 207");
    }
}
