package com.dentalclinic;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DentalClinicSystem system = new DentalClinicSystem();
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        do {
            System.out.println("\n====================================");
            System.out.println("  DENTAL CLINIC MANAGEMENT SYSTEM");
            System.out.println("====================================");
            System.out.println("1. Add Doctor");
            System.out.println("2. Add Patient");
            System.out.println("3. Book Appointment");
            System.out.println("4. View Appointments");
            System.out.println("5. Remove Doctor");
            System.out.println("6. Remove Patient");
            System.out.println("7. Search Patient");
            System.out.println("8. Search Doctor");
            System.out.println("9. Generate Daily Report");

            try {
                System.out.print("\nChoose an option (Please choose a number between 1 and 9): ");
                choice = Integer.parseInt(scanner.nextLine());
                // Input validation
                if (choice < 1 || choice > 9) {
                    System.out.println("Invalid input. Please choose a number between 1 and 9.");
                    continue; // Ask for input again
                }

                switch (choice) {
                    case 1:
                        system.addDoctor();
                        break;
                    case 2:
                        system.addPatient();
                        break;
                    case 3:
                        system.bookAppointment();
                        break;
                    case 4:
                        system.viewAppointments();
                        break;
                    case 5:
                        system.removeDoctor();
                        break;
                    case 6:
                        system.removePatient();
                        break;
                    case 7:
                        system.searchPatient();
                        break;
                    case 8:
                        system.searchDoctor();
                        break;
                    case 9:
                    	system.generateDailyReport();
                    	break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number between 1 and 9.");
            }
        } while (choice != 9); // Exit when choice is 9

        scanner.close();
    }
}
