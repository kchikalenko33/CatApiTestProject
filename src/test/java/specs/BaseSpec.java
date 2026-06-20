package specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class BaseSpec {
    public static RequestSpecification baseSpecReq() {
        return new RequestSpecBuilder()
                .setBaseUri("http://localhost:18080")
                .setContentType("application/json")
                .build();
    }
}
