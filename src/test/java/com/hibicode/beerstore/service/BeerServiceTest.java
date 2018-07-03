package com.hibicode.beerstore.service;

import com.hibicode.beerstore.model.Beer;
import com.hibicode.beerstore.model.BeerType;
import com.hibicode.beerstore.repository.Beers;
import com.hibicode.beerstore.service.exception.BeerAlreadyExistException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.when;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class BeerServiceTest {

    @Mock
    private Beers beersMocked;

    private BeerService beerService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        beerService = new BeerService(beersMocked);
    }

    @Test(expected = BeerAlreadyExistException.class)
    public void should_refuse_creation_for_new_beer_that_already_exists() {
        when(beersMocked.findByNameAndType("Ultimate", BeerType.IPA)).thenThrow(new BeerAlreadyExistException());

        final Beer beer = new Beer();
        beer.setName("Ultimate");
        beer.setType(BeerType.IPA);
        beer.setVolume(new BigDecimal("500"));

        beerService.save(beer);
    }

    @Test
    public void should_create_new_beer() {
        when(beersMocked.findByNameAndType("Ultimate", BeerType.IPA)).thenReturn(Optional.ofNullable(null));

        Beer newBeer = new Beer();
        newBeer.setName("Ultimate");
        newBeer.setType(BeerType.IPA);
        newBeer.setVolume(new BigDecimal("500"));

        Beer beerMocked = new Beer();
        beerMocked.setId(10L);
        beerMocked.setName("Ultimate");
        beerMocked.setType(BeerType.IPA);
        beerMocked.setVolume(new BigDecimal("500"));

        when(beersMocked.save(newBeer)).thenReturn(beerMocked);

        Beer beerSaved = beerService.save(newBeer);

        assertThat(beerSaved.getId(), equalTo(10L));
        assertThat(beerSaved.getName(), equalTo("Ultimate"));
        assertThat(beerSaved.getType(), equalTo(BeerType.IPA));
    }

    @Test
    public void should_update_an_existing_beer() {
        when(beersMocked.findByNameAndType("Ultimate New England", BeerType.IPA)).thenReturn(Optional.ofNullable(null));

        Beer beerToUpdate = new Beer();
        beerToUpdate.setId(10L);
        beerToUpdate.setName("Ultimate New England");
        beerToUpdate.setType(BeerType.IPA);
        beerToUpdate.setVolume(new BigDecimal("500"));

        when(beersMocked.save(beerToUpdate)).thenReturn(beerToUpdate);

        Beer beerSaved = beerService.save(beerToUpdate);

        assertThat(beerSaved.getId(), equalTo(10L));
        assertThat(beerSaved.getName(), equalTo("Ultimate New England"));
        assertThat(beerSaved.getType(), equalTo(BeerType.IPA));
    }
}