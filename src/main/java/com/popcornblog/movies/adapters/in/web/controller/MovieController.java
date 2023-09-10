package com.popcornblog.movies.adapters.in.web.controller;

import com.popcornblog.movies.adapters.in.web.dto.request.MovieRequestDto;
import com.popcornblog.movies.adapters.in.web.dto.request.MovieUpdateDto;
import com.popcornblog.movies.adapters.in.web.dto.response.MessageDto;
import com.popcornblog.movies.adapters.in.web.dto.response.MovieResponseDto;
import com.popcornblog.movies.adapters.in.web.mappers.MessageDtoMapper;
import com.popcornblog.movies.adapters.in.web.mappers.MovieDtoMapper;
import com.popcornblog.movies.core.ports.in.*;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("v1/api/movies")
public class MovieController {

    private final CreateMovieUseCasePort createMovieUseCasePort;
    private final GetMovieByIdUseCasePort getMovieByIdUseCasePort;
    private final GetMoviesByDateUseCasePort getMoviesByDateUseCasePort;
    private final UpdateMovieUseCasePort updateMovieUseCasePort;
    private final DeleteMovieUseCasePort deleteMovieUseCasePort;

    private final MovieDtoMapper dtoMapper;
    private final MessageDtoMapper messageDtoMapper;

    public MovieController(CreateMovieUseCasePort createMovieUseCasePort,
                           GetMovieByIdUseCasePort getMovieByIdUseCasePort,
                           GetMoviesByDateUseCasePort getMoviesByDateUseCasePort,
                           UpdateMovieUseCasePort updateMovieUseCasePort,
                           DeleteMovieUseCasePort deleteMovieUseCasePort,
                           MovieDtoMapper dtoMapper,
                           MessageDtoMapper messageDtoMapper) {
        this.createMovieUseCasePort = createMovieUseCasePort;
        this.getMovieByIdUseCasePort = getMovieByIdUseCasePort;
        this.getMoviesByDateUseCasePort = getMoviesByDateUseCasePort;
        this.updateMovieUseCasePort = updateMovieUseCasePort;
        this.deleteMovieUseCasePort = deleteMovieUseCasePort;
        this.dtoMapper = dtoMapper;
        this.messageDtoMapper = messageDtoMapper;
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<MovieResponseDto> getAllMoviesBetweenDates(
            @RequestParam(name = "start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "end_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return getMoviesByDateUseCasePort.execute(startDate, endDate)
                .stream()
                .map(dtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{movieId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public MovieResponseDto getMovieById(@PathVariable Long movieId) {
        return this.dtoMapper.toDto(getMovieByIdUseCasePort.execute(movieId));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public MovieResponseDto createMovie(@Valid @RequestBody MovieRequestDto requestDto) {
        return this.dtoMapper.toDto(
                createMovieUseCasePort.execute(
                        this.dtoMapper.toModel(requestDto)));
    }

    @PatchMapping(value ="/{movieId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MovieResponseDto updateMovie(@PathVariable Long movieId, @Valid @RequestBody MovieUpdateDto movie) {
        return this.dtoMapper.toDto(
                updateMovieUseCasePort.execute(
                        movieId, this.dtoMapper.updateToModel(movie)));
    }

    @DeleteMapping("/{movieId}")
    public MessageDto deleteMovie(@PathVariable Long movieId) {
        return this.messageDtoMapper.toDto(deleteMovieUseCasePort.execute(movieId));
    }

}