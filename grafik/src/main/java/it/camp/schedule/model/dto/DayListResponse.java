package it.camp.schedule.model.dto;

import it.camp.schedule.model.Day;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class DayListResponse {
    private final List<Day> dayList;
}
