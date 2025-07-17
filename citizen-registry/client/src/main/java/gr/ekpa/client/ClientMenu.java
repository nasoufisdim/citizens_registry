package gr.ekpa.client;

import java.net.URI;
import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import gr.ekpa.domain.Citizen;

@SpringBootApplication
public class ClientMenu implements CommandLineRunner {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "http://localhost:8080/api/citizens";

    public static void main(String[] args) {
        SpringApplication.run(ClientMenu.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Citizen Registry Menu ---");
            System.out.println("1. Προσθήκη πολίτη");
            System.out.println("2. Εμφάνιση πολίτη με ΑΤ");
            System.out.println("3. Διαγραφή πολίτη");
            System.out.println("4. Ενημέρωση ΑΦΜ/Διεύθυνσης");
            System.out.println("5. Λίστα όλων των πολιτών");
            System.out.println("0. Έξοδος");
            System.out.print("Επιλογή: ");
            String option = scanner.nextLine();

            try {
                switch (option) {
                    case "1" -> addCitizen(scanner);
                    case "2" -> getCitizen(scanner);
                    case "3" -> deleteCitizen(scanner);
                    case "4" -> updateCitizen(scanner);
                    case "5" -> listCitizens();
                    default -> {
                        System.out.println("Έξοδος...");
                        return;
                    }
                }
            } catch (Exception e) {
                System.out.println("Σφάλμα: " + e.getMessage());
            }
        }
    }

    private void addCitizen(Scanner scanner) {
        System.out.print("ΑΤ (8 χαρακτήρες): ");
        String at = scanner.nextLine();
        System.out.print("Όνομα: ");
        String firstName = scanner.nextLine();
        System.out.print("Επώνυμο: ");
        String lastName = scanner.nextLine();
        System.out.print("Φύλο: ");
        String gender = scanner.nextLine();
        System.out.print("Ημ. γέννησης (yyyy-MM-dd): ");
        String birthDate = scanner.nextLine();
        System.out.print("ΑΦΜ (προαιρετικό): ");
        String afm = scanner.nextLine();
        System.out.print("Διεύθυνση (προαιρετικό): ");
        String address = scanner.nextLine();

        Citizen citizen = new Citizen(at, firstName, lastName, gender, java.time.LocalDate.parse(birthDate),
                afm.isBlank() ? null : afm, address.isBlank() ? null : address);

        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, citizen, String.class);
        System.out.println("Απάντηση: " + response.getBody());
    }

    private void getCitizen(Scanner scanner) {
        System.out.print("Δώσε ΑΤ: ");
        String at = scanner.nextLine();
        String url = baseUrl + "/" + at;
        String response = restTemplate.getForObject(url, String.class);
        System.out.println("Απάντηση:\n" + response);
    }

    private void deleteCitizen(Scanner scanner) {
        System.out.print("Δώσε ΑΤ: ");
        String at = scanner.nextLine();
        restTemplate.delete(baseUrl + "/" + at);
        System.out.println("Διαγράφηκε ο πολίτης.");
    }

    private void updateCitizen(Scanner scanner) {
        System.out.print("Δώσε ΑΤ: ");
        String at = scanner.nextLine();
        System.out.print("Νέος ΑΦΜ (ή κενό): ");
        String afm = scanner.nextLine();
        System.out.print("Νέα διεύθυνση (ή κενό): ");
        String address = scanner.nextLine();

        URI uri = URI.create(baseUrl + "/" + at + "?afm=" + afm + "&address=" + address);
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.PUT, HttpEntity.EMPTY, String.class);
        System.out.println("Απάντηση: " + response.getBody());
    }

    private void listCitizens() {
        String response = restTemplate.getForObject(baseUrl, String.class);
        System.out.println("Λίστα πολιτών:\n" + response);
    }
}
