import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AttendeeGraph extends JFrame {

    private int totalAttendees;

    public AttendeeGraph() {
        setTitle("Total Attendees Count");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize total attendees count
        try {
            FileReader reader = new FileReader("attendees.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);

            int count = 0;
            while (bufferedReader.readLine() != null) {
                count++;
            }
            totalAttendees = count;

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Create a Graphics2D object
        Graphics2D g2d = (Graphics2D) g;

        // Draw the x and y axes
        g2d.drawLine(50, 350, 350, 350); // x-axis
        g2d.drawLine(50, 350, 50, 50); // y-axis

        // Draw the pie chart
        drawPieChart(g2d, totalAttendees, getWidth() / 2, getHeight() / 2, 100, 0, 360);

        // Draw chart title
        g.drawString("Total Attendees", getWidth() / 2 - 50, getHeight() - 10);
    }

    private void drawPieChart(Graphics2D g2d, int value, int x, int y, int radius, int startAngle, int arcAngle) {
        // Calculate the end angle
        int endAngle = startAngle + arcAngle;

        // Draw the pie sector
        g2d.setColor(Color.blue); // You can change the color
        g2d.fillArc(x - radius, y - radius, 2 * radius, 2 * radius, startAngle, arcAngle);

        // Draw the value as text at the center
        g2d.setColor(Color.black); // You can change the color
        g2d.drawString(String.valueOf(value), x - 10, y + 5);
    }

    public static void main(String[] args) {
        // Handle Swing components on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new AttendeeGraph());
    }
}
