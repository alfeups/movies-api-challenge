package com.popcornblog.movies.core.ports.in;

import com.popcornblog.movies.core.domain.model.Message;

public interface DeleteMovieUseCasePort {
    Message execute(Long movieId);
}
