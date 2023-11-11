package it.camp.schedule.controllers.rest;

import it.camp.schedule.model.DayOff;
import it.camp.schedule.model.dto.DayOffListResponse;
import it.camp.schedule.services.IDayOffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = "/api/v1/dayOff")
public class DayOffRestController {
    @Autowired
    IDayOffService dayOffService;
    @RequestMapping(path = "/toAccept", method = RequestMethod.GET)
    public DayOffListResponse toAccept() {
        return new DayOffListResponse(this.dayOffService.toAccept());
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void discardDayOff(@PathVariable int id) {
        this.dayOffService.discardDayOff(id);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<DayOff> findById(@PathVariable int id) {
        Optional<DayOff> dayOffBox = this.dayOffService.findById(id);
        return dayOffBox.map(dayOff -> ResponseEntity.status(OK).body(dayOff))
                .orElseGet(() -> ResponseEntity.status(NOT_FOUND).build());
    }
}
