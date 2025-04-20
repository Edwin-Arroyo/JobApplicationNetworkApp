package com.jobappnetwork.server;

import com.jobappnetwork.protocol.Protocol;
import com.jobappnetwork.server.model.JobPosting;
import com.jobappnetwork.server.model.Application;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Manages data for the job application network.
 * This class is responsible for storing and retrieving job postings,
 * applications, and other data.
 */
public class DataManager {
    // In-memory data storage
    private final Map<String, JobPosting> jobPostings;
    private final Map<String, Application> applications;
    private int nextJobId;
    private int nextApplicationId;

    /**
     * Creates a new DataManager with empty data stores.
     */
    public DataManager() {
        this.jobPostings = new HashMap<>();
        this.applications = new HashMap<>();
        this.nextJobId = 1;
        this.nextApplicationId = 1;
    }

    /**
     * Step 5: Receive job posting/send it to users (job posting is created and
     * stored)
     */
    public String createJobPosting(String jobData) {
        try {
            String[] parts = jobData.split("\\|");
            // System.out.println("Debug - Received job data parts: " +
            // Arrays.toString(parts));
            if (parts.length != 6) {
                return "ERROR: Invalid job data format - Expected 6 parts, got " + parts.length;
            }

            String jobId = "JOB" + nextJobId++;
            JobPosting job = new JobPosting(
                    jobId,
                    parts[0], // title
                    parts[1], // company
                    parts[2], // location
                    parts[3], // description
                    parts[4], // skills
                    parts[5] // salary
            );

            jobPostings.put(jobId, job);
            return "Job posted successfully with ID: " + jobId;
        } catch (Exception e) {
            return "Error creating job posting: " + e.getMessage();
        }
    }

    /**
     * Step 8: Receive application for a job posting (application is created and
     * stored)
     */
    public String createApplication(String applicationData) {
        try {
            String[] parts = applicationData.split("\\|");
            // System.out.println("Debug - DataManager: Received application data parts: " +
            // Arrays.toString(parts));
            if (parts.length != 3) {
                return "ERROR: Invalid application data format - Expected 3 parts (jobId|applicantId|resume), got "
                        + parts.length;
            }

            String jobId = parts[0];
            String resume = parts[2];

            // Check if the job exists
            if (!jobPostings.containsKey(jobId)) {
                return "Error: Job not found";
            }

            // Generate a unique job seeker ID if not provided
            String jobSeekerId = "JS" + UUID.randomUUID().toString().substring(0, 8);

            String applicationId = "APP" + nextApplicationId++;
            Application application = new Application(
                    applicationId,
                    jobSeekerId,
                    jobId,
                    Protocol.STATUS_PENDING,
                    resume);

            applications.put(applicationId, application);
            return "Application submitted successfully with ID: " + applicationId;
        } catch (Exception e) {
            return "Error creating application: " + e.getMessage();
        }
    }

    /**
     * Step 9: Update job seeker application status (status is updated here)
     */
    public String updateApplicationStatus(String applicationData, int newStatus) {
        try {
            String applicationId = applicationData;

            // Check if the application exists
            if (!applications.containsKey(applicationId)) {
                return "Error: Application not found";
            }

            Application application = applications.get(applicationId);
            application.setStatus(newStatus);

            return "Application status updated successfully";
        } catch (Exception e) {
            return "Error updating application status: " + e.getMessage();
        }
    }

    /**
     * Step 12: Update a job posting (would be implemented here if updating postings
     * is supported)
     */
    // (No method for updating job postings, but would go here)

    /**
     * Step 13: Send posting update to job seekers (would be implemented here if
     * supported)
     */
    // (No method for sending posting updates, but would go here)

    /**
     * Step 14: Print out updated posting (job postings are formatted and returned)
     */
    public String getAllJobPostings() {
        // System.out.println("Debug - Getting all job postings. Current count: " +
        // jobPostings.size());
        // System.out.println("Debug - Job postings map: " + jobPostings);

        if (jobPostings.isEmpty()) {
            return "No job postings available";
        }

        StringBuilder result = new StringBuilder("Available Jobs:\n");
        for (JobPosting job : jobPostings.values()) {
            result.append(job.toString()).append("\n");
        }

        return result.toString();
    }

    /**
     * Gets all applications.
     * 
     * @return A formatted string of all applications
     */
    public String getAllApplications() {
        if (applications.isEmpty()) {
            return "No applications available";
        }

        StringBuilder result = new StringBuilder("All Applications:\n");
        for (Application app : applications.values()) {
            result.append(app.toString()).append("\n");
        }

        return result.toString();
    }

    /**
     * Gets applications for the current job seeker.
     * 
     * @return A formatted string of applications for the current job seeker
     */
    public String getCurrentJobSeekerApplications() {
        List<Application> userApplications = new ArrayList<>();

        for (Application app : applications.values()) {
            // For now, we'll return all applications since we don't have a way to identify
            // the current user
            // In a real application, we would use session management or authentication
            userApplications.add(app);
        }

        if (userApplications.isEmpty()) {
            return "No applications found for the current job seeker";
        }

        StringBuilder result = new StringBuilder("Your Applications:\n");
        for (Application app : userApplications) {
            result.append("Application ID: ").append(app.getId()).append("\n");
            result.append("Status: ").append(getStatusText(app.getStatus())).append("\n");

            // Get and display job details
            String jobId = app.getJobPostingId();
            JobPosting job = jobPostings.get(jobId);
            if (job != null) {
                result.append("\nJob Details:\n");
                result.append("Title: ").append(job.getTitle()).append("\n");
                result.append("Company: ").append(job.getCompany()).append("\n");
                result.append("Location: ").append(job.getLocation()).append("\n");
                result.append("Description: ").append(job.getDescription()).append("\n");
                result.append("Skills: ").append(job.getSkills()).append("\n");
                result.append("Salary: ").append(job.getSalary()).append("\n");
            } else {
                result.append("Job details not available (Job ID: ").append(jobId).append(")\n");
            }

            result.append("\n-------------------\n");
        }

        return result.toString();
    }

    /**
     * Gets the text representation of an application status.
     * 
     * @param status The status code
     * @return The text representation of the status
     */
    private String getStatusText(int status) {
        switch (status) {
            case 300:
                return "Pending";
            case 301:
                return "Accepted";
            case 302:
                return "Rejected";
            default:
                return "Unknown";
        }
    }
}