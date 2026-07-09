package db.entity;

import testData.Color;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CatEntity (Integer id, String name, Integer age, Color color, Integer breedId, Integer ownerId,
                         Double weight, Boolean vaccinated, LocalDate birthDate, String status, LocalDateTime createdAt,
                         LocalDateTime updatedAt) {

}
