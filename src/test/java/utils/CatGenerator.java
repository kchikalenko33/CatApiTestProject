package utils;

import lombok.Builder;
import lombok.experimental.UtilityClass;
import models.CatCreateRequestDto;
import net.datafaker.Faker;
import testData.Breed;
import testData.Color;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

@UtilityClass
@Builder
public class CatGenerator {
    private static final Faker faker = new Faker();
    public static final int MIN_AGE = 0;
    public static final int MAX_AGE = 21;
    public static final double MIN_WEIGHT = 0.5;
    public static final double MAX_WEIGHT = 15.00;

    public static CatCreateRequestDto randomCat() {
        int age = randomAge();

        return CatCreateRequestDto.builder()
                .name(randomName())
                .age(age)
                .color(randomColor())
                .breed(randomBreed())
                .weight(randomWeight())
                .vaccinated(randomVaccinated())
                .birthDate(randomBirthday(age))
                .ownerEmail(randomEmail())
                .build();
    }


    public static String randomName() {
        return faker.cat().name();
    }

    public static Color randomColor() {
        return randomEnum(Color.class);
    }

    public static Breed randomBreed() {
        return randomEnum(Breed.class);
    }

    public static String randomEmail() {
        return faker.internet().emailAddress();
    }

    public static boolean randomVaccinated() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    public static int randomAge() {
        return ThreadLocalRandom.current().nextInt(MIN_AGE, MAX_AGE);
    }

    public static double randomWeight() {
        return (double) Math.round(ThreadLocalRandom.current().nextDouble(MIN_WEIGHT, MAX_WEIGHT) * 10) / 10;
    }

    public static LocalDate randomBirthday(int age) {
        return LocalDate.now().minusYears(age).minusDays(ThreadLocalRandom.current().nextInt(0, 366));
    }

    private static <T extends Enum<?>> T randomEnum(Class<T> tClass) {
        T[] enumValues = tClass.getEnumConstants();

        if (enumValues.length == 0) {
            throw new IllegalArgumentException("Enum пустой");
        }

        return enumValues[ThreadLocalRandom.current().nextInt(enumValues.length)];
    }
}
