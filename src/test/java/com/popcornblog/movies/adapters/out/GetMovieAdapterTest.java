package com.popcornblog.movies.adapters.out;

import com.popcornblog.movies.adapters.out.entities.MovieEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import com.popcornblog.movies.adapters.out.mappers.MovieEntityMapper;
import com.popcornblog.movies.adapters.out.repositories.MovieRepository;
import com.popcornblog.movies.core.domain.exceptions.MovieNotFoundException;
import com.popcornblog.movies.core.domain.model.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetMovieAdapterTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private MovieEntityMapper entityMapper;

    @InjectMocks
    private GetMovieAdapter getMovieAdapter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        ReflectionTestUtils.setField(getMovieAdapter, "movieRepository", movieRepository);
        ReflectionTestUtils.setField(getMovieAdapter, "entityMapper", entityMapper);
    }


    @Test
    public void whenGetMovieExistsThenReturnMovie() {
        Long movieId = 1L;
        MovieEntity expectedMovieEntity = getMovieEntity(movieId);
        Movie expectedMovie = getMovie(movieId);

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(expectedMovieEntity));
        when(entityMapper.toModel(expectedMovieEntity)).thenReturn(expectedMovie);

        Movie actualMovie = getMovieAdapter.getMovie(movieId);

        assertEquals(expectedMovie, actualMovie);
        verify(movieRepository, times(1)).findById(movieId);
        verify(entityMapper, times(1)).toModel(expectedMovieEntity);
    }

    @Test
    public void whenGetMovieNotFoundThenThrowMovieNotFoundException() {
        Long movieId = 1L;
        when(movieRepository.findById(movieId)).thenReturn(Optional.empty());

        assertThrows(MovieNotFoundException.class, () -> getMovieAdapter.getMovie(movieId));
        verify(movieRepository, times(1)).findById(movieId);
    }


    @Test
    public void whenGetMoviesBetweenValidDateRangeThenReturnListOfMovies() {
        LocalDate initialDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);

        List<MovieEntity> expectedMovieEntities = getMovieEntities();
        List<Movie> expectedMovies = getMovies();

        when(movieRepository.findByLaunchDateBetween(initialDate, endDate)).thenReturn(expectedMovieEntities);
        when(entityMapper.toModel(any())).thenReturn(new Movie());

        List<Movie> actualMovies = getMovieAdapter.getMoviesBetween(initialDate, endDate);

        assertEquals(expectedMovies.size(), actualMovies.size());
        verify(movieRepository, times(1)).findByLaunchDateBetween(initialDate, endDate);
        verify(entityMapper, times(expectedMovies.size())).toModel(any());
    }

    private static List<Movie> getMovies() {
        List<Movie> expectedMovies = Arrays.asList(new Movie(), new Movie(), new Movie());
        return expectedMovies;
    }

    private static List<MovieEntity> getMovieEntities() {
        List<MovieEntity> expectedMovieEntities = Arrays.asList(new MovieEntity(), new MovieEntity(), new MovieEntity());
        return expectedMovieEntities;
    }


    private static Movie getMovie(Long movieId) {
        Movie expectedMovie = new Movie();
        expectedMovie.setId(movieId);
        return expectedMovie;
    }

    private static MovieEntity getMovieEntity(Long movieId) {
        MovieEntity expectedMovieEntity = new MovieEntity();
        expectedMovieEntity.setId(movieId);
        return expectedMovieEntity;
    }
}