package tests.catList;

import io.restassured.response.Response;
import models.CatCreateResponseDto;
import models.CatListResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import steps.CrudCatStep;

import java.util.Comparator;
import java.util.List;

import static assertions.catList.CatListHttpAssertions.assertOk200Fluent;
import static org.junit.jupiter.api.Assertions.*;

public class GetCatListTest {
    private final CrudCatStep step = new CrudCatStep();

    @Test
    @DisplayName("GET cats без параметров")
    void getAllCatsTest() {
        Response response = step.getCatList();

        CatListResponseDto catList = response.as(CatListResponseDto.class);

        List<CatCreateResponseDto> expected = catList.getContent().stream()
                .sorted(Comparator.comparing(CatCreateResponseDto::getName))
                .toList();

        List<CatCreateResponseDto> actual = catList.getContent();


        assertAll("Проверки списка котов",
                () -> assertOk200Fluent(response),
                () -> assertNotNull(catList),
                () -> assertNotNull(catList.getContent()),
                () -> assertEquals(catList.getNumberOfElements(), catList.getContent().size()),
                () -> assertEquals(expected, actual),
                () -> assertNotNull(catList.getTotalElements()), // todo запросить полный список без param и сравнить
                () -> assertNotNull(catList.getTotalPages()), // тоже самое
                () -> assertTrue(catList.getPageable().getPaged()),
                () -> assertTrue(catList.getSort().getSorted()));

    }

    @Test
    @DisplayName("GET cats с параметрами page = 0, size = 1, sort = name")
    void getAllCatsWithParamTest() {
        Response responseWithParam = step.getCatList("10", "name", "0");
        Response response = step.getCatList();

        assertEquals(responseWithParam.getBody().asString(),
                response.getBody().asString(), "Ответы должны быть идентичными");
    }

    @Test
    @DisplayName("Получение первой страницы с сортировкой по id")
    void getAllCatsByIdTest() {
        Response response = step.getCatList("10", "id", "0");
        CatListResponseDto catList = response.as(CatListResponseDto.class);

        assertEquals(200, response.getStatusCode());
        assertNotNull(catList);
        assertNotNull(catList.getContent());
        assertEquals(catList.getNumberOfElements(), catList.getContent().size());
    }
}
