import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

class Speaker {
    private String name;
    private String bio;
    private String photo;
    private String username;
    private String password;
    private List<String> presentationMaterials;
    private List<String> feedbacks;
    private static final String SPEAKER_FILE_PATH = "speakers.txt";
    private static String loggedInSpeaker;

    public Speaker(String username, String password) {
        this.username = username;
        this.password = password;
        this.presentationMaterials = new ArrayList<>();
        this.feedbacks = new ArrayList<>();
    }

    // ... (rest of the Speaker class remains unchanged)

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static boolean login(String username, String password) {
        try {
            FileReader reader = new FileReader(SPEAKER_FILE_PATH);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] credentials = line.split(":");
                if (credentials.length == 2) {
                    String storedUsername = credentials[0].trim();
                    String storedPassword = credentials[1].trim();
                    if (storedUsername.equals(username) && storedPassword.equals(password)) {
                        loggedInSpeaker = username;
                        System.out.println("Speaker login successful!");
                        bufferedReader.close();
                        return true;
                    }
                }
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Speaker login failed. Incorrect username or password.");
        return false;
    }

    public static boolean simulateSpeakerQASession() {
        System.out.println("Welcome, Speaker!");

        // Q&A with the speaker
        System.out.println("Q&A Session with Speaker:");

        // Ask about the upcoming presentation
        System.out.println("What is the title of your upcoming presentation?");
        Scanner scanner = new Scanner(System.in);
        String presentationTitle = scanner.nextLine();
        System.out.println("Presentation Title: " + presentationTitle);

        // Allow the speaker to submit presentation materials
        System.out.println("Do you have any presentation materials to submit? (yes/no)");
        String submitMaterials = scanner.nextLine();
        if (submitMaterials.equalsIgnoreCase("yes")) {
            System.out.println("Please enter the material link:");
            String materialLink = scanner.nextLine();
            // Submit the material
            try {
                downloadFile(materialLink, System.getProperty("user.dir"), presentationTitle);
                System.out.println("File downloaded successfully.");
            } catch (IOException e) {
                System.err.println("Error downloading file: " + e.getMessage());
            }
            System.out.println("Material submitted successfully!");
        } else {
            System.out.println("No materials submitted.");
        }

        // Gather feedback from the speaker
        System.out.println("Please provide feedback on the event:");
        String feedback = scanner.nextLine();
        // Collect feedback
        // Speaker.loggedInSpeaker.getFeedback(feedback);
        System.out.println("Feedback " + feedback + " has been taken. Thank you!");
        return true;
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

    public boolean uploadPresentation(String speakerUsername) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username");
        String securedUsername = scanner.nextLine();
        System.out.println("Enter password");
        String securedPassword = scanner.nextLine();
        if (login(securedUsername, securedPassword)) {
            System.out.println("Authentication successfull");
            System.out.println("Note: Presentation url must be pdf url");
            System.out.println("Enter presentation document url");
            String pdfUrl = scanner.nextLine();
            // Check if the URL ends with ".pdf"
            if (pdfUrl.toLowerCase().endsWith(".pdf")) {
                // Assuming the presentation material is directly added to the list
                submitPresentationMaterial(pdfUrl);
                System.out.println("Presentation uploaded successfully!");
            } else {
                System.out.println("Invalid URL. Presentation must be a PDF document.");
            }
        } else {
            System.out.println("Authentication failed!");
        }
        return true;
    }

    public boolean submitPresentationMaterial(String pdfUrl) {
        if (!Files.exists(Paths.get("presentations.txt"))) {
            createPresentationsFile();
        }

        try {
            String presentationData = username + ":" + pdfUrl;
            String updatedData = "";

            // Check if the presentation data already exists
            boolean presentationDataExists = false;
            for (String line : Files.readAllLines(Paths.get("presentations.txt"))) {
                if (line.trim().startsWith(username + ":")) {
                    // Replace existing data
                    updatedData += presentationData + System.lineSeparator();
                    presentationDataExists = true;
                } else {
                    updatedData += line + System.lineSeparator();
                }
            }

            // If presentation data doesn't exist, add new data
            if (!presentationDataExists) {
                updatedData += presentationData + System.lineSeparator();
            }

            // Write the updated data to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("presentations.txt"))) {
                writer.write(updatedData);
                writer.flush();
            }

            // Update the presentation materials list
            if (!presentationDataExists) {
                presentationMaterials.add(pdfUrl);
            }

            System.out.println("Presentation material submitted successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void createPresentationsFile() {
        try {
            Files.createFile(Paths.get("presentations.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean updatePresentation(String speakerUsername) {
        System.out.println("Note: Presentation url must be pdf url");
        System.out.println("Enter presentation document url");
        Scanner scanner = new Scanner(System.in);
        String pdfUrl = scanner.nextLine();
        // Check if the URL ends with ".pdf"
        if (pdfUrl.toLowerCase().endsWith(".pdf")) {
            // Assuming the presentation material is directly added to the list
            updatePresentationMaterial(pdfUrl);
        } else {
            System.out.println("Invalid URL. Presentation must be a PDF document.");
        }
        return true;
    }

    private boolean updatePresentationMaterial(String pdfUrl) {
        if (!Files.exists(Paths.get("presentations.txt"))) {
            createPresentationsFile();
        }

        try {
            String presentationData = username + ":" + pdfUrl;
            String updatedData = "";

            // Check if the presentation data already exists
            boolean presentationDataExists = false;
            for (String line : Files.readAllLines(Paths.get("presentations.txt"))) {
                if (line.trim().startsWith(username + ":")) {
                    // Replace existing data
                    updatedData += presentationData + System.lineSeparator();
                    presentationDataExists = true;
                } else {
                    updatedData += line + System.lineSeparator();
                }
            }

            // If presentation data doesn't exist, add new data
            if (!presentationDataExists) {
                updatedData += presentationData + System.lineSeparator();
            }

            // Write the updated data to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("presentations.txt"))) {
                writer.write(updatedData);
                writer.flush();
            }

            // Update the presentation materials list
            if (!presentationDataExists) {
                presentationMaterials.add(pdfUrl);
            }

            System.out.println("Presentation material updated successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
