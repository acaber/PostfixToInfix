/**
 * FileName: TreeNode.java
 * Author: Rebecca Johnson
 * Date: 9/14/2017
 * Description: Abstract class used to define the nodes in the
 * 		expression tree.
 */

public abstract class TreeNode {
    	
	 int added = 0;
	 String registry = "";
	
	 //defines nodes
	 String value;
	 TreeNode left;
	 TreeNode right;
   
	 //abstract method that sets the values of the nodes
	 abstract public void setNodes(String value, TreeNode leftNode, TreeNode rightNode);
    
	 //abstract method that will return the node data
	 abstract public String getNodes();
    
}