package com.popcornblog.movies.core.ports.in;

import com.popcornblog.movies.core.domain.model.Movie;

public interface UpdateMovieUseCasePort {
    Movie execute(Long movieId, Movie movie);
}
