package com.popcornblog.movies.adapters.in.web.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MovieResponseDto {

    private Long id;
    private String title;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate launchDate;
    private Integer rating;
    private BigDecimal revenue;
}
