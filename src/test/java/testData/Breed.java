package testData;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Breed {
    MAINE_COON("MAINE COON"),
    BRITISH_SHORTHAIR("BRITISH_SHORTHAIR"),
    SCOTTISH_FOLD("SCOTTISH_FOLD"),
    SIAMESE("SIAMESE"),
    PERSIAN("PERSIAN"),
    SPHYNX("SPHYNX"),
    BENGAL("BENGAL"),
    RAGDOLL("RAGDOLL"),
    MIXED("MIXED");

    private final String breed;

    Breed(String breed) {
        this.breed = breed;
    }

    @JsonValue
    public String getBreed() {
        return breed;
    }
}
