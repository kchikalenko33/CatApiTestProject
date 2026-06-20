package models;

import lombok.*;
import testData.Breed;
import testData.Color;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CatCreateRequestDto {
    private String name;
    private Integer age;
    private Color color;
    private Breed breed;
    private Double weight;
    private Boolean vaccinated;
    private LocalDate birthDate;
    private String ownerEmail;
}
