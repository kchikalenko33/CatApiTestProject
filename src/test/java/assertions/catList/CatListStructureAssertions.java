package assertions.catList;

import io.qameta.allure.Step;
import lombok.experimental.UtilityClass;
import models.CatListResponseDto;

import static org.junit.jupiter.api.Assertions.*;

@UtilityClass
public class CatListStructureAssertions {

    @Step("Структура ответа списка котов должна быть заполнена")
    public static void assertValidStructure(CatListResponseDto actual) {
        assertAll("Проверка структуры ответа",
                () -> assertNotNull(actual, "Ответ не должен быть null"),
                () -> assertNotNull(actual.getContent(), "Поле content не должно быть null"),
                () -> assertNotNull(actual.getPageable(), "Поле pageable не должно быть null"),
                () -> assertNotNull(actual.getSort(), "Поле sort не должно быть null"),
                () -> assertNotNull(actual.getTotalElements(), "Поле totalElements не должно быть null"),
                () -> assertNotNull(actual.getTotalPages(), "Поле totalPages не должно быть null")
        );
    }
}
