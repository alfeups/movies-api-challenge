package com.popcornblog.movies.adapters.out;

import com.popcornblog.movies.adapters.out.mappers.MovieEntityMapper;
import com.popcornblog.movies.adapters.out.repositories.MovieRepository;
import com.popcornblog.movies.core.domain.exceptions.EntityNotFoundException;
import com.popcornblog.movies.core.domain.exceptions.MovieNotFoundException;
import com.popcornblog.movies.core.domain.model.Movie;
import com.popcornblog.movies.core.ports.out.GetMovieByIdAdapterPort;
import com.popcornblog.movies.core.ports.out.GetMoviesByDateAdapterPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class GetMovieAdapter implements GetMovieByIdAdapterPort, GetMoviesByDateAdapterPort {

    private final MovieRepository movieRepository;
    private final MovieEntityMapper entityMapper;

    @Override
    public Movie getMovie(Long movieId) {
        return this.entityMapper.toModel(
                this.movieRepository.findById(movieId)
                        .orElseThrow( () -> new MovieNotFoundException(movieId)));
    }

    @Override
    public List<Movie> getMoviesBetween(LocalDate initialDate, LocalDate endDate) {
        return this.movieRepository.findByLaunchDateBetween(initialDate, endDate)
                .stream()
                .map(entityMapper::toModel)
                .collect(Collectors.toList());
    }
}
