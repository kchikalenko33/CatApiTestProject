package client;

import endpoints.CatEndpoint;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.CatCreateRequestDto;
import specs.BaseSpec;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class CatClient {

    private final RequestSpecification rq = BaseSpec.baseSpecReq();

    public Response getCatList() {
        return given()
                .spec(rq)
                .when()
                .get(CatEndpoint.GET_ALL_CATS.getPath());
    }

    public Response getCatListBySize(String size) {
        return given()
                .spec(rq)
                .queryParam("size", size)
                .when()
                .get(CatEndpoint.GET_ALL_CATS.getPath());
    }

    public Response getCatListBySort(String sort) {
        return given()
                .spec(rq)
                .queryParam(sort)
                .when()
                .get(CatEndpoint.GET_ALL_CATS.getPath());
    }

    public Response getCatListByPage(String page) {
        return given()
                .spec(rq)
                .queryParam("page", page)
                .when()
                .get(CatEndpoint.GET_ALL_CATS.getPath());
    }

    public Response getCatList(String size, String sort, String page) {
        return given()
                .spec(rq)
                .queryParam(size)
                .queryParam(sort)
                .queryParam(page)
                .when()
                .get(CatEndpoint.GET_ALL_CATS.getPath());
    }

    public Response createCat(CatCreateRequestDto cat) {
        return given()
                .spec(rq)
                .body(cat)
                .when()
                .post(CatEndpoint.CREATE_CAT.getPath());
    }

    public Response getCatByRow(String id) {
        return given()
                .spec(rq)
                .pathParam("id", id)
                .when()
                .log().all()
                .get(CatEndpoint.GET_CAT.getPath());
    }

    public Response deleteCats(List<Integer> ids) {
        return given()
                .spec(rq)
                .body(Map.of("ids", ids))
                .when()
                .delete(CatEndpoint.DELETE_BULK.getPath());

    }
}
