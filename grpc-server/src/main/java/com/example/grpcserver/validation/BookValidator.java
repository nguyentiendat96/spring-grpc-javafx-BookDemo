package com.example.grpcserver.validation;

import com.example.grpc.service.Search;
import org.springframework.stereotype.Component;

@Component
public class BookValidator {

    public boolean validateForSearch(Search request) {
        if (request.getBookName().isEmpty() || request.getAuthor().isEmpty()) {
            return false;
        }
        return true;
    }
}
