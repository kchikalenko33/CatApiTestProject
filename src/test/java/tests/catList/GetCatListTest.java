package tests.catList;

import client.CatClient;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.greaterThan;

public class GetCatListTest {
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
}
