package com.mirajiik.report_cards.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mirajiik.report_cards.model.durationSerializers.DurationSerializer;
import com.mirajiik.report_cards.model.durationSerializers.DurationDeserializer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Duration;

@Getter
@RequiredArgsConstructor
public class ResultCard {
    @JsonSerialize(using = DurationSerializer.class)
    @JsonDeserialize(using = DurationDeserializer.class)
    private final Duration requiredWorkTime;

    @JsonSerialize(using = DurationSerializer.class)
    @JsonDeserialize(using = DurationDeserializer.class)
    private final Duration timeWorked;

    private Duration differenceTime;

    @JsonSerialize(using = DurationSerializer.class)
    @JsonDeserialize(using = DurationDeserializer.class)
    public Duration getDifferenceTime() {
        return requiredWorkTime.minus(timeWorked);
    }
}
