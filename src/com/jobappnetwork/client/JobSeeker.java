package com.jobappnetwork.client;

import com.jobappnetwork.protocol.Protocol;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * Represents a job seeker on the app.
 * This class contains methods for job seekers to interact with the application.
 */
public class JobSeeker {
    private String id;
    private String fullName;
    private String resume;
    private List<String> skills; // List of job seeker's skills
    private List<String> applications; // List of application IDs
    private Scanner scanner;

    /**
     * Creates a new JobSeeker
     * 
     * @param fullName The job seeker's full name
     */
    public JobSeeker(String fullName) {
        this.fullName = fullName;
        this.skills = new ArrayList<>();
        this.applications = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the job seeker menu and handles user input.
     * 
     * @param clientInteraction The client interaction object to communicate with
     *                          the server
     */
    public void showMenu(ClientInteraction clientInteraction) {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n===== JOB SEEKER MENU =====");
            System.out.println("1. View Available Jobs");
            System.out.println("2. Apply to a Job");
            System.out.println("3. View My Applications");
            System.out.println("4. Disconnect from the server");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    viewJobs(clientInteraction);
                    break;
                case 2:
                    applyToJob(clientInteraction);
                    break;
                case 3:
                    viewMyApplications(clientInteraction);
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Views all available job postings.
     * 
     * @param clientInteraction The client interaction object to communicate with
     *                          the server
     */
    private void viewJobs(ClientInteraction clientInteraction) {
        System.out.println("\n===== AVAILABLE JOBS =====");
        // Send request to view jobs
        // System.out.println("Debug - JobSeeker: Sending VIEW_JOBS command (" +
        // Protocol.VIEW_JOBS + ")");
        String response = clientInteraction.sendCommand(Protocol.VIEW_JOBS);
        // System.out.println("Debug - JobSeeker: Received response from server");

        // Display the response with proper formatting
        if (response != null && !response.trim().isEmpty()) {
            System.out.println("\n" + response);
        } else {
            System.out.println("\nNo jobs are currently available.");
        }

        // Add a pause to let user read the listings
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Allows the job seeker to apply to a job.
     * 
     * @param clientInteraction The client interaction object to communicate with
     *                          the server
     */
    private void applyToJob(ClientInteraction clientInteraction) {
        System.out.println("\n===== APPLY TO JOB =====");
        System.out.print("Enter job ID to apply: ");
        String jobId = scanner.nextLine();

        // Ask if user wants to upload a file or enter text
        System.out.println("\nResume Options:");
        System.out.println("1. Upload a file");
        System.out.println("2. Enter resume text");
        System.out.print("Enter your choice: ");

        int resumeChoice = getIntInput();
        String resumeContent = "";

        if (resumeChoice == 1) {
            // File upload option
            System.out.print("Enter the path to your resume file: ");
            String filePath = scanner.nextLine();

            try {
                File resumeFile = new File(filePath);
                if (!resumeFile.exists()) {
                    System.out.println("File not found. Please check the path and try again.");
                    return;
                }

                // Read the file and convert to Base64
                FileInputStream fileInputStream = new FileInputStream(resumeFile);
                byte[] fileBytes = new byte[(int) resumeFile.length()];
                fileInputStream.read(fileBytes);
                fileInputStream.close();

                // Convert to Base64
                resumeContent = Base64.getEncoder().encodeToString(fileBytes);
                System.out.println("Resume file uploaded successfully.");
            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
                return;
            }
        } else if (resumeChoice == 2) {
            // Text input option
            System.out.println("Enter your resume text (type 'END' on a new line when finished):");
            StringBuilder resumeBuilder = new StringBuilder();
            String line;
            while (!(line = scanner.nextLine()).equals("END")) {
                resumeBuilder.append(line).append("\n");
            }
            resumeContent = resumeBuilder.toString();
        } else {
            System.out.println("Invalid choice. Returning to menu.");
            return;
        }

        // Send the application data
        // Format: jobId|applicantId|resume
        String applicationData = jobId + "|" + id + "|" + resumeContent;
        // System.out.println("Debug - JobSeeker: Sending application data with jobId: "
        // + jobId + ", applicantId: " + id);
        String response = clientInteraction.sendCommandWithData(Protocol.APPLY_TO_JOB, applicationData);

        // Display the response
        System.out.println(response);
    }

    /**
     * Displays all applications submitted by the job seeker.
     * 
     * @param clientInteraction The client interaction object to communicate with
     *                          the server
     */
    private void viewMyApplications(ClientInteraction clientInteraction) {
        System.out.println("\n===== MY APPLICATIONS =====");
        // Step 11 & 14: Print out new application status and updated posting
        // System.out.println("Debug - JobSeeker: Sending VIEW_MY_APPLICATIONS command
        // with ID: " + id);
        String response = clientInteraction.sendCommand(Protocol.VIEW_MY_APPLICATIONS);

        // Display the response
        System.out.println(response);
    }

    /**
     * Adds a skill to the job seeker's profile.
     * 
     * @param skill The skill to add
     */
    public void addSkill(String skill) {
        if (!skills.contains(skill)) {
            skills.add(skill);
        }
    }

    /**
     * Updates the job seeker's resume.
     * 
     * @param resume The new resume
     */
    public void updateResume(String resume) {
        this.resume = resume;
    }

    /**
     * Helper method to get integer input from the user
     */
    private int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getResume() {
        return resume;
    }

    public List<String> getSkills() {
        return new ArrayList<>(skills);
    }

    public List<String> getApplications() {
        return new ArrayList<>(applications);
    }
}