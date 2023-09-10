package com.popcornblog.movies.core.domain.usecase;

import com.popcornblog.movies.core.domain.model.Movie;
import com.popcornblog.movies.core.domain.utils.ObjectUpdater;
import com.popcornblog.movies.core.ports.in.UpdateMovieUseCasePort;
import com.popcornblog.movies.core.ports.out.GetMovieByIdAdapterPort;
import com.popcornblog.movies.core.ports.out.SaveMovieAdapterPort;

public class UpdateMovieUseCase implements UpdateMovieUseCasePort {

    private final GetMovieByIdAdapterPort getMovieByIdAdapterPort;
    private final SaveMovieAdapterPort saveMovieAdapterPort;

    public UpdateMovieUseCase(GetMovieByIdAdapterPort getMovieByIdAdapterPort, SaveMovieAdapterPort saveMovieAdapterPort) {
        this.getMovieByIdAdapterPort = getMovieByIdAdapterPort;
        this.saveMovieAdapterPort = saveMovieAdapterPort;
    }


    @Override
    public Movie execute(Long movieId, Movie movieRequest) {
        var movieEntity = this.getMovieByIdAdapterPort.getMovie(movieId);
        ObjectUpdater.updateNonNullProperties(movieRequest, movieEntity);
        return this.saveMovieAdapterPort.saveMovie(movieEntity);
    }

}
