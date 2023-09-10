package com.popcornblog.movies.core.domain.usecase;

import com.popcornblog.movies.core.domain.model.Movie;
import com.popcornblog.movies.core.ports.out.GetMovieByIdAdapterPort;
import com.popcornblog.movies.core.ports.out.GetMoviesByDateAdapterPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetMovieUseCaseTest {

    @Mock
    private GetMovieByIdAdapterPort getMovieByIdAdapterPort;

    @Mock
    private GetMoviesByDateAdapterPort getMoviesByDateAdapterPort;

    @InjectMocks
    private GetMovieUseCase getMovieUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(getMovieUseCase, "getMovieByIdAdapterPort", getMovieByIdAdapterPort);
        ReflectionTestUtils.setField(getMovieUseCase, "getMoviesByDateAdapterPort", getMoviesByDateAdapterPort);
    }

    @Test
    public void whenGetMovieById_thenReturnMovie() {
        Long movieId = 1L;
        Movie expectedMovie = new Movie();
        when(getMovieByIdAdapterPort.getMovie(movieId)).thenReturn(expectedMovie);
        Movie result = getMovieUseCase.execute(movieId);
        assertEquals(expectedMovie, result);
    }

    @Test
    public void whenGetMoviesByDate_thenReturnListOfMovies() {
        LocalDate initialDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        List<Movie> expectedMovies = Arrays.asList(new Movie(), new Movie(), new Movie());

        when(getMoviesByDateAdapterPort.getMoviesBetween(initialDate, endDate)).thenReturn(expectedMovies);

        List<Movie> result = getMovieUseCase.execute(initialDate, endDate);

        assertEquals(expectedMovies.size(), result.size());
    }
}