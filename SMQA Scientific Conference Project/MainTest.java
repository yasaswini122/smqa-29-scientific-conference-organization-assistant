import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;




class MainTest {
	
	private final InputStream originalSystemIn = System.in;
    private ByteArrayInputStream mockTestIn;
	
	@Test
    public void testOrganizerLogin() {
        Organizer organizer = new Organizer();

        // Test case for successful login
        assertTrue(organizer.organizerLogin("o1", "1234"));
        
        // Test case for failed login
        assertFalse(organizer.organizerLogin("invalidUsername", "invalidPassword"));
    }

	@Test
    public void testOrganizerLoginMutation() {
        deleteOrganizerEntry("o1234:1234");
        Organizer organizer = new Organizer();
        
        // Original credentials for testing
        organizer.organizerSignup("o1234", "1234");

        // Original behavior - Valid login
        assertTrue(organizer.organizerLogin("o1234", "1234")); // Original behavior should pass
        
        // Mutated behavior - Changing the condition in the if statement
        assertTrue(organizer.mutateAndCheckOriginalLogin("o1234", "1234")); // Original behavior should still pass
        
        assertFalse(organizer.mutateAndCheckOriginalLogin("ooooo1234", "1234"));
        // Mutated behavior - Introducing mutation in the logic
        assertFalse(organizer.mutateAndCheckMutatedLogin("o1234", "1234")); // This should fail due to the mutation
        
    }
	
	@Test
	public void testOrganizerSignup() {
        deleteOrganizerEntry("o1111:1234");
        Organizer organizer = new Organizer();
        // Test case for save Organizer Credentials 
        boolean organizerSignup = organizer.organizerSignup("o1111", "1234");
        assertTrue(organizerSignup);
	}
	
	private void deleteOrganizerEntry(String entryToDelete) {
		try {
	        // Read the content of organizer_credentials.txt
	        File file = new File("organizer_credentials.txt");
	        File tempFile = new File("temp.txt");
	        BufferedReader reader = new BufferedReader(new FileReader(file));
	        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
	
	        String currentLine;
	        while ((currentLine = reader.readLine()) != null) {
	            // Skip the line that needs to be deleted
	            if (!currentLine.trim().equals(entryToDelete)) {
	                writer.write(currentLine + System.getProperty("line.separator"));
	            }
	        }
	        writer.close();
	        reader.close();
        } catch (IOException e) {
        	 e.printStackTrace();
        }
	}
	
	@Test
	public void testOrganizerSignupExists() {
        Organizer organizer = new Organizer();
        // Test case for save Organizer Credentials 
        boolean organizerSignup = organizer.organizerSignup("o1", "1234");
        assertTrue(organizerSignup);
	}
	
	@Test
	public void testaddAttendeeOrganizerLoggedIn() {
        Organizer organizer = new Organizer();
        // Test case to add Attendee Credentials 
        assertTrue(organizer.organizerLogin("o1", "1234"));
        organizer.deleteAttendee("aaa1");
        boolean addAttendee = organizer.addAttendee("aaa1", "1234");
        assertFalse(addAttendee);
	}
	
	@Test
	public void testaddAttendeeOrganizerNotLoggedIn() {
        Organizer organizer = new Organizer();
        // Test case to add Attendee Credentials 
        organizer.deleteAttendee("aaa1");
        boolean addAttendee = organizer.addAttendee("aaa1", "1234");
        assertFalse(addAttendee);
	}
	
	@Test
	public void testaddSpeaker() {
        Organizer organizer = new Organizer();
        // Test case to add Speaker Credentials 
        assertTrue(organizer.organizerLogin("o1", "1234"));
        organizer.deleteAttendee("ssss1");
        boolean addSpeaker = organizer.addSpeaker("ssss1", "1234");
        assertTrue(addSpeaker);
	}
	
	@Test
	public void testlistSpeakers() {
        Organizer organizer = new Organizer();
        // Test case to list Speakers 
        assertTrue(organizer.organizerLogin("o1", "1234"));
        boolean listSpeakersFlag = organizer.listSpeakers();
        assertTrue(listSpeakersFlag);
	}
	
	@Test
	public void testlistAttendees() {
        Organizer organizer = new Organizer();
        // Test case to list Attendees  
        assertTrue(organizer.organizerLogin("o1", "1234"));
        boolean listAttendeesFlag = organizer.listAttendees();
        assertTrue(listAttendeesFlag);
	}
	
