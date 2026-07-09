package assertions.catList;

import io.qameta.allure.Step;
import lombok.experimental.UtilityClass;
import models.CatListResponseDto;
import models.PageableDto;

import static org.junit.jupiter.api.Assertions.*;

@UtilityClass
public class CatListPaginationAssertions {

    @Step("Текущая страница не должна быть последней")
    public static void assertIsNotLastPage(CatListResponseDto response) {
        assertFalse(response.getLast(), "Поле last должно быть false");
    }

    @Step("Номер страницы должен совпадать")
    public static void assertSamePageNumber(PageableDto actual, PageableDto expected) {
        assertEquals(actual.getPageNumber(), expected.getPageNumber(), "Номер страницы должен совпадать");
    }

    @Step("Размер страницы должен совпадать")
    public static void assertSamePageSize(PageableDto actual, PageableDto expected) {
        assertEquals(actual.getPageSize(), expected.getPageSize(), "Кол-во страниц должно совпадать");
    }

    @Step("Ответ должен содержать первую страницу")
    public static void assertIsFirstPage(CatListResponseDto response) {
        assertTrue(response.getFirst(), "Поле first должно быть true");
    }

    @Step("Ответ должен содержать первую страницу")
    public static void assertIsNotFirstPage(CatListResponseDto response) {
        assertFalse(response.getFirst(), "Поле first должно быть false");
    }

    @Step("Ответ должен содержать первую страницу")
    public static void assertIsLastPage(CatListResponseDto response) {
        assertTrue(response.getLast(), "Поле last должно быть true");
    }

    @Step("Пагинация должна быть включена")
    public static void assertIsPaged(PageableDto pageable) {
        assertTrue(pageable.getPaged(), "Поле paged должно быть true");
    }

    @Step("Ответ не должен быть unpaged")
    public static void assertIsNotUnpaged(PageableDto pageable) {
        assertFalse(pageable.getUnpaged(), "Поле unpaged должно быть false");
    }

    @Step("Список должен быть отсортирован")
    public static void assertIsSorted(CatListResponseDto response) {
        assertTrue(response.getSort().getSorted(), "Поле sorted должно быть true");
    }

    @Step("Номер страницы должен быть {expectedPageNumber}")
    public static void assertPageNumberEquals(PageableDto pageable, int expectedPageNumber) {
        assertEquals(expectedPageNumber, pageable.getPageNumber(),
                "Номер страницы должен совпадать с ожидаемым");
    }

    public static void checkTotalPagesNotEmpty(CatListResponseDto actual) {
        assertTrue(actual.getTotalPages() > 0,
                "Общее количество страниц должно быть больше 0");
    }
}
