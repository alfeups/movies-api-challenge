package com.popcornblog.movies.core.ports.in;

import com.popcornblog.movies.core.domain.model.Movie;

import java.time.LocalDate;
import java.util.List;

public interface GetMoviesByDateUseCasePort {

    List<Movie> execute(LocalDate initialDate, LocalDate endDate);
}
