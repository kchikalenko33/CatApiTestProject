package tests.catList;

import io.qameta.allure.Epic;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
import models.CatCreateRequestDto;
import models.CatCreateResponseDto;
import models.CatListResponseDto;
import models.PageableDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import steps.CrudCatStep;

import java.util.List;

import static assertions.catList.CatListContentAssertions.*;
import static assertions.catList.CatListHttpAssertions.*;
import static assertions.catList.CatListPaginationAssertions.*;
import static assertions.catList.CatListStructureAssertions.assertValidStructure;
import static org.junit.jupiter.api.Assertions.*;
import static utils.CatGenerator.randomCat;

@Epic("Проверки для списка котов")
public class GetCatListTest {
    private final CrudCatStep step = new CrudCatStep();

    @Test
    @DisplayName("GET /cats без параметров возвращает отсортированный список с пагинацией")
    void shouldReturnPagedAndSortedCatsWhenRequestHasNoParams() {
        Response response = step.getCatList();
        CatListResponseDto actual = response.as(CatListResponseDto.class);

        assertAll("Проверка ответа",
                () -> assertOk(response),
                () -> assertContentTypeJson(response),
                () -> assertResponseTime(response, 3000L),
                () -> assertValidStructure(actual)
        );

        List<CatCreateResponseDto> actualContent = actual.getContent();

        assertAll("Проверка содержимого и пагинации",
                () -> assertNumberOfElementsMatchesContentSize(actual),
                () -> assertSortedByNameAsc(actualContent),
                () -> assertIsPaged(actual.getPageable()),
                () -> assertIsSorted(actual)
        );
    }

    @Test
    @DisplayName("Получение первой страницы с сортировкой по id")
    void getAllCatsByIdTest() {
        Response response = step.getCatList("10", "id", "0");

        assertAll("Проверка ответа",
                () -> assertOk(response),
                () -> assertContentTypeJson(response),
                () -> assertResponseTime(response, 3000L)
        );

        CatListResponseDto actual = response.as(CatListResponseDto.class);

        assertAll("Проверка структуры, пагинации и сортировки",
                () -> assertValidStructure(actual),
                () -> assertNumberOfElementsMatchesContentSize(actual),
                () -> assertIsFirstPage(actual),
                () -> assertPageNumberEquals(actual.getPageable(), 0),
                () -> assertIsPaged(actual.getPageable()),
                () -> assertIsSorted(actual),
                () -> assertSortedByIdAsc(actual.getContent())
        );
    }

    @Nested
    @DisplayName("Проверка пагинации списка котов")
    class PaginationTest {

        @Test
        @TmsLink("TC-P-01")
        @DisplayName("page=0 возвращает первую страницу и совпадает с дефолтной пагинацией")
        void checkFirstPaginationTest() {
            Response actualResponse = step.getCatList("10", "name", "0");
            Response expectedResponse = step.getCatList();

            assertAll("Проверка HTTP ответа",
                    () -> assertOk(actualResponse),
                    () -> assertOk(expectedResponse),
                    () -> assertContentTypeJson(actualResponse),
                    () -> assertContentTypeJson(expectedResponse));

            CatListResponseDto actual = actualResponse.as(CatListResponseDto.class);
            CatListResponseDto expected = expectedResponse.as(CatListResponseDto.class);

            PageableDto actualPageable = actual.getPageable();
            PageableDto expectedPageable = expected.getPageable();

            assertAll("Проверка содержимого ответа",
                    () -> assertSameContent(actual, expected),
                    () -> assertSamePageNumber(actualPageable, expectedPageable),
                    () -> assertSamePageSize(actualPageable, expectedPageable),
                    () -> assertIsFirstPage(actual),
                    () -> assertIsPaged(actualPageable),
                    () -> assertIsNotUnpaged(actualPageable),
                    () -> assertIsNotLastPage(actual));
        }

        @Test
        @TmsLink("TC-P-02")
        @DisplayName("page=1 (вторая страница)")
        void checkSecondPaginationTest() {
            Response response = step.getCatListByPage("1");

            CatListResponseDto actual = response.as(CatListResponseDto.class);
            PageableDto pageable = actual.getPageable();

            assertAll("Проверка второй страницы",
                    () -> assertOk(response),
                    () -> assertContentTypeJson(response),
                    () -> assertPageNumberEquals(pageable, 1),
                    () -> assertIsNotFirstPage(actual),
                    () -> assertIsPaged(pageable),
                    () -> assertIsNotUnpaged(pageable)
            );
        }

        @Test
        @TmsLink("TC-P-03")
        @DisplayName("Проверка последней страницы")
        void checkLastPageTest() {
            Response firstResponse = step.getCatListByPage("0");
            CatListResponseDto firstPage = firstResponse.as(CatListResponseDto.class);
            int totalPages = firstPage.getTotalPages();
            int lastPageNumber = totalPages - 1;

            Response response = step.getCatListByPage(String.valueOf(lastPageNumber));
            CatListResponseDto actual = response.as(CatListResponseDto.class);

            assertAll("Проверка последней страницы",
                    () -> assertOk(response),
                    () -> assertContentTypeJson(response),
                    () -> assertPageNumberEquals(actual.getPageable(), lastPageNumber),
                    () -> assertIsLastPage(actual),
                    () -> assertIsNotFirstPage(actual),
                    () -> checkTotalPagesNotEmpty(actual)
            );
        }

    }

    @Nested
    @DisplayName("Проверка бизнес логики списка Котов")
    class BusinessFunctionTest {
        @BeforeEach
        void clearCatList() {
            step.clearCatList();
            //assertMultiStatus(step.deleteAllCats());
        }

        @Test
        @TmsLink("TC-F-01")
        @DisplayName("Проверка, что список котов пуст при отсутствии данных")
        void checkListEmptyTest() {
            Response response = step.getCatList();
            List<CatCreateResponseDto> actual = response.as(CatListResponseDto.class).getContent();

            assertAll("Проверка пустого списка",
                    () -> assertOk(response),
                    () -> assertTrue(actual.isEmpty(), "Список должен быть пустым")
            );
        }


        @Test
        @TmsLink("TC-F-02")
        @DisplayName("Проверка всех полей у созданного кота")
        void checkAllFieldsInCatTest() {
            CatCreateRequestDto request = randomCat();

            step.createCat(request);
            Response response = step.getCatList();
            List<CatCreateResponseDto> cats = response.as(CatListResponseDto.class).getContent();

            assertAll("Проверка структуры ответа",
                    () -> assertFalse(cats.isEmpty(), "Список не должен быть пустым"),
                    () -> assertEquals(1, cats.size(), "Должен быть создан ровно один кот")
            );

            CatCreateResponseDto actual = cats.get(0);

            // Проверка каждого поля
            assertAll("Проверка полей кота",
                    // id - целое положительное число
                    () -> assertNotNull(actual.getId(), "id не должен быть null"),
                    () -> assertTrue(actual.getId() > 0, "id должен быть больше 0"),

                    // name - непустая строка
                    () -> assertNotNull(actual.getName(), "name не должен быть null"),
                    () -> assertFalse(actual.getName().isBlank(), "name не должен быть пустым или состоять из пробелов"),


                    // age - целое неотрицательное число
                    () -> assertNotNull(actual.getAge(), "age не должен быть null"),
                    () -> assertTrue(actual.getAge() >= 0, "age должен быть >= 0"),


                    // color - строка из справочника
                    () -> assertNotNull(actual.getColor(), "color не должен быть null"),

                    // breed - непустая строка
                    () -> assertNotNull(actual.getBreed(), "breed не должен быть null"),
                    () -> assertFalse(actual.getBreed().isBlank(), "breed не должен быть пустым или состоять из пробелов"),

                    // weight - положительное число
                    () -> assertNotNull(actual.getWeight(), "weight не должен быть null"),
                    () -> assertTrue(actual.getWeight() > 0, "weight должен быть > 0"),


                    // vaccinated - логическое значение
                    () -> assertNotNull(actual.getVaccinated(), "vaccinated не должен быть null"),

                    // birthDate - формат YYYY-MM-DD, не из будущего
                    () -> assertNotNull(actual.getBirthDate(), "birthDate не должен быть null"),


                    // ownerEmail - корректный email
                    () -> assertNotNull(actual.getOwnerEmail(), "ownerEmail не должен быть null"),


                    // status - строка из справочника
                    () -> assertNotNull(actual.getStatus(), "status не должен быть null"),


                    // createdAt - формат ISO-8601 date-time
                    () -> assertNotNull(actual.getCreatedAt(), "createdAt не должен быть null"),


                    // updatedAt - формат ISO-8601 date-time, не раньше createdAt
                    () -> assertNotNull(actual.getUpdatedAt(), "updatedAt не должен быть null")

            );

        }
    }
}