	@Test
	public void testdeleteSpeaker() {
        Organizer organizer = new Organizer();
        // Test case to delete Speaker
        assertTrue(organizer.organizerLogin("o1", "1234"));
        boolean deleteSpeakerFlag = organizer.deleteSpeaker("s2");
        assertTrue(deleteSpeakerFlag);
	}
	
	@Test
	public void testdeleteAttendee() {
        Organizer organizer = new Organizer();
        // Test case to delete Attendee 
        boolean deleteAttendeeFlag = organizer.deleteAttendee("a2");
        assertTrue(deleteAttendeeFlag);
	}
	
	@Test
	public void testSendInvitationToSponser() {
		Organizer organizer = new Organizer();
		// Test case to send invitation to sponser
        assertTrue(organizer.organizerLogin("o1", "1234"));
		boolean sendInvitationToSponserFlag = organizer.sendInvitationToSponser("priyankagajjarapu83@gmail.com");
		assertTrue(sendInvitationToSponserFlag);
	}
	
	@Test
	public void testSendUpdateToAttendee() {
		Organizer organizer = new Organizer();
		// Test case to send update to attendee
        assertTrue(organizer.organizerLogin("o1", "1234"));
        boolean testSendUpdateToAttendeeFlag = organizer.sendUpdatetoAttendee("priyankagajjarapu83@gmail.com", "Hello Attendee");
		assertTrue(testSendUpdateToAttendeeFlag);
	}
	
	@Test
	public void testSendUpdateToSpeaker() {
		Organizer organizer = new Organizer();
		// Test case to send update to speaker
        assertTrue(organizer.organizerLogin("o1", "1234"));
        boolean testSendUpdateToSpeakerFlag = organizer.sendUpdatetoSpeaker("priyankagajjarapu83@gmail.com", "Hello Speaker");
		assertTrue(testSendUpdateToSpeakerFlag);
	}

	private void provideInput(String data) {
		mockTestIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(mockTestIn);
    }
	
	@Test 
	public void testSendVirtualSessionLinks() {
		Organizer organizer = new Organizer();
		// Test case to send virtual session links
        assertTrue(organizer.organizerLogin("o1", "1234"));
        provideInput("https://www.googlemeet.com\npriyankagajjarapu83@gmail.com\n");
        boolean sendVirtualSessionLinksFlag = organizer.sendVirtualSessionLinks();
        assertTrue(sendVirtualSessionLinksFlag);
		
	}
	
	@Test
	public void testviewPollingResults() {
		Organizer organizer = new Organizer();
		// Test case to view polling results
        assertTrue(organizer.organizerLogin("o1", "1234"));
		boolean viewPollingResultsFlag = organizer.viewPollingResults();
		assertTrue(viewPollingResultsFlag);
	}
	
	@Test
	public void testsendPaymenyLinks() {
		Organizer organizer = new Organizer();
		// Test case to send payment link
        assertTrue(organizer.organizerLogin("o1", "1234"));
        provideInput("a1\npriyankagajjarapu83@gmail.com\nvip\n");
		boolean sendPaymenyLinksFlag = Organizer.sendPaymenyLinks();
		assertTrue(sendPaymenyLinksFlag);
	}
	
	@Test
	public void testgenerateAttendanceReports() {
		Organizer organizer = new Organizer();
		// Test case to generate attendance reports
        assertTrue(organizer.organizerLogin("o1", "1234"));
		boolean generateAttendanceReportsFlag = organizer.generateAttendanceReports();
		assertTrue(generateAttendanceReportsFlag);
	}
	
	@Test
	public void testsimulateAttendeeQASessionWithFeedback() {
		Attendee attendee = new Attendee("a1","1234");
		// Test case to simulate attendee qa session with feedback
        provideInput("web\nExcellent\n");
		boolean simulateAttendeeQASessionFlag = attendee.simulateAttendeeQASession();
		assertTrue(simulateAttendeeQASessionFlag);
	}
	
	@Test
	public void testsimulateAttendeeQASessionWithoutFeedback() {
		Attendee attendee = new Attendee("a1","1234");
		// Test case to simulate attendee qa session with empty feedback
        provideInput("web\n\n");
		boolean simulateAttendeeQASessionFlag = attendee.simulateAttendeeQASession();
		assertTrue(simulateAttendeeQASessionFlag);
	}
	
	@Test
	public void testsimulateAttendeeQASessionWithoutInterests() {
		Attendee attendee = new Attendee("a1","1234");
		// Test case to simulate attendee qa session with empty feedback
        provideInput("\nExcellent\n");
		boolean simulateAttendeeQASessionFlag = attendee.simulateAttendeeQASession();
		assertTrue(simulateAttendeeQASessionFlag);
	}
	
