import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

class Donor {
    String name;
    String bloodGroup;
    int age;
    String contact;
    String medicalHistory;
    String[] donationDates = new String[10]; // Max 10 donations
    int donationCount = 0;

    Donor(String name, String bloodGroup, int age, String contact, String medicalHistory) {
        this.name = name;
        this.bloodGroup = bloodGroup;
        this.age = age;
        this.contact = contact;
        this.medicalHistory = medicalHistory;
    }

    void display() {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║              DONOR PROFILE              ║");
        System.out.println("╚══════════════════════════════════════════╝");
        System.out.printf("► Name            : %s\n", name);
        System.out.printf("► Blood Group     : %s\n", bloodGroup);
        System.out.printf("► Age             : %d\n", age);
        System.out.printf("► Contact         : %s\n", contact);
        System.out.printf("► Medical History : %s\n", medicalHistory);
        System.out.printf("► Donations Made  : %d\n", donationCount);
        System.out.println("════════════════════════════════════════════");
    }

    void addDonation(String date) {
        if (donationCount < donationDates.length) {
            donationDates[donationCount++] = date;
        } else {
            System.out.println("⚠ Maximum donation history reached.");
        }
    }

    void showDonationHistory() {
        System.out.println("\n╔══════════════════════════════════╗");
        System.out.println("║         DONATION HISTORY         ║");
        System.out.println("╚══════════════════════════════════╝");

        if (donationCount == 0) {
            System.out.println("⚠ No donation records found.");
        } else {
            for (int i = 0; i < donationCount; i++) {
                System.out.printf("► %d. %s\n", i + 1, donationDates[i]);
            }
        }
        System.out.println("════════════════════════════════════\n");
    }
}

public class BloodDonationSystem {
    static Donor[] donors = new Donor[100];
    static int donorCount = 0;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;

        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║  BLOOD DONATION MANAGEMENT SYSTEM   ║");
        System.out.println("╚══════════════════════════════════════╝");

