package testData;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Breed {
    MAINE_COON("Maine coon"),
    BRITISH_SHORTHAIR("British shorthair"),
    SCOTTISH_FOLD("Scottish fold"),
    SIAMESE("Siamese"),
    PERSIAN("Persian"),
    SPHYNX("Sphynx"),
    BENGAL("Bengal"),
    RAGDOLL("Ragdoll"),
    MIXED("Mixed");


    private final String breed;

    Breed(String breed) {
        this.breed = breed;
    }

    @JsonValue
    public String getBreed() {
        return breed;
    }
}
