package client.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class GUI
{    
    
    public JButton b1;
    public JButton b2;
    public JButton b3;
    public JButton b4;
    public JButton b5;
    public ArrayList <String> sending  = new ArrayList <String>();
    public boolean pressed;
    private JFrame result = new JFrame("RESULT");
    private String message = "";
    
    public GUI(String name)
    {
        JFrame frame = new JFrame(name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JLabel label = new JLabel("Please select an option:");
         b1 = new JButton("Search catalogue courses.");
         b2 = new JButton("Add course to student courses.");
         b3 = new JButton("Remove course from student courses.");
         b4 = new JButton("View all courses in catalogue.");
         b5 = new JButton("View all courses taken by student.");

        JPanel panel = new JPanel(new GridBagLayout());
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
        
        frame.add(panel);
        frame.setSize(400, 300);
        frame.setResizable(false); 
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        listeners();
    }

    private void listeners() {
        b1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                JFrame f = new JFrame();
                JPanel p = new JPanel();
                
                String courseName = JOptionPane.showInputDialog(p,"Enter the name of the course you want to search for:");
                String courseNumber = JOptionPane.showInputDialog(p,"Enter the number of the course you want to search for:");
                
                
                sending.add(courseName + "," + courseNumber + ",1");
                pressed = true;
            }
                
        });
        
        b2.addActionListener(new ActionListener() {

            
            @Override
            public void actionPerformed(ActionEvent arg0) {
                JFrame f = new JFrame();
                JPanel p = new JPanel();
                
                String studentId = JOptionPane.showInputDialog(p,"Enter the student's id:");
                String courseName = JOptionPane.showInputDialog(p,"Enter the name of the course you want to enroll in:");
                String courseNumber = JOptionPane.showInputDialog(p,"Enter the number of the course you want to enroll in:");
                String section = JOptionPane.showInputDialog(p,"Enter the section you want to enroll in:");
                
                sending.add(studentId + "," + courseName + ",3");
                pressed = true;
            }
            
                
        });
        
        b3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                JFrame f = new JFrame();
                JPanel p = new JPanel();
                
                String studentId = JOptionPane.showInputDialog(p,"Enter the student's id:");
                String courseName = JOptionPane.showInputDialog(p,"Enter the name of the course you want to remove:");
                
                
                sending.add(studentId + "," + courseName + ",3");   
                pressed = true;
            }
                
        });
        
        b4.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                
                
                sending.add("4");   
                pressed = true;
            }
                
        });
        
        b5.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                JFrame f = new JFrame();
                JPanel p = new JPanel();
                
                String studentId = JOptionPane.showInputDialog(p,"Enter the student's id:");
                sending.add(studentId + ",5");  
                pressed = true;
            }
                
        });

        
    }

    public void displayMessageWindow(String message)
    {
        
        if(!message.contentEquals("end"))
        {
            this.message += message + "\n";
            
        }
        else
        {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JTextArea textArea = new JTextArea(20,40);
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            textArea.append(this.message);
            panel.add(scrollPane);
            result.add(panel);
            result.setSize(500, 400);
            result.setResizable(false); 
            result.setVisible(true);
            result.setLocationRelativeTo(null);
        }
        
   
    }   

    public void displayCatalogue(String[] allCourseList)
    {
        JFrame frame = new JFrame("Course Catalogue");
        JPanel panel = new JPanel();

        JTextArea textArea = new JTextArea(20, 40);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        for (int i = 0; i < allCourseList.length; i++)
        {
            textArea.append(" " + allCourseList[i] + "\n");
        }
        
        panel.add(scrollPane);
        frame.add(panel);
        frame.setSize(500, 375);
        frame.setResizable(false); 
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
    
    public void displayStudentCourses(String studentName, String studentID, String studentCourseList)
    {
        JFrame frame = new JFrame("Enrolled Course(s)");
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel textPanel = new JPanel();
        JLabel label = new JLabel("     Student Name: " + studentName + "               Student ID: " + studentID);
        
        JTextArea textArea = new JTextArea(18, 40);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        
      
            textArea.append(" " + studentCourseList);
        
        
        textPanel.add(scrollPane);
        titlePanel.add(label);
        mainPanel.add(titlePanel, "North");
        mainPanel.add(textPanel, "Center");
        
        frame.add(mainPanel);
        frame.setSize(500, 375);
        frame.setResizable(false); 
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }


    /**
     * Driver code
     * Remove when done
     * @param args
     */
//    public static void main(String[] args)
//    {
//        GUI myGUI = new GUI();
//        myGUI.displayCatalogue("");
//        myGUI.displayStudentCourses("John Smith", "12345678");
//        myGUI.displayMessageWindow("Name of frame goes here", "Pass whatever string you want here...");
//    }

    
    
}