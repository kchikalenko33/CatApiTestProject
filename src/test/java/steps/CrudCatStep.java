package steps;

import client.CatClient;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.CatCreateRequestDto;

public class CrudCatStep {
    private CatClient catClient = new CatClient();

    @Step("Создание кота")
    public Response createCat(CatCreateRequestDto cat) {
        return catClient.createCat(cat)
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Получение кота по ID")
    public Response getCatById(String id) {
        return catClient.getCatByRow(id)
                .then()
                .log().all()
                .contentType(ContentType.JSON)
                .extract().response();
    }

}
