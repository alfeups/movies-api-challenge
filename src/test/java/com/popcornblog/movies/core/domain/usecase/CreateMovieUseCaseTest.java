package com.popcornblog.movies.core.domain.usecase;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import com.popcornblog.movies.core.domain.model.Movie;
import com.popcornblog.movies.core.ports.out.SaveMovieAdapterPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class CreateMovieUseCaseTest {

    @Mock
    private SaveMovieAdapterPort saveMovieAdapterPort;

    @InjectMocks
    private CreateMovieUseCase createMovieUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(createMovieUseCase, "saveMovieAdapterPort", saveMovieAdapterPort);
    }

    @Test
    public void whenExecute_thenReturAMovie() {
        Movie inputMovie = new Movie();
        Movie savedMovie = new Movie();

        when(saveMovieAdapterPort.saveMovie(inputMovie)).thenReturn(savedMovie);

        Movie result = createMovieUseCase.execute(inputMovie);

        assertEquals(savedMovie, result);
    }
}
