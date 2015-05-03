package tudelft.ti2806.pl3.data.graph;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import tudelft.ti2806.pl3.data.Genome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SingleNode implements Node {
	// Parse data
	protected int nodeId;
	protected Genome[] source;
	protected int refStartPoint;
	protected int refEndPoint;
	protected byte[] content;
	
	// Location data
	protected long xaxisStart = -1;
	protected int yaxisOrder;
	
	/**
	 * A list of all nodes from incoming connections from this node.
	 * 
	 * @see getIncoming
	 */
	private List<SingleNode> incoming = new ArrayList<SingleNode>();
	
	/**
	 * {@link #incoming} is a list of all incoming connections from this node.
	 * {@link #incoming} should not be used in a filtered graph, because it may
	 * contain references to nodes which are not in the filtered graph.
	 * 
	 * <p>
	 * This field is not interesting for {@link CombinedNode}, because these
	 * nodes only appear after filtering of a graph.
	 */
	public List<SingleNode> getIncoming() {
		return incoming;
	}
	
	/**
	 * A list of all nodes from outgoing connections from this node.
	 * 
	 * @see #getOutgoing
	 */
	private List<SingleNode> outgoing = new ArrayList<SingleNode>();
	
	/**
	 * {@link #outgoing} is a list of all outgoing connections from this node.
	 * {@link #outgoing} should not be used in a filtered graph, because it may
	 * contain references to nodes which are not in the filtered graph.
	 * 
	 * <p>
	 * This field is not interesting for {@link CombinedNode}, because these
	 * nodes only appear after filtering of a graph.
	 * 
	 * @return the list of all nodes from outgoing edges from this node
	 */
	public List<SingleNode> getOutgoing() {
		return outgoing;
	}
	
	/**
	 * Initialise a {@code SingleNode}.
	 *
	 * @param nodeId
	 *            the id of the node
	 * @param source
	 *            the names of the genomes where this piece is comming from
	 * @param refStartPoint
	 *            the start index on the genome
	 * @param refEndPoint
	 *            the end index on the genome
	 * @param contentOfTheNode
	 *            the size of this {@code Node}
	 */
	public SingleNode(int nodeId, Genome[] source, int refStartPoint,
			int refEndPoint, byte[] contentOfTheNode) {
		this.nodeId = nodeId;
		if (source == null) {
			this.source = null;
		} else {
			this.source = source.clone();
		}
		this.refStartPoint = refStartPoint;
		this.refEndPoint = refEndPoint;
		if (contentOfTheNode == null) {
			this.content = null;
		} else {
			this.content = contentOfTheNode.clone();
		}
	}
	
	@Override
	public String toString() {
		return "SingleNode [nodeId=" + nodeId + ", source="
				+ Arrays.toString(source) + ", refStartPoint=" + refStartPoint
				+ ", refEndPoint=" + refEndPoint + ", content="
				+ Arrays.toString(content) + ", incoming=" + incoming
				+ ", outgoing=" + outgoing + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(content);
		result = prime * result + nodeId;
		result = prime * result + refEndPoint;
		result = prime * result + refStartPoint;
		result = prime * result + Arrays.hashCode(source);
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		SingleNode other = (SingleNode) obj;
		if (!Arrays.equals(content, other.content)) {
			return false;
		}
		if (incoming == null) {
			if (other.incoming != null) {
				return false;
			}
		} else if (!incoming.equals(other.incoming)) {
			return false;
		}
		if (nodeId != other.nodeId) {
			return false;
		}
		if (outgoing == null) {
			if (other.outgoing != null) {
				return false;
			}
		} else if (!outgoing.equals(other.outgoing)) {
			return false;
		}
		if (refEndPoint != other.refEndPoint) {
			return false;
		}
		if (refStartPoint != other.refStartPoint) {
			return false;
		}
		if (!Arrays.equals(source, other.source)) {
			return false;
		}
		return true;
	}
	
	@Override
	public int getNodeId() {
		return nodeId;
	}

	// Suppressed in the interest of space and time
	@SuppressFBWarnings({"EI_EXPOSE_REP"})
	@Override
	public Genome[] getSource() {
		return source;
	}
	
	@Override
	public int getRefStartPoint() {
		return refStartPoint;
	}
	
	@Override
	public int getRefEndPoint() {
		return refEndPoint;
	}

	// Suppressed in the interest of space and time
	@SuppressFBWarnings({"EI_EXPOSE_REP"})
	@Override
	public byte[] getContent() {
		return content;
	}
	
	@Override
	public int getYaxisOrder() {
		return yaxisOrder;
	}
	
	@Override
	public long getXaxisStart() {
		return xaxisStart;
	}
	
	@Override
	public long getXEnd() {
		return xaxisStart + this.getWidth();
	}
	
	@Override
	public long getWidth() {
		return content.length;
	}
	
	/**
	 * Recursive method for calculating the axis start.<br>
	 * The space between nodes has a minimum of 1, because we have to reserve
	 * space for the edges.
	 * 
	 * @return the calculated startX value.
	 */
	public long calculateStartX() {
		if (this.getXaxisStart() != -1) {
			return this.getXaxisStart();
		}
		long max = 0;
		for (SingleNode incomingNode : this.getIncoming()) {
			max = Math.max(max,
					incomingNode.calculateStartX() + incomingNode.getWidth()
							+ 1);
			// We want one extra space for the edges.
		}
		this.xaxisStart = max;
		return max;
	}
	
	/**
	 * Calculates the whitespace available on the right side of this node.
	 * 
	 * @return the number of base pairs that fit in the whitespace on the right
	 *         side of the node.
	 */
	public long calculateWhitespaceOnRightSide() {
		long min = Long.MAX_VALUE;
		for (SingleNode incomingNode : this.getOutgoing()) {
			min = Math.min(min, incomingNode.getXaxisStart());
		}
		return min - this.getXEnd();
	}
}
