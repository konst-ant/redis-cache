package com.example.rediscache.client;

import com.example.rediscache.model.Films;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "swapiClient", url = "${application.clients.swapi-baseurl}")
public interface SwapiClient {

    @GetMapping(produces = {"application/json"}, path = "/api/films")
    Films getFilms();

    @GetMapping(produces = {"application/json"}, path = "/api/people/?page={page}")
    Films getPeoplePage(@PathVariable(value = "page") int page);

}
