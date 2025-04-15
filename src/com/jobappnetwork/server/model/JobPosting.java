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
     * Creates a new job posting.
     * 
     * @param id          The job posting ID
     * @param title       The job title
     * @param company     The company name
     * @param location    The job location
     * @param description The job description
     * @param skills      Required skills
     * @param salary      Salary range
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
     * Returns a string representation of the job posting.
     * 
     * @return A formatted string with job details
     */
    @Override
    public String toString() {
        return String.format(
                "ID: %s\nTitle: %s\nCompany: %s\nLocation: %s\nDescription: %s\nSkills: %s\nSalary: %s",
                id, title, company, location, description, skills, salary);
    }
}