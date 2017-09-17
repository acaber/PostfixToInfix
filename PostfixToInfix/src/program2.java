import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;

/**
 * FileName: program2.java
 * Author: Rebecca Johnson
 * Date: 9/14/2017
 * Description: This class receives builds the GUI and sets the 
 * 		results to the output text field.
 */

public class program2 extends JFrame {

		//construct tree button
		private static JButton constructTreeBtn;
		
		//text fields
		private static JTextField inputTextField;
		private static JTextField outputTextField;
		
		//labels
		private static JLabel inputJLabel;
		private static JLabel outputJLabel;
		private static JLabel space1;
		private static JLabel space2;
		
		//panel
		private JPanel panel;
		
		//constructor
		public program2() {
			
			//sets window title
			super("Three Address Generator");
			
			//calls setFrame() method to set up frame
			setFrame();
			
			//calls setPanel() method to set up panel
			setPanel();
			
			//strings used for GUI display
			String inputString = "Enter Postfix Expression";
			String resultString = "Infix Expression";
			String spaceString = " ";
			
			//white space to allow for correct formatting of GUI
			space1 = new JLabel(String.format("%50s", spaceString));
			space2 = new JLabel(String.format("%50s", spaceString));
			
			//input label
			inputJLabel = new JLabel(String.format("%15s", inputString));
			
			//output label
			outputJLabel = new JLabel(String.format("%5s", resultString));
			
			//construct tree button 
			constructTreeBtn = new JButton("Construct Tree");
			constructTreeBtn.setToolTipText("Constructs the tree");
			
			//input text field
			inputTextField = new JTextField(25);
			inputTextField.setEditable(true);
			
			//results text field
			outputTextField = new JTextField(25);
			outputTextField.setEditable(false);
			
			//calls addComponents() method to add components to panel
			addComponents();	
		}
				
		//adds components to panel
		private void addComponents() {
			panel.add(inputJLabel);
			panel.add(inputTextField);
			panel.add(space1);
			panel.add(constructTreeBtn);
			panel.add(space2);
			panel.add(outputJLabel);
			panel.add(outputTextField);
		}
		
		//sets up the frame
		private void setFrame() {
			setSize(450, 140);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLayout(new BorderLayout());
		}
		
		//sets up the panel to be added to the frame
		private void setPanel() {
			panel = new JPanel();
			panel.setLayout(new FlowLayout(FlowLayout.CENTER));
			add(panel, BorderLayout.CENTER);
		}

		//main method
		public static void main(String[] args) {
			program2 g = new program2();
			g.setVisible(true);
		
			//construct tree button action listener
			constructTreeBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) { 
				
					//creates an ExpressionTree object
					ExpressionTree tree = new ExpressionTree();
					
					//removes whitespace
					String expression = inputTextField.getText().replaceAll("\\s+", "");;

					//sets isValid to false
					boolean isValid = false;
					String infix = "";
            
					//executes if the postfix expression is an even amount of digits or if its less than 3 characters
					if( expression.length() % 2 == 0 || expression.length() < 3) 
						JOptionPane.showMessageDialog(null, "Error: Please enter a postfix expression.");
							
					else {
						
						//sets isValid to return value of buildTree
						isValid = tree.buildTree(expression);
						
						if(isValid) {
							try {
								
								//retrieves the infix expression
								infix = tree.getExpressionString();
								
								//trims the beginning and ending parentheses
								infix = (infix.substring(1, infix.length() -1));
								
								//adds spaces back into expression
								infix = infix.replace("", " ").trim();
								
								//sets the outputTextField to the infix expression
								outputTextField.setText(infix);
								
								//tries to close the writer
								if (tree.writer != null)
			            			tree.writer.close();
								
							} catch (RuntimeException | IOException error) {
								error.printStackTrace();
							}
						}
					}
			}
		});
    }
}