# JobApplicationNetworkApp

A Java, terminal-based, client-server application that simulates the job application and hiring process. The system allows job seekers to browse job postings, submit applications, and track their application status. Hiring Managers can post jobs, view applications, and accept or reject an application.

## Features

- **Job Seeker Features:**

  - View available job postings
  - Apply to jobs with resume upload
  - Track application status
  - View application history

- **Hiring Manager Features:**
  - Post new job listings
  - View received applications
  - Accept or reject applications
  - Manage job postings

## Prerequisites

- Java Development Kit (JDK) 8 or higher


## Setup and Running

1. **Compile the Application:**

   ```bash
   javac -d bin src/com/jobappnetwork/**/*.java
   ```

2. **Start the Server:**

   ```bash
   java -cp bin com.jobappnetwork.Main
   ```

   Server port is set to 8080.

3. **Run the Client:**
   ```bash
   java -cp bin com.jobappnetwork.client.ClientLauncher
   ```

## Architecture

Client-server architecture:

- Server handles data management and logic 
- Clients have to connect to the server to perform any operations
- Communication is done via the protocol class
- Data is kept and managed server-side

