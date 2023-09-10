package com.popcornblog.movies.core.domain.usecase;

import com.popcornblog.movies.core.domain.model.Movie;
import com.popcornblog.movies.core.ports.out.GetMovieByIdAdapterPort;
import com.popcornblog.movies.core.ports.out.SaveMovieAdapterPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateMovieUseCaseTest {

    @Mock
    private GetMovieByIdAdapterPort getMovieByIdAdapterPort;

    @Mock
    private SaveMovieAdapterPort saveMovieAdapterPort;

    @InjectMocks
    private UpdateMovieUseCase updateMovieUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(updateMovieUseCase, "getMovieByIdAdapterPort", getMovieByIdAdapterPort);
        ReflectionTestUtils.setField(updateMovieUseCase, "saveMovieAdapterPort", saveMovieAdapterPort);
    }

    @Test
    public void whenExecuteWithValidMovieIdAndMovieRequest_thenReturnUpdatedMovie() {
        Long movieId = 1L;
        Movie movieRequest = new Movie();
        Movie existingMovie = new Movie();
        Movie updatedMovie = new Movie();

        when(getMovieByIdAdapterPort.getMovie(movieId)).thenReturn(existingMovie);

        when(saveMovieAdapterPort.saveMovie(any())).thenReturn(updatedMovie);

        Movie result = updateMovieUseCase.execute(movieId, movieRequest);

        assertEquals(updatedMovie, result);
        verify(getMovieByIdAdapterPort, times(1)).getMovie(movieId);
        verify(saveMovieAdapterPort, times(1)).saveMovie(any());
    }
}
