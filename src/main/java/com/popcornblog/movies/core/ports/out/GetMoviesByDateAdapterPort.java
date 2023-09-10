package com.popcornblog.movies.core.ports.out;

import com.popcornblog.movies.core.domain.model.Movie;

import java.time.LocalDate;
import java.util.List;

public interface GetMoviesByDateAdapterPort {
    List<Movie> getMoviesBetween(LocalDate initialDate, LocalDate endDate);

}
