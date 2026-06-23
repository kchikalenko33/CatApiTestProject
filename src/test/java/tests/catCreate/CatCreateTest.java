package tests.catCreate;

import io.restassured.response.Response;
import models.CatCreateRequestDto;
import org.junit.jupiter.api.Test;
import steps.CrudCatStep;
import testData.CatGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CatCreateTest {
    private CrudCatStep step = new CrudCatStep();

    @Test
    void createCatTest() {
        CatCreateRequestDto cat = CatGenerator.createCat();

        Response response = step.createCat(cat);
        assertEquals(201, response.statusCode());
        assertNotNull(response.path("id"));
    }
}
