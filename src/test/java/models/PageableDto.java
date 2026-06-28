package models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PageableDto {
    private Integer pageNumber;
    private Integer pageSize;
    private SortDto sort;
    private Integer offset;
    private Boolean paged;
    private Boolean unpaged;
}
