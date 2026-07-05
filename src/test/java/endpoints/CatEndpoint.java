package endpoints;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CatEndpoint {
    GET_ALL_CATS("/api/v1/cats"),
    CREATE_CAT("/api/v1/cats"),
    GET_CAT("/api/v1/cats/{id}"),
    DELETE_BULK("/api/v1/cats/bulk");

    private final String path;

    CatEndpoint(String path) {
        this.path = path;
    }

    @JsonValue
    public String getPath() {
        return path;
    }
}
