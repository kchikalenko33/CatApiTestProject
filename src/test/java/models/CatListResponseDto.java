package models;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CatListResponseDto {
    private List<CatCreateResponseDto> content;
    private PageableDto pageable;
    private Boolean last;
    private Integer totalElements;
    private Integer totalPages;
    private Integer size;
    private Integer number;
    private Boolean first;
    private SortDto sort;
    private Integer numberOfElements;
    private Boolean empty;
}
