import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BarGraph extends JFrame {

    private int[] attendees;
    private int[] speakers;

    public BarGraph() {
        setTitle("Bar Graph");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize attendees array
        try {
            FileReader reader = new FileReader("attendees.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);

            int count = 0;
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                count++;
            }
            attendees = new int[] { count }; // Assuming you want a single bar for simplicity

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Initialize speakers array
        try {
            FileReader reader = new FileReader("speakers.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);

            List<String> speakersList = new ArrayList<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                speakersList.add(line);
            }
            speakers = new int[] { speakersList.size() }; // Assuming you want a single bar for simplicity

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Draw x and y axes
        g.drawLine(50, 350, 350, 350); // x-axis
        g.drawLine(50, 50, 50, 350); // y-axis

        // Draw x-axis label
        g.drawString("People", 180, 380);

        // Draw y-axis label
        g.drawString("Number", 10, 200);

        // Draw data bars and y-axis values for attendees
        if (attendees != null) {
            int barWidth = 40;
            int x = 60;

            for (int i = 0; i < attendees.length; i++) {
                int barHeight = attendees[i] * 5; // Scale the attendees count
                g.fillRect(x, 350 - barHeight, barWidth, barHeight); // Draw a bar

                // Draw y-axis value
                g.drawString("Attendees", x + barWidth / 2 - 5, 365);

                // Draw count label
                g.drawString(String.valueOf(attendees[i]), x + barWidth / 2 - 5, 350 - barHeight - 5);

                x += 60; // Increase x for the next bar
            }
        }

        // Draw data bars and y-axis values for speakers
        if (speakers != null) {
            int barWidth = 40;
            int x = 160; // Adjust the starting position for speakers

            for (int i = 0; i < speakers.length; i++) {
                int barHeight = speakers[i] * 5; // Scale the speakers count
                g.fillRect(x, 350 - barHeight, barWidth, barHeight); // Draw a bar

                // Draw y-axis value
                g.drawString("Speakers", x + barWidth / 2 - 5, 365);

                // Draw count label
                g.drawString(String.valueOf(speakers[i]), x + barWidth / 2 - 5, 350 - barHeight - 5);

                x += 60; // Increase x for the next bar
            }
        }
    }

    public static void main(String[] args) {
        // Handle Swing components on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new BarGraph());
    }
}