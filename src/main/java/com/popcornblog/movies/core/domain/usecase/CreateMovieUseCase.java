package com.popcornblog.movies.core.domain.usecase;

import com.popcornblog.movies.core.domain.model.Movie;
import com.popcornblog.movies.core.ports.out.SaveMovieAdapterPort;
import com.popcornblog.movies.core.ports.in.CreateMovieUseCasePort;


public class CreateMovieUseCase implements CreateMovieUseCasePort {

    private final SaveMovieAdapterPort saveMovieAdapterPort;

    public CreateMovieUseCase(SaveMovieAdapterPort saveMovieAdapterPort){
        this.saveMovieAdapterPort = saveMovieAdapterPort;
    }

    @Override
    public Movie execute(Movie movie) {
        return saveMovieAdapterPort.saveMovie(movie);
    }
}