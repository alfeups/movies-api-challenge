package com.popcornblog.movies.core.ports.out;

import com.popcornblog.movies.core.domain.model.Movie;

public interface SaveMovieAdapterPort {
    Movie saveMovie(Movie movie);
}
