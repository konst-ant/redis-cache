package com.example.rediscache.service;

import com.example.rediscache.client.SwapiClient;
import com.example.rediscache.model.CheckResult;
import com.example.rediscache.model.Films;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FilmServiceImpl implements FilmService {

    private final Logger log = LoggerFactory.getLogger(FilmServiceImpl.class);

    private final SwapiClient swapiClient;

    public FilmServiceImpl(SwapiClient swapiClient) {
        this.swapiClient = swapiClient;
    }

    public CheckResult check() {
        /**
         * Some business logic goes here
         */
        CheckResult checkResult = new CheckResult();
        checkResult.setStatus(true);
        checkResult.setFailedCheck("none");
        checkResult.setMessage("All checks were successful !");

        return checkResult;
    }

    public Films getFilms() {
        log.info("Going to call external Swapi now!!!");
        Films result = swapiClient.getFilms();
        return result;
    }

}
