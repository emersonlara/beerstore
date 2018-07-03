package com.hibicode.beerstore.resource;

import com.hibicode.beerstore.model.Beer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/beers")
public class BeersResource {

    @PostMapping
    public void createBeer(@Valid @RequestBody Beer beer) {
        System.out.println(">>>> beer: " + beer.getName());
    }
}
