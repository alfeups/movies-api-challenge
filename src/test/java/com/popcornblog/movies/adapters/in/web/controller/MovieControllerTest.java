package com.popcornblog.movies.adapters.in.web.controller;

import com.popcornblog.movies.adapters.in.web.dto.request.MovieRequestDto;
import com.popcornblog.movies.adapters.in.web.dto.response.MessageDto;
import com.popcornblog.movies.adapters.in.web.dto.response.MovieResponseDto;
import com.popcornblog.movies.adapters.in.web.mappers.MessageDtoMapper;
import com.popcornblog.movies.adapters.in.web.mappers.MovieDtoMapper;
import com.popcornblog.movies.adapters.out.entities.MovieEntity;
import com.popcornblog.movies.core.domain.model.Message;
import com.popcornblog.movies.core.domain.model.Movie;
import com.popcornblog.movies.core.ports.in.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
public class MovieControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CreateMovieUseCasePort createMovieUseCasePort;

    @Mock
    private GetMovieByIdUseCasePort getMovieByIdUseCasePort;

    @Mock
    private GetMoviesByDateUseCasePort getMoviesByDateUseCasePort;

    @Mock
    private UpdateMovieUseCasePort updateMovieUseCasePort;

    @Mock
    private DeleteMovieUseCasePort deleteMovieUseCasePort;

    @Mock
    private MovieDtoMapper dtoMapper;

    @Mock
    private MessageDtoMapper messageDtoMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(new MovieController(
                createMovieUseCasePort,
                getMovieByIdUseCasePort,
                getMoviesByDateUseCasePort,
                updateMovieUseCasePort,
                deleteMovieUseCasePort,
                dtoMapper,
                messageDtoMapper
        )).build();
    }

    @Test
    void whenGetAllMoviesBetweenDates_ThenReturnMovieResponseDtoList() throws Exception {

        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2022, 12, 31);
        List<Movie> movies = Arrays.asList(
                new Movie(1L, "Movie 1", LocalDate.of(2022, 5, 15), 8, new BigDecimal("1200000.60")),
                new Movie(2L, "Movie 2", LocalDate.of(2022, 7, 20),10, new BigDecimal("2200000.60"))
        );

        List<MovieResponseDto> movieDtos = Arrays.asList(
                new MovieResponseDto(1L, "Movie 1", LocalDate.of(2022, 5, 15), 8, new BigDecimal("1200000.60")),
                new MovieResponseDto(2L, "Movie 2", LocalDate.of(2022,07,20),10, new BigDecimal("2200000.60"))
        );

        when(getMoviesByDateUseCasePort.execute(startDate, endDate)).thenReturn(movies);
        when(dtoMapper.toDto(any(Movie.class))).thenReturn(movieDtos.get(0), movieDtos.get(1));

        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.get("/v1/api/movies")
                        .param("start_date", "2022-01-01")
                        .param("end_date", "2022-12-31")
                        .accept(MediaType.APPLICATION_JSON)
        );

        MvcResult mvcResult = result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        assertThat(responseBody).contains("Movie 1", "Movie 2", "2022-05-15", "2022-07-20");
    }

    @Test
    void whenGetMovieById_ThenReturnMovieResponseDto() throws Exception {
        Movie movie = buildMovie();
        MovieResponseDto responseDto = buildMovieResponseDto();

        when(getMovieByIdUseCasePort.execute(1L)).thenReturn(movie);
        when(dtoMapper.toDto(any(Movie.class))).thenReturn(responseDto);

        mockMvc.perform(get("/v1/api/movies/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Sample Movie"))
                .andExpect(jsonPath("$.launch_date").value("2022-05-15"));
    }

    @Test
    public void whenCreateMovie_ThenReturnCreatedMovieResponseDto() throws Exception {
        MovieRequestDto requestDto = buildMovieRequestDto();
        Movie movie = buildMovie();
        MovieResponseDto responseDto = buildMovieResponseDto();

        when(dtoMapper.toModel(requestDto)).thenReturn(movie);
        when(createMovieUseCasePort.execute(movie)).thenReturn(movie);
        when(dtoMapper.toDto(movie)).thenReturn(responseDto);

        mockMvc.perform(post("/v1/api/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Sample Movie\",\"launch_date\":\"2022-05-15\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Sample Movie"))
                .andExpect(jsonPath("$.launch_date").value("2022-05-15"));
    }

    @Test
    void whenUpdateMovie_ThenReturnUpdatedMovieResponseDto() throws Exception {
        Movie movie = buildMovie();
        MovieRequestDto requestDto = buildMovieRequestDto();
        MovieResponseDto responseDto = buildMovieResponseDto();
        responseDto.setTitle("Updated Movie");
        responseDto.setLaunchDate(LocalDate.parse("2023-06-20"));

        when(updateMovieUseCasePort.execute(eq(1L), any())).thenReturn(movie);
        when(dtoMapper.toDto(movie)).thenReturn(responseDto);

        mockMvc.perform(patch("/v1/api/movies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated Movie\",\"launch_date\":\"2023-06-20\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Updated Movie"))
                .andExpect(jsonPath("$.launch_date").value("2023-06-20"));
    }

    @Test
    void whenDeleteMovie_ThenReturnMessageDto() throws Exception {
        MessageDto messageDto = new MessageDto();
        messageDto.setMessage("Movie ID: 1 was deleted successfully.");

        Message deleteMessage = new Message(); // Assuming this is how you create a Message object
        when(deleteMovieUseCasePort.execute(1L)).thenReturn(deleteMessage);
        when(messageDtoMapper.toDto(deleteMessage)).thenReturn(messageDto);

        mockMvc.perform(delete("/v1/api/movies/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Movie ID: 1 was deleted successfully."));
    }

    private static MovieResponseDto buildMovieResponseDto() {
        MovieResponseDto responseDto = new MovieResponseDto();
        responseDto.setId(1L);
        responseDto.setTitle("Sample Movie");
        responseDto.setLaunchDate(LocalDate.of(2022, 5, 15));
        return responseDto;
    }

    private static Movie buildMovie() {
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Sample Movie");
        movie.setLaunchDate(LocalDate.of(2022, 5, 15));
        return movie;
    }

    private static MovieRequestDto buildMovieRequestDto() {
        MovieRequestDto requestDto = new MovieRequestDto();
        requestDto.setTitle("Sample Movie");
        requestDto.setLaunchDate(LocalDate.of(2022, 5, 15));
        return requestDto;
    }

}