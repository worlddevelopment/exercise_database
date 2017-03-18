package aufgaben_db;


import org.odftoolkit.odfdom.dom.element.office.OfficeTextElement;
import org.odftoolkit.odfdom.pkg.OdfFileDom;


/**
 * For storing a common Node (e.g. org.w3c.dom.Node) and the order index
 * in which place this Node occurred within all Nodes that have been
 * traversed. The nodes traversed at this point of time are still
 * identical from the beginning on to a certain point/index for each
 * (at this moment still identical) Parts' XML markup document DOM.
 * (That means the last part had to traverse most Nodes as this
 * part declaration is the last in the XML).
 *
 * Exactly spoken it's the sheetdraft's DOM that is being traversed as
 * the initial parts each are exact copies of the sheetdraft.
 *
 * @reason
 * This was required as elementsTraversed.indexOf() seems not to be able
 * to differentiate between one text:office node and another of the same
 * type. As many of one type/tagname can occur this is a big problem
 * e.g. instead of 33 for deepestAllPartsCommonParent 1 was returned!
 * @author J. R.I.B.-Wein, worlddevelopment
 */
public class NodeTraversed extends OfficeTextElement {


	// ======= ATTRIBUTES
	private static final long serialVersionUID = 1L;

	private int index_order_position_within_traversed_nodes;

//	private org.w3c.dom.Node node;




	// ======= CONSTRUCTORS
	public NodeTraversed(OdfFileDom ownerDoc) {
		super(ownerDoc);
	}

//	public NodeTraversed(org.w3c.dom.Node node, int index_order_position_within_traversed_nodes) {
//		this.node = node;
//		this.index_order_position_within_traversed_nodes = index_order_position_within_traversed_nodes;
//	}




	// ======= METHODS
	public void setIndex(int index_order_position_within_traversed_nodes) {
		this.index_order_position_within_traversed_nodes = index_order_position_within_traversed_nodes;
	}
	public int getIndex() {
		return this.index_order_position_within_traversed_nodes;
	}
//	public org.w3c.dom.Node getNode() {
//		return this.node;
//	}



}
