package gr.ekpa.service.controller;

import gr.ekpa.domain.Citizen;
import gr.ekpa.service.service.CitizenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/citizens")
public class CitizenController {

    private final CitizenService service;

    public CitizenController(CitizenService service) {
        this.service = service;
    }

    @GetMapping
    public List<Citizen> getAll() {
        return service.getAllCitizens();
    }

    @GetMapping("/{at}")
    public ResponseEntity<Object> getById(@PathVariable String at) {
        Optional<Citizen> citizen = service.getById(at);
        if (citizen.isPresent()) {
            return ResponseEntity.ok(citizen.get());
        } else {
            return ResponseEntity.badRequest().body("Ο πολίτης δεν βρέθηκε.");
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Citizen citizen) {
        try {
            return ResponseEntity.ok(service.create(citizen));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{at}")
    public ResponseEntity<?> update(@PathVariable String at,
                                    @RequestParam(required = false) String afm,
                                    @RequestParam(required = false) String address) {
        try {
            return ResponseEntity.ok(service.update(at, afm, address));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{at}")
    public ResponseEntity<?> delete(@PathVariable String at) {
        try {
            service.delete(at);
            return ResponseEntity.ok("Ο πολίτης διαγράφηκε.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public List<Citizen> search(@RequestParam(required = false) String firstName,
                                @RequestParam(required = false) String lastName,
                                @RequestParam(required = false) String gender,
                                @RequestParam(required = false) String afm,
                                @RequestParam(required = false) String address) {
        return service.search(firstName, lastName, gender, afm, address);
    }
}
