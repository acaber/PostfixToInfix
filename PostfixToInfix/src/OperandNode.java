/**
 * FileName: OperandNode.java
 * Author: Rebecca Johnson
 * Date: 9/14/2017
 * Description: Child class used to set value of operand nodes. 
 * 		It is derived from the abstract TreeNode class.
 */

public class OperandNode extends TreeNode {
    	
	//setter method to set value of nodes
	public void setNodes(String data, TreeNode leftNode, TreeNode rightNode){
		this.value = data;
	}

	//getter method to return the node data
	public String getNodes() {
		return this.value;	
	}
}