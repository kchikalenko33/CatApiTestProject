package tests.catList;

import io.qameta.allure.Epic;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
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
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        @TmsLink("TC-P-01")
        @DisplayName("page=1 (вторая страница)")
        void checkSecondPaginationTest() {
            Response response = step.getCatListByPage("1");

        }
    }

    @Nested
    @DisplayName("Проверка бизнес логики списка Котов")
    class BusinessFunctionTest {
        @BeforeEach
        void clearCatList() {
            assertMultiStatus(step.deleteAllCats());
        }

        @Test
        @DisplayName("")
        void checkListEmptyTest() {
           Response response = step.getCatList();

           List<CatCreateResponseDto> actual = response.as(CatListResponseDto.class).getContent();

           assertTrue(actual.isEmpty());
        }
    }
}
