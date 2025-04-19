package com.jobappnetwork.client;

import java.util.Scanner;
import java.util.UUID;

/**
 * A launcher class for the client application.
 * This class allows the user to choose between running as a JobSeeker or
 * HiringManager.
 */
public class ClientLauncher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("===== JOB APPLICATION NETWORK CLIENT =====");
        System.out.println("1. Login as Job Seeker");
        System.out.println("2. Login as Hiring Manager");
        System.out.print("Enter your choice: ");

        int choice = 0;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.close();
            return;
        }

        System.out.print("Enter your full name: ");
        String fullName = scanner.nextLine();

        switch (choice) {
            case 1:
                JobSeeker jobSeeker = new JobSeeker(fullName);
                // Generate a unique ID for the job seeker
                String jobSeekerId = UUID.randomUUID().toString();
                jobSeeker.setId(jobSeekerId);
                // System.out.println("Debug - ClientLauncher: Created job seeker with ID: " +
                // jobSeekerId);

                ClientInteraction clientInteraction = new ClientInteraction();
                jobSeeker.showMenu(clientInteraction);
                clientInteraction.close();
                break;
            case 2:
                HiringManager hiringManager = new HiringManager(fullName);
                // Generate a unique ID for the hiring manager
                String hiringManagerId = UUID.randomUUID().toString();
                hiringManager.setId(hiringManagerId);
                // System.out.println("Debug - ClientLauncher: Created hiring manager with ID: "
                // + hiringManagerId);

                ClientInteraction hmClientInteraction = new ClientInteraction();
                hiringManager.showMenu(hmClientInteraction);
                hmClientInteraction.close();
                break;
            default:
                System.out.println("Invalid choice. Please run the program again.");
        }

        scanner.close();
    }
}