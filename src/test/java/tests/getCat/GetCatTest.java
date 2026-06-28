package tests.getCat;

import io.restassured.response.Response;
import models.CatCreateRequestDto;
import models.CatCreateResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import steps.CrudCatStep;
import testData.CatGenerator;
import testData.Color;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetCatTest {
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
    void getCatByIdNotFoundTest() {
        Response response = step.getCatById("1000");

        assertEquals(404, response.statusCode());
        assertNotNull(response.path("error"));
        assertEquals("Cat with id 1000 not found", response.path("message"));
    }

    @ParameterizedTest
    @CsvSource({"0.1", "abc", "-7", "0007", "99999999999999999999"})
    void getCatByIdWithNoneIdTest(String id) {
        Response response = step.getCatById(id);

        assertEquals(400, response.statusCode());
        assertEquals("Bad Request", response.path("error"));
        assertEquals("Parameter 'id' should be of type 'Long' but received: '%s'".formatted(id), response.path("message"));
    }

    @Test
    void createCatAndGetCatById() {
        CatCreateRequestDto cat = CatGenerator.createCat();
        Integer id = step.createCat(cat).as(CatCreateResponseDto.class).getId();
        Response response = step.getCatById(id.toString());

        assertEquals(id, response.path("id"), "ID должны совпадать");
    }
}
