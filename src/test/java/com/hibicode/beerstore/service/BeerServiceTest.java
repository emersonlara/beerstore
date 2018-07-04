package com.hibicode.beerstore.service;

import com.hibicode.beerstore.model.Beer;
import com.hibicode.beerstore.model.BeerType;
import com.hibicode.beerstore.repository.Beers;
import com.hibicode.beerstore.service.exception.BeerAlreadyExistException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.Mockito.when;

public class BeerServiceTest {

    @Mock
    private Beers beersMocked;

    private BeerService beerService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        beerService = new BeerService(beersMocked);
    }

    @Test
    public void should_refuse_creation_for_new_beer_that_already_exists() {
        final Beer beerInDatabase = new Beer(10L, "Ultimate", BeerType.IPA, new BigDecimal("500"));
        when(beersMocked.findByNameAndType("Ultimate", BeerType.IPA)).thenReturn(Optional.of(beerInDatabase));

        final Beer beer = new Beer();
        beer.setName("Ultimate");
        beer.setType(BeerType.IPA);
        beer.setVolume(new BigDecimal("300"));

        thrown.expect(BeerAlreadyExistException.class);
        thrown.expect(hasProperty("code", is("beer.alreadyExists")));
        thrown.expect(hasProperty("status", is(HttpStatus.BAD_REQUEST)));

        beerService.save(beer);
    }

    @Test
    public void should_create_new_beer() {
        when(beersMocked.findByNameAndType("Ultimate", BeerType.IPA)).thenReturn(Optional.ofNullable(null));

        final Beer newBeer = new Beer("Ultimate", BeerType.IPA, new BigDecimal("500"));
        final Beer beerMocked = new Beer(10L, "Ultimate", BeerType.IPA, new BigDecimal("500"));
        when(beersMocked.save(newBeer)).thenReturn(beerMocked);

        final Beer beerSaved = beerService.save(newBeer);

        assertThat(beerSaved.getId(), equalTo(10L));
        assertThat(beerSaved.getName(), equalTo("Ultimate"));
        assertThat(beerSaved.getType(), equalTo(BeerType.IPA));
    }

    @Test(expected = BeerAlreadyExistException.class)
    public void should_deny_update_of_an_existing_beer_that_already_exists() {
        final Beer beerInDatabase = new Beer(10L, "Ultimate New", BeerType.IPA, new BigDecimal("500"));
        when(beersMocked.findByNameAndType("Ultimate New", BeerType.IPA)).thenReturn(Optional.of(beerInDatabase));

        final Beer beerToUpdate = new Beer(5L, "Ultimate New", BeerType.IPA, new BigDecimal("200"));

        beerService.save(beerToUpdate);
    }

    @Test
    public void should_update_beer_with_the_same_id() {
        final Beer beerInDatabase = new Beer(10L, "Ultimate New", BeerType.IPA, new BigDecimal("500"));
        when(beersMocked.findByNameAndType("Ultimate New", BeerType.IPA)).thenReturn(Optional.of(beerInDatabase));

        final Beer beerToUpdate = new Beer(10L, "Ultimate New", BeerType.IPA, new BigDecimal("200"));
        final Beer beerMocked = new Beer(10L, "Ultimate New", BeerType.IPA, new BigDecimal("200"));
        when(beersMocked.save(beerToUpdate)).thenReturn(beerMocked);

        final Beer beerUpdated = beerService.save(beerToUpdate);
        assertThat(beerUpdated.getId(), equalTo(10L));
        assertThat(beerUpdated.getName(), equalTo("Ultimate New"));
        assertThat(beerUpdated.getType(), equalTo(BeerType.IPA));
        assertThat(beerUpdated.getVolume(), equalTo(new BigDecimal("200")));
    }
}