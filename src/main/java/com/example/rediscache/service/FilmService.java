package com.example.rediscache.service;

import com.example.rediscache.model.CheckResult;
import com.example.rediscache.model.Films;

public interface FilmService {

    CheckResult check();

    Films getFilms();
}
