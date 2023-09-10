package com.popcornblog.movies.core.domain.exceptions;

public class MovieNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public MovieNotFoundException(String message) {
        super(message);
    }

    public MovieNotFoundException(Long movieId) {
        this(String.format("There is no movie record with code %d", movieId));
    }
}
