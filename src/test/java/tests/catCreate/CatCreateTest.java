package tests.catCreate;

import client.CatClient;
import io.restassured.response.Response;
import models.CatCreateRequestDto;
import org.junit.jupiter.api.Test;
import testData.Breed;
import testData.Color;

import java.time.LocalDate;

import static org.hamcrest.Matchers.notNullValue;

public class CatCreateTest {
    private CatClient catClient = new CatClient();

    @Test
    void createCatTest() {
        CatCreateRequestDto cat = CatCreateRequestDto
                .builder()
                .name("Whiskers")
                .age(3)
                .color(Color.TABBY)
                .breed(Breed.MAINE_COON)
                .weight(4.5)
                .vaccinated(true)
                .birthDate(LocalDate.of(2020, 12, 1))
                .ownerEmail("owner@example.com")
                .build();

        Response response = catClient.createCat(cat)
                .then()
                .log().all()
                .statusCode(201)
                .body("id", notNullValue())
                .log().all()
                .extract().response();

        response.prettyPrint();
    }
}
