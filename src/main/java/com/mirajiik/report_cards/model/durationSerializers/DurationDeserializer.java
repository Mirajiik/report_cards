package com.mirajiik.report_cards.model.durationSerializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Duration;

public class DurationDeserializer extends JsonDeserializer<Duration> {
    @Override
    public Duration deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        String[] splitText = p.getText().split(":");
        if (splitText.length != 2) {
            throw new IOException();
        }
        return Duration.parse("PT" + splitText[0] + "H" + Math.abs(Integer.parseInt(splitText[1])) + "M");
    }
}
