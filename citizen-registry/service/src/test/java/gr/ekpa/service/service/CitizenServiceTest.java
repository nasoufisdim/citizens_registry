package gr.ekpa.service.service;

import gr.ekpa.domain.Citizen;
import gr.ekpa.service.repository.CitizenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CitizenServiceTest {

    private CitizenRepository repository;
    private CitizenService service;

    @BeforeEach
    public void setup() {
        repository = mock(CitizenRepository.class);
        service = new CitizenService(repository);
    }

    @Test
    public void testCreateCitizenSuccessfully() {
        Citizen citizen = new Citizen("AA000001", "Giannis", "Alexiou", "Male",
                LocalDate.of(1980, 6, 20), "999999999", "Volos");

        when(repository.existsById("AA000001")).thenReturn(false);
        when(repository.save(citizen)).thenReturn(citizen);

        Citizen saved = service.create(citizen);
        assertEquals("Giannis", saved.getFirstName());
        verify(repository).save(citizen);
    }

    @Test
    public void testCreateCitizenAlreadyExists() {
        Citizen citizen = new Citizen("AA000001", "Giannis", "Alexiou", "Male",
                LocalDate.of(1980, 6, 20), "999999999", "Volos");

        when(repository.existsById("AA000001")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> service.create(citizen));
    }

    @Test
    public void testUpdateWithInvalidAfm() {
        Citizen existing = new Citizen("AT999999", "Eva", "Dimitriou", "Female",
                LocalDate.of(1975, 4, 1), "111111111", "Athens");

        when(repository.findById("AT999999")).thenReturn(Optional.of(existing));

        assertThrows(IllegalArgumentException.class,
                () -> service.update("AT999999", "invalid", "New Address"));
    }

    @Test
    public void testDeleteNonExistentCitizen() {
        when(repository.existsById("X9999999")).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> service.delete("X9999999"));
    }

    @Test
    public void testSearchByGenderReturnsCorrectResults() {
        Citizen c1 = new Citizen("AT0001", "Nikos", "A", "Male", LocalDate.of(1990, 1, 1), "123456789", "Athens");
        Citizen c2 = new Citizen("AT0002", "Maria", "B", "Female", LocalDate.of(1992, 2, 2), "987654321", "Patras");

        when(repository.findAll()).thenReturn(List.of(c1, c2));

        List<Citizen> results = service.search(null, null, "Female", null, null);

        assertEquals(1, results.size());
        assertEquals("Maria", results.get(0).getFirstName());
    }
}
