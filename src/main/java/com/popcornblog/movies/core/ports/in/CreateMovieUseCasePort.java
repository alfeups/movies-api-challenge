package com.popcornblog.movies.core.ports.in;

import com.popcornblog.movies.core.domain.model.Movie;

public interface CreateMovieUseCasePort {
    Movie execute(Movie movie);
}
