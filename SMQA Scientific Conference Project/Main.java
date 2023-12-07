import java.util.Scanner;

public class Main {
	
	public Main() {
		Scanner scanner = new Scanner(System.in);
        Organizer organizer = new Organizer();
	}
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Organizer organizer = new Organizer();

        boolean loggedIn = false;

        while (!loggedIn) {
            // Login/signup options
            System.out.println("Choose an option:");
            System.out.println("1. Login as Organizer");
            System.out.println("2. Signup as Organizer");
            System.out.println("3. Login as Attendee");
            System.out.println("4. Login as Speaker");
            System.out.println("5. Exit");
            System.out.print("Enter option number: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (option) {
                case 1:
                    // Login as Organizer
                    System.out.print("Enter organizer username: ");
                    String organizerUsername = scanner.nextLine();
                    System.out.print("Enter organizer password: ");
                    String organizerPassword = scanner.nextLine();
                    loggedIn = organizer.organizerLogin(organizerUsername, organizerPassword);
                    if (!loggedIn) {
                        System.out.println("Organizer login failed. Please try again.");
                    } else {
                        boolean orgLogout = false;
                        boolean attendee2 = false;
                        // while (!orgLogout) {
                        System.out.println("Login successful!");
                        // Displaying main options for the Organizer
                        while (!attendee2) {
                            System.out.println("Organizer Options:");
                            System.out.println("1. Add Attendee");
                            System.out.println("2. Add Speaker");
                            System.out.println("3. Track Speakers");
                            System.out.println("4. Track Attendees");
                            System.out.println("5. Delete Attendee");
                            System.out.println("6. Delete Speaker");
                            System.out.println("7. Show all Speakers, Attendees");
                            System.out.println("8. Send invitation to sponser");
                            System.out.println("9. Send Update to Speaker");
                            System.out.println("10. Send Update to Attendee");
                            System.out.println("11. Show Attendees & Speakers count visualization");
                            System.out.println("12. Send virtual session links to attendees");
                            System.out.println("13. Send payment link to attendees");
                            System.out.println("14. Generate report contains all users");
                            System.out.println("15. Generate participation certificate");
                            System.out.println("16. View Polling results");
                            System.out.println("17. Logout");
                            System.out.print("Enter your choice: ");
                            int organizerOption = scanner.nextInt();
                            scanner.nextLine(); // Consume the newline character
                            switch (organizerOption) {
                                case 1:
                                    // Add Attendee
                                    System.out.print("Enter attendee username: ");
                                    String attendeeUsername = scanner.nextLine();
                                    System.out.print("Enter attendee password: ");
                                    String attendeePassword = scanner.nextLine();
                                    attendee2 = organizer.addAttendee(attendeeUsername, attendeePassword);
                                    break;
                                case 2:
                                    // Add Speaker
                                    System.out.print("Enter speaker username: ");
                                    String speakerUsername = scanner.nextLine();
                                    System.out.print("Enter speaker password: ");
                                    String speakerPassword = scanner.nextLine();
                                    boolean addSpeakerFlag = organizer.addSpeaker(speakerUsername, speakerPassword);
                                    break;
                                case 3:
                                    // List Speakers
                                	boolean listSpeakerFlag = organizer.listSpeakers();
                                    break;
                                case 4:
                                    // List Attendees
                                	boolean listAttendeeFlag = organizer.listAttendees();
                                    break;
                                case 5:
                                    // Delete Attendee
                                    System.out.print("Enter the username of the attendee to delete: ");
                                    String deleteAttendeeUsername = scanner.nextLine();
                                    boolean deleteAttendeeFlag = organizer.deleteAttendee(deleteAttendeeUsername);
                                    break;
                                case 6:
                                    // Delete Speaker
                                    System.out.print("Enter the username of the speaker to delete: ");
                                    String deleteSpeakerUsername = scanner.nextLine();
                                    boolean deleteSpeakerFlag = organizer.deleteSpeaker(deleteSpeakerUsername);
                                    break;
                                case 7:
                                    // List Speakers
                                    boolean listSpeakersFlag = organizer.listSpeakers();
                                    // List Attendees
                                    boolean listAttendeesFlag = organizer.listAttendees();
                                    break;
                                case 8:
                                    // Send invitation to sponser
                                    System.out.print("Enter the email of the sponser to send invitation: ");
                                    String sponserEmail = scanner.nextLine();
                                    organizer.sendInvitationToSponser(sponserEmail);
                                    break;
                                case 9:
                                    // Send update to Speaker
                                    System.out.println("Enter Speaker email: ");
                                    String speakerEmail = scanner.nextLine();
                                    System.out.println("Write the update message: ");
                                    String speakerMessage = scanner.nextLine();
                                    organizer.sendUpdatetoSpeaker(speakerEmail, speakerMessage);
                                    break;
                                case 10:
                                    // Send update to Attendee
                                    System.out.println("Enter Attendee email: ");
                                    String attendeeEmail = scanner.nextLine();
                                    System.out.println("Write the update message: ");
                                    String attendeeMessage = scanner.nextLine();
                                    organizer.sendUpdatetoAttendee(attendeeEmail, attendeeMessage);
                                    break;
                                case 11:
                                    AttendeeSpeakerCountGraph attendeeSpeakerCountGraph = new AttendeeSpeakerCountGraph();
                                    break;
                                case 12:
                                    organizer.sendVirtualSessionLinks();
                                    break;
                                case 13:
                                    organizer.sendPaymenyLinks();
                                    break;
                                case 14:
                                    organizer.generateAttendanceReports();
                                    break;
                                case 15:
                                    System.out.println("Please enter attendee full name");
                                    String attendeeName = scanner.nextLine();
                                    organizer.generateCertificate(attendeeName, "Scientific Conference 2023");
                                    break;
                                case 16:
                                    // View polling results
                                    organizer.viewPollingResults();
                                    break;
                                case 17:
                                    // Logout
                                    orgLogout = true;
                                    organizer.organizerLogout();
                                    attendee2 = true;
                                    loggedIn = false;
                                    break;
                                default:
                                    System.out.println("Invalid option. Please enter a number between 1 and 7.");
                            }
                        }
                    }
                    // }
                    break;
                case 2:
                    // Signup as Organizer
                    System.out.print("Enter new organizer Username: ");
                    String newOrganizerUsername = scanner.nextLine();
                    System.out.print("Enter new organizer password: ");
                    String newOrganizerPassword = scanner.nextLine();
                    boolean organizerFlag = organizer.organizerSignup(newOrganizerUsername, newOrganizerPassword);
                    System.out.println("Organizer signup successful! Now you can log in.");
                    break;
                case 3:
                    // Login as Attendee
                    System.out.print("Enter attendee username: ");
                    String attendeeUsername = scanner.nextLine();
                    System.out.print("Enter attendee password: ");
                    String attendeePassword = scanner.nextLine();
                    // Placeholder, you need to implement login logic in Attendee class
                    boolean attendeeLoggedIn = Attendee.login(attendeeUsername, attendeePassword);
                    if (attendeeLoggedIn) {
                        System.out.println("Attendee login successful!");
                        // Add attendee-specific logic here
                        Attendee attendee = new Attendee(attendeeUsername, attendeePassword);
                        while (attendeeLoggedIn) {
                            // Add your speaker-specific logic here
                            System.out.println("Attendee Options:");
                            System.out.println("1. simulate q&a session");
                            System.out.println("2. Download presentation");
                            System.out.println("3. Participate in a poll");
                            System.out.println("4. Logout");
                            System.out.print("Enter your choice: ");
                            int attendeeOption = scanner.nextInt();
                            scanner.nextLine(); // Consume the newline character
                            if (attendeeOption == 1) {
                                attendee.simulateAttendeeQASession();
                            } else if (attendeeOption == 2) {
                                System.out.print("Enter Speaker username to download PDF: ");
                                String speakerToDownload = scanner.nextLine();
                                attendee.downloadSpeakerPDF(speakerToDownload);
                            } else if (attendeeOption == 3) {
                                attendee.polloingQuestion(attendeeUsername);
                            } else {
                                attendeeLoggedIn = false;
                            }
                        }

                    } else {
                        System.out.println("Attendee login failed. Please try again.");
                    }
                    break;
                case 4:
                    // Login as Speaker
                    System.out.print("Enter speaker username: ");
                    String speakerUsername = scanner.nextLine();
                    System.out.print("Enter speaker password: ");
                    String speakerPassword = scanner.nextLine();
                    // Placeholder, you need to implement login logic in Speaker class
                    boolean speakerLoggedIn = Speaker.login(speakerUsername, speakerPassword);
                    if (speakerLoggedIn) {
                        Speaker speaker = new Speaker(speakerUsername, speakerPassword);
                        System.out.println("Speaker login successful!");
                        while (speakerLoggedIn) {
                            // Add your speaker-specific logic here
                            System.out.println("Speaker Options:");
                            System.out.println("1. simulate q&a session");
                            System.out.println("2. Upload presentation");
                            System.out.println("3. Update presentation");
                            System.out.println("4. Show visualization of no.of attendees");
                            System.out.println("5: Logout");
                            System.out.print("Enter your choice: ");
                            int speakerOption = scanner.nextInt();
                            scanner.nextLine(); // Consume the newline character
                            if (speakerOption == 1) {
                                speaker.simulateSpeakerQASession();
                            } else if (speakerOption == 2) {
                                speaker.uploadPresentation(speakerUsername);
                            } else if (speakerOption == 3) {
                                speaker.updatePresentation(speakerUsername);
                            } else if (speakerOption == 4) {
                                AttendeeGraph attendeeGraph = new AttendeeGraph();
                            } else {
                                System.out.println("Logged out successfully");
                                speakerLoggedIn = false;
                            }
                        }
                    } else {
                        System.out.println("Speaker login failed. Please try again.");
                    }
                    break;
                case 5:
                    // Exit the program
                    System.out.println("Exiting the program. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please enter a valid option.");
            }
        }

        scanner.close();
    }
    
}
