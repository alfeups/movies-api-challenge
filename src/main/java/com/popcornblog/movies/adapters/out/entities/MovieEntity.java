package com.popcornblog.movies.adapters.out.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@NoArgsConstructor
@Data
@Entity
@Table(name = "movies")
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "launch_date", nullable = false)
    private LocalDate launchDate;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "revenue", nullable = false)
    private BigDecimal revenue;

    public MovieEntity(Long id, String title, LocalDate launchDate, Integer rating, BigDecimal revenue) {
        this.id = id;
        this.title = title;
        this.launchDate = launchDate;
        this.rating = rating;
        this.revenue = revenue;
    }
}