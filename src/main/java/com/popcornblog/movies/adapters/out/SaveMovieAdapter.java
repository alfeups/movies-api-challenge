package com.popcornblog.movies.adapters.out;

import com.popcornblog.movies.adapters.out.mappers.MovieEntityMapper;
import com.popcornblog.movies.adapters.out.repositories.MovieRepository;
import com.popcornblog.movies.core.domain.model.Movie;
import com.popcornblog.movies.core.ports.out.SaveMovieAdapterPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SaveMovieAdapter implements SaveMovieAdapterPort {

    private final MovieRepository movieRepository;
    private final MovieEntityMapper entityMapper;

    @Override
    public Movie saveMovie(Movie movie) {
        return this.entityMapper.toModel(
                this.movieRepository.save(
                        this.entityMapper.toEntity(movie)));
    }
}
