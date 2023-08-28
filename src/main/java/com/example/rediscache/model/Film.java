package com.example.rediscache.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;


public class Film {
    public String title;

    @JsonProperty("episode_id")
    public short episodeId;

    @JsonProperty("opening_crawl")
    public String openingCrawl;

    public String director;

    public String producer;

    @JsonProperty("release_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate releaseDate;

    public List<String> characters;

    public List<String> planets;

    public List<String> starships;

    public List<String> vehicles;

    public List<String> species;

    public String created;

    public String edited;

    public String url;
}
