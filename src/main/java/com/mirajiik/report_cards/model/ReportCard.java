package com.mirajiik.report_cards.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mirajiik.report_cards.model.durationSerializers.DurationSerializer;
import com.mirajiik.report_cards.model.durationSerializers.DurationDeserializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class ReportCard {
    public ReportCard(LocalDate date) { this.dayDate = date;}

    private LocalDate dayDate;

    private LocalTime startWork;
    private LocalTime endWork;

    private LocalTime workingHours = LocalTime.of(4,0);

    @JsonSerialize(using = DurationSerializer.class)
    @JsonDeserialize(using = DurationDeserializer.class)
    private Duration amountTimeWorked;

    public Duration getAmountTimeWorked() {
        if ((startWork != null) && (endWork != null)) {
            Duration timeBetween = Duration.between(startWork, endWork).abs();
            if (timeBetween.toHours() >= 4) {
                timeBetween = timeBetween.minusMinutes(45);
            }
            return timeBetween;
        }
        return Duration.ZERO;
    }

    public void setTime(LocalTime start, LocalTime end) {
        this.startWork = start;
        this.endWork = end;
    }

    public String toDayString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMMM");
        return dtf.format(dayDate);
    }
}
