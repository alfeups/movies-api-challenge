package com.popcornblog.movies.core.ports.in;

import com.popcornblog.movies.core.domain.model.Movie;

public interface GetMovieByIdUseCasePort {
    Movie execute(Long id);
}
