package it.camp.schedule.model.dto;

import it.camp.schedule.controllers.rest.RestConstants;
import it.camp.schedule.model.DayOff;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DayOffDTO {
    private int id;
    private String user;
    private int dayOfYear;
    private boolean approved;
    private LocalDate date;

    public DayOffDTO(DayOff dayOff) {
        this.id = dayOff.getId();
        this.user = RestConstants.API_LOCATION + "user/" + dayOff.getUser().getId();
        this.dayOfYear = dayOff.getDayOfYear();
        this.approved = dayOff.isApproved();
        this.date = dayOff.getDate();
    }
}
