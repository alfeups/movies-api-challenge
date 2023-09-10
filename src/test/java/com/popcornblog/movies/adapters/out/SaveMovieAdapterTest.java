package com.popcornblog.movies.adapters.out;

import com.popcornblog.movies.adapters.out.entities.MovieEntity;
import com.popcornblog.movies.adapters.out.mappers.MovieEntityMapper;
import com.popcornblog.movies.adapters.out.repositories.MovieRepository;
import com.popcornblog.movies.core.domain.model.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;

public class SaveMovieAdapterTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private MovieEntityMapper entityMapper;

    @InjectMocks
    private SaveMovieAdapter saveMovieAdapter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        ReflectionTestUtils.setField(saveMovieAdapter, "movieRepository", movieRepository);
        ReflectionTestUtils.setField(saveMovieAdapter, "entityMapper", entityMapper);
    }

    @Test
    public void whenSavingMovie_thenReturnSuccess() {
        Movie inputMovie = new Movie();
        Movie savedMovie = new Movie();

        when(entityMapper.toEntity(inputMovie)).thenReturn(new MovieEntity());
        when(movieRepository.save(any())).thenReturn(new MovieEntity());
        when(entityMapper.toModel(any())).thenReturn(savedMovie);

        saveMovieAdapter.saveMovie(inputMovie);

        verify(entityMapper, times(1)).toEntity(inputMovie);
        verify(movieRepository, times(1)).save(any());
        verify(entityMapper, times(1)).toModel(any());
    }

}



