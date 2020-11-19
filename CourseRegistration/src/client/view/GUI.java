package client.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

/** 
 * The display that will show the Client the options
 * @author Ayush Chaudhari
 * @author Duan Le
 * @author Vu Ha
 * @since April 20th 2020
 */
public class GUI
{    
    /**
     * Option 1
     */
    public JButton b1;
    
    /**
     * Option 2
     */
    public JButton b2;
    
    /**
     * Option 3
     */
    public JButton b3;
    
    /**
     * Option 4
     */
    public JButton b4;
    
    /**
     * Option 5
     */
    public JButton b5;
    
    /**
     * ArrayList of strings that will be used when sending an input to the Client
     */
    public ArrayList <String> sending  = new ArrayList <String>();
    
    /**
     * Used to tell if button is pressed or not
     */
    public boolean pressed;
    
    /**
     * A text area
     */
    JTextArea textArea = new JTextArea(20, 40);
    
    /**
     * Creates the GUI
     * @param name is the name of the GUI
     */
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

    /**
     * Listeners that add a string to the ArrayList when they are pressed
     */
    private void listeners() {
        b1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
            	textArea.setText("");
                JFrame f = new JFrame();
                JPanel p = new JPanel();
               
                String courseName = null;
                String courseNumber = null;
                while(courseName == null || courseNumber == null)
                {
                	 courseName = JOptionPane.showInputDialog(p,"Enter the name of the course you want to search for:");
                     courseNumber = JOptionPane.showInputDialog(p,"Enter the number of the course you want to search for:");
                     
                     if(courseName == null || courseNumber == null)
                     {
                    	 JOptionPane.showMessageDialog(p, "Input(s) empty, please try again");
                     }
                }
                	  
                sending.add(courseName + "," + courseNumber + ",1");
                pressed = true;
            }
                
        });
        
        b2.addActionListener(new ActionListener() {

            
            @Override
            public void actionPerformed(ActionEvent arg0) {
            	textArea.setText("");
                JFrame f = new JFrame();
                JPanel p = new JPanel();
                
                String studentId = null;
                String courseName = null;
                String courseNumber = null;
                String section = null;
                
                while(studentId == null || courseName == null || courseNumber == null || section == null)
                {
                	studentId = JOptionPane.showInputDialog(p,"Enter the student's id:");
                	courseName = JOptionPane.showInputDialog(p,"Enter the name of the course you want to enroll in:");
                	courseNumber = JOptionPane.showInputDialog(p,"Enter the number of the course you want to enroll in:");
                    section = JOptionPane.showInputDialog(p,"Enter the section you want to enroll in:");
                    
                    if(studentId == null || courseName == null || courseNumber == null || section == null)
                    {
                    	JOptionPane.showMessageDialog(p, "Input(s) empty, please try again");
                    }
                }
                
                
                sending.add(studentId + "," + courseName + "," + courseNumber + "," + section + ",2");
                pressed = true;
            }
            
                
        });
        
        b3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
            	textArea.setText("");
                JFrame f = new JFrame();
                JPanel p = new JPanel();
                
                String studentId = null;
                String courseName = null;
                String courseNumber = null;
                String courseSec = null;
              
                while(studentId == null || courseName == null || courseNumber == null)
                {
                	studentId = JOptionPane.showInputDialog(p,"Enter the student's id:");
                    courseName = JOptionPane.showInputDialog(p,"Enter the name of the course you want to remove:");
                    courseNumber = JOptionPane.showInputDialog("Enter the number of the course you want to remove: ");
                    courseSec = JOptionPane.showInputDialog("Please enter the section of the course you want to remove: ");
                    
                    if(studentId == null || courseName == null || courseNumber == null)
                    {
                    	JOptionPane.showMessageDialog(p, "Input(s) empty, please try again");
                    }
                }
                

                sending.add(studentId + "," + courseName +  "," + courseNumber + "," + courseSec + ",3");   
                pressed = true;
            }
                
        });
        
        b4.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
            	textArea.setText("");
                
                
                sending.add("4");   
                pressed = true;
            }
                
        });
        
        b5.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
            	textArea.setText("");
                JFrame f = new JFrame();
                JPanel p = new JPanel();
                
                String studentId = null;
                
                while(studentId == null)
                {
                	 studentId = JOptionPane.showInputDialog(p,"Enter the student's id:");
                	 
                	 if(studentId == null)
                	 {
                		 JOptionPane.showMessageDialog(p, "Input is empty, please try again");
                	 }
                }
               
                sending.add(studentId + ",5");  
                pressed = true;
            }
                
        });

        
    }

    /**
     * Display for Option 1
     * @param message is what will be displayed
     */
    public void displayMessageWindowfor1(String message)
    {
        	JFrame frame = new JFrame("Course");
            JPanel panel = new JPanel(new FlowLayout());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            textArea.append(message);
            panel.add(scrollPane);
            frame.add(panel);
            frame.setSize(500, 400);
            frame.setResizable(false); 
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);

    }   
    
    /**
     * Display for Option 4
     * @param allCourseList is what will be displayed
     */
     public void displayCatalogue(String allCourseList)
        {
          
          
    	  		JFrame frame = new JFrame("Course Catalouge");
                 JPanel panel = new JPanel();
                    textArea.setEditable(false);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                    

                    textArea.append(allCourseList);
                    
                    panel.add(scrollPane);
                    frame.add(panel);
                    frame.setSize(500, 375);
                    frame.setResizable(false); 
                    frame.setVisible(true);
                    frame.setLocationRelativeTo(null);
                    
           
        }
    
     /**
      * Display for Option 5
      * @param message is what will be displayed
      */
    public void displayStudentCourses(String message)
    {
        JFrame frame = new JFrame("Enrolled Course(s)");
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel textPanel = new JPanel();

        
        
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        
      
        textArea.append(message);
        
        
        textPanel.add(scrollPane);
        mainPanel.add(titlePanel, "North");
        mainPanel.add(textPanel, "Center");
        
        frame.add(mainPanel);
        frame.setSize(500, 375);
        frame.setResizable(false); 
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    /**
     * Display for Option 2 and 3
     * @param response is what will be displayed
     */
	public void displayMessageWindowfor2(String response) {
		
		JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame, response);
		
	}

	

}

    
