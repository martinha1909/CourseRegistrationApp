import java.awt.*;
import javax.swing.*;

public class GUI
{    
    public GUI() {}

    public void displayMenu(String name)
    {
        JFrame frame = new JFrame(name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JLabel label = new JLabel("Please select an option:");
        JButton b1 = new JButton("Search catalogue courses.");
        JButton b2 = new JButton("Add course to student courses.");
        JButton b3 = new JButton("Remove course from student courses.");
        JButton b4 = new JButton("View all courses in catalogue.");
        JButton b5 = new JButton("View all courses taken by student.");

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);
        
        //label.setHorizontalAlignment(JLabel.CENTER); //This centers the label
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(label, gbc);
        
        gbc.gridy = 1;
        panel.add(b1, gbc);
       
        gbc.gridy = 2;
        panel.add(b2, gbc);

        gbc.gridy = 3;
        panel.add(b3, gbc);
        
        gbc.gridy = 4;
        panel.add(b4, gbc);
        
        gbc.gridy = 5;
        panel.add(b5, gbc);
        
        frame.add(panel, BorderLayout.CENTER);
        frame.setSize(400, 300);
        frame.setResizable(false); 
        frame.setVisible(true);
    }

    /**
     * Test code
     * Remove when done
     * @param args
     */
    // public static void main(String[] args)
    // {
    //     GUI myGUI = new GUI("Course Registration");
    //     myGUI.displayMenu();
    // }
}