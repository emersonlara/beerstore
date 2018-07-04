package com.hibicode.beerstore.resource;

import com.hibicode.beerstore.model.Beer;
import com.hibicode.beerstore.service.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/beers")
public class BeersResource {

    @Autowired
    private BeerService beerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Beer create(@Valid @RequestBody Beer beer) {
        return beerService.save(beer);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable  Long id, @Valid @RequestBody Beer beer) {
        beer.setId(id);
        beerService.save(beer);
    }
}
