package com.jobappnetwork.server.model;

/**
 * Represents a job posting in the system.
 */
public class JobPosting {
    private String id;
    private String title;
    private String company;
    private String location;
    private String description;
    private String skills;
    private String salary;

    /**
     * Step 4: Hiring Manager selects 'Create a job posting' (GUI triggers this
     * constructor)
     * Step 5: Server receives JobPosting and sends it to users (JobPosting object
     * is created and distributed)
     */
    public JobPosting(String id, String title, String company, String location,
            String description, String skills, String salary) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.location = location;
        this.description = description;
        this.skills = skills;
        this.salary = salary;
    }

    /**
     * Gets the job posting ID.
     * 
     * @return The job posting ID
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the job title.
     * 
     * @return The job title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the company name.
     * 
     * @return The company name
     */
    public String getCompany() {
        return company;
    }

    /**
     * Gets the job location.
     * 
     * @return The job location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Gets the job description.
     * 
     * @return The job description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the required skills.
     * 
     * @return The required skills
     */
    public String getSkills() {
        return skills;
    }

    /**
     * Gets the salary range.
     * 
     * @return The salary range
     */
    public String getSalary() {
        return salary;
    }

    /**
     * Step 12: Update a job posting (A new JobPosting object may be created with
     * updated info)
     */
    // (No setters present, but updating would involve creating a new JobPosting)

    /**
     * Step 14: Print out updated posting (toString() used to display job posting)
     */
    @Override
    public String toString() {
        return String.format(
                "ID: %s\nTitle: %s\nCompany: %s\nLocation: %s\nDescription: %s\nSkills: %s\nSalary: %s",
                id, title, company, location, description, skills, salary);
    }
}