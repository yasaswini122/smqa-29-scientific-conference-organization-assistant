import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;

class Attendee extends JFrame {
    private String username;
    private String password;
    private static final String ATTENDEE_FILE_PATH = "attendees.txt";
    private static String loggedInAttendee;
    private List<String> downloadedMaterials;
    private List<String> sessionLinks;
    private List<String> sessionRecommendations;
    private List<String> joinedNetworkingSessions;
    private List<String> createdSessionLinks;
    private int[] attendees = { 10, 30, 15, 25, 20 };

    public Attendee(String username, String password) {
        this.username = username;
        this.password = password;
        this.downloadedMaterials = new ArrayList<>();
        this.sessionLinks = new ArrayList<>();
        this.sessionRecommendations = new ArrayList<>();
        this.joinedNetworkingSessions = new ArrayList<>();
        this.createdSessionLinks = new ArrayList<>();
        setTitle("Attendees Count Graph");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static boolean login(String username, String password) {
        try {
            FileReader reader = new FileReader(ATTENDEE_FILE_PATH);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] credentials = line.split(":");
                if (credentials.length == 2) {
                    String storedUsername = credentials[0].trim();
                    String storedPassword = credentials[1].trim();
                    if (storedUsername.equals(username) && storedPassword.equals(password)) {
                        loggedInAttendee = username;
                        System.out.println("Attendee login successful!");
                        bufferedReader.close();
                        return true;
                    }
                }
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Attendee login failed. Incorrect username or password.");
        return false;
    }

    public static boolean simulateAttendeeQASession() {
        // Simulate attendee login
        System.out.println("Welcome, Attendee!");
        // Q&A with the attendee
        System.out.println("Q&A Session with Attendee:");

        // Ask about interests and preferences
        System.out.println("What are your interests or preferences for sessions?");
        System.out.println("for example Java,Web,Mobile,AI..");
        System.out.println("Enter it:");
        Scanner scanner = new Scanner(System.in);
        String interests = scanner.nextLine();
        System.out.println("Interests: " + interests);

        // Provide session recommendations based on interests
        System.out.println("Here are some recommended sessions for you:");
        List<Session> recommendedSessions = recommendSessionsBasedOnInterests(interests);
        displayRecommendedSessions(recommendedSessions);

        System.out.println("Leave us a feedback?");
        String feedback = scanner.nextLine();
        if (feedback.isEmpty()) {
            System.out.println("No feedback has been!");
        } else {
            System.out.println("your feedback" + feedback + "has been taken!");
        }
        return true;
    }

    private static List<Session> recommendSessionsBasedOnInterests(String interests) {
        List<Session> allSessions = createSampleSessions();
        writeSessionsToFile(allSessions);
        List<Session> recommendedSessions = new ArrayList<>();
        for (Session session : allSessions) {
            if (session.getDescription().toLowerCase().contains(interests.toLowerCase())) {
                recommendedSessions.add(session);
            }
        }

        return recommendedSessions;
    }

    private static void displayRecommendedSessions(List<Session> sessions) {
        if (sessions.isEmpty()) {
            System.out.println("No sessions matching your interests.");
        } else {
            for (Session session : sessions) {
                System.out.println("Title: " + session.getTitle());
                System.out.println("Description: " + session.getDescription());
                System.out.println("---------------");
            }
        }
    }

    private static List<Session> createSampleSessions() {
        // Simulate a list of sessions with predefined titles and descriptions.
        List<Session> sessions = new ArrayList<>();
        sessions.add(new Session("Java Programming Basics", "Introduction to Java programming fundamentals."));
        sessions.add(new Session("Web Development Workshop", "Hands-on session on building web applications."));
        sessions.add(new Session("Data Science Essentials", "Exploring the world of data science and analytics."));
        sessions.add(new Session("Mobile App Development", "Learn to build mobile applications for iOS and Android."));
        sessions.add(new Session("Cloud Computing Overview", "Introduction to cloud computing technologies."));
        sessions.add(new Session("Cybersecurity Best Practices", "Securing your applications and data."));
        sessions.add(new Session("AI and Machine Learning Trends", "Current trends and applications of AI and ML."));

        return sessions;
    }

    private static void writeSessionsToFile(List<Session> sessions) {
        try {
            FileWriter writer = new FileWriter("sessions.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            for (Session session : sessions) {
                bufferedWriter.write("Title: " + session.getTitle() + "\n");
                bufferedWriter.write("Description: " + session.getDescription() + "\n\n");
            }

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean downloadSpeakerPDF(String speakerToDownload) {
        try {
            // Read the presentations.txt file
            String presentationsFilePath = "presentations.txt";
            String pdfUrl = "";

            for (String line : Files.readAllLines(Paths.get(presentationsFilePath))) {
                if (line.trim().startsWith(speakerToDownload + ":")) {
                    pdfUrl = line.trim().substring(speakerToDownload.length() + 1);
                    break;
                }
            }

            if (!pdfUrl.isEmpty()) {
                // Generate a unique filename based on current time
                String currentTime = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String newFileName = speakerToDownload + "_" + currentTime + ".pdf";

                // Download and save the PDF
                // Replace the following line with your actual download logic
                // This could involve copying the file, downloading it from a URL, etc.
                System.out.println("Downloading " + pdfUrl + " for " + speakerToDownload);

                // Save the downloaded PDF to a new file
                savePDFToFile(pdfUrl, newFileName);
            } else {
                System.out.println("Speaker with username " + speakerToDownload + " not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private static void savePDFToFile(String pdfUrl, String newFileName) {
        try {
            downloadFile(pdfUrl, System.getProperty("user.dir"), newFileName);
            System.out.println("File downloaded successfully.");
        } catch (IOException e) {
            System.err.println("Error downloading file: " + e.getMessage());
        }
        System.out.println("Saving PDF to file: " + newFileName);
    }

    private static void downloadFile(String fileURL, String saveDir, String fileName) throws IOException {
        URL url = new URL(fileURL);
        try (BufferedInputStream in = new BufferedInputStream(url.openStream());
                FileOutputStream fileOutputStream = new FileOutputStream(saveDir + "/" + fileName + ".pdf")) {

            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        }
    }

    public static boolean polloingQuestion(String attendeeUsername) {
        Scanner scanner = new Scanner(System.in);
        // Polling for attendees
        System.out.println("Attendee Poll:");
        System.out.println("How satisfied are you with the conference?");
        System.out.println("1. Very satisfied");
        System.out.println("2. Satisfied");
        System.out.println("3. Neutral");
        System.out.println("4. Dissatisfied");
        System.out.print("Enter your choice: ");
        int pollResponse = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        // Store the poll response in a file
        storePollResponse(attendeeUsername, pollResponse);
        return true;
    }

    private static void storePollResponse(String attendeeUsername, int pollResponse) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("attendee_poll_responses.txt", true))) {
            String pollData = attendeeUsername + ":" + pollResponse;
            writer.write(pollData);
            writer.newLine();
            System.out.println("Poll response stored successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
