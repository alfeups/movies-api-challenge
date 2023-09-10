package com.popcornblog.movies.core.domain.usecase;

import com.popcornblog.movies.core.domain.model.Message;
import com.popcornblog.movies.core.ports.in.DeleteMovieUseCasePort;
import com.popcornblog.movies.core.ports.out.DeleteMovieAdapterPort;

public class DeleteMovieUseCase implements DeleteMovieUseCasePort {

    private final DeleteMovieAdapterPort deleteMovieAdapterPort;

    public DeleteMovieUseCase(DeleteMovieAdapterPort deleteMovieAdapterPort) {
        this.deleteMovieAdapterPort = deleteMovieAdapterPort;
    }


    @Override
    public Message execute(Long movieId) {
        var idReturn = deleteMovieAdapterPort.deleteMovie(movieId);

        Message messageResponse = new Message();
        messageResponse.setMessage(String.format("The movie ID: %d was deleted successfully.", idReturn));

        return messageResponse;
    }
}
