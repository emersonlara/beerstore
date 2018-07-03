package com.hibicode.beerstore.model;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class BeerTest {

    @Test
    public void should_make_beers_equals_by_id_only() {
        Beer b1 = new Beer();
        b1.setId(1L);
        b1.setName("A");

        Beer b2 = new Beer();
        b2.setId(1L);
        b2.setName("B");

        assertThat(b1, equalTo(b2));
    }

    @Test
    public void should_make_beers_different_because_of_id() {
        Beer b1 = new Beer();
        b1.setId(1L);
        b1.setName("A");

        Beer b2 = new Beer();
        b2.setId(2L);
        b2.setName("A");

        assertThat(b1, not(equalTo(b2)));
    }

}