package assertions.catList;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CatListHttpAssertions {
    @Step("Проверка успешного статус кода 200")
    public static void assertOk200(Response response) {
        assertEquals(HttpStatus.SC_OK, response.getStatusCode(), "Статус код должен быть %s".formatted(HttpStatus.SC_OK));
    }

    @Step("Проверка успешного статус кода 200")
    public static void assertOk200Fluent(Response response) {
        response.then().statusCode(HttpStatus.SC_OK);
    }

    @Step("Проверка contentType = JSON")
    public static void assertContentTypeJson(Response response) {
        response.then().contentType(ContentType.JSON);
    }

    @Step("Проверка времени ответа")
    public static void assertResponseTime(Response response, Long time) {
        response.then().time(Matchers.lessThan(time));
    }
}
