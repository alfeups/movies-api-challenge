package com.popcornblog.movies.adapters.out.mappers;

import com.popcornblog.movies.adapters.out.entities.MovieEntity;
import com.popcornblog.movies.core.domain.model.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieEntityMapper {

    Movie toModel(MovieEntity entity);
    MovieEntity toEntity(Movie model);
}
