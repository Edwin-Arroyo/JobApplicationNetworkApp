package com.jobappnetwork.client;

import com.jobappnetwork.protocol.Protocol;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a hiring manager on the app.
 * This class contains methods for hiring managers to interact with the
 * application.
 */
public class HiringManager {
    private String id;
    private String fullName;
    private List<String> jobPostings; // List of job posting IDs
    private Scanner scanner;

    /**
     * Creates a new HiringManager
     * 
     * @param fullName The hiring manager's full name
     */
    public HiringManager(String fullName) {
        this.fullName = fullName;
        this.jobPostings = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the hiring manager menu and handles user input.
     * 
     * @param clientInteraction The client interaction object to communicate with
     *                          the server
     */
    public void showMenu(ClientInteraction clientInteraction) {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n===== HIRING MANAGER MENU =====");
            System.out.println("1. Post a New Job");
            System.out.println("2. View Applications");
            System.out.println("3. Accept an Application");
            System.out.println("4. Reject an Application");
            System.out.println("5. Disconnect from the server");
            System.out.print("Enter your choice: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    postJob(clientInteraction);
                    break;
                case 2:
                    viewApplications(clientInteraction);
                    break;
                case 3:
                    acceptApplication(clientInteraction);
                    break;
                case 4:
                    rejectApplication(clientInteraction);
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Allows the hiring manager to post a new job.
     * 
     * @param clientInteraction The client interaction object to communicate with
     *                          the server
     */
    private void postJob(ClientInteraction clientInteraction) {
        System.out.println("\n===== POST A NEW JOB =====");

        System.out.print("Enter job title: ");
        String jobTitle = scanner.nextLine();

        System.out.print("Enter company name: ");
        String companyName = scanner.nextLine();

        System.out.print("Enter job location: ");
        String location = scanner.nextLine();

        System.out.print("Enter job description: ");
        String description = scanner.nextLine();

        System.out.print("Enter required skills (comma-separated): ");
        String skills = scanner.nextLine();

        System.out.print("Enter salary range: ");
        String salaryRange = scanner.nextLine();

        // Format job data for transmission
        String jobData = String.format("%s|%s|%s|%s|%s|%s",
                jobTitle, // parts[0] - title
                companyName, // parts[1] - company
                description, // parts[2] - description
                location, // parts[3] - location
                skills, // parts[4] - skills
                salaryRange // parts[5] - salary
        );

        // System.out.println("Debug - Sending job data: " + jobData);

        // Send the job data directly without sending a separate command first
        String response = clientInteraction.sendCommandWithData(Protocol.POST_JOB, jobData);

        // Display the response
        System.out.println(response);
    }

    /**
     * Allows the hiring manager to view applications for their job postings.
     * 
     * @param clientInteraction The client interaction object to communicate with
     *                          the server
     */
    private void viewApplications(ClientInteraction clientInteraction) {
        System.out.println("\n===== VIEW APPLICATIONS =====");

        // Send request to view applications
        String response = clientInteraction.sendCommand(Protocol.VIEW_APPLICATIONS);

        // Display the response
        System.out.println(response);
    }

    /**
     * Allows the hiring manager to accept an application.
     * 
     * @param clientInteraction The client interaction object to communicate with
     *                          the server
     */
    private void acceptApplication(ClientInteraction clientInteraction) {
        System.out.println("\n===== ACCEPT APPLICATION =====");

        System.out.print("Enter application ID to accept: ");
        String applicationId = scanner.nextLine();

        // Send the application ID
        // System.out.println("Debug - HiringManager: Sending application ID to accept:
        // " + applicationId);
        String response = clientInteraction.sendCommandWithData(Protocol.ACCEPT_APPLICATION, applicationId);

        // Display the response
        System.out.println(response);
    }

    /**
     * Allows the hiring manager to reject an application.
     * 
     * @param clientInteraction The client interaction object to communicate with
     *                          the server
     */
    private void rejectApplication(ClientInteraction clientInteraction) {
        System.out.println("\n===== REJECT APPLICATION =====");

        System.out.print("Enter application ID to reject: ");
        String applicationId = scanner.nextLine();

        // Send the application ID
        // System.out.println("Debug - HiringManager: Sending application ID to reject:
        // " + applicationId);
        String response = clientInteraction.sendCommandWithData(Protocol.REJECT_APPLICATION, applicationId);

        // Display the response
        System.out.println(response);
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

    public List<String> getJobPostings() {
        return new ArrayList<>(jobPostings);
    }
}
