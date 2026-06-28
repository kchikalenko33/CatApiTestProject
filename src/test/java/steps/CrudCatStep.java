package steps;

import client.CatClient;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.CatCreateRequestDto;

public class CrudCatStep {
    private final CatClient catClient = new CatClient();

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

    @Step("Получение котов без параметров")
    public Response getCatList() {
        return catClient.getCatList()
                .then()
                .log().all()
                .contentType(ContentType.JSON)
                .extract().response();
    }

    @Step("Получение котов по всем параметрам")
    public Response getCatList(String size, String sort, String page) {
        return catClient.getCatList(size, sort, page)
                .then()
                .log().all()
                .contentType(ContentType.JSON)
                .extract().response();
    }

    @Step("Получения котов с заданным размером")
    public Response getCatListBySize(String size) {
        return catClient.getCatListBySize(size)
                .then()
                .log().all()
                .contentType(ContentType.JSON)
                .extract().response();
    }

    @Step
    public Response getCatListBySort(String sort) {
        return catClient.getCatListBySort(sort)
                .then()
                .log().all()
                .contentType(ContentType.JSON)
                .extract().response();
    }

    public Response getCatListByPage(String page) {
        return catClient.getCatListByPage(page)
                .then()
                .log().all()
                .contentType(ContentType.JSON)
                .extract().response();
    }
}
