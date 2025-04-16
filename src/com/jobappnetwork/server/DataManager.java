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
            String[] listingData = jobData.split("\\|");
            if (listingData.length < 6) {
                return "Error: Invalid job data format";
            }

            String jobId = "JOB" + nextJobId++;
            JobPosting job = new JobPosting(
                    jobId,
                    listingData[0], // title
                    listingData[1], // company
                    listingData[2], // location
                    listingData[3], // description
                    listingData[4], // skills
                    listingData[5] // salary
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
            String[] listingData = applicationData.split("\\|");
            if (listingData.length < 2) {
                return "Error: Invalid application data format";
            }

            String jobId = listingData[0];
            String resume = listingData[1];

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
     * Gets applications by job seeker ID.
     * 
     * @param jobSeekerId The job seeker ID
     * @return A formatted string of applications for the job seeker
     */
    public String getApplicationsByJobSeeker(String jobSeekerId) {
        List<Application> userApplications = new ArrayList<>();

        for (Application app : applications.values()) {
            if (app.getJobSeekerId().equals(jobSeekerId)) {
                userApplications.add(app);
            }
        }

        if (userApplications.isEmpty()) {
            return "No applications found for job seeker ID: " + jobSeekerId;
        }

        StringBuilder result = new StringBuilder("Your Applications:\n");
        for (Application app : userApplications) {
            result.append(app.toString()).append("\n");
        }

        return result.toString();
    }
}