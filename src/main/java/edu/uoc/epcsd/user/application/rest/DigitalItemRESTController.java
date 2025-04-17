package edu.uoc.epcsd.user.application.rest;

import edu.uoc.epcsd.user.application.rest.request.CreateDigitalItemRequest;
import edu.uoc.epcsd.user.domain.Alert;
import edu.uoc.epcsd.user.domain.DigitalItem;
import edu.uoc.epcsd.user.domain.service.DigitalItemService;
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
@RequestMapping("/digitalItem")
public class DigitalItemRESTController {

    private final DigitalItemService digitalItemService;

    @GetMapping("/allItems")
    @ResponseStatus(HttpStatus.OK)
    public List<DigitalItem> getAllDigitalItem() {
        log.trace("getAllDigitalItem");

        return digitalItemService.findAllDigitalItem();
    }
    
    // TODO: add the code for the missing system operations here: 
    // use the corresponding mapping HTTP request annotation with the parameter: "/{digitalItemId}"
    // and create the method getDigitalItemById(@PathVariable @NotNull Long digitalItemId)
    // which call the corresponding getDigitalItemById method 
    @GetMapping("/{digitalItemId}")
    public ResponseEntity<DigitalItem> getDigitalItemById(@PathVariable @NotNull Long digitalItemId) {
        log.trace("getDigitalItemById");
        DigitalItem digitalItem = digitalItemService.getDigitalItemById(digitalItemId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(digitalItem);
    }
    // TODO: add the code for the missing system operations here: 
    // use the corresponding mapping HTTP request annotation with the parameter: "/digitalItemBySession"
    // and create the method findDigitalItemBySession(@RequestParam @NotNull Long digitalSessionId)
    // which call the corresponding findDigitalItemBySession method
    @GetMapping("/digitalItemBySession")
    public List<DigitalItem> findDigitalItemBySession(@RequestParam @NotNull Long digitalSessionId) {
        log.trace("findDigitalItemBySession");
        return digitalItemService.findDigitalItemBySession(digitalSessionId);
    }
    
    // TODO: add the code for the missing system operations here: 
    // use the corresponding mapping HTTP request annotation with the parameter: "/addItem"
    // and create the method addDigitalItem(@RequestBody @Valid CreateDigitalItemRequest createDigitalItemRequest)
    // which call the corresponding addDigitalItemm method
    @PostMapping
    public ResponseEntity<Long> addDigitalItem(@RequestBody @Valid CreateDigitalItemRequest createDigitalItemRequest) {
        log.trace("addDigitalItem");
        log.trace("Creating digital item " + createDigitalItemRequest);
        Long digitalItemId = digitalItemService.addDigitalItem(DigitalItem.builder()
                .digitalSessionId(createDigitalItemRequest.getDigitalSessionId())
                .description(createDigitalItemRequest.getDescription())
                .lat(createDigitalItemRequest.getLat())
                .lon(createDigitalItemRequest.getLon())
                .link(createDigitalItemRequest.getLink()).build());

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(digitalItemId)
                .toUri();

        return ResponseEntity.created(uri).body(digitalItemId);
    }

    // TODO: add the code for the missing system operations here: 
    // use the corresponding mapping HTTP request annotation with the parameter: "/updateItem/{digitalItemId}"
    // and create the method updateDigitalItem(@PathVariable @NotNull Long digitalItemId, @RequestBody @Valid CreateDigitalItemRequest updateDigitalItemRequest)
    // which call the corresponding updateDigitalItem method
    @PutMapping(" /updateItem/{digitalItemId}")
    public ResponseEntity<Long> updateDigitalItem(@PathVariable @NotNull Long digitalItemId, @RequestBody @Valid CreateDigitalItemRequest updateDigitalItemRequest) {
        log.trace("updateDigitalItem");
        log.trace("Updating digital item " + updateDigitalItemRequest);

        Long updatedDigitalItemId = digitalItemService.updateDigitalItem(digitalItemId, updateDigitalItemRequest.getDescription(), updateDigitalItemRequest.getLink(), updateDigitalItemRequest.getLat(), updateDigitalItemRequest.getLon());

        return ResponseEntity.ok(updatedDigitalItemId);
    }


    // TODO: add the code for the missing system operations here: 
    // use the corresponding mapping HTTP request annotation with the parameter: "/reviewDigitalItem/{digitalItemId}"
    // and create the method setDigitalItemForReview(@PathVariable @NotNull Long digitalItemId)
    // which call the corresponding setDigitalItemForReview method
    @PatchMapping("/reviewDigitalItem/{digitalItemId}")
    public ResponseEntity<Void> setDigitalItemForReview(@PathVariable @NotNull Long digitalItemId) {
        log.trace("setDigitalItemForReview");
        digitalItemService.setDigitalItemForReview(digitalItemId);
        return ResponseEntity.noContent().build();
    }
    
    // TODO: add the code for the missing system operations here: 
    // use the corresponding mapping HTTP request annotation with the parameter: "/approveDigitalItem/{digitalItemId}"
    // and create the method approvePendingDigitalItem(@PathVariable @NotNull Long digitalItemId)
    // which call the corresponding approvePendingDigitalItem method
    @PatchMapping("/approveDigitalItem/{digitalItemId}")
    public ResponseEntity<Void> approvePendingDigitalItem(@PathVariable @NotNull Long digitalItemId) {
        log.trace("approveDigitalItem");
        digitalItemService.approvePendingDigitalItem(digitalItemId);
        return ResponseEntity.noContent().build();
    }
    
    // TODO: add the code for the missing system operations here: 
    // use the corresponding mapping HTTP request annotation with the parameter: "/rejectDigitalItem/{digitalItemId}"
    // and create the method rejectPendingDigitalItem(@PathVariable @NotNull Long digitalItemId)
    // which call the corresponding rejectPendingDigitalItem method
    @PatchMapping("/rejectDigitalItem/{digitalItemId}")
    public ResponseEntity<Void> rejectPendingDigitalItem(@PathVariable @NotNull Long digitalItemId) {
        log.trace("rejectDigitalItem");
        digitalItemService.rejectPendingDigitalItem(digitalItemId);
        return ResponseEntity.noContent().build();
    }
    
    // TODO: add the code for the missing system operations here: 
    // use the corresponding mapping HTTP request annotation with the parameter: "/dropItem/{digitalItemId}"
    // and create the method dropDigitalItem(@PathVariable @NotNull Long digitalItemId)
    // which call the corresponding dropDigitalItem method
    @DeleteMapping("/dropItem/{digitalItemId}")
    public ResponseEntity<Void> dropDigitalItem(@PathVariable @NotNull Long digitalItemId) {
        log.trace("dropDigitalItem");
        digitalItemService.dropDigitalItem(digitalItemId);
        return ResponseEntity.noContent().build();
    }
  
}
