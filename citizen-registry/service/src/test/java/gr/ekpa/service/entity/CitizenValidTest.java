package gr.ekpa.service.entity;

import gr.ekpa.domain.Citizen;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CitizenValidTest {

    private static Validator validator;

    @BeforeAll
    public static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidCitizen() {
        Citizen citizen = new Citizen("AB123456", "Maria", "Papadopoulou", "Female",
                LocalDate.of(1990, 1, 1), "123456789", "Athens");

        Set<ConstraintViolation<Citizen>> violations = validator.validate(citizen);

        for (ConstraintViolation<Citizen> violation : violations) {
            System.out.println("❗Violation: " + violation.getPropertyPath() + " - " + violation.getMessage());
        }

        assertEquals(0, violations.size(), "Ένας έγκυρος πολίτης δεν πρέπει να έχει violations");
    }


    @Test
    public void testInvalidAt() {
        Citizen citizen = new Citizen("123", "Anna", "Kosta", "Female",
                LocalDate.of(1995, 3, 10), "123456789", "Athens");

        Set<ConstraintViolation<Citizen>> violations = validator.validate(citizen);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("at")));
    }
 
    @Test
    public void testMissingFields() {
        Citizen citizen = new Citizen(null, "", "", "", null, "abc", null);

        Set<ConstraintViolation<Citizen>> violations = validator.validate(citizen);
        assertTrue(violations.size() >= 4); // απαιτούμενα πεδία
    }
}
