package com.example.myworkmap.model.dto.request;

import lombok.*;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SortRequestDto {
    @Builder.Default
    private String field = "email";
    @Builder.Default
    private Sort.Direction type = Sort.Direction.ASC;
}
