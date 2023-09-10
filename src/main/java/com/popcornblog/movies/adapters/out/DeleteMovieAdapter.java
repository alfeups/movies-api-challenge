package com.popcornblog.movies.adapters.out;

import com.popcornblog.movies.adapters.out.repositories.MovieRepository;
import com.popcornblog.movies.core.domain.exceptions.BusinessException;
import com.popcornblog.movies.core.domain.exceptions.MovieNotFoundException;
import com.popcornblog.movies.core.ports.out.DeleteMovieAdapterPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DeleteMovieAdapter implements DeleteMovieAdapterPort {

    private final MovieRepository movieRepository;

    @Override
    public Long deleteMovie(Long movieId) {
        var entity = this.movieRepository.findById(movieId)
                .orElseThrow( () -> new MovieNotFoundException(movieId));
        this.movieRepository.delete(entity);
        return movieId;
    }
}
