package tudelft.ti2806.pl3.visualization.wrapper.operation;

import tudelft.ti2806.pl3.visualization.wrapper.CombineWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.HorizontalWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.NodePosition;
import tudelft.ti2806.pl3.visualization.wrapper.NodeWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.SingleWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.SpaceWrapper;
import tudelft.ti2806.pl3.visualization.wrapper.VerticalWrapper;

/**
 * A {@link WrapperOperation} is used to when we want to handle different
 * implementations of {@link NodeWrapper} in a different way without identifying
 * and casting the objects.
 * 
 * <p>
 * Examples of usage:
 * <ul>
 * <li>Calculating which {@link NodeWrapper} should collapse.
 * <li>Calculating the order of the nodes within a {@link CombineWrapper}.
 * <li>Calculating the y position of a {@link NodeWrapper}.
 * </ul>
 * 
 * @author Sam Smulders
 *
 */
public interface WrapperOperation {
	
	/**
	 * This method makes the node call back to prevent identifying the class
	 * type and to prevent casting.<br>
	 * This method should most likely be left untouched.
	 * 
	 * @param node
	 *            the node which is called to make a call back
	 * @param container
	 *            the wrapper containing this node
	 */
	default void calculate(NodeWrapper node, NodeWrapper container) {
		node.calculate(this, container);
	}
	
	/**
	 * Executes the operation for {@link HorizontalWrapper}.
	 * 
	 * <p>
	 * The default method just calls the {@link #calculate(NodeWrapper)} for
	 * each {@link NodeWrapper} within this {@link CombineWrapper}.
	 * 
	 * @param wrapper
	 *            the node to perform the operation on
	 * @param container
	 *            the wrapper containing this node<br>
	 *            {@code null} if this node isn't wrapped
	 */
	default void calculate(HorizontalWrapper wrapper, NodeWrapper container) {
		for (NodeWrapper node : wrapper.getNodeList()) {
			calculate(node, container);
		}
	}
	
	/**
	 * Executes the operation for {@link VerticalWrapper}.
	 * 
	 * <p>
	 * The default method just calls the {@link #calculate(NodeWrapper)} for
	 * each {@link NodeWrapper} within this {@link CombineWrapper}.
	 * 
	 * @param wrapper
	 *            the node to perform the operation on
	 * @param container
	 *            the wrapper containing this node<br>
	 *            {@code null} if this node isn't wrapped
	 */
	default void calculate(VerticalWrapper wrapper, NodeWrapper container) {
		for (NodeWrapper node : wrapper.getNodeList()) {
			calculate(node, container);
		}
	}
	
	/**
	 * Executes the operation for {@link SpaceWrapper}.
	 * 
	 * <p>
	 * The default method just calls the {@link #calculate(NodeWrapper)} for
	 * each {@link NodeWrapper} within this {@link CombineWrapper}.
	 * 
	 * @param wrapper
	 *            the node to perform the operation on
	 * @param container
	 *            the wrapper containing this node<br>
	 *            {@code null} if this node isn't wrapped
	 */
	default void calculate(SpaceWrapper wrapper, NodeWrapper container) {
		for (NodeWrapper node : wrapper.getNodeList()) {
			calculate(node, container);
		}
	}
	
	/**
	 * Executes the operation for {@link SingleWrapper}.
	 * 
	 * <p>
	 * The default method just calls the {@link #calculate(NodeWrapper)} for the
	 * {@link NodeWrapper} within this {@link SingleWrapper}.
	 * 
	 * @param wrapper
	 *            the node to perform the operation on
	 * @param container
	 *            the wrapper containing this node<br>
	 *            {@code null} if this node isn't wrapped
	 */
	default void calculate(SingleWrapper wrapper, NodeWrapper container) {
		calculate(wrapper.getNode(), container);
	}
	
	/**
	 * Executes the operation for {@link NodePosition}.
	 * 
	 * <p>
	 * The default method is empty.
	 * 
	 * @param wrapper
	 *            the node to perform the operation on
	 * @param container
	 *            the wrapper containing this node<br>
	 *            {@code null} if this node isn't wrapped
	 */
	default void calculate(NodePosition wrapper, NodeWrapper container) {
		
	}
}