	@Test
	public void testattendeeLogin() {
		Attendee attendee = new Attendee("a1","1234");
		// Test case for attendee login
		boolean testattendeeLoginFlag = attendee.login("a1", "1234");
		assertTrue(testattendeeLoginFlag);
	}
	
	@Test
	public void testdownloadSpeakerPDF() {
		Attendee attendee = new Attendee("a1","1234");
		// Test case to download pdf
		boolean downloadSpeakerPDFFlag = attendee.downloadSpeakerPDF("s1");
		assertTrue(downloadSpeakerPDFFlag);
	}
	
	@Test
	public void testpolloingQuestion() {
		Attendee attendee = new Attendee("a1","1234");
		// Test case for polling question
        provideInput("1\n");
		boolean polloingQuestionFlag = attendee.polloingQuestion("a1");
		assertTrue(polloingQuestionFlag);
	}
	
	@Test
	public void testspeakerLogin() {
		Speaker speaker = new Speaker("s1","1234");
		// Test case for speaker login
		boolean testspeakerLoginFlag = speaker.login("s1", "1234");
		assertTrue(testspeakerLoginFlag);
	}
	
	@Test
	public void testsimulateSpeakerQASession() {
		Speaker speaker = new Speaker("s1","1234");
		// Test case to simulate speaker qa session
        provideInput("Java Programming\nyes\nhttps://www.tutorialspoint.com/java/java_tutorial.pdf\nExcellent\n");
		boolean simulateSpeakerQASessionFlag = speaker.simulateSpeakerQASession();
		assertTrue(simulateSpeakerQASessionFlag);
	}
	
	@Test
	public void testuploadPresentationSpeakerFound() {
		Speaker speaker = new Speaker("s1","1234");
		// Test case to upload presentation
        provideInput("s1\n1234\nhttps://www.tutorialspoint.com/java/java_tutorial.pdf\n");
		boolean uploadPresentationFlag = speaker.uploadPresentation("s1");
		assertTrue(uploadPresentationFlag);
	}
	
	@Test
	public void testuploadPresentationSpeakerNotFound() {
		Speaker speaker = new Speaker("okokoooook","1234");
		// Test case to upload presentation
        provideInput("okokokookkk\n1234\nhttps://www.tutorialspoint.com/java/java_tutorial.pdf\n");
		boolean uploadPresentationFlag = speaker.uploadPresentation("okokoooook");
		assertTrue(uploadPresentationFlag);
	}
	
	@Test
	public void testUpdatePresentation() {
		Speaker speaker = new Speaker("s1","1234");
		// Test case to update presentation
        provideInput("https://www.tutorialspoint.com/java/java_tutorial.pdf\n");
		boolean updatePresentationFlag = speaker.updatePresentation("s1");
		assertTrue(updatePresentationFlag);
	}
	
	@Test
	public void testsubmitPresentationMaterial() {
		Speaker speaker = new Speaker("s1","1234");
		// Test case to submit presentation material
		boolean submitPresentationMaterialFlag = speaker.submitPresentationMaterial("sample.pdf");
		assertTrue(submitPresentationMaterialFlag);
	}

	@Test
    public void testAttendeeGraph() {
        AttendeeGraph attendeeGraph = new AttendeeGraph();

        // Assert
        assertNotNull(attendeeGraph);
    }
	
	@Test
	public void testAttendeeSpeakerCountGraph() {
		AttendeeSpeakerCountGraph attendeeSpeakerCountGraph = new AttendeeSpeakerCountGraph();
		assertNotNull(attendeeSpeakerCountGraph);
	}
	
	@Test
    void testRandomLoginAttemptsAttendee() {
        Random random = new Random();
        String[] usernames = {"user1", "user2", "user3", "user4"};
        String[] passwords = {"pass1", "pass2", "pass3", "pass4"};

        for (int i = 0; i < 10; i++) {
            int randomIndex = random.nextInt(usernames.length);
            String randomUsername = usernames[randomIndex];
            String randomPassword = passwords[randomIndex];

            boolean expectedLoginResult = (randomIndex % 2 == 0); // Simulating random valid/invalid attempts

            assertEquals(expectedLoginResult, Attendee.login(randomUsername, randomPassword));
        }
    }
	
