/**
 * FileName: OperatorNode.java
 * Author: Rebecca Johnson
 * Date: 9/14/2017
 * Description: Child class used to set value of operator nodes. 
 * 		It is derived from the abstract TreeNode class.
 */

public class OperatorNode extends TreeNode {
      
	//setter method to set value of nodes
	public void setNodes(String operator, TreeNode leftNode, TreeNode rightNode) {
        this.value = operator;
        this.left = leftNode;
        this.right = rightNode;
	}

	//getter method to return the node data
	public String getNodes() {
		return this.value;
	}
}