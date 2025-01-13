package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {
    private Doctor doctor;
    private Patient patient;
    private String treatment;
    private String prescription;
    private double billingAmount;
    private double amountPaid;
    private double balance;
    private String billId;
    private String patientId;  // Add the Patient ID
    private LocalDateTime dateTime;

    private static final String CLINIC_NAME = "Dental Life Clinic";
    private static final String CLINIC_ADDRESS = "Parit Raja";
    private static final String CLINIC_CONTACT = "019-343533";
    
    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public String getTreatment() {
        return treatment;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }


    public Appointment(Doctor doctor, Patient patient, String treatment, String prescription, double billingAmount, double amountPaid, double balance) {
        this.doctor = doctor;
        this.patient = patient;
        this.treatment = treatment;
        this.prescription = treatment;
        this.billingAmount = billingAmount;
        this.amountPaid = amountPaid;
        this.balance = balance;
        this.billId = generateBillId();
        this.patientId = generatePatientId();  // Generate the Patient ID
        this.dateTime = LocalDateTime.now();
    }

    private String generateBillId() {
        return "BILL" + System.currentTimeMillis();
    }

    private String generatePatientId() {
        return "PAT" + System.currentTimeMillis();  // Generate a unique patient ID based on current time
    }

    public double getBillingAmount() {
        return billingAmount;
    }

    public String generateReceipt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("""
                ----------------------------------------
                        %s
                    %s
                    %s
                ----------------------------------------
                Bill ID: %s
                Patient ID: %s
                Date/Time: %s

                Patient Name: %s
                Treatment Details: %s

                Total Amount: RM %.2f
                Amount Paid: RM %.2f
                Balance: RM %.2f
                ----------------------------------------
                Thank you for visiting %s!
                """,
                CLINIC_NAME, CLINIC_ADDRESS, CLINIC_CONTACT,
                billId, patientId, dateTime.format(formatter),
                patient.getName(), prescription,
                billingAmount, amountPaid, balance, CLINIC_NAME);
    }

    @Override
    public String toString() {
        return "Appointment with " + doctor + " for " + patient + "\nTreatment: " + treatment +
               "\nPrescription: " + prescription + "\nBilling Amount: RM " + String.format("%.2f", billingAmount);
    }
}
