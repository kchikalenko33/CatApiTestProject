package tests.catCreate;

import db.entity.CatEntity;
import db.repository.CatRepository;
import io.restassured.response.Response;
import models.CatCreateRequestDto;
import models.CatCreateResponseDto;
import org.junit.jupiter.api.Test;
import steps.CrudCatStep;
import testData.CatGenerator;
import tests.BaseDbTest;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static utils.CatGenerator.randomCat;

public class CatCreateTest extends BaseDbTest {
    private final CrudCatStep step = new CrudCatStep();

    @Test
    void createCatTest() {
        CatCreateRequestDto cat = CatGenerator.createCat();

        Response response = step.createCat(cat);
        assertEquals(201, response.statusCode());
        assertNotNull(response.path("id"));
    }

    @Test
    void createCatAndAssertDbTest() throws SQLException {
        CatCreateRequestDto cat = randomCat();

        Response response = step.createCat(cat);
        Integer id = response.as(CatCreateResponseDto.class).getId();

        CatRepository catRepository = new CatRepository();

        CatEntity catDb = catRepository.getCatById(id);

        assertNotNull(catDb);

        assertAll(
                () -> assertEquals(id, catDb.id()),
                () -> assertEquals(cat.getName(), catDb.name()),
                () -> assertEquals(cat.getAge(), catDb.age()),
                () -> assertEquals(cat.getColor(), catDb.color()),
                () -> assertNotNull(catDb.breedId()),
                () -> assertEquals(cat.getWeight(), catDb.weight()),
                () -> assertEquals(cat.getVaccinated(), catDb.vaccinated()),
                () -> assertEquals(cat.getBirthDate(), catDb.birthDate()),
                () -> assertNotNull(catDb.ownerId())
        );
    }
}
