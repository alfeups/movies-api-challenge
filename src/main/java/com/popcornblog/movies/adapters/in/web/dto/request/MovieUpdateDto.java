package com.popcornblog.movies.adapters.in.web.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.*;
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
public class MovieUpdateDto {

    private String title;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate launchDate;
    @Min(value = 0, message = "Rating must be a positive integer")
    @Max(value = 10, message = "Rating must be between 0 and 10")
    private Integer rating;
    private BigDecimal revenue;
}
