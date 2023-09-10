package com.popcornblog.movies.adapters.in.web.mappers;

import com.popcornblog.movies.adapters.in.web.dto.response.MessageDto;
import com.popcornblog.movies.core.domain.model.Message;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageDtoMapper {

    MessageDto toDto(Message message);

    Message toModel(MessageDto messageDto);
}
