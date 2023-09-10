package com.popcornblog.movies.core.ports.out;

import com.popcornblog.movies.core.domain.model.Movie;

public interface GetMovieByIdAdapterPort {
    Movie getMovie(Long id);
}
