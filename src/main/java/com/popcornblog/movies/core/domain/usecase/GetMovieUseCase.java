package com.popcornblog.movies.core.domain.usecase;

import com.popcornblog.movies.core.domain.model.Movie;
import com.popcornblog.movies.core.ports.in.GetMovieByIdUseCasePort;
import com.popcornblog.movies.core.ports.in.GetMoviesByDateUseCasePort;
import com.popcornblog.movies.core.ports.out.GetMovieByIdAdapterPort;
import com.popcornblog.movies.core.ports.out.GetMoviesByDateAdapterPort;

import java.time.LocalDate;
import java.util.List;

public class GetMovieUseCase implements GetMovieByIdUseCasePort, GetMoviesByDateUseCasePort {

    private final GetMovieByIdAdapterPort getMovieByIdAdapterPort;
    private final GetMoviesByDateAdapterPort getMoviesByDateAdapterPort;

    public GetMovieUseCase(GetMovieByIdAdapterPort getMovieByIdAdapterPort, GetMoviesByDateAdapterPort getMoviesByDateAdapterPort) {
        this.getMovieByIdAdapterPort = getMovieByIdAdapterPort;
        this.getMoviesByDateAdapterPort = getMoviesByDateAdapterPort;
    }


    @Override
    public Movie execute(Long id) {
        return getMovieByIdAdapterPort.getMovie(id);
    }

    @Override
    public List<Movie> execute(LocalDate initialDate, LocalDate endDate) {
        return getMoviesByDateAdapterPort.getMoviesBetween(initialDate, endDate);
    }
}
