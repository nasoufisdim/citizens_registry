package gr.ekpa.service.entity;

import gr.ekpa.domain.Citizen;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class CitizenTest {

    @Test
    public void testCreateCitizenWithAllFields() {
        Citizen citizen = new Citizen("AB123456", "Maria", "Nika", "Female",
                LocalDate.of(1990, 1, 1), "123456789", "Athens");

        assertEquals("AB123456", citizen.getAt());
        assertEquals("Maria", citizen.getFirstName());
        assertEquals("Nika", citizen.getLastName());
        assertEquals("Female", citizen.getGender());
        assertEquals(LocalDate.of(1990, 1, 1), citizen.getBirthDate());
        assertEquals("123456789", citizen.getAfm());
        assertEquals("Athens", citizen.getAddress());
    }

    @Test
    public void testSettersAndGetters() {
        Citizen citizen = new Citizen();
        citizen.setAt("CD987654");
        citizen.setFirstName("Nikos");
        citizen.setLastName("Petrou");
        citizen.setGender("Male");
        citizen.setBirthDate(LocalDate.of(1985, 5, 5));
        citizen.setAfm("987654321");
        citizen.setAddress("Thessaloniki");

        assertEquals("CD987654", citizen.getAt());
        assertEquals("Nikos", citizen.getFirstName());
        assertEquals("Petrou", citizen.getLastName());
        assertEquals("Male", citizen.getGender());
        assertEquals(LocalDate.of(1985, 5, 5), citizen.getBirthDate());
        assertEquals("987654321", citizen.getAfm());
        assertEquals("Thessaloniki", citizen.getAddress());
    }
}
