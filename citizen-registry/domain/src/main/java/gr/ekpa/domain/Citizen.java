package gr.ekpa.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "citizens")
public class Citizen {

    @Id
    @Column(length = 8, unique = true, nullable = false)
    @Pattern(regexp = "^[A-Za-z0-9]{8}$", message = "Ο ΑΤ πρέπει να έχει 8 αλφαριθμητικούς χαρακτήρες.")
    private String at;

    @NotBlank(message = "Το όνομα είναι υποχρεωτικό.")
    private String firstName;

    @NotBlank(message = "Το επώνυμο είναι υποχρεωτικό.")
    private String lastName;

    @NotBlank(message = "Το φύλο είναι υποχρεωτικό.")
    private String gender;

    @NotNull(message = "Η ημερομηνία γέννησης είναι υποχρεωτική.")
    private LocalDate birthDate;

    @Pattern(regexp = "^[0-9]{9}$", message = "Ο ΑΦΜ πρέπει να αποτελείται από 9 ψηφία.")
    private String afm;

    private String address;

    public Citizen() {}

    public Citizen(String at, String firstName, String lastName, String gender,
                   LocalDate birthDate, String afm, String address) {
        this.at = at;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.afm = afm;
        this.address = address;
    }

    public String getAt() {
        return at;
    }

    public void setAt(String at) {
        this.at = at;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getAfm() {
        return afm;
    }

    public void setAfm(String afm) {
        this.afm = afm;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFormattedBirthDate() {
        return birthDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
}