        do {
            System.out.println("\n========== MENU ==========");
            System.out.println("1. Add Donor");
            System.out.println("2. View All Donors");
            System.out.println("3. Search Donor by Blood Group");
            System.out.println("4. Update Donor");
            System.out.println("5. Delete Donor");
            System.out.println("6. List Eligible Donors");
            System.out.println("7. Count Donors by Blood Group");
            System.out.println("8. Display Total Donors");
            System.out.println("9. Add Donation for Donor");
            System.out.println("10. View Donation History");
            System.out.println("11. Exit");
            System.out.print("Select Option (1-11): ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addDonor();
                case 2 -> viewDonors();
                case 3 -> searchByBloodGroup();
                case 4 -> updateDonor();
                case 5 -> deleteDonor();
                case 6 -> listEligibleDonors();
                case 7 -> countByBloodGroup();
                case 8 -> System.out.println("► Total Registered Donors: " + donorCount);
                case 9 -> addDonation();
                case 10 -> viewDonationHistory();
                case 11 -> System.out.println("Exiting system... Thank you!");
                default -> System.out.println("⚠ Invalid choice. Please try again.");
            }

        } while (choice != 11);
    }

    static void addDonor() {
        System.out.println("\n🩸 ADD NEW DONOR 🩸");
        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        // Blood Group Validation
        String[] validBG = { "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-" };
        String bloodGroup;
        boolean BGok;
        do {
            System.out.print("Enter Blood Group (A+, A-, B+, B-, AB+, AB-, O+, O-): ");
            bloodGroup = sc.nextLine().toUpperCase();
            BGok = false;
            for (String bg : validBG) {
                if (bg.equals(bloodGroup)) {
                    BGok = true;
                    break;
                }
            }
            if (!BGok) {
                System.out.println("⚠ Invalid blood group. Please enter a valid one.");
            }
        } while (!BGok);

        System.out.print("Enter Age: ");
        int age = sc.nextInt();
        sc.nextLine();

        // Contact number validation loop
        String contact;
        do {
            System.out.print("Enter Contact Number (11 digits): ");
            contact = sc.nextLine();
            if (!contact.matches("\\d{11}")) {
                System.out.println("⚠ Invalid contact number. It must be exactly 11 digits. Please try again.");
            }
        } while (!contact.matches("\\d{11}"));

        // Guided medical history questionnaire
        System.out.println("\nPlease answer the following medical history questions with Y or N:");
        String[] conditions = {
            "Heart Disease", "Diabetes", "Hypertension",
            "Hepatitis", "HIV/AIDS", "Severe Asthma", "Cancer"
        };
        List<String> historyList = new ArrayList<>();

        for (String condition : conditions) {
            String ans;
            do {
                System.out.printf("Do you have %s? (Y/N): ", condition);
                ans = sc.nextLine().trim().toUpperCase();
                if (!ans.equals("Y") && !ans.equals("N")) {
                    System.out.println("⚠ Please enter 'Y' or 'N'.");
                }
            } while (!ans.equals("Y") && !ans.equals("N"));

            if (ans.equals("Y")) {
                historyList.add(condition);
            }
        }

        String medicalHistory = historyList.isEmpty()
            ? "None"
            : String.join(", ", historyList);

        donors[donorCount++] = new Donor(name, bloodGroup, age, contact, medicalHistory);
        System.out.println("✔ Donor added successfully!");
    }

    static void viewDonors() {
        if (donorCount == 0) {
            System.out.println("⚠ No donors to display.\n");
            return;
        }

        System.out.println("\n📋 LIST OF ALL DONORS");
        for (int i = 0; i < donorCount; i++) {
            System.out.println("\n--- Donor ID: " + (i + 1) + " ---");
            donors[i].display();
        }
    }

    static void searchByBloodGroup() {
        System.out.print("Enter blood group to search: ");
        String group = sc.nextLine();
        boolean found = false;

        System.out.println("\n🔍 Search Results:");
        for (int i = 0; i < donorCount; i++) {
            if (donors[i].bloodGroup.equalsIgnoreCase(group)) {
                System.out.println("\n--- Donor ID: " + (i + 1) + " ---");
                donors[i].display();
                found = true;
            }
        }

        if (!found) {
            System.out.println("⚠ No donors found with blood group: " + group);
        }
    }

    static void updateDonor() {
        System.out.print("Enter Donor ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();

        if (id < 1 || id > donorCount) {
            System.out.println("⚠ Invalid Donor ID.");
            return;
        }

        Donor d = donors[id - 1];

        // Name
        System.out.println("Previous Name            : " + d.name);
        System.out.print  ("Enter New Name           : ");
        d.name = sc.nextLine();

        // Blood Group Validation
        String[] validBG = { "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-" };
        String newBG;
        boolean BGok;
        do {
            System.out.println("Previous Blood Group     : " + d.bloodGroup);
            System.out.print  ("Enter New Blood Group    : ");
            newBG = sc.nextLine().toUpperCase();
            BGok = false;
            for (String bg : validBG) {
                if (bg.equals(newBG)) {
                    BGok = true;
                    break;
                }
            }
            if (!BGok) {
                System.out.println("⚠ Invalid blood group. Please enter one of: A+, A-, B+, B-, AB+, AB-, O+, O-.");
            }
        } while (!BGok);
        d.bloodGroup = newBG;

        // Age
        System.out.println("Previous Age             : " + d.age);
        System.out.print  ("Enter New Age            : ");
        d.age = sc.nextInt();
        sc.nextLine();

        // Contact
        System.out.println("Previous Contact Number  : " + d.contact);
        System.out.print  ("Enter New Contact Number : ");
        d.contact = sc.nextLine();

        // Medical History
        System.out.println("Previous Medical History : " + d.medicalHistory);
        System.out.print  ("Enter New Medical History: ");
        d.medicalHistory = sc.nextLine();

        System.out.println("✔ Donor updated successfully.");
    }

    static void deleteDonor() {
        System.out.print("Enter Donor ID to delete: ");
        int id = sc.nextInt();
        sc.nextLine();

        if (id < 1 || id > donorCount) {
            System.out.println("⚠ Invalid Donor ID.");
            return;
        }

        for (int i = id - 1; i < donorCount - 1; i++) {
            donors[i] = donors[i + 1];
        }
        donorCount--;

        System.out.println("✔ Donor deleted successfully.");
    }

    static void listEligibleDonors() {
        System.out.println("\n🩺 ELIGIBLE DONORS (Age > 18, No serious illness)");
        boolean found = false;

        for (int i = 0; i < donorCount; i++) {
            String history = donors[i].medicalHistory.toLowerCase();
            if (donors[i].age > 18 &&
                !history.contains("cancer") &&
                !history.contains("hepatitis") &&
                !history.contains("hiv")) {
                System.out.println("\n--- Donor ID: " + (i + 1) + " ---");
                donors[i].display();
                found = true;
            }
        }

        if (!found) {
            System.out.println("⚠ No eligible donors found.");
        }
    }

    static void countByBloodGroup() {
        System.out.print("Enter blood group to count: ");
        String group = sc.nextLine();
        int count = 0;

        for (int i = 0; i < donorCount; i++) {
            if (donors[i].bloodGroup.equalsIgnoreCase(group)) {
                count++;
            }
        }

        System.out.println("✔ Total donors with blood group " + group + ": " + count);
    }

    static void addDonation() {
        System.out.print("Enter Donor ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        if (id < 1 || id > donorCount) {
            System.out.println("⚠ Invalid Donor ID.");
            return;
        }

        System.out.print("Enter donation date (YYYY-MM-DD): ");
        String date = sc.nextLine();

        donors[id - 1].addDonation(date);
        System.out.println("✔ Donation recorded successfully.");
    }

    static void viewDonationHistory() {
        System.out.print("Enter Donor ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        if (id < 1 || id > donorCount) {
            System.out.println("⚠ Invalid Donor ID.");
            return;
        }

        donors[id - 1].showDonationHistory();
    }
}
