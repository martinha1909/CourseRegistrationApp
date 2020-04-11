import java.awt.*;
import javax.swing.*;

public class GUI
{
    JFrame frame;
    JButton b1;
    JButton b2;
    JButton b3;
    JButton b4;
    JButton b5;

    public GUI(String name)
    {
        frame = new JFrame(name);
        b1 = new JButton("Search catalogue courses.");
        b2 = new JButton("Add course to student courses.");
        b3 = new JButton("Remove course from student courses.");
        b4 = new JButton("View all courses in catalogue.");
        b5 = new JButton("View all courses taken by student.");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        frame.add(b1, gbc);
        frame.add(b2, gbc);
        frame.add(b3, gbc);
        frame.add(b4, gbc);
        frame.add(b5, gbc);
        frame.setSize(400, 300);
        frame.setVisible(true);     
    }

}