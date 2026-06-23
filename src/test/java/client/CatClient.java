package client;

import endpoints.CatEndpoint;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.CatCreateRequestDto;
import specs.BaseSpec;

import static io.restassured.RestAssured.given;

public class CatClient {

   private RequestSpecification rq = BaseSpec.baseSpecReq();

    public Response getCatList() {
        return given()
                .spec(rq)
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

    public Response getCatById(Integer id) {
        return given()
                .spec(rq)
                .pathParam("id", id)
                .when()
                .log().all()
                .get(CatEndpoint.GET_CAT.getPath());

    }

    public Response getCatByRow (String id) {
        return given()
                .spec(rq)
                .pathParam("id", id)
                .when()
                .log().all()
                .get(CatEndpoint.GET_CAT.getPath());
    }
}
