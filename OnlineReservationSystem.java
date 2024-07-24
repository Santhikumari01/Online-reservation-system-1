import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

class User {
    String username;
    String password;

    User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    boolean authenticate(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
}

class Reservation {
    String trainNumber;
    String trainName;
    String classType;
    String dateOfJourney;
    String fromPlace;
    String toDestination;
    String pnrNumber;

    Reservation(String trainNumber, String trainName, String classType, String dateOfJourney, String fromPlace, String toDestination) {
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.classType = classType;
        this.dateOfJourney = dateOfJourney;
        this.fromPlace = fromPlace;
        this.toDestination = toDestination;
        this.pnrNumber = UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return "PNR: " + pnrNumber + ", Train: " + trainName + " (" + trainNumber + "), Class: " + classType + 
               ", Date: " + dateOfJourney + ", From: " + fromPlace + ", To: " + toDestination;
    }
}

public class OnlineReservationSystem {
    static Scanner scanner = new Scanner(System.in);
    static List<User> users = new ArrayList<>();
    static List<Reservation> reservations = new ArrayList<>();

    public static void main(String[] args) {
        users.add(new User("admin", "password")); // Default user

        if (login()) {
            while (true) {
                System.out.println("\n1. Make a Reservation");
                System.out.println("2. Cancel a Reservation");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        makeReservation();
                        break;
                    case 2:
                        cancelReservation();
                        break;
                    case 3:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice, please try again.");
                }
            }
        } else {
            System.out.println("Invalid login credentials.");
        }
    }

    static boolean login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.authenticate(username, password)) {
                return true;
            }
        }
        return false;
    }

    static void makeReservation() {
        System.out.print("Enter train number: ");
        String trainNumber = scanner.nextLine();
        System.out.print("Enter train name: ");
        String trainName = scanner.nextLine();
        System.out.print("Enter class type: ");
        String classType = scanner.nextLine();
        System.out.print("Enter date of journey (dd-mm-yyyy): ");
        String dateOfJourney = scanner.nextLine();
        System.out.print("Enter from place: ");
        String fromPlace = scanner.nextLine();
        System.out.print("Enter to destination: ");
        String toDestination = scanner.nextLine();

        Reservation reservation = new Reservation(trainNumber, trainName, classType, dateOfJourney, fromPlace, toDestination);
        reservations.add(reservation);

        System.out.println("Reservation made successfully! Your PNR number is: " + reservation.pnrNumber);
    }

    static void cancelReservation() {
        System.out.print("Enter PNR number: ");
        String pnrNumber = scanner.nextLine();

        Reservation toCancel = null;
        for (Reservation reservation : reservations) {
            if (reservation.pnrNumber.equals(pnrNumber)) {
                toCancel = reservation;
                break;
            }
        }

        if (toCancel != null) {
            reservations.remove(toCancel);
            System.out.println("Reservation canceled successfully!");
        } else {
            System.out.println("No reservation found with PNR number: " + pnrNumber);
        }
    }
}
