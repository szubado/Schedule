package it.camp.schedule.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class DayOffListResponse {
    private final List<DayOffDTO> dayOffList;
}
