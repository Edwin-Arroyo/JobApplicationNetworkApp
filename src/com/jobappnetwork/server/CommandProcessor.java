package com.jobappnetwork.server;

import com.jobappnetwork.protocol.Protocol;
import java.util.Arrays;
import java.util.List;

/**
 * Processes commands received from clients.
 * This class is responsible for interpreting commands and
 * delegating them to the appropriate data manager methods.
 */
public class CommandProcessor {
    private final DataManager dataManager;

    // Commands that require additional data
    private static final List<Integer> DATA_REQUIRING_COMMANDS = Arrays.asList(
            Protocol.POST_JOB,
            Protocol.APPLY_TO_JOB,
            Protocol.ACCEPT_APPLICATION,
            Protocol.REJECT_APPLICATION);

    /**
     * Creates a new CommandProcessor with the given data manager.
     * 
     * @param dataManager The data manager to use for data operations
     */
    public CommandProcessor(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    /**
     * Processes a command that doesn't require additional data.
     * 
     * @param command The command to process
     * @return The response to send back to the client
     */
    public String processCommand(int command) {
        // System.out.println("Debug - Processing command: " +
        // Protocol.getCommandName(command));

        switch (command) {
            case Protocol.VIEW_JOBS:
                // Step 6: View job postings
                // System.out.println("Debug - Handling VIEW_JOBS command");
                return dataManager.getAllJobPostings();

            case Protocol.VIEW_APPLICATIONS:
                return dataManager.getAllApplications();

            case Protocol.VIEW_MY_APPLICATIONS:
                return "Please provide your job seeker ID";

            case Protocol.SELECT_ROLE:
                return "Please select a role (1 for Job Seeker, 2 for Hiring Manager)";

            default:
                return "Unknown command: " + command;
        }
    }

    /**
     * Processes a command that requires additional data.
     * 
     * @param command The command to process
     * @param data    The additional data for the command
     * @return The response to send back to the client
     */
    public String processCommandWithData(int command, String data) {
        // System.out.println("Debug - Processing command: " +
        // Protocol.getCommandName(command) + ", data: " + data);

        switch (command) {
            case Protocol.POST_JOB:
                return dataManager.createJobPosting(data);

            case Protocol.APPLY_TO_JOB:
                // Step 7: Send application to job posting
                return dataManager.createApplication(data);

            case Protocol.ACCEPT_APPLICATION:
                return dataManager.updateApplicationStatus(data, Protocol.STATUS_ACCEPTED);

            case Protocol.REJECT_APPLICATION:
                return dataManager.updateApplicationStatus(data, Protocol.STATUS_REJECTED);

            case Protocol.VIEW_MY_APPLICATIONS:
                return dataManager.getApplicationsByJobSeeker(data);

            default:
                return "Unknown command with data: " + command;
        }
    }

    /**
     * Checks if a command requires additional data.
     * 
     * @param command The command to check
     * @return true if the command requires additional data, false otherwise
     */
    public boolean requiresData(int command) {
        return DATA_REQUIRING_COMMANDS.contains(command);
    }
}