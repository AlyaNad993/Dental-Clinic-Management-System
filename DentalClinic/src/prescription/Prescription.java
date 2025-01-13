package prescription;

	import java.util.ArrayList;
	import java.util.List;

	public class Prescription {
	    private String treatmentName;
	    private List<String> medicines;

	    public Prescription(String treatmentName) {
	        this.treatmentName = treatmentName;
	        this.medicines = new ArrayList<>();
	    }

	    public void addMedicine(String medicine) {
	        medicines.add(medicine);
	    }

	    public String getTreatmentName() {
	        return treatmentName;
	    }

	    public List<String> getMedicines() {
	        return medicines;
	    }

	    public void displayPrescription(int billNumber, String patientName, String date) {
	        System.out.println("\n===============================");
	        System.out.println("            BILL");
	        System.out.println("===============================");
	        System.out.printf("Bill No: %d\n", billNumber);
	        System.out.printf("Patient Name: %s\n", patientName);
	        System.out.printf("Treatment: %s\n", treatmentName);
	        System.out.println("Medicines prescribed:");
	        for (String medicine : medicines) {
	            System.out.println("- " + medicine);
	        }
	        System.out.printf("Date: %s\n", date);
	        System.out.println("===============================");
	    }
	}
