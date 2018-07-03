package com.hibicode.beerstore.service.exception;

import org.springframework.http.HttpStatus;

public class BeerAlreadyExistException extends BusinessException {

    public BeerAlreadyExistException() {
        super("beer.alreadyExists", HttpStatus.BAD_REQUEST);
    }

}
