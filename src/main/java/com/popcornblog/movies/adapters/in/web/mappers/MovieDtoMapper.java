package com.popcornblog.movies.adapters.in.web.mappers;

import com.popcornblog.movies.adapters.in.web.dto.request.MovieRequestDto;
import com.popcornblog.movies.adapters.in.web.dto.request.MovieUpdateDto;
import com.popcornblog.movies.adapters.in.web.dto.response.MessageDto;
import com.popcornblog.movies.adapters.in.web.dto.response.MovieResponseDto;
import com.popcornblog.movies.core.domain.model.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieDtoMapper {


    Movie toModel(MovieRequestDto requestDto);

    MovieResponseDto toDto(Movie requestDto);

    Movie updateToModel(MovieUpdateDto updateDto);



}
