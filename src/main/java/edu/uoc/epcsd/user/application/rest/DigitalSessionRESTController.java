package edu.uoc.epcsd.user.application.rest;

import edu.uoc.epcsd.user.application.rest.request.CreateDigitalSessionRequest;
import edu.uoc.epcsd.user.domain.DigitalItem;
import edu.uoc.epcsd.user.domain.DigitalSession;
import edu.uoc.epcsd.user.domain.service.DigitalSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@Log4j2
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/digital")
public class DigitalSessionRESTController {

    private final DigitalSessionService digitalSessionService;

    @GetMapping("/allDigital")
    @ResponseStatus(HttpStatus.OK)
    public List<DigitalSession> getAllDigitalSession() {
        log.trace("getAllDigitalSession");

        return digitalSessionService.findAllDigitalSession();
    }
    
    // TODO: add the code for the missing system operations here: 
    // use the corresponding mapping HTTP request annotation with the parameter: "/{digitalSessionId}"
    // and create the method getDigitalSessionById(@PathVariable @NotNull Long digitalSessionId)
    // which call the corresponding getDigitalSessionById method
    @GetMapping("/{digitalSessionId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DigitalSession> getDigitalSessionById(@PathVariable @NotNull Long digitalSessionId) {
        log.trace("getDigitalSessionById");
        return digitalSessionService.getDigitalSessionById(digitalSessionId).map(digitalSession -> ResponseEntity.ok().body(digitalSession))
                .orElse(ResponseEntity.notFound().build());
    }

    // TODO: add the code for the missing system operations here: 
    // use the corresponding mapping HTTP request annotation with the parameter: "/digitalByUser"
    // and create the method findDigitalSessionByUser(@RequestParam @NotNull Long userId)
    // which call the corresponding findDigitalSessionByUser method
    @GetMapping("/digitalByUser")
    @ResponseStatus(HttpStatus.OK)
    public List<DigitalSession> findDigitalSessionByUser(@RequestParam @NotNull Long userId) {
        log.trace("findDigitalSessionByUser");
        return digitalSessionService.findDigitalSessionByUser(userId);
    }
       
    // TODO: add the code for the missing system operations here: 
    // use the corresponding mapping HTTP request annotation with the parameter: "/createDigital"
    // and create the method createDigitalSession(@RequestBody @Valid CreateDigitalSessionRequest createDigitalSessionRequest)
    // which call the corresponding createDigitalSession method
    @PostMapping("/createDigital")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Long> createDigitalSession(@RequestBody @Valid CreateDigitalSessionRequest createDigitalSessionRequest) {
        log.trace("createDigitalSession");
        Long newDigitalSessionId = digitalSessionService.createDigitalSession(DigitalSession.builder()
                .description(createDigitalSessionRequest.getDescription())
                .location(createDigitalSessionRequest.getLocation())
                .link(createDigitalSessionRequest.getLink())
                .userId(createDigitalSessionRequest.getUserId()).build());

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newDigitalSessionId)
                .toUri();

        return ResponseEntity.created(uri).body(newDigitalSessionId);
    }

    // TODO: add the code for the missing system operations here: 
    // use the corresponding mapping HTTP request annotation with the parameter: "/updateDigital/{digitalSessionId}"
    // and create the method updateDigitalSession(@PathVariable @NotNull Long digitalSessionId, @RequestBody @Valid CreateDigitalSessionRequest updateDigitalSessionRequest)
    // which call the corresponding updateDigitalSession method
    @PutMapping("/updateDigital/{digitalSessionId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> updateDigitalSession(@PathVariable @NotNull Long digitalSessionId, @RequestBody @Valid CreateDigitalSessionRequest updateDigitalSessionRequest) {
        log.trace("updateDigitalSession");
        Long upDigitalSessionId = digitalSessionService.updateDigitalSession(digitalSessionId, updateDigitalSessionRequest.getDescription(), updateDigitalSessionRequest.getLink(), updateDigitalSessionRequest.getLocation(), updateDigitalSessionRequest.getUserId());
        return ResponseEntity.ok(upDigitalSessionId);

    }

    // TODO: add the code for the missing system operations here: 
    // use the corresponding mapping HTTP request annotation with the parameter: "/removeDigital/{digitalSessionId}"
    // and create the method removeDigitalSession(@PathVariable @NotNull Long digitalSessionId
    // which call the corresponding removeDigitalSession method
    @DeleteMapping("/removeDigital/{digitalSessionId}")
    public ResponseEntity<Void> removeDigitalSession(@PathVariable @NotNull Long digitalSessionId) {
        log.trace("removeDigitalSession");
        digitalSessionService.removeDigitalSession(digitalSessionId);
        return ResponseEntity.noContent().build();
    }

}
