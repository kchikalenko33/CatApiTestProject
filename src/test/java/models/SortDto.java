package models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SortDto {
    private Boolean empty;
    private Boolean unsorted;
    private Boolean sorted;
}
