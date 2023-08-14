package com.example.myworkmap.model.dto.request;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AdminPageRequestDto {
    @Builder.Default
    private int offSet = 0;
    @Builder.Default
    private int limit = 10;
    @Builder.Default
    private SortRequestDto sort = new SortRequestDto();
}
