package com.popcornblog.movies.adapters.in.web.exceptions.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class Problem {


    private Integer status;
    private String type;
    private String title;
    private String userMessage;
    private String detail;
    private LocalDateTime timestamp;
}
