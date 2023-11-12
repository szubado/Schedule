package it.camp.schedule.controllers.rest;

import com.mysql.cj.x.protobuf.Mysqlx;
import it.camp.schedule.model.Day;
import it.camp.schedule.model.DayOff;
import it.camp.schedule.model.dto.DayListResponse;
import it.camp.schedule.services.IDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(path = "/api/v1/day")
public class DayRestController {
    @Autowired
    IDayService dayService;
    @RequestMapping(path = "/month", method = RequestMethod.GET)
    public DayListResponse findDaysByMonth(@RequestParam int month) {
        return new DayListResponse(this.dayService.findByMonth(month));
    }

    @RequestMapping(path = "/accept/{month}", method = RequestMethod.PUT)
    public ResponseEntity<Day> acceptDuties(@PathVariable int month) {
        this.dayService.acceptDuties(month);
        return ResponseEntity.status(OK).build();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Day> findDayById(@PathVariable int id) {
        Optional<Day> dayBox = this.dayService.findById(id);
        return dayBox.map(day -> ResponseEntity.status(OK).body(day))
                .orElseGet(() -> ResponseEntity.status(NOT_FOUND).build());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Day> saveDay(@RequestBody Day day) {
        this.dayService.saveDay(day);
        return ResponseEntity.status(CREATED).body(day);
    }
}
