package tests;

import client.CatClient;
import io.restassured.response.Response;
import models.CatCreateRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import testData.Breed;
import testData.Color;

import java.time.LocalDate;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

public class CatApiDemoTest {
    private CatClient catClient = new CatClient();

    @Test
    void getAllCatsTest() {
        Response response = catClient.getCatList()
                .then()
                .statusCode(200)
                .body("content.size()", greaterThan(0))
                .extract().response();

        Assertions.assertEquals(response.jsonPath().getString("content[0].name"), "Bella", "д.б Bella");
    }

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
                .statusCode(201)
                .body("id", notNullValue())
                .extract().response();

        response.prettyPrint();
    }
}
