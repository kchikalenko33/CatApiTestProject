package models;

import lombok.*;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CatListResponseDto that = (CatListResponseDto) o;
        return Objects.equals(content, that.content) && Objects.equals(pageable, that.pageable)
                && Objects.equals(last, that.last) && Objects.equals(totalElements, that.totalElements)
                && Objects.equals(totalPages, that.totalPages) && Objects.equals(size, that.size)
                && Objects.equals(number, that.number) && Objects.equals(first, that.first)
                && Objects.equals(sort, that.sort) && Objects.equals(numberOfElements, that.numberOfElements)
                && Objects.equals(empty, that.empty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, pageable, last, totalElements, totalPages, size, number,
                first, sort, numberOfElements, empty);
    }
}
