package com.mirajiik.report_cards.service;

import com.mirajiik.report_cards.model.ReportCard;
import com.mirajiik.report_cards.model.ResultCard;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public abstract class ReportCardService {
    abstract public boolean create(ReportCard card);

    abstract public boolean update(ReportCard card);
    abstract public boolean update(int day, ReportCard card);

    abstract public List<ReportCard> readAll();

    abstract public ReportCard read(int day);

    abstract public boolean delete(int day);

    public ResultCard getResult() {
        Duration requiredTime = Duration.ZERO;
        Duration timeWorked = Duration.ZERO;
        LocalDate currentDate =  LocalDate.now();
        int i = 0;
        List<ReportCard> days = readAll();
        while ((i < days.size()) && (days.get(i).getDayDate().isBefore(currentDate) || days.get(i).getDayDate().isEqual(currentDate))) {
            requiredTime = requiredTime.plusHours(days.get(i).getWorkingHours().getHour());
            timeWorked = timeWorked.plus(days.get(i).getAmountTimeWorked());
            i++;
        }
        return new ResultCard(requiredTime, timeWorked);
    }

    public void init() {
        final List<DayOfWeek> weekends = Arrays.asList(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
        LocalDate currDate = LocalDate.now();
        DayOfWeek dayOfWeek = LocalDate.of(currDate.getYear(), currDate.getMonth(), 1).getDayOfWeek();
        for (int i = 1; i <= currDate.getMonth().length(currDate.isLeapYear()); i++) {
            if (!weekends.contains(dayOfWeek.plus(i - 1))) {
                create(new ReportCard(LocalDate.of(currDate.getYear(), currDate.getMonth(), i)));
            }
        }
    }
}
