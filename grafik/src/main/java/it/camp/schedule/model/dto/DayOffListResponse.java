package it.camp.schedule.model.dto;

import it.camp.schedule.model.DayOff;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class DayOffListResponse {
    private final List<DayOff> dayOffList;
}
