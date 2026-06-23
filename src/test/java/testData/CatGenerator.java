package testData;

import models.CatCreateRequestDto;

import java.time.LocalDate;

public class CatGenerator {
    public static CatCreateRequestDto createCat() {
        return CatCreateRequestDto
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
    }
}
