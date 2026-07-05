package assertions.catList;

import io.qameta.allure.Step;
import lombok.experimental.UtilityClass;
import models.CatCreateResponseDto;
import models.CatListResponseDto;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@UtilityClass
public class CatListContentAssertions {

    @Step("Количество элементов должно совпадать с размером content")
    public static void assertSameContent(CatListResponseDto actual, CatListResponseDto expected) {
        assertEquals(actual.getContent(), expected.getContent(),
                "Содержимое списка котов должно совпадать");
    }

    @Step("Список котов должен быть отсортирован по имени по возрастанию")
    public static void assertSortedByNameAsc(List<CatCreateResponseDto> cats) {
        List<String> names = cats.stream()
                .map(CatCreateResponseDto::getName)
                .toList();

        List<String> sortedNames = names.stream()
                .sorted()
                .toList();

        assertEquals(sortedNames, names, "Список котов должен быть отсортирован по имени по возрастанию");
    }

    @Step("Количество элементов должно совпадать с размером content")
    public static void assertNumberOfElementsMatchesContentSize(CatListResponseDto actual) {
        assertEquals(actual.getNumberOfElements(), actual.getContent().size(),
                "numberOfElements должен совпадать с размером content");
    }

    @Step("Список котов должен быть отсортирован по id по возрастанию")
    public static void assertSortedByIdAsc(List<CatCreateResponseDto> actual) {
        List<CatCreateResponseDto> expected = actual.stream()
                .sorted(Comparator.comparing(CatCreateResponseDto::getId))
                .toList();

        assertEquals(expected, actual,
                "Список котов должен быть отсортирован по id по возрастанию");
    }
}
