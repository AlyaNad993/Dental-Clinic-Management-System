package com.dentalclinic;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
import model.Doctor;
import model.Patient;
import model.Appointment;
import payment.Payment;
//import com.dentalclinic.prescription.Prescription;

public class DentalClinicSystem {
    private Map<String, Doctor> doctors = new HashMap<>();
    private Map<String, Patient> patients = new HashMap<>();
    private List<Appointment> appointments;
    private Scanner scanner;

    public DentalClinicSystem() {
        appointments = new ArrayList<>();
        scanner = new Scanner(System.in);
    }
    

     //TO ADD DOCTORS INFORMATION
    public void addDoctor() {
        System.out.print("Enter doctor's name: ");
        String doctorName = scanner.nextLine();
     //Input validation for doctor's name to allow only letters (uppercase and lowercase)
        while (!doctorName.matches("[a-zA-Z ]+")) { // Allow letters and spaces
            System.out.println("Invalid input. Please enter only letters for doctor's name.");
            System.out.print("Enter doctor's name: ");
            doctorName = scanner.nextLine();
        }
        
        
     //TO ADD DOCTORS AGE
        System.out.print("Enter doctor's age: ");
        int doctorAge = 0;
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number for doctor's age.");
            System.out.print("Enter doctor's age: ");
            scanner.next(); 
        }
        doctorAge = scanner.nextInt();
        scanner.nextLine(); //Consume the leftover newline character
        
        
     //TO ADD DOCTOR SPECIALIZATION
        System.out.print("Enter specialization: ");
        String specialization = scanner.nextLine();
     //Input validation for doctor's name to allow only letters (uppercase and lowercase)
        while (!specialization.matches("[a-zA-Z ]+")) { // Allow letters and spaces
            System.out.println("Invalid input. Please enter only letters for doctor's specialization.");
            System.out.print("Enter specialization: ");
            specialization = scanner.nextLine();
        }
        doctors.put(doctorName, new Doctor(doctorName, doctorAge, specialization));
        System.out.println("Doctor added successfully.");
    }
    

     //TO ADD PATIENTS INFORMATION
    public void addPatient() {
       String name;
        do {
            System.out.print("Enter patient's name: ");
            name = scanner.nextLine();
            if (!name.matches("[a-zA-Z ]+")) { // Allow letters and spaces
                System.out.println("Invalid input. Please enter only letters for the patient's name.");
            }
        } while (!name.matches("[a-zA-Z ]+"));
        

    //TO ADD PATIENTS AGE
    System.out.print("Enter patient's age: ");
    int patientAge = 0;
    while (!scanner.hasNextInt()) {
        System.out.println("Invalid input. Please enter a number for the patient's age.");
        System.out.print("Enter patient's age: ");
        scanner.next(); // Consume invalid input
    }
    patientAge = scanner.nextInt();
    scanner.nextLine(); // Consume the leftover newline character
    //Add the patient to the map
    patients.put(name, new Patient(name, patientAge));
    System.out.println("Patient added successfully.");
}

    
    //TO BOOK APPOINTMENT AT THIS CLINIC
    public void bookAppointment() {
    	boolean continueBooking = true;
    	while (continueBooking) {
        System.out.println("\nAvailable Doctors:");
        for (String doctorName : doctors.keySet()) {
            System.out.println(doctorName);
        }
        System.out.print("Select doctor by name: ");
        String doctorName = scanner.nextLine();
     //Input validation to ensure only letters and spaces are allowed
        while (!doctorName.matches("[a-zA-Z ]+")) {
            System.out.println("Invalid input. Please enter a valid name (letters and spaces only).");
            System.out.print("Select doctor by name: ");
            doctorName = scanner.nextLine();
        }

        System.out.println("\nAvailable Patients:");
        for (String name : patients.keySet()) {
            System.out.println(name);
        }
        System.out.print("Select patient by name: ");
        String name = scanner.nextLine();
     //Input validation to ensure only letters and spaces are allowed
        while (!name.matches("[a-zA-Z ]+")) {
            System.out.println("Invalid input. Please enter a valid name (letters and spaces only).");
            System.out.print("Select doctor by name: ");
            name = scanner.nextLine();
        }
        

     //AFTER BOOK APPOINTMENT PATIENTS WILL CHOOSE THEIR TREATMENTS
        Payment.displayTreatments();
        int treatmentChoice = -1;
        while (treatmentChoice < 1 || treatmentChoice > 10) {
            System.out.print("\nEnter treatment choice (1-10): ");
            String input = scanner.nextLine().trim();
            
            // Check for symbols or letters first
            if (input.matches(".*[a-zA-Z!@#$%^&*(),.?\":{}|<>].*")) {
                if (input.matches(".*[a-zA-Z].*")) {
                    System.out.println("Letters are not allowed. Please enter a number between 1 and 10.");
                } else {
                    System.out.println("Special characters are not allowed. Please enter a number between 1 and 10.");
                }
                continue;
            }
            
            // Check if it's a number
            if (input.matches("\\d+")) {
                treatmentChoice = Integer.parseInt(input);
                // Validate range 1-10
                if (treatmentChoice < 1 || treatmentChoice > 10) {
                    System.out.println("Please enter a number between 1 and 10 only.");
                    treatmentChoice = -1; // Reset to continue loop
                }
            } else {
                System.out.println("Invalid input. Please enter a number between 1 and 10.");
            }
        }

        int quantity = -1;
        while (quantity < 0) {
            System.out.print("How many treatments do you want? ");
            String input = scanner.nextLine().trim();
            
            // Check for invalid characters (letters or symbols)
            if (input.matches(".*[a-zA-Z!@#$%^&*(),.?\":{}|<>].*")) {
                if (input.matches(".*[a-zA-Z].*")) {
                    System.out.println("Letters are not allowed. Please enter a valid number.");
                } else {
                    System.out.println("Special characters are not allowed. Please enter a valid number.");
                }
                continue;
            }
            
            // Check if it's a valid positive number
            if (input.matches("\\d+")) {
                quantity = Integer.parseInt(input);
                if (quantity == 0) {
                    System.out.println("Quantity cannot be zero. Please enter a positive number.");
                    quantity = -1; // Reset to continue loop
                } else if (quantity > 100) { // Adding a reasonable upper limit
                    System.out.println("Quantity seems too high. Please enter a reasonable number.");
                    quantity = -1;
                }
            } else {
                System.out.println("Invalid input. Please enter a positive number.");
            }
        }
        
        
     //CALCULATE TOTAL AND PROCESS PAYMENT
        double totalAmount = Payment.calculateTotal(treatmentChoice, quantity);
        System.out.printf("Total amount: RM%.2f\n", totalAmount);
        double amountPaid = 0;
        double balance = 0;
        //double[] paymentResult = Payment.processPayment(totalAmount);
        //double amountPaid = paymentResult[0];
        //double balance = paymentResult[1];
        
     //Process payment
        while (true) {
            System.out.print("Please enter payment amount: RM ");
            String input = scanner.nextLine().trim();

            // Check if the input is a valid positive number
            if (!input.matches("\\d+(\\.\\d{1,2})?")) {  // Allows integers or decimals up to two decimal places
                System.out.println("Invalid input. Please enter a valid number without letters, special symbols, or negative signs.");
                continue;
            }

            // Convert the validated string to a double
            amountPaid = Double.parseDouble(input);

            if (amountPaid < totalAmount) {
                System.out.printf("Insufficient payment. You need to pay at least RM %.2f. Please try again.\n", totalAmount);
            } else {
                balance = amountPaid - totalAmount;
                System.out.printf("Payment successful. Your balance is: RM %.2f\n", balance);
                break;
            }
        }
        
        
     //CONFIRM APPOINTMENT IF PAYMENT SUCESSFULL
        if (balance >= 0) {
            Appointment appointment = new Appointment(doctors.get(doctorName), patients.get(name), 
                "Treatment " + treatmentChoice, "Prescription details", totalAmount, amountPaid, balance);
            appointments.add(appointment);
            System .out.println("Appointment booked successfully.");
       
     //GENERATE AND DISPLAY RECEIPT
            String receipt = appointment.generateReceipt();
            System.out.println("\n**************** Receipt ***************");
  //          System.out.println("  ----------------------------------------")
            System.out.println(receipt);
        } else {
            System.out.println("Payment failed. Appointment not booked.");
        }
     // Prompt user for the next action
        boolean validInput = false;
        String response;

        do {
            System.out.print("\nHave a next patient? (yes/no): ");
            response = scanner.nextLine().trim().toLowerCase();
            
            // Input validation
            if (response.matches("^(yes|no)$")) {
                validInput = true;
            } else {
                if (response.matches(".*\\d+.*")) {
                    System.out.println("Numbers are not allowed. Please enter 'yes' or 'no' only.");
                } else if (response.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
                    System.out.println("Special characters are not allowed. Please enter 'yes' or 'no' only.");
                } else {
                    System.out.println("Invalid input. Please enter 'yes' or 'no' only.");
                }
            }
        } while (!validInput);

        if ("yes".equals(response)) {
            // Exit the current method and return to the main menu
            return;    
        } else {  // response must be "no" at this point
            // Generate report and stop the system
            generateDailyReport();
            System.exit(0);
        }
    }
}
    
    
      //AFTER BOOK APPOINTMENT PATIENTS CAN VIEW THEIR APPOINTMENTS
    public void viewAppointments() {
        if (appointments.isEmpty()) {
            System.out.println("\nNo appointments found.");
            return;
        }

        System.out.println("\n========== APPOINTMENTS LIST ==========");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        //GROUP DOCTOR BY APPOINTMENTS
        Map<String, List<Appointment>> appointmentsByDoctor = new HashMap<>();
        
        for (Appointment appointment : appointments) {
            String doctorName = appointment.getDoctor().getName();
            appointmentsByDoctor.computeIfAbsent(doctorName, k -> new ArrayList<>()).add(appointment);
        }
        

        //DISPLAY APPOINTMENTS BY GROUP DOCTOR
        for (Map.Entry<String, List<Appointment>> entry : appointmentsByDoctor.entrySet()) {
            String doctorName = entry.getKey();
            List<Appointment> doctorAppointments = entry.getValue();
            
            System.out.println("\nDoctor: Dr. " + doctorName);
            System.out.println("Number of appointments: " + doctorAppointments.size());
            System.out.println("-----------------------------------------");
            
            for (Appointment appointment : doctorAppointments) {
                System.out.println("Date/Time: " + appointment.getDateTime().format(formatter));
                System.out.println("Patient: " + appointment.getPatient().getName());
                System.out.println("Treatment: " + appointment.getTreatment());
                System.out.println("Amount: RM " + String.format("%.2f", appointment.getBillingAmount()));
                System.out.println("-----------------------------------------");
            }
        }
        System.out.println("\nTotal appointments: " + appointments.size());
        System.out.println("System shutting down. Goodbye!");
    }
    
    
      //ADMIN CAN REMOVE DOCTORS
    public void removeDoctor() {
        System.out.println("Available Doctors:");
        for (String doctorName : doctors.keySet()) {
            System.out.println(doctorName);
        }
        System.out.print("Select doctor by name to remove: ");
        String doctorName = scanner.nextLine();
      //Validate input to ensure it contains only letters and spaces
        if (!doctorName.matches("[a-zA-Z\\s]+")) {
            System.out.println("Invalid input. Please enter a valid name containing only letters and spaces.");
            return; // Exit the method early if the input is invalid
        }

        if (doctors.containsKey(doctorName)) {
            doctors.remove(doctorName);
            System.out.println("Doctor removed successfully.");
        } else {
            System.out.println("No doctor found with the given name.");
        }
    }
    
    
      //ADMIN CAN REMOVE PATIENTS
    public void removePatient() {
        System.out.println("Available Patients:");
        for (String name : patients.keySet()) {
            System.out.println(name);
        }
        System.out.print("Select patient by name to remove: ");
        String name = scanner.nextLine();
        //Validate input to ensure it contains only letters and spaces
        if (!name.matches("[a-zA-Z\\s]+")) {
            System.out.println("Invalid input. Please enter a valid name containing only letters and spaces.");
            return; // Exit the method early if the input is invalid
        }

        if (patients.containsKey(name)) {
            patients.remove(name);
            System.out.println("Patient removed successfully.");
        } else {
            System.out.println("No patient found with the given name.");
        }
    }
    
      //ADMIN CAN SEARCH PATIENTS
    public void searchPatient() {
        System.out.print("Enter patient's name to search: ");
        String name = scanner.nextLine();
     // Validate input to ensure it contains only letters and spaces
        if (!name.matches("[a-zA-Z\\s]+")) {
            System.out.println("Invalid input. Please enter a valid name containing only letters and spaces.");
            return; // Exit the method early if the input is invalid
        }

        // Search for patients whose names start with the input
        boolean found = false;
        //Search for patients whose names start with the input
        for (Map.Entry<String, Patient> patientEntry : patients.entrySet()) {
            if (patientEntry.getKey().startsWith(name)) {
                System.out.println("Patient found: " + patientEntry.getValue());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No patients found with the given name.");
        }
    }
    
    
      //ADMIN CAN SEARCH DOCTOR
    public void searchDoctor() {
        System.out.print("Enter doctor's name to search: ");
        String doctorName = scanner.nextLine();
     // Validate input to ensure it contains only letters and spaces
        if (!doctorName.matches("[a-zA-Z\\s]+")) {
            System.out.println("Invalid input. Please enter a valid name containing only letters and spaces.");
            return; // Exit the method early if the input is invalid
        }

        // Search for doctors whose names start with the input
        boolean found = false;
        //Search for doctors whose names start with the input
        for (Map.Entry<String, Doctor> doctorEntry : doctors.entrySet()) {
            if (doctorEntry.getKey().startsWith(doctorName)) {
                System.out.println("Doctor found: " + doctorEntry.getValue());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No doctors found with the given name.");
        }
    }
    
    
    //GENERATE REPORT OF THE DAY
    public void generateDailyReport() {
        if (appointments.isEmpty()) {
            System.out.println("\nNo appointments found.");
            return;
        }

        //Get date input from user
        System.out.print("Enter date to view (YYYY-MM-DD): ");
        String dateStr = scanner.nextLine();
        
        //Create formatter for parsing and displaying dates
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        
        try {
            LocalDate requestedDate = LocalDate.parse(dateStr, dateFormatter);
            
            // Group appointments by doctor for the specified date
            Map<Doctor, List<Appointment>> dailyAppointments = new HashMap<>();
            
            // Filter appointments for the requested date
            for (Appointment appointment : appointments) {
                if (appointment.getDateTime().toLocalDate().equals(requestedDate)) {
                    dailyAppointments.computeIfAbsent(
                        appointment.getDoctor(), 
                        k -> new ArrayList<>()
                    ).add(appointment);
                }
            }
            
            if (dailyAppointments.isEmpty()) {
                System.out.println("\nNo appointments found for " + dateStr);
                return;
            }
            
            // Print the daily report
            System.out.println("\n========== DAILY APPOINTMENTS REPORT ==========");
            System.out.println("Date: " + dateStr);
            System.out.println("============================================");
            
            int totalPatients = 0;
            double totalRevenue = 0.0;
            
            for (Map.Entry<Doctor, List<Appointment>> entry : dailyAppointments.entrySet()) {
                Doctor doctor = entry.getKey();
                List<Appointment> doctorAppointments = entry.getValue();
                
                System.out.println("\nDoctor: Dr. " + doctor.getName());
                System.out.println("Specialization: " + doctor.getSpecialization());
                System.out.println("Number of patients: " + doctorAppointments.size());
                System.out.println("-----------------------------------------");
                
                double doctorDailyRevenue = 0.0;
                
                for (Appointment appointment : doctorAppointments) {
                    System.out.println("Time: " + appointment.getDateTime().format(timeFormatter));
                    System.out.println("Patient: " + appointment.getPatient().getName());
                    System.out.println("Treatment: " + appointment.getTreatment());
                    System.out.println("Amount: RM " + String.format("%.2f", appointment.getBillingAmount()));
                    System.out.println("-----------------------------------------");
                    
                    doctorDailyRevenue += appointment.getBillingAmount();
                }
                
                totalPatients += doctorAppointments.size();
                totalRevenue += doctorDailyRevenue;
                
                System.out.println("Doctor's daily revenue: RM " + String.format("%.2f", doctorDailyRevenue));
            }
            
            //Print daily summary
            System.out.println("\n============ DAILY SUMMARY ============");
            System.out.println("Total doctors with appointments: " + dailyAppointments.size());
            System.out.println("Total patients seen: " + totalPatients);
            System.out.println("Total revenue: RM " + String.format("%.2f", totalRevenue));
            System.out.println("======================================");
            
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD format.");
        }
    }
}