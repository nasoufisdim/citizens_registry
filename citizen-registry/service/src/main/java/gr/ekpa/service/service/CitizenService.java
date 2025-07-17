package gr.ekpa.service.service;

import gr.ekpa.domain.Citizen;
import gr.ekpa.service.repository.CitizenRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CitizenService {

    private final CitizenRepository repository;

    public CitizenService(CitizenRepository repository) {
        this.repository = repository;
    }

    public List<Citizen> getAllCitizens() {
        return repository.findAll();
    }

    public Optional<Citizen> getById(String at) {
        return repository.findById(at);
    }

    public Citizen create(Citizen citizen) {
        if (repository.existsById(citizen.getAt())) {
            throw new IllegalArgumentException("Ο πολίτης με ΑΤ " + citizen.getAt() + " υπάρχει ήδη.");
        }
        return repository.save(citizen);
    }

    public Citizen update(String at, String afm, String address) {
        Citizen citizen = repository.findById(at)
            .orElseThrow(() -> new IllegalArgumentException("Ο πολίτης με ΑΤ " + at + " δεν βρέθηκε."));

        if (afm != null && !afm.matches("\\d{9}")) {
            throw new IllegalArgumentException("Ο ΑΦΜ πρέπει να έχει 9 ψηφία.");
        }

        citizen.setAfm(afm);
        citizen.setAddress(address);
        return repository.save(citizen);
    }

    public void delete(String at) {
        if (!repository.existsById(at)) {
            throw new IllegalArgumentException("Ο ΑΤ δεν βρέθηκε για διαγραφή.");
        }
        repository.deleteById(at);
    }

    public List<Citizen> search(String firstName, String lastName, String gender, String afm, String address) {
        return repository.findAll().stream().filter(c ->
            (firstName == null || c.getFirstName().toLowerCase().contains(firstName.toLowerCase())) &&
            (lastName == null || c.getLastName().toLowerCase().contains(lastName.toLowerCase())) &&
            (gender == null || c.getGender().equalsIgnoreCase(gender)) &&
            (afm == null || (c.getAfm() != null && c.getAfm().equals(afm))) &&
            (address == null || (c.getAddress() != null && c.getAddress().toLowerCase().contains(address.toLowerCase())))
        ).toList();
    }
}
