package com.example.rediscache.model;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilmsDeserializer extends StdDeserializer<Films> {

    public FilmsDeserializer() {
        this(null);
    }

    public FilmsDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Films deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        JsonNode results = node.get("results");
        List<Film> films = new ArrayList<>();
        if (results.isArray()) {
            for (JsonNode jsonNode : results) {
                Film film = context.readValue(jsonNode.traverse(), Film.class);
                films.add(film);
            }
        }
        Films result = new Films();
        result.films = films;
        return result;
    }
}
