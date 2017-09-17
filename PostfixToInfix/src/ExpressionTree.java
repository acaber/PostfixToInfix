import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.System.*;

/**
 * FileName: ExpressionTree.java
 * Author: Rebecca Johnson
 * Date: 9/14/2017
 * Description: This class receives a postfix expression and creates an
 * 		expression tree that stores each value in the expression. It will 
 * 		then create a corresponding infix expression and create a file that
 * 		contains the three address format data.
 */


 public class ExpressionTree {

    private TreeNode root;
    BufferedWriter writer;
    private int regCounter = 0;

    //constructor
    public ExpressionTree(){
    	
    	try {
    		
    		//attempts to create and open the file
            writer = new BufferedWriter(new FileWriter("ThreeAddressRegistry.txt", true));
            
            //initiates new expression
            writer.write("New Expression: ");
            
            //moves the writer to the next line
            writer.newLine();
            
        } catch(Exception e) {
        	
        	//prints out the error message
            JOptionPane.showMessageDialog(null, "Error: File cannot be opened.");
          
        }
    }

    //this method builds the expression tree
    public boolean buildTree(String expression) throws RuntimeException {

    	//stores the expression to an array, splitting up each character
        String[] postfix = expression.split("");
        
        //creates new array of TreeNode objects
        TreeNode[] nodesArray = new TreeNode[postfix.length];
        
        //declares and initializes counter
        int counter = 0;

        //goes through each element in the postfix expression
        for (String token : postfix) {
        	
        	//checks if token is an integer
            if(isInteger(token)){
            	
            	//sets current index of nodesArray as an OperandNode object
                nodesArray[counter] = new OperandNode();
                
                //sets current index to the current token
                nodesArray[counter].setNodes(token, null, null);
            }
            
            //this executes if the token is not an integer
            else {
            	
            	//checks if token is not a valid token
            	if (!isValidOperator(token)) {
            		
            		//prints out error message
                    JOptionPane.showMessageDialog(null, "Invalid token: " + token);
                    
                    //throws new RuntimeException error
                    throw new RuntimeException();
                  
                } 
            	
            	//executes if token is a valid token
            	else {
            		
            		//sets current index of nodesArray as an OperatorNode object
                    nodesArray[counter] = new OperatorNode();
                    
                    //sets current index to the current token
                    nodesArray[counter].setNodes(token, null, null);
                }  
            }
            
            //increments counter
            counter ++; 
            
        } 

        int length = postfix.length;
       

        //executes as long as the length is greater than 1
        while(length > 1) {
        	
            for(int i = 0; i < postfix.length; i++) {
            	
            	//executes if the current index of nodesArray is not null
                if(nodesArray[i] != null) {
                	
                	//executes if the current node has not already been added
                    if (nodesArray[i].added != 1) {
                        
                    	//checks that current index is not a number
                    	if(!isInteger(nodesArray[i].getNodes())) {
                    
                    		//calls performNodeOperation method
                    		performNodeOperation(nodesArray, i, postfix.length);
                    		length = length - 2;
                            i = - 1;
                           }
                    	}
                    } 
                
                else
                    break;
                }
            }
        
        //sets the root to the first element in array
        root = nodesArray[0];
        return true;
        
    }

    //this method returns the infix expression
    public String getExpressionString() throws IOException {
        return buildInfixExpression(root);
    }

    //this method performs operations for each node
    private void performNodeOperation(TreeNode[] ar, int count, int size) {
    	
    	//creates a new instance of an operatorNode object
    	TreeNode t = new OperatorNode();
    	
    	//sets the nodes data
        t.setNodes(ar[count].getNodes(), ar[count - 2], ar[count - 1]);
        
        //changes added for this node to 1
        t.added = 1;
        
        //updates registry value
        t.registry = "R" + regCounter++;
        
        //updates index in treeNode array and sets it to t
        ar[count - 2] = t;
        
        //calls java's arraycopy method to update the array with the new nodes
        arraycopy(ar, count - 1 + 2, ar, count - 1, size - 2 - (count - 1));
        ar[size - 1] = null;
        ar[size - 2] = null;
        
    }

    //this method checks if the token is an integer
    private boolean isInteger(String s) {
	   
    	try{
    		Integer.parseInt(s);
    		return true;
    	} catch(Exception e) {
    		return false;
	    }
    	
    }
   
    //this method check if the token is a valid operator
    private boolean isValidOperator(String op) {
    	
    	if(op.equals("+"))
    		return true;
    	else if(op.equals("-"))
    		return true;
    	else if(op.equals("*"))
    		return true;
    	else if(op.equals("/"))
    		return true;
    	else
    		return false;
    	
	}
   
    //this method creates the infix expression from the expression tree
    private String buildInfixExpression(TreeNode node) throws IOException {

        String infixExpression = "";

        //executes if the node is not null
        if(node != null) {
        	
        	//executes if the node has been added
            if(node.added == 1)
            	//adds left parentheses
                infixExpression ="(";
            
            //executes if the left and right nodes are not null
            if(node.left != null && node.right != null) 
            	//recursively adds the left node to the expression
                infixExpression = infixExpression + buildInfixExpression(node.left);
            
            //adds operator to expression
            infixExpression = infixExpression + node.value;

            //executes if the left and right nodes are not null
            if(node.left != null && node.right != null) 
            	//recursively adds the right node to the expression
                infixExpression = infixExpression + buildInfixExpression(node.right);
            
            //executes if the node has been added
            if(node.added == 1)
            	//adds right parentheses
                infixExpression = infixExpression + ")";

            //executes if the left and right nodes are not null
            if(node.left != null && node.right != null) {
                
            	//string to hold left and right node
            	String leftNode;
                String rightNode;
            	
                //executes if left node has been added
                if(node.left.added == 1) 
                	//updates left node registry
                    leftNode = node.left.registry;
                
                else 
                	//updates left node value
                    leftNode = node.left.value;
                
                //executes if right node has been added
                if(node.right.added == 1) 
                	//updates right node registry
                    rightNode = node.right.registry;
                
                else 
                	//updates left node value
                	rightNode = node.right.value;

                //checks if operator is a plus sign and adds it to the registry
                if(node.value.equals("+"))
                        writer.write("Add " + node.registry + " " + leftNode + " " + rightNode);
               
                //checks if operator is a minus sign and adds it to the registry
                else if(node.value.equals("-"))
                        writer.write("Sub " + node.registry + " " + leftNode + " " + rightNode);
                
                //checks if operator is a multiplication sign and adds it to the registry
                else if(node.value.equals("*"))
                        writer.write("Mul " + node.registry + " " + leftNode + " " + rightNode);
                
                //checks if operator is a division sign and adds it to the registry
                else if(node.value.equals("/"))
                        writer.write("Div " + node.registry + " " + leftNode + " " + rightNode);
           
                //moves writer to a new line
                writer.newLine();
            
            }
        }

        //returns the infix expression
        return infixExpression;

    }

}