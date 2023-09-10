package com.popcornblog.movies.core.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Generated
public class Movie {

    private Long id;
    private String title;
    private LocalDate launchDate;
    private Integer rating;
    private BigDecimal revenue;

}