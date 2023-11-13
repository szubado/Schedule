package it.camp.schedule.model.dto;

import it.camp.schedule.controllers.rest.RestConstants;
import it.camp.schedule.model.Day;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DayDTO {
    private int id;
    private String user1;
    private String user2;
    private boolean approved;
    private LocalDate date;

    public DayDTO(Day day) {
        this.id = day.getId();
        this.user1 = RestConstants.API_LOCATION + "user/" + day.getUser1().getId();
        this.user2 = RestConstants.API_LOCATION + "user/" + day.getUser2().getId();
        this.approved = day.isApproved();
        this.date = day.getDate();
    }
}
