package com.popcornblog.movies.adapters.out;

import com.popcornblog.movies.adapters.out.entities.MovieEntity;
import com.popcornblog.movies.adapters.out.repositories.MovieRepository;
import com.popcornblog.movies.core.domain.exceptions.MovieNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteMovieAdapterTest {

    @InjectMocks
    private DeleteMovieAdapter deleteMovieAdapter;

    @Mock
    private MovieRepository movieRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        ReflectionTestUtils.setField(deleteMovieAdapter, "movieRepository", movieRepository);
    }

    @Test
    void whenDeleteMovie_ThenReturnMovieId() {
        MovieEntity movieEntity = buildMovieEntity();
        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(movieEntity));
        Long deletedMovieId = deleteMovieAdapter.deleteMovie(1L);
        verify(movieRepository).delete(movieEntity);
        assertEquals(1L, deletedMovieId);
    }

    @Test
    void whenDeleteNonExistingMovie_ThenThrowMovieNotFoundException() {
        assertThrows(MovieNotFoundException.class, () -> deleteMovieAdapter.deleteMovie(10L));
        verify(movieRepository, never()).delete(any());
    }

    private MovieEntity buildMovieEntity(){
        return MovieEntity.builder()
                .id(1L)
                .title("MOVIE")
                .rating(10)
                .revenue(BigDecimal.valueOf(10000000))
                .build();
    }
}
