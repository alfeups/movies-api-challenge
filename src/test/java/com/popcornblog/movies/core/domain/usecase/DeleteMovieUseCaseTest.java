package com.popcornblog.movies.core.domain.usecase;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import com.popcornblog.movies.core.domain.exceptions.MovieNotFoundException;
import com.popcornblog.movies.core.domain.model.Message;
import com.popcornblog.movies.core.ports.out.DeleteMovieAdapterPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class DeleteMovieUseCaseTest {

    @Mock
    private DeleteMovieAdapterPort deleteMovieAdapterPort;

    @InjectMocks
    private DeleteMovieUseCase deleteMovieUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(deleteMovieUseCase, "deleteMovieAdapterPort", deleteMovieAdapterPort);
    }

    @Test
    public void whenMovieDeletedSuccessfully_thenReturnSuccessMessage() {
        Long movieId = 1L;

        when(deleteMovieAdapterPort.deleteMovie(movieId)).thenReturn(1L);
        Message result = deleteMovieUseCase.execute(movieId);

        assertEquals("The movie ID: 1 was deleted successfully.", result.getMessage());
    }

    @Test
    public void whenMovieNotFound_thenThrowMovieNotFoundException() {
        Long movieId = 1L;

        when(deleteMovieAdapterPort.deleteMovie(movieId)).thenThrow(new MovieNotFoundException(movieId));

        assertThrows(MovieNotFoundException.class, () -> deleteMovieUseCase.execute(movieId));
    }
}
