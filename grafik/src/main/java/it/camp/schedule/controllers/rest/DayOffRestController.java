package it.camp.schedule.controllers.rest;

import it.camp.schedule.model.DayOff;
import it.camp.schedule.model.User;
import it.camp.schedule.model.dto.DayDTO;
import it.camp.schedule.model.dto.DayOffDTO;
import it.camp.schedule.model.dto.DayOffListResponse;
import it.camp.schedule.services.IDayOffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(path = "/api/v1/dayOff")
public class DayOffRestController {
    @Autowired
    IDayOffService dayOffService;

    @RequestMapping(path = "/toApprove", method = RequestMethod.GET)
    public DayOffListResponse findNotAppByUser(@RequestBody(required = false) User user) {
        if (user == null) {
            return new DayOffListResponse(this.dayOffService.toAccept().stream().map(DayOffDTO::new).toList());
        } else {
            return new DayOffListResponse(this.dayOffService.findNotApprovedByUser(user).stream()
                    .map(DayOffDTO::new).toList());
        }
    }

    @RequestMapping(path = "/approved", method = RequestMethod.GET)
    public DayOffListResponse findAppByUser(@RequestBody(required = false) User user) {
        if (user == null) {
            return new DayOffListResponse(this.dayOffService.findByApproved(true).stream().map(DayOffDTO::new).toList());
        } else {
            return new DayOffListResponse(this.dayOffService.findApprovedByUser(user).stream().map(DayOffDTO::new).toList());
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void discardDayOff(@PathVariable int id) {
        this.dayOffService.discardDayOff(id);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<DayOffDTO> findDayOffById(@PathVariable int id) {
        Optional<DayOff> dayOffBox = this.dayOffService.findById(id);
        return dayOffBox.map(dayOff -> ResponseEntity.ok(new DayOffDTO(dayOff)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<DayOff> saveDayOff(@RequestBody DayOff dayOff) {
        dayOff.setId(0);
        this.dayOffService.saveDayOff(dayOff);
        return ResponseEntity.status(CREATED).body(dayOff);
    }

    @RequestMapping(path = "/approved/{month}", method = RequestMethod.GET)
    public DayOffListResponse findDaysOffNotAppInMonth(@PathVariable int month) {
        return new DayOffListResponse(this.dayOffService.areDaysOffApproved(month).stream()
                .map(DayOffDTO::new).toList());
    }
}
