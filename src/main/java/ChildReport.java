
import java.io.*;
import java.util.*;

public class ChildReport {

    String name;
    String date;
    String behaviour;
    String healthStatus;
    String activities;
    String notes;

    public ChildReport(String name, String date, String behaviour, String healthStatus, String activities, String notes) {
        this.name = name;
        this.date = date;
        this.behaviour = behaviour;
        this.healthStatus = healthStatus;
        this.activities = activities;
        this.notes = notes;
    }

    public static void writeReport() {
        Scanner sc = new Scanner(System.in);
        // ------------------ Menu ----------------
        while (true) {
            System.out.println("\n==== KidsCare Report System ====");
            System.out.println("1. Record New Report");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");

            String option = sc.nextLine();

            if (option.equals("2")) {
                System.out.println("Exiting system...");
                return;
            } else if (!option.equals("1")) {
                System.out.println("Invalid choice. Please enter 1 or 2.");
                continue;
            }

                // -------- option 1 : Record new report ---------
                List<String> kids = new ArrayList<>();

                //------- read children name from input file --------------------
                try (BufferedReader br = new BufferedReader(new FileReader("inputFile.txt"))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        line = line.trim();
                        if (line.isEmpty()) {
                            continue;
                        }
                        if (line.toLowerCase().startsWith("child")) {

                            String detailsLine = br.readLine();
                            if (detailsLine != null) {
                                detailsLine = detailsLine.trim();

                                String[] parts = detailsLine.split(",");
                                String firstName = "";
                                String lastName = "";
                                for (String part : parts) {
                                    part = part.trim();
                                    if (part.toLowerCase().startsWith("first name:")) {
                                        firstName = part.substring(part.indexOf(":") + 1).trim();
                                    } else if (part.toLowerCase().startsWith("last name:")) {
                                        lastName = part.substring(part.indexOf(":") + 1).trim();
                                    }
                                }
                                if (!firstName.isEmpty() && !lastName.isEmpty()) {
                                    kids.add(firstName + " " + lastName);
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Error reading input file.");
                    e.printStackTrace();
                    return;
                }

                if (kids.isEmpty()) {
                    System.out.println("No kids found in the input file.");
                    return;
                }

                //-------------- List children name and choose one to write report for --------------------
                System.out.println("\nList of kids:");
                for (int i = 0; i < kids.size(); i++) {
                    System.out.println((i + 1) + ". " + kids.get(i));
                }

                System.out.print("Enter the number of the kid to write a report for: ");
                int choice;
                try {
                    choice = Integer.parseInt(sc.nextLine());
                    if (choice < 1 || choice > kids.size()) {
                        System.out.println("Invalid choice.");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input.");
                    continue;
                }

                String name = kids.get(choice - 1);

                //------------------ Teacher enters report details-------------
                System.out.print("Date (DD-MM-YYYY): ");
                String date = sc.nextLine();
                System.out.print("Behaviour: ");
                String behaviour = sc.nextLine();
                System.out.print("Health status: ");
                String healthStatus = sc.nextLine();
                System.out.print("Activities done today: ");
                String activities = sc.nextLine();
                System.out.print("Additional notes: ");
                String notes = sc.nextLine();

                ChildReport child = new ChildReport(name, date, behaviour, healthStatus, activities, notes);

                //-------------- save the report into a file ------------
                try (BufferedWriter bw = new BufferedWriter(new FileWriter("reports.txt", true))) {
                    bw.write(generateReportText(child));
                    System.out.println("\nReport saved successfully to reports.txt");
                } catch (IOException e) {
                    System.out.println("Error saving report.");
                    e.printStackTrace();
                }

                System.out.println("Report submitted successfully and sent to parent!");
            }
            
        } 

    private static String generateReportText(ChildReport child) {
        return "Report for " + child.name + " (Date: " + child.date + ")\n"
                + "Behaviour: " + child.behaviour + "\n"
                + "Health Status: " + child.healthStatus + "\n"
                + "Activities: " + child.activities + "\n"
                + "Additional Notes: " + child.notes + "\n"
                + "---------------------------\n";
    }

    public static void writeReportForTest(String name, String date, String behaviour,
            String healthStatus, String activities, String notes,
            String outputFile) {
        ChildReport child = new ChildReport(name, date, behaviour, healthStatus, activities, notes);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile, true))) {
            bw.write(generateReportText(child));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
