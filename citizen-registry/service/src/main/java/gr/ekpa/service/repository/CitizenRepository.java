package gr.ekpa.service.repository;

import gr.ekpa.domain.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen, String> {
    List<Citizen> findByFirstNameContainingIgnoreCase(String firstName);
    List<Citizen> findByLastNameContainingIgnoreCase(String lastName);
    List<Citizen> findByGender(String gender);
    List<Citizen> findByAfm(String afm);
    List<Citizen> findByAddressContainingIgnoreCase(String address);
}
