package com.mirajiik.report_cards.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mirajiik.report_cards.service.ReportCardService;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class FillerWorkingDays {
    public static Map<Integer, ReportCard> getWorkingDays() {
        int days = LocalDate.now().lengthOfMonth();
        int currYear = LocalDate.now().getYear();
        int currMonth = LocalDate.now().getMonthValue();
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<Integer, ReportCard> cards = new HashMap<>();
        try {
//            String URL = STR."http://localhost:4000/api/calendar/\{currYear}/\{currMonth}";
            String URL = "http://localhost:4000/api/calendar/" + currYear + "/" + currMonth;
            String stringJsonMonth = restTemplate.getForObject(URL, String.class);
            Map<String, Object> jsonMap = objectMapper.readValue(stringJsonMonth, new TypeReference<>() {
            });
            if ((Integer) jsonMap.get("status") != 200)
                throw new IOException("Ошибка чтения JSON!");
            for (int i = 1; i <= days; i++) {
                jsonMap = objectMapper.readValue(restTemplate.getForObject(URL + "/" + i, String.class), new TypeReference<>() {
                });
//                jsonMap = objectMapper.readValue(restTemplate.getForObject(STR."\{URL}/\{i}", String.class), new TypeReference<>() {
//                });
                if ((boolean) jsonMap.get("isWorkingDay")) {
                    ReportCard currCard = new ReportCard(LocalDate.of(currYear, currMonth, i));
                    if (jsonMap.containsKey("isShortDay") && (boolean) jsonMap.get("isShortDay")) {
                        currCard.setWorkingHours(LocalTime.of(3, 0));
                    }
                    cards.put(i, currCard);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return cards;
    }

    public static boolean fillWorkingDays(ReportCardService service) {
        Map<Integer, ReportCard> map = getWorkingDays();
        if (Objects.isNull(map)) {
            return false;
        }
        int days = LocalDate.now().lengthOfMonth();
        for (int i = 1; i <= days; i++) {
            if (map.containsKey(i) && !Objects.isNull(service.read(i))) {
                service.update(map.get(i));
            } else if (!map.containsKey(i)) {
                service.delete(i);
            } else {
                service.create(map.get(i));
            }
        }
        return true;
    }
}
