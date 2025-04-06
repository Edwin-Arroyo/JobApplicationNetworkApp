package com.jobappnetwork.protocol;
import java.util.List;
import java.util.Arrays;
/*
 *  Protocol for the Job Application Network Application 
 * There will be 2 user roles - Job seeker and Hiring Managers
 * 
 */
public class Protocol {
    
    // initial state 
    public static final int WAITING = 0;

    // Client Role Selection
    public static final int SELECT_ROLE = 1;
    public static final int ROLE_JOB_SEEKER = 10;
    public static final int ROLE_HIRING_MANAGER = 11;

    // Job Seeker Actions (100s)
    public static final int VIEW_JOBS = 100;
    public static final int APPLY_TO_JOB = 101;
    public static final int VIEW_MY_APPLICATIONS = 102;

    // Hiring Manager Actions (200s)
    public static final int POST_JOB = 200;
    public static final int VIEW_APPLICATIONS = 201;
    public static final int ACCEPT_APPLICATION = 202;
    public static final int REJECT_APPLICATION = 203;

    // Job Application statuses (300s)
    public static final int STATUS_PENDING = 300;
    public static final int STATUS_ACCEPTED = 301;
    public static final int STATUS_REJECTED = 302;

    // Server codes (400s)
    public static final int SUCCESS = 400;
    public static final int FAILURE = 401;

    /*
     * Translate the integer codes into strings
     * 
     * @param code - integer that corresponds to a state in the application
     */
    public static String getCommandName(int code) {
        switch (code) {
            case WAITING: return "WAITING";
            case SELECT_ROLE: return "SELECT_ROLE";
            case ROLE_JOB_SEEKER: return "ROLE_JOB_SEEKER";
            case ROLE_HIRING_MANAGER: return "ROLE_HIRING_MANAGER";
            case VIEW_JOBS: return "SEARCH_JOBS";
            case APPLY_TO_JOB: return "APPLY_TO_JOB";
            case VIEW_MY_APPLICATIONS: return "VIEW_MY_APPLICATIONS";
            case POST_JOB: return "POST_JOB";
            case VIEW_APPLICATIONS: return "VIEW_APPLICATIONS";
            case ACCEPT_APPLICATION: return "ACCEPT_APPLICATION";
            case REJECT_APPLICATION: return "REJECT_APPLICATION";
            case STATUS_PENDING: return "STATUS_PENDING";
            case STATUS_ACCEPTED: return "STATUS_ACCEPTED";
            case STATUS_REJECTED: return "STATUS_REJECTED";
            case SUCCESS: return "RESPONSE_SUCCESS";
            case FAILURE: return "RESPONSE_FAILURE";
            default: return "UNKNOWN_COMMAND (" + code + ")";
        }
    }

    /*
     * Checks if a command is valid 
     * 
     * @param code - corresponds to a state in the applcation 
     * @return true if the command is valid, false if it is not 
     */
    public static boolean isValidCommand(int code){
        // code should be in between any given state in the application 
        return code >= WAITING && code <= FAILURE;
    }

    /*
     * Checks is a client is a job seeker 
     * 
     * @param code - corresponds to a state in the application 
     * @return true if the command belongs to a job seeker, false otherwise
     */
    public static boolean isJobSeeker(int code){
        return code >= 100 && code < 200; // job seeker codes range form 100 - 199
    }

    /*
     * Checks if a client is a hiring manager 
     * 
     * @param code - corresponds to a state in the program 
     * @return true is the client is a hiring manager, false otherwise 
     */
    public static boolean isHiringManager(int code){
            return code >= 200 && code < 300; // Hiring manager codes range from 200-300
    }

    /*
     * Returns a list of the available actions to a job seeker 
     * 
     * @return a list of available actions 
     */
    public static List<Integer> getJobSeekerActions(){
            return Arrays.asList(VIEW_JOBS, APPLY_TO_JOB, VIEW_MY_APPLICATIONS);
    }

    /*
     * Returns a list of available actions to the hiring manager 
     * 
     * @return list of available actions for a hiring manager 
     */
    public static List<Integer> getHiringManagerCommands() {
        return Arrays.asList(POST_JOB, VIEW_APPLICATIONS, ACCEPT_APPLICATION, REJECT_APPLICATION);
    }

}
