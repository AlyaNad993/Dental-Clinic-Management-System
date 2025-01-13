package payment;

import model.Doctor;
import model.Patient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Payment {
    static int[] treatmentPrices = {800, 150, 150, 50, 200, 600, 160, 300, 6000, 98};
    static Scanner sc = new Scanner(System.in);

    private static final String CLINIC_NAME = "Dental Life Clinic";
    private static final String CLINIC_ADDRESS = "Parit Raja";
    private static final String CLINIC_CONTACT = "019-343533";

    public static void displayTreatments() {
        System.out.println("\n"+"******* Welcome To DENTAL LIFE CLINIC *******");
        System.out.println("Choose Your Treatment                 Price");
        System.out.println("1. Zoom Whitening System              RM800");
        System.out.println("2. Scaling & Polishing                RM150");
        System.out.println("3. Braces                             RM150");
        System.out.println("4. Dental Check-Up                    RM50");
        System.out.println("5. Dental Surgery                     RM200");
        System.out.println("6. Root Canal Anterior                RM600");
        System.out.println("7. Filling (White Restoration)        RM160");
        System.out.println("8. Retainer                           RM300");
        System.out.println("9. Implant Procedure                  RM6000");
        System.out.println("10. X-Ray (Panoramic)                 RM98");
    }

    // Calculates the total cost based on treatment choice and quantity
    public static double calculateTotal(int treatmentChoice, int quantity) {
        if (treatmentChoice < 1 || treatmentChoice > treatmentPrices.length) {
            throw new IllegalArgumentException("Invalid treatment choice.");
        }
        return treatmentPrices[treatmentChoice - 1] * quantity;
    }

    // Processes payment, ensures sufficient payment is provided
    public static double processPayment(double totalAmount) {
        double payment = 0;
        double balance = 0;

        while (true) {
            System.out.print("Please enter payment amount:RM ");
            payment = sc.nextDouble();

            if (payment < totalAmount) {
                System.out.printf("Insufficient payment. You need to pay at least RM %.2f. Please try again.\n", totalAmount);
            } else {
                balance = payment - totalAmount;
                System.out.printf("Payment successful. Your balance is: RM %.2f\n", balance);
                break; // Exit the loop once payment is sufficient
            }
        }

        return payment;
        //return new double[] {payment,balance};
    }

	public static double getTreatmentCost(int treatmentChoice) {
		// TODO Auto-generated method stub
		return 0;
	}
}