	@Test
    void testRandomLoginAttemptsSpeaker() {
        Random random = new Random();
        String[] usernames = {"user1", "user2", "user3", "user4"};
        String[] passwords = {"pass1", "pass2", "pass3", "pass4"};

        for (int i = 0; i < 10; i++) {
            int randomIndex = random.nextInt(usernames.length);
            String randomUsername = usernames[randomIndex];
            String randomPassword = passwords[randomIndex];

            boolean expectedLoginResult = (randomIndex % 2 == 0); // Simulating random valid/invalid attempts

            assertEquals(expectedLoginResult, Speaker.login(randomUsername, randomPassword));
        }
    }
	
	@Test
    void testGenerateCertificate() {
		Organizer organizer = new Organizer();
        String attendeeName = "John Doe";
        String conferenceName = "Tech Conference";

        String generatedCertificate = organizer.generateCertificate(attendeeName, conferenceName);

        // Verify that the generated certificate is not null
        assertNotNull(generatedCertificate);

        // Verify that the generated certificate contains the attendee's name and conference name
        assertTrue(generatedCertificate.contains(attendeeName));
        assertTrue(generatedCertificate.contains(conferenceName));

        // Verify that the certificate file is created
        File certificateFile = new File("certificate.html");
        assertTrue(certificateFile.exists());

        // Clean up - delete the generated certificate file
        certificateFile.delete();
    }
	
	@Test
    void testGenerateCertificateError() throws IOException {
        String attendeeName = "John Doe";
        String conferenceName = "Tech Conference";

        // Mock FileWriter to throw IOException when write() is called
        FileWriter mockFileWriter = mock(FileWriter.class);
        doThrow(IOException.class).when(mockFileWriter).write(any(String.class));

        // Mock loadHtmlTemplate method to return a simple template
        Organizer organizer = new Organizer();

        String generatedCertificate = organizer.generateCertificate(attendeeName, conferenceName);

        // Verify that the generated certificate is null due to IOException
        assertNull(generatedCertificate);
    }
	
	@Test
	void testorganizerLogout() {
		Organizer organizer = new Organizer();
		boolean organizerLogoutFlag = organizer.organizerLogout();
		assertTrue(organizerLogoutFlag);
	}
	
	@Test
    void testPollWithEmptyOptions() {
        String question = "What is your favorite programming language?";
        String options = "";

        Poll poll = new Poll(question, options);

        // Validate constructor and getters
        assertEquals(question, poll.getQuestion());
        assertEquals(options, poll.getOptions());
    }

    @Test
    void testPollWithNullQuestion() {
        String question = null;
        String options = "Option 1,Option 2,Option 3";

        Poll poll = new Poll(question, options);

        // Validate constructor and getters
        assertNull(poll.getQuestion());
        assertEquals(options, poll.getOptions());
    }

    @Test
    void testPollWithNullOptions() {
        String question = "What is your favorite color?";
        String options = null;

        Poll poll = new Poll(question, options);

        // Validate constructor and getters
        assertEquals(question, poll.getQuestion());
        assertNull(poll.getOptions());
    }
    
    @Test
    void testMainConstructor() throws NoSuchFieldException, IllegalAccessException {
        // Prepare test input for scanner (simulate user input)
        String input = "username\npassword\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Create an instance of Main (calling its constructor)
        Main main = new Main();

        assertNotNull(main);
    }
    
    @Test
    public void testBarGraph() {
        BarGraph bargraph = new BarGraph();

        // Assert
        assertNotNull(bargraph);
    }
    
    @Test
    public void testcreatePresentationsFile() {

    }
    
    @Test
    void testCreatePresentationsFile() {
    	Speaker speaker = new Speaker("s1","1234");
    	speaker.createPresentationsFile();

        // Check if the file was created
        File file = new File("presentations.txt");
        assertTrue(file.exists());

        // Additional checks can be performed if needed
        // For example, verifying if the file is a regular file
        assertTrue(file.isFile());

        // Clean up: Delete the file after testing
        file.delete();
    }
    
    @Test
    void testGetPassword() {
        // Create an instance of Speaker
        String username = "testUser";
        String password = "testPassword";
        Speaker speaker = new Speaker(username, password);

        // Call the getPassword() method and compare the result
        String retrievedPassword = speaker.getPassword();

        // Check if the retrieved password matches the expected password
        assertEquals(password, retrievedPassword);
    }
    
    @Test
    void testSaveSpeakers() {
    	Organizer organizer = new Organizer();
        // Create a list of test speakers
        List<Speaker> testSpeakers = new ArrayList<>();
        testSpeakers.add(new Speaker("s1", "1234"));
        // Call the saveSpeakers method
        organizer.saveSpeakers(testSpeakers);
    }
}
