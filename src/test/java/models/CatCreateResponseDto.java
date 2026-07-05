package models;

import lombok.*;
import testData.Color;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
public class CatCreateResponseDto {
    private Integer id;
    private String name;
    private Integer age;
    private Color color;
    private String breed;
    private Double weight;
    private Boolean vaccinated;
    private LocalDate birthDate;
    private String ownerEmail;
    private String status;
    private LocalDate createdAt;
    private LocalDate updatedAt;

}
