package com.jobappnetwork.server.model;

/**
 * Represents a job application in the system.
 */
public class Application {
    private String id;
    private String jobSeekerId;
    private String jobPostingId;
    private int status;
    private String resume;

    /**
     * Creates a new application.
     * 
     * @param id           The application ID
     * @param jobSeekerId  The job seeker ID
     * @param jobPostingId The job posting ID
     * @param status       The application status
     * @param resume       The resume content
     */
    public Application(String id, String jobSeekerId, String jobPostingId,
            int status, String resume) {
        this.id = id;
        this.jobSeekerId = jobSeekerId;
        this.jobPostingId = jobPostingId;
        this.status = status;
        this.resume = resume;
    }

    /**
     * Gets the application ID.
     * 
     * @return The application ID
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the job seeker ID.
     * 
     * @return The job seeker ID
     */
    public String getJobSeekerId() {
        return jobSeekerId;
    }

    /**
     * Gets the job posting ID.
     * 
     * @return The job posting ID
     */
    public String getJobPostingId() {
        return jobPostingId;
    }

    /**
     * Gets the application status.
     * 
     * @return The application status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the application status.
     * 
     * @param status The new status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Gets the resume content.
     * 
     * @return The resume content
     */
    public String getResume() {
        return resume;
    }

    /**
     * Returns a string representation of the application.
     * 
     * @return A formatted string with application details
     */
    @Override
    public String toString() {
        String statusText;
        switch (status) {
            case 300:
                statusText = "Pending";
                break;
            case 301:
                statusText = "Accepted";
                break;
            case 302:
                statusText = "Rejected";
                break;
            default:
                statusText = "Unknown";
        }

        return String.format(
                "ID: %s\nJob Seeker ID: %s\nJob Posting ID: %s\nStatus: %s",
                id, jobSeekerId, jobPostingId, statusText);
    }
}