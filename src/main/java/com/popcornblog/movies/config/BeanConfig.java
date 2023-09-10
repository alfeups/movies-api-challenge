package com.popcornblog.movies.config;

import com.popcornblog.movies.core.domain.usecase.CreateMovieUseCase;
import com.popcornblog.movies.core.domain.usecase.DeleteMovieUseCase;
import com.popcornblog.movies.core.domain.usecase.GetMovieUseCase;
import com.popcornblog.movies.core.domain.usecase.UpdateMovieUseCase;
import com.popcornblog.movies.core.ports.in.*;
import com.popcornblog.movies.core.ports.out.DeleteMovieAdapterPort;
import com.popcornblog.movies.core.ports.out.GetMovieByIdAdapterPort;
import com.popcornblog.movies.core.ports.out.GetMoviesByDateAdapterPort;
import com.popcornblog.movies.core.ports.out.SaveMovieAdapterPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {


    @Bean
    public CreateMovieUseCasePort createMovieUseCasePort(SaveMovieAdapterPort saveMovieAdapterPort){
        return new CreateMovieUseCase(saveMovieAdapterPort);
    }

    @Bean
    public GetMoviesByDateUseCasePort getMoviesByDateUseCasePort(GetMovieByIdAdapterPort getMovieByIdAdapterPort, GetMoviesByDateAdapterPort getMoviesByDateAdapterPort ){
        return new GetMovieUseCase(getMovieByIdAdapterPort, getMoviesByDateAdapterPort);
    }

    @Bean
    public GetMovieByIdUseCasePort getMovieByIdUseCasePort(GetMovieByIdAdapterPort getMovieByIdAdapterPort, GetMoviesByDateAdapterPort getMoviesByDateAdapterPort ){
        return new GetMovieUseCase(getMovieByIdAdapterPort, getMoviesByDateAdapterPort);
    }

    @Bean
    public UpdateMovieUseCasePort updateMovieUseCasePort(GetMovieByIdAdapterPort getMovieByIdAdapterPort, SaveMovieAdapterPort saveMovieAdapterPort){
        return new UpdateMovieUseCase(getMovieByIdAdapterPort, saveMovieAdapterPort);
    }

    @Bean
    public DeleteMovieUseCasePort deleteMovieUseCasePort(DeleteMovieAdapterPort deleteMovieAdapterPort){
        return new DeleteMovieUseCase(deleteMovieAdapterPort);
    }


}
