import java.awt.*;
import javax.swing.*;

public class GUI
{
    JFrame frame;
    
    public GUI(String name)
    {
        frame = new JFrame(name);
    }

    public void displayMenu()
    {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JLabel label = new JLabel("Please select an option:");
        JButton b1 = new JButton("Search catalogue courses.");
        JButton b2 = new JButton("Add course to student courses.");
        JButton b3 = new JButton("Remove course from student courses.");
        JButton b4 = new JButton("View all courses in catalogue.");
        JButton b5 = new JButton("View all courses taken by student.");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);
        
        //label.setHorizontalAlignment(JLabel.CENTER); //This centers the label
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(label, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        buttonPanel.add(b1, gbc);
       
        gbc.gridx = 0;
        gbc.gridy = 2;
        buttonPanel.add(b2, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        buttonPanel.add(b3, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        buttonPanel.add(b4, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        buttonPanel.add(b5, gbc);
        
        frame.add(buttonPanel, BorderLayout.CENTER);
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