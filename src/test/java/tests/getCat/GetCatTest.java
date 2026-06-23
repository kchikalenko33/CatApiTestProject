package tests.getCat;

import client.CatClient;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.CatCreateRequestDto;
import models.CatCreateResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import steps.CrudCatStep;
import testData.CatGenerator;
import testData.Color;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetCatTest {
    private CatClient catClient = new CatClient();
    private CrudCatStep step = new CrudCatStep();

    @Test
    void getCatByIdCat() {
        Response response = step.getCatById("7");

        assertEquals(200, response.statusCode());
        assertEquals(7, response.jsonPath().getInt("id"));
        assertEquals("Bella", response.path("name"));
        assertNotNull(response.path("age"));
        assertEquals(Color.CREAM.getColor(), response.path("color"));
    }

    @Test
    void getCatById_shouldReturn404_whenCatDoesNotExistTest() {
        catClient.getCatById(1000)
                .then()
                .log().all()
                .statusCode(404)
                .contentType(ContentType.JSON)
                .body("error", notNullValue())
                .body("message", containsString(String.valueOf(1000)));
    }

    @ParameterizedTest
    @CsvSource({"0.1", "abc", "-7", "0007", "99999999999999999999"})
    void getCatByIdWithNoneIdTest(String id) {
        catClient.getCatByRow(id)
                .then()
                .log().all()
                .statusCode(400)
                .contentType(ContentType.JSON)
                .body("error", equalTo("Bad Request"))
                .body("message", equalTo("Parameter 'id' should be of type 'Long' but received: '%s'".formatted(id)));
    }

    @Test
    void createCatAndGetCatById() {
        CatCreateRequestDto cat = CatGenerator.createCat();
        Integer id = step.createCat(cat).as(CatCreateResponseDto.class).getId();
        Response response = step.getCatById(id.toString());

        assertEquals(id, response.path("id"), "ID должны совпадать");
    }
}
