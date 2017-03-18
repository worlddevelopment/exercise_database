package core;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.docx4j.TraversalUtil;
import org.docx4j.XmlUtils;
import org.docx4j.TraversalUtil.CallbackImpl;
import org.docx4j.wml.Br;
import org.docx4j.wml.CommentRangeEnd;
import org.docx4j.wml.CommentRangeStart;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.Drawing;
import org.docx4j.wml.Hdr;
import org.docx4j.wml.SdtBlock;
import org.docx4j.wml.R.CommentReference;
import org.docx4j.wml.R.Tab;
import org.jdom2.Content;
import org.jdom2.Element;
import org.jvnet.jaxb2_commons.ppp.Child;
import org.w3c.dom.NodeList;

import docx4j_library.DocxUtils;





/**
 * A sheetdraft Part is defined by a resulting String extracted
 * from the sheet it belonged to.
 *
 * @author J.R.I.Balzer-Wein, worlddevelopment
 *
 */
public class Part extends ContentToImage {


	// ======= ATTRIBUTES
//	private int sheetdraft_id;
//	//private Sheetdraft sheetdraft_part_is_from;
//	private String sheetdraft_filelink_part_is_from;//easier to keep integrity
	/*
	Now even easier: a method determines the sheetdraft_filelink on
	the fly. This is possible because the filelink of the part
	contains the filelink to the sheetdraft it belongs to.
	*/


	// In content to image:
	//private String filelink;
	// Within the part filelink:
	//private String sheetdraft_filelink = 0;//the part belongs to currently
	//private String originsheetdraft_filelink; better not store this
	// => load it from db if needed via getOriginsheetdraftFromDB

	/**
	 * From splitter/split by hint in the filelink we determine the
	 * best fitting pattern of enum Muster.java.
	 * We also first try to determine the pattern/Muster on the fly
	 * with automatism for several part declarations.
	 * The finally successful declarations with all the extra data
	 * like line of file, ... will be stored here AND in the Part
	 * objects redundantly!
	 * Of a splitby/splitter is given, determine best fitting declaration
	 */
	private Declaration splitbyDeclaration;

	private String header; // Each part goes with individual formatting
	//private int is_native_format;// for joining here not needs be in D


	// Date TODO: Clear if int or string suitable.
	private Calendar whencreated = Calendar.getInstance();
	private Calendar whenchanged = Calendar.getInstance();



	public StringBuffer textBuffer = new StringBuffer();
	public void clearTraversedAndTextBuffer() {
		textBuffer = new StringBuffer();
		isDeclarationFound = false;
		sheetdraftElementsTraversed = new ArrayList<Object>();
		// Not 0 because it is incremented prior to use:
		sheetdraftElementsTraversed_index = -1;

		wayTowardsRoot = new ArrayList<Object>();
	}
	// 1st dimension: depth; 2nd: elements within this depth!
	public List<Object> sheetdraftElementsTraversed
		= new ArrayList<Object>();
	/*BUG?: If levelDepth is set first to 0 (until now all fine), then
	to 1 via recursion OutOfBounds!?
	List<List<Element>> sheetdraftElementsTraversedLevelsWithBranches
	= new ArrayList<List<Element>>();
	// Create a List for this element and its siblings (they are this
	// depth's leaves).
	if (sheetdraftElementsTraversedLevelsWithBranches.isEmpty()
			|| (levelDepth >= sheetdraftElementsTraversedLevelsWithBranches.size()
			&& sheetdraftElementsTraversedLevelsWithBranches.get(levelDepth) != null)
			|| sheetdraftElementsTraversedLevelsWithBranches.get(levelDepth) == null) {
		sheetdraftElementsTraversedLevelsWithBranches.add(
				levelDepth, new ArrayList<Element>());
	}
	sheetdraftElementsTraversedLevelsWithBranches.get(levelDepth)
		.add(sheetdraftElementsTraversed_index, e);
	*/
	//Map<Element, Integer> sheetdraftElementsTraversed_depth
	//= new HashMap<Element, Integer>();
	public List<Integer> sheetdraftElementsTraversed_depth
		= new ArrayList<Integer>();

	// When we emerge from our search, this points to the
	// elementReachedWhenDeclarationFoundInNativeFormat.
	int sheetdraftElementsTraversed_depth_max = -1;
	int sheetdraftElementsTraversed_index = -1;
	Object sheetdraftElementReachedWhenDeclarationFoundInNativeFormat;
	int sheetdraftElementReachedWhenDeclarationFoundInNativeFormat_index;

	Object deepestAllPartsCommonParentElement;
	//Element deepestAllPartsCommonParentElement;
	// The below can be used to find the one above, too:
	// (is set while emergine)
	Object deepestAllPartsCommonParentElement_sChildContainingThisPart;
	//int elementsTraversed_deepestCommonParentElement_index;
	//int elementsTraversed_deepestCommonParentElement_sChildContainingThisPart_index;

	// Required for being able to dive deeper to remove parts that
	// share a branch (i.e. deepestParentElement_sChild)
	// because several parts could be spread across different tables
	// and lists within a document.
	// ArrayList for having an array underneath to get reliable and
	// quick (constant time) index access.
	List<Object> wayTowardsRoot = new ArrayList<Object>();

	Object highestParentElementContainingThisPartOnly;


	/**
	 * Determines element reached when declaration finally was found in
	 * the XML.
	 *
	 * Could be used on the part's XML too but currently is only
	 * used on the sheetdraft's markup. That's why the
	 * sheetdraftElementReachedWhenDeclarationFoundInNativeFormat
	 * has the preceding 'sheetdraft'.
	 *
	 * @param o
	 * @throws Exception
	 */
	public void travelDownUntilDeclarationFound(org.w3c.dom.Node  o)
		throws Exception {
		travelDownUntilDeclarationFound(o, this.getDeclaration());
	}
	public void travelDownUntilDeclarationFound(
			org.w3c.dom.Node  o, Declaration declaration) {
		travelDownUntilDeclarationFound(o, declaration, 0);
	}
	boolean isDeclarationFound = false;
	public void travelDownUntilDeclarationFound(
			org.w3c.dom.Node o, Declaration declaration, int levelDepth) {
		// Store extra information:
		if (sheetdraftElementsTraversed_depth_max < levelDepth) {
			sheetdraftElementsTraversed_depth_max = levelDepth;
		}


		// Store the Element as traversed. (Only elements are stored
		// as Text has no children!)
		sheetdraftElementsTraversed.add(++sheetdraftElementsTraversed_index, o);
		sheetdraftElementsTraversed_depth.add(sheetdraftElementsTraversed_index, levelDepth);


		// Declaration not yet identified! Then we have to add more plain
		// text Elements.
		if (o instanceof org.w3c.dom.Element) {
			//Element elementChildProcessedAfterThisEmergingFrom;
			org.w3c.dom.Element e = (org.w3c.dom.Element) o;
			String elementName = e.getNodeName();//getQualifiedName();
			elementName = e.getTagName();

			// To find the declaration the text tags have to be
			// evaluated.
			// Add additional formatting.
			// Standalone elements:
			if (elementName.equals("text:tab")) { // add tab for text:tab
			//if (o instanceof org.w3c.dom.Element) {
				textBuffer.append("\t");
				//return;
			}
			else if (elementName.equals("text:line-break")) {
				textBuffer.append(System.getProperty("line.separator"));
				//return;
			}
			else if (elementName.equals("text:s")) { // add space for text:s
				textBuffer.append(" ");
				//return;
			}

			// Non-standalone nodes:
			if (elementName.equals("text:p") || elementName.equals("text:h")
					|| elementName.equals("text:list")
					/*|| elementName.equals("text:list-item")*/
					) {
				// Formerly simply \n without rewind \r:
				textBuffer.append(System.getProperty("line.separator"));
			}
			else if (elementName.equals("text:note-citation")) {
				textBuffer.append(" (");
			}
			else if (elementName.equals("text:span")) {
				//textBuffer.append(" "); This creates a mess as it
				// liberately adds spaces!
				// It's assumed spaces are where they are required already.
			}





			// Text only nodes or better said its text-only contents
			// will be added here:
			//List<Content> children = e.getContent();
			org.w3c.dom.NodeList children = e.getChildNodes();
			//Iterator<?> iterator = children.iterator();
			//while (iterator.hasNext()) {
			//	Object child = iterator.next();
			for (int i = 0; i < children.getLength(); i++) {

				if (isDeclarationFound) {
					return ;// otherwise the last child will always
					// be the found one eventhough it may already have
					// been found in the first child (text) node (because
					// the loop otherwise continues if we emerge from
					// the child where the declaration finally was found!
				}
				// Recursively process the child element
				travelDownUntilDeclarationFound(
						children.item(i), declaration, levelDepth + 1);

			}


			// Another line break to be inserted?
			if (elementName.equals("text:p") || elementName.equals("text:h")
					|| elementName.equals("text:list")
					|| elementName.equals("text:list-item")
					) {
				// Formerly simply \n without rewind \r:
				textBuffer.append(System.getProperty("line.separator"));
			}
			else if (elementName.equals("text:note-citation")) {
				textBuffer.append(") ");
			}
			else if (elementName.equals("text:span")) {
				//textBuffer.append(" "); This creates a mess as it
				// liberately adds spaces! It's
				// assumed spaces are where they are required already.
			}

		}
		//TEXT
		else if (o instanceof org.w3c.dom.Text) {
			// If object is a Text Node, then append the text.
			org.w3c.dom.Text t = (org.w3c.dom.Text) o;
			textBuffer.append(t.getTextContent());
			//textBuffer.append(System.getProperty("line.separator"));

			// After processing each element we check for the
			// termination or cancel condition:
			String dec_plain_text = declaration.getFirstWord().trim()
				+ " " + declaration.getSecondWord().trim() + " "
				+ declaration.getThirdWord().trim();
			/*
			Have we found the Declaration in the native format markup
			again?
			Do not use match because point (.) could be contained e.g.
			'1. Part' and would be interpreted as regex .,
			allowing to match things that must not match.
			*/
			if (textBuffer.toString().replaceAll("(\r\n)+|[\r\n]+", " ")
					.replaceAll("[ ][ ]+", " ")
					.contains(dec_plain_text)
					&& !isThisElementAlreadyTheDeclarationOfAnotherPart(o)) {
				/*<-- if a similar declaration occurs twice, and one
				of those before were of this kind, then we have to skip
				the first occurrence in the part textBuffer and
				match the second (Note: we should even then be aware that
				there might be even more than two similar declarations,
				so TODO skip a dynamic amount of declarations).
				*/
				// This is redundant because as we stop here the
				// sheetdraftElementsTraversed_index is not incremented
				// and points to exactly the following element:
				this.sheetdraftElementReachedWhenDeclarationFoundInNativeFormat = o;
				//.getParentNode();
				//= sheetdraftElementsTraversed.get(sheetdraftElementsTraversed_index);//the root
				this.sheetdraftElementReachedWhenDeclarationFoundInNativeFormat_index
					= sheetdraftElementsTraversed_index;
//				this.sheetdraftElementReachedWhenDeclarationFoundInNativeFormat_depth
//				= levelDepth;
				isDeclarationFound = true;
				return ;
			}
		}
//		else if (o instanceof Drawing) {
//
//		}


		/* object not instance of Element */
		// => We reached the end, no more elements and still the
		// termination condition did
		//not grip!! => No such element we looked for was found!
		return ;
	}



//	public org.w3c.dom.Node getDeepestAllPartsCommonParentElement() {
//		return (deepestAllPartsCommonParentElement_sChildContainingThisPart)
//				.getParentNode();
//		if (elementsTraversed_deepestCommonParentElement_index < 1) {
//			System.out.print(
//				Global.addMessage("No deepest to all parts common parent element has been looked for."
//						+ "The index in all traversed elements currently is: " + elementsTraversed_deepestCommonParentElement_index, "danger")
//			);
//			return null;
//		}
//		return sheetdraftElementsTraversed.get(elementsTraversed_deepestCommonParentElement_index);
//	}



	/**
	 * Deletes non-part related xml content and the then no longer
	 * referenced refs.
	 *
	 * Called from Sheetdraft.java.
	 *
	 * @param sheetdraftDeepestCommonParentElement_index
	 * @param exception <-- The deepest common parent element's child containing this part('s declaration).
	 * @throws Exception
	 */
	public void deleteAllChildrenOfExceptFor(int sheetdraftDeepestCommonParentElement_index)
			throws Exception {
		deleteAllChildrenOfExceptFor(sheetdraftDeepestCommonParentElement_index, null);
	}
	public void deleteAllChildrenOfExceptFor(int sheetdraftDeepestCommonParentElement_index
			, Part partSucceding)
			throws Exception {
		// As the elementsTraversed are equal for the part and the
		// sheetdraft the following is correct so that we can get the
		// deepest common parent element despite aiming at the XML of
		// this part:
		org.w3c.dom.Node candidate
			= (org.w3c.dom.Node)sheetdraftElementsTraversed
			.get(sheetdraftDeepestCommonParentElement_index);
		if (candidate != ((org.w3c.dom.Node)
					deepestAllPartsCommonParentElement_sChildContainingThisPart)
				.getParentNode()) {
			System.out.print(
				Global.addMessage("DeleteAllChildrenNodesOfExceptFor discovered a discrepancy: Redebug this method!",
						"danger")
			);
		}
		deleteAllChildrenOfExceptFor(
				candidate,
				// For all parts individually determined while
				// emerging up while looking for the deepest to all
				// parts common parent(Safer than decrementing index)
				(org.w3c.dom.Node) deepestAllPartsCommonParentElement_sChildContainingThisPart
				//<-- for each part determined while emerging up
				// while looking for the deepest to all parts
				// common parent element.
				/* nolongerTODO For not cleanly formatted documents
				 * check if this element still is available
				 * in any other part's traversed elements list.
				 */
				//, sheetdraftElementsTraversed.get(
				//		sheetdraftDeepestCommonParentElement_index + 1)
				, partSucceding
		);
	}



	public void removeAllSiblingsOf(org.w3c.dom.Node deepestAllPartsCommonParentElement_sChildContainingThisPart
			, Part partAfterThis) throws Exception {
		deleteAllChildrenOfExceptFor(deepestAllPartsCommonParentElement_sChildContainingThisPart
				.getParentNode()
				, deepestAllPartsCommonParentElement_sChildContainingThisPart, partAfterThis);
	}



	public void deleteAllChildrenOfExceptFor(org.w3c.dom.Node deepestCommonParentElement
			, org.w3c.dom.Node exception, Part partAfterThis)
			throws Exception {

		if (deepestCommonParentElement == null) {
			System.out.print("delete all children of except for resulted in the parent being null!");
			return ;
		}

		/*
		DEPRECATED, now doing this when travelling down to
		determine where part declaration can be found!

		// Travel down to find the deepest element that o n l y
		// contains this part and no other parts anymore!
		// because the deepestAllPartsCommonParentElement
		// containing all parts does not imply that the child
		// nodes only contain one part each!
		boolean isElementContainingOnePartOnly;
		isElementContainingOnePartOnly = false;
		int wayTowardsRoot_index;
		wayTowardsRoot_index = wayTowardsRoot.size();// - 1;
		while (!isElementContainingOnePartOnly
				&& --wayTowardsRoot_index > 0) {
			// way _index equals zero once the element when the
			// part declaration was found is reached.
			// TODO at least that should be the case?
			org.w3c.dom.Node parentElement;
			if (wayTowardsRoot.get(wayTowardsRoot_index)
					instanceof org.w3c.dom.Node) {
				parentElement = (org.w3c.dom.Node) wayTowardsRoot
					.get(wayTowardsRoot_index);
			}
			// Assumption that only one part is contained
			// (so this node is safe to be removed)
			isElementContainingOnePartOnly = true;
			//for (org.w3c.dom.Node sibling : parentElement
					//.getParentNode()
					.getChildNodes().) {
			for (Part part : sheetdraft.allPartsRawContent
					.values()) {
				//if (sibling.equals(siblingToSpare)) {
				//	continue;
				//}
				if (part.equals(this)) {
					continue;
				}
				if (part.wayTowardsRoot.contains(parentElement)) {
					isElementContainingOnePartOnly = false;
					break;
				}
				//TODO save this first element where only part within
			}

		}
		org.w3c.dom.Node siblingToSpare;
		if (wayTowardsRoot.get(wayTowardsRoot_index - 1)
				stanceof org.w3c.dom.Node) {
			siblingToSpare = (org.w3c.dom.Node) wayTowardsRoot
				get(wayTowardsRoot_index - 1);
		}
		*/



		//LOAD THE XML
		/*
		SAXBuilder sax = new SAXBuilder();
		Document doc
			= sax.build(Global.getInputStream(filelink, "content.xml"));
		TextDocument part_textDocument
			= TextDocument.loadDocument(filelink);
		org.w3c.dom.Node part_rootElement
			= part_textDocument.getContentRoot();
		*/


		// Divided through 2 because of start and eng tag.
		//int elementsCountEstimation = doc.getContent().toString()
		//.split("<").length / 2;
		// For lists this is no longer necessary, just out of interest
		// how good such estimates prove to be:
		//elementsTraversed = new Element[elementsCountEstimation];



		// First get the elements in the markup of this part
		// filesystem representation.

		// Start with root Element and find deepest common parent
		// element again in this file:
		//getDeepestAllPartsCommonParentElement();
		//= findDeepestCommonParentElementEquivalentRecursively(
		//		doc.getRootElement()
		//		, sheetdraftDeepestCommonParentElement);



		if (partAfterThis == null) {
			// GUESS WHAT STILL BELONGS TO THE PART IF NOT ALL
			// CONTENT OF THIS PART IS CONTAINED WITHIN ONE
			// XML-TAG!
			// Remove all below/deeper than the deepest common parent
			// element but not the part xml markup:
			boolean keep_next_element_because_the_before_was_a_heading
				= false;
			//for (Element child : sheetdraftDeepestCommonParentElement
			//		.getChildren()) {
			NodeList childNodes = deepestCommonParentElement
				.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				org.w3c.dom.Node child = childNodes.item(i);
				// Spare the exception, that is the part that
				// shall remain:
				if (!child.equals(exception)) {

					/*
					IF deletion of references that are no longer
					referenced is NOT possible
					after having removed these main content xml nodes,
					THEN REFERENCES MUST BE FOLLOWED!
					*/

					// Declaring sequence content of the odt?? Why not
					// dynamically, OpenDocumentFormat?
					if (child.getNodeName().equals("text:sequence-decls")) {
						continue;
					}

					// stand alone elements
					if (child.getNodeName().equals("text:s")) {
						continue;
					}
					else if (child.getNodeName().equals("text:tab")) {
						continue;
					}
					else if (child.getNodeName().equals("text:line-break")) {
						continue;
					}

					// Does this to a high probability belong the
					// part declaration?
					if (keep_next_element_because_the_before_was_a_heading) {
						// This time it was not a heading neither one
						// of the standalone elements above?
						// => No longer keep the next few elements.
						if (!child.getNodeName().equals("text:h")) {
							keep_next_element_because_the_before_was_a_heading
								= false;
						}
						continue;
					}
					//child.getParentNode().removeChild(child);
					//this.deepestAllPartsCommonParentElement_sChildContainingThisPart
					//	getParentNode()
					org.w3c.dom.Node deletedNode
						= deepestCommonParentElement.removeChild(child);
					if (deletedNode.equals(child)) {
						--i;// The deleted node will be replaced by
						// the following childNodes, this shift has
						// to be taken into account.
					}

//					if (child instanceof org.odftoolkit.odfdom
//							.dom.element.text.TextAElement) {
//						this.deepestAllPartsCommonParentElement_sChildContainingThisPart
//							.getParentNode().removeChild((TextAElement)child);
//					}
//					else if (child instanceof org.odftoolkit.odfdom
//							.dom.element.text.TextHElement) {
//						((OfficeTextElement)(this
//								.deepestAllPartsCommonParentElement_sChildContainingThisPart
//								.getParentNode()))
//							.removeChild((TextHElement)child);
//					}
//					else if (child instanceof org.odftoolkit.odfdom
//							om.element.text.TextPElement) {
//						this.deepestAllPartsCommonParentElement_sChildContainingThisPart
//								.getParentNode().removeChild(
//									(TextPElement)child);
//					}
					/*
					For following references a recursive approach
					would be better suited:
					Otherwise references in children will be ignored
					as they are deleted at once.
					*/
					//deleteElementRecursively(child);
				}
				// Is this a heading? Then better keep the next
				// p(aragraph) too as an part
				// will not exist in a heading alone!?
				else {//child.getNodeName().equals("text:h")) {
					keep_next_element_because_the_before_was_a_heading
						= true;
				}

			}

		}

		// No follow up part given or this is the last one?
		else {
			// USE THE GIVEN THIS PART FOLLOWING PART FOR
			// ACCESS TO ITS DECLARATION ELEMENT.
			boolean reachedThisPartDeclarationElement = false;
			boolean reachedNextPartDeclarationElement = false;
			//for (Element child : sheetdraftDeepestCommonParentElement
			//		.getChildren()) {
			if (deepestCommonParentElement.getChildNodes() == null) {
				System.out.print("no child nodes: "
						+ deepestCommonParentElement.toString());
				return ;
			}
			NodeList childNodes = deepestCommonParentElement
				.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				org.w3c.dom.Node child = childNodes.item(i);

				if (reachedThisPartDeclarationElement
						/*&& !reachedNextPartDeclarationElement*/) {
					// ALREADY PERFORMED IN FOLLOWING CHECK
					//if (child.equals(partAfterThis.deepestAllPartsCommonParentElement_sChildContainingThisPart)) {
						reachedNextPartDeclarationElment //= true;
						= checkIfNextPartAlreadyReached(
								child, child, partAfterThis);
//					}
				}
				//spare the exception, that is the part that shall remain
				if (!child.equals(exception)/*Because once we reached the follow up part we can remove again.*/
						&& (!reachedThisPartDeclarationElement
							|| reachedNextPartDeclarationElement)) {

					/*
					IF deletion of references that are no longer
					referenced is NOT possible
					after having removed these main content xml nodes,
					THEN REFERENCES MUST BE FOLLOWED!
					*/

					// Declaring sequence content of the odt??
					// Why not dynamically, OpenDocumentFormat??
					if (child.getNodeName().equals(
								"text:sequence-decls")) {
						continue;
					}

					// Stand alone elements better stay with the
					// document for now:
					if (child.getNodeName().equals("text:s")) {
						continue;
					}
					else if (child.getNodeName().equals("text:tab")) {
						continue;
					}
					else if (child.getNodeName().equals(
								"text:line-break")) {
						continue;
					}

					//else
					//child.getParentNode().removeChild(child);
					org.w3c.dom.Node deletedNode
						= deepestCommonParentElement.removeChild(child);
					// Attention:removeChild shifts all the childNodes!
					if (deletedNode.equals(child)) {
						--i;
					}
					/*
					For following references a recursive approach
					would be better suited:
					Otherwise references in children will be ignored
					as they are deleted at once.
					*/
					//deleteElementRecursively(child);
				}
				else {
					reachedThisPartDeclarationElement = true;
					/*
					Is the next part within the same branch as
					this part?
					Then we have reached begin and end of the elements
					to spare at once and only have
					to skip one element. The rest has to be removed.
					*/
					reachedNextPartDeclarationElement
					= checkIfNextPartAlreadyReached(child, child, partAfterThis);
				}

			}

		}


		//SAVE THE XML BACK TO THE FILESYSTEM
		/*
		new SAXOutputter().output(doc);
		ReadWrite.write(doc.toString(), filelink + "content.xml");
		Global.addFileToZip(filelink, filelink + "content.xml"
				, "content.xml");
		*/
		/*
		part_textDocument.save(filelink);
		*/


	}



	/**
	 * THIS IS OBSOLETE AS IT'S NOW DONE BY ONLY STORING THE INDEX
	 * INSTEAD OF THE OBJECT -
	 * THEN THERE IS NO EQUIVALENT OBJECT TO BE FOUND ANY MORE!
	 *
	 * IT'S NOW A HELPER FUNCTION ONLY.
	 *
	 * The goal is to find the equivalent to the deepest to all
	 * parts common parent xml content element that is equivalent
	 * to the sheetdraft one as initially the part's and
	 * sheetdraft's xml content is equal/the same.
	 *
	 * This is needed to delete or remove content from this part
	 * filesystem native format representation (e.g.
	 * Part_1__splitby_INTDOT.odt.odt Note the last extension!).
	 *
	 * @param element Starting at root element, recurring down the tree
	 * @param sheetdraftElementToFindTheEqualOne The equivalent to
	 * find in this part's xml.
	 * @return The element equivalent to
	 * sheetdraftElementToFindTheEqualOneTo or null.
	 */
	public static Element findEquivalentElementRecursively(
			Element element, Element sheetdraftElementToFindTheEqualOne
			) {

		// After processing each element we check for the termination
		// or cancel condition:
		// Have we found the equivalent to the sheetdraft's markup
		// deepestCommonParentElement?
		if (element.equals(sheetdraftElementToFindTheEqualOne)) {
			return element;
		}

		// Continue examining this elements children
		List<Content> children = element.getContent();
		Iterator<?> iterator = children.iterator();
		while (iterator.hasNext()) {
			Object child = iterator.next();
			if (child instanceof Element) {
				Element childElement = (Element)child;
				// Recursively process the child element.
				Element candidate = findEquivalentElementRecursively(
						childElement
						, sheetdraftElementToFindTheEqualOne);
				if (candidate != null) {
					// It's only not null if the termination/cancel
					// condition has gripped.Then we immediately return
					// to the next higher level. Finally emerging.
					return candidate;
				}
			}
		}

		// => We reached the end, no more elements and still the
		// termination condition did not grip!
		// => No such element we looked for was found!
		return null;


	}



	public void determineDeclaration() throws Exception {
		this.splitbyDeclaration = findDeclaration(filelink);
	}



	public Declaration findDeclaration() throws Exception {
		return findDeclaration(filelink);
	}



	public Declaration findDeclaration(String filelink)
		throws Exception {
		// Redetermine the declaration of this part:
		extractPlainText();
		String splitter = Global.extractSplitByFromFilelink(filelink);
		java.util.regex.Pattern splitterPattern = Global
			.determinePatternBestFittingToSplitterHint(splitter);

		List<DeclarationSet> declarationSets = DeclarationFinder
			.findDeclarationSets(getPlainText(), false);
		/*
		The problem is: the pattern is the same for all declarations
		of one kind, ie. parts or solutions
		or solutions. => we have to examine all possible declaration
		sets not only the most successful one. The most successful
		one might furthermore be different when looked for in the
		part's text instead of on the sheetdraft's text.
		*/
		for (DeclarationSet declarationSet: declarationSets) {
			// The pattern check can't go here nomore as the set's
			// patterns are wildly mixed (part + solution).
			for (Declaration d : declarationSet.declarations) {
				// => Part or Solution? Does the pattern match?
				if (d.getMatchedPattern().name()
						.equalsIgnoreCase(splitter)
						|| d.getMatchedPattern().getPattern()
						.equals(splitterPattern)) {
					/*<-- relevant only if more than one splitter found
					and that would be a bad sign anyway as only one
					part is expected.*/
					return d;
				}
			}
		}
		System.out.println("No declaration could be found in part"
				+ " file : " + filelink);
//				+ "\r\nNow deriving it from the splitby expression"
//				+ " as stored in the filelink: ");
//		//Global.determinePatternMusterNameBestFittingToSplitterHint(
//				splitter);
//		Muster m = Muster.getMusterByName(splitter);
//		if (m != null) {
//			new Declaration(m, ) TODO
//		}


		return null;
	}




	// ======= CONSTRUCTOR
	// CREATE NEW PART.
	public Part(String filelink) throws Exception {
		// Redetermine the declaration of this part:
		this(filelink, Global.extractSplitByFromFilelink(filelink), "");
		this.splitbyDeclaration = findDeclaration(filelink);
	}



	public Part(String filelink, String splitby, String header
			/*, Sheetdraft sheetdraft*/) throws FileNotFoundException {
		// CREATE PART FROM DATABASE (plainText to be read from
		// filesystem) if wee need sheetdraft filelink, here it is:
		// getSheetdraftFilelink()
		this(
				filelink
				, new Declaration(Global
					.determinePatternBestFittingToSplitterHint(splitby)
					, "", 0)
				, ReadWrite.loadText(filelink)
				//, ReadWrite.loadText(filelink)/*rawContent critical: docx is zip!*/
				, header
				, Global.booleanToInt(
					Global.isFilelinkNativeFormat(filelink))
				, Calendar.getInstance().getTimeInMillis()
				, Calendar.getInstance().getTimeInMillis()
		);

	}
	public Part(String filelink, Declaration splitterDeclaration
			, String header, long whencreated, long whenchanged
			/*, Sheetdraft sheetdraft*/) throws FileNotFoundException {
		// CREATE PART FROM DATABASE (plainText to be read from
		// filesystem). If we need sheetdraft filelink, here it is:
		// getSheetdraftFilelink()
		this(
				filelink
				, splitterDeclaration
				, ReadWrite.loadText(filelink)
				//, ReadWrite.loadText(filelink)/*rawContent critical: docx is zip!*/
				, header
				, Global.booleanToInt(
					Global.isFilelinkNativeFormat(filelink))
				, whencreated
				, whenchanged
		);

	}



	public Part(String filelink, Declaration splitterDeclaration
			, String header
			/*, Sheetdraft sheetdraft*/) throws FileNotFoundException {
		// CREATE PART FROM DATABASE (plainText to be read from
		// filesystem)
		this(
				filelink
				, splitterDeclaration
				, ReadWrite.loadText(filelink)
				//, ReadWrite.loadText(filelink)/*rawContent critical: docx is zip!*/
				, header
				, Global.booleanToInt(Global.isFilelinkNativeFormat(
						filelink))
				, Calendar.getInstance().getTimeInMillis()
				, Calendar.getInstance().getTimeInMillis()
		);

	}



	public Part(String filelink, Declaration splitbyDeclaration
			, String[] plainText/*, String[] rawContent*/
			, String header/*, Sheetdraft sheetdraft*/) {
		// CREATE PART
		this(
				filelink
				, splitbyDeclaration
				, plainText
				//, rawContent
				, header
				, Global.booleanToInt(Global.isFilelinkNativeFormat(filelink))
				, Calendar.getInstance().getTimeInMillis()
				, Calendar.getInstance().getTimeInMillis()
		);

	}



	public Part(/*String sheetdraft_id,*/ String filelink
			/*, String originsheetdraft_filelink*/,
			Declaration declarationSplitbySuccessfully
			, String[] plainText/*, String[] rawContent*/
			, String header
			, int is_native_format, long whencreated, long whenchanged
			/*, Sheetdraft sheetdraft*/){

		this.splitbyDeclaration = declarationSplitbySuccessfully;
//		this.sheetdraft_id = sheetdraft_id;
		if (!filelink.startsWith(File.separator)) {
			filelink = Global.root + filelink;
		}
		this.filelink = filelink;
//		this.originsheetdraft_filelink = originsheetdraft_filelink;
		this.plainText = plainText;
		//this.rawContent = rawContent;
		this.header = header;
//		this.isNativeFormat = is_native_format;// DETERMINED ON THE FLY
		//IN THE DB WE NEED IT FOR JOINING! HERE WE DON'T!
		this.whencreated.setTimeInMillis(whencreated);
		this.whenchanged.setTimeInMillis(whenchanged);



		// Create files for rawContent and plainText.
		// This 'redundancy' allows preview generation.
		// Also online editing of parts at a later point and still
		// having the original document content in the original file.
		//
		// WARNING: THIS IMPLIES THAT CHANGES DON'T PROPAGATE UPWARDS
		// TO THE SHEET. TODO: BUTTON FOR QUICKLY CREATING NEW DRAFT
		// OUT OF THE MODIFIED PART.
		File file = new File(filelink);
		if (!file.exists()) {
			//file.mkdirs();// ATTENTION:CREATES THE FILE AS DIRECTORY!
		}
//		ReadWrite.writeText(plainText
//				, Global.extractFilename(filelink) + ".txt");
//		ReadWrite.writeRawContentToDiskDependingOnEnding(this);
//		ReadWrite.writeText(rawContent, filelink);

	}



	// ======= METHODS
	/**
	 * Prior to that you have to set the NEW filelink to this part
	 * object!
	 */
	public void moveToNewFilelink(String newFilelink) {
		// Update/set the filelink according to the sheetdraft's filelink!
		this.setFilelink(newFilelink);

		//Move the part files to the new location according to
		//the newly set part-filelinks.
		Global.renameFile(
				Global.root + this.getFilelinkPrevious(),
				Global.root + this.getFilelink()
		);
		Global.renameFile(
				Global.root + this.getImageLinkPrevious(),
				Global.root + this.getImageLink()
		);
		Global.renameFile(
				Global.root + this.getPDFLinkPrevious(),
				Global.root + this.getPDFLink()
		);

		Global.renameAllDerivativesOfFilelink(filelink, newFilelink);

		System.out.println((Global.message += "*done* Part moved"));
		System.out.println("----------------------------------------");


	}



	/**
	 * Prior to that you have to set the NEW filelink to this
	 * part object!
	 * @throws SQLException
	 * @throws IOException
	 */
	public void updateDBToNewFilelink() throws SQLException
		, IOException {

		// Update the new filelinks in the database too.
		System.out.println((Global.message += "Updating part"
					+ " filelinks in database."));
		System.out.println("----------------------------------------");
		System.out.println((Global.message += "Checking if filelink"
					+ " already exists in database."));
		if (!Global.query("SELECT `filelink` FROM `part`"
					+ " WHERE `filelink` = '"
					+ this.getFilelink() + "'").wasNull()) {
			// Attention, query was NOT empty!
			// => ABORT
			System.out.println("->WARNING! New filelink '"
			+ this.filelink
			+ "' already exists in database.");
			System.out.println("->Aborting ...");
			System.out.println("=>Thus those databases will be joined.");
			System.out.println("That means the database part"
					+ " filelink synchronization will be skipped."
					+ " Thus the potential two candidates are joined!"
					+ " Nevertheless this is no problem and could"
					+ " even be intentionally, if user want to join"
					+ " two REDUNDANT entities. *Done*");
			System.out.println("------------------------------------");
		}

		System.out.println("->GREEN, no problem, filelink is unique. *Done*");
		System.out.println("----------------------------------------");
		System.out.println("Updating the part's filelink.");
		// update part
		Global.query("UPDATE part SET filelink='"
				+ this.getFilelink() + "' " + " WHERE filelink='"
				+ this.getFilelinkPrevious() + "' ");
		// update draftpartassignment
		Global.query("UPDATE draftpartassignment SET filelink='"
				+ this.getFilelink() + "' " + " WHERE filelink='"
				+ this.getFilelinkPrevious() + "' ");

	}








	// ======= HELPER
	public String getSplitBy() {
		return Global.extractSplitterFromFilelink(filelink);
		// The following is no longer used as we use e.g. INTDOT
		// instead of the corresponding regex directly as the regex
		// easily contains invalid filesystem characters.
		//return this.splitbyDeclaration.getMatchedPattern().toString();
	}
	public String getSplitByRegexPattern() {
		return this.splitbyDeclaration.getMatchedPattern().toString();
	}

	public Declaration getDeclaration() throws Exception {
		if (this.splitbyDeclaration == null) {
			determineDeclaration();
		}
		return this.splitbyDeclaration;
	}


	// This result can also be achieved via Global
	// .extractSheetdraftFilelinkFromPartFilelink.
	public String getSheetdraftFilelinkFromId()
		throws SQLException, IOException {
		return Global.extractSheetdraftFilelinkFromPartFilelink(
				filelink);
		// The below is only needed if IDs are used for sheetdrafts!
		// Currently not the case!
//		java.sql.ResultSet res =
//		Global.query("SELECT sheetdraft.filelink"
//				+ " FROM sheetdraft, part"
//				+ " WHERE sheetdraft.id = part.sheetdraft_id");
//		while (res.next()) {
//			return res.getString("filelink");
//		}
//		// Tackle memory leaks by closing result set and its statement:
//		Global.queryTidyUp(res);
//
//		Global.addMessage("No sheetdraft! Concerned part: "
//				+ filelink + ". Returning empty string.", "warning");
//		return "";
	}



	// The entry in the database has once been stored using the
	// Sheetdraft filelink from the generated part filelink
	// at part creation/extraction time!
	public String getOriginSheetdraftFilelink()
		throws IOException, SQLException {
		java.sql.ResultSet res =
		Global.query("SELECT originsheetdraft_filelink"
				+ " FROM part WHERE filelink = " //TODO missing '
				+ this.getFilelinkRelative());
		if (res != null && res.next()) {
			return res.getString("originsheetdraft_filelink");
		}
		// Tackle memory leaks by closing result set, its statement:
		Global.queryTidyUp(res);

		Global.addMessage("No originsheetdraft found in database:"
				+ " Concerned part: "
				+ filelink + ". Returning empty string.", "warning");
		return "";
	}



	public String getHeader() {
		return header;
	}




//	Declaration declaration;
	DeclarationSet declarationsOfAllParts;//for skipping the first occurrence of a declaration if there is a double (i.e. if one part exists twice or more often).
	int levelDepth;
//	public void setDeclaration(Declaration declaration) {
//		this.declaration = declaration;
//	}
	public void setDeclarationsOfAllParts(DeclarationSet declarationSet) {
		this.declarationsOfAllParts = declarationSet;
	}



	private boolean isThisElementAlreadyTheDeclarationOfAnotherPart(Object o) {
		if (declarationsOfAllParts == null) {
			System.out.println("No declarations of other parts given. So assuming there are no doubles.");
			return false;
		}
		for (Declaration otherDeclaration : declarationsOfAllParts.declarations) {
			if (otherDeclaration == this.splitbyDeclaration) {
				continue;/*only other declarations are relevant*/
			}
			if (otherDeclaration.equals(this.splitbyDeclaration)) {
				return true;
			}
		}
		return false;
	}






	// ======= DOCX
	public class Docx4JTravelCallback extends CallbackImpl {


		/**
		 * That's where the non-error correction general processing
		 * action happens.
		 * @param o The parent object to walk/travel along - deeper
		 * and deeper into the XML tree.
		 */
		@Override
		public List<Object> apply(Object o) {



			return null;
		}




		@Override // to setParent //<-- now done in default handler too.
		public void walkJAXBElements(Object o) {
		//public void DOCX_travelDownUntilDeclarationFound(Object o
		//		, Declaration declaration, int levelDepth) {

			// Store extra information:
			if (sheetdraftElementsTraversed_depth_max < levelDepth) {
				sheetdraftElementsTraversed_depth_max = levelDepth;
			}


			// Store the Element as traversed. (Only elements are stored as Text has no children!)
			sheetdraftElementsTraversed.add(++sheetdraftElementsTraversed_index, o);
			sheetdraftElementsTraversed_depth.add(sheetdraftElementsTraversed_index, levelDepth);

			boolean wasTextAddedInThisLevelDepth = false;
			List<Object> children = null;

			// ======= 1a)
			// TEXT
			// To find the declaration the text tags have to be
			// evaluated.
			if (o instanceof org.docx4j.wml.Text) {
				// If object is a Text Node, then append the text.
				org.docx4j.wml.Text t = (org.docx4j.wml.Text) o;
				textBuffer.append(t.getValue());
				//textBuffer.append(System.getProperty(
				//		"line.separator"));//TODO former line.separator
				wasTextAddedInThisLevelDepth = true;
			}
			// TEXT VIA JAXBElement.
			else if (o instanceof JAXBElement) {
				JAXBElement<?> e = (JAXBElement<?>) o;
				String tagname = e.getName().getLocalPart();//getQualifiedName();
				System.out.println(tagname);
				//Child unwrapped = (Child)XmlUtils.unwrap(o);


				//TEXT
				//if (tagname.equals("t")) {
				if (e.getValue() instanceof org.docx4j.wml.Text) {
					textBuffer.append(
							((org.docx4j.wml.Text) e.getValue())
							.getValue()
					);
					wasTextAddedInThisLevelDepth = true;
				}
				//}



				// Get childnodes wrapped into JAXBElement.
				// General approach:
				else if (e.getValue() instanceof ContentAccessor) {
					children = ((org.docx4j.wml.ContentAccessor)
							e.getValue()).getContent();
				}

				// Specific approach (TODO use if tables have to be
				// dealt with more closely)
				// TABLE
				//if (tagname.equals("tbl")) {
				else if (e.getValue() instanceof org.docx4j.wml.Tbl) {
					children = ((org.docx4j.wml.Tbl) e.getValue())
						.getContent();//getChildren(e.getValue());
				}
				//}
				//if (tagname.equals("tr")) {
				else if (e.getValue() instanceof org.docx4j.wml.Tr) {
					children = ((org.docx4j.wml.Tr) e.getValue())
						.getContent();//getChildren(e.getValue());
				}
				//}
			}


			// ======= 1b) If text has been added, this examines
			// the cancel/termination condition:
			if (wasTextAddedInThisLevelDepth) {
				// After processing each element we check for
				// the termination or cancel condition:
				String dec_plain_text
					= splitbyDeclaration.getFirstWord().trim();
				// Alternative to the following if statements:
				//dec_plain_text.replaceAll(" $", "");
				if (splitbyDeclaration.getSecondWord() != null
						&& !splitbyDeclaration
						.getSecondWord().isEmpty()) {
					dec_plain_text = dec_plain_text + " "
						+ splitbyDeclaration.getSecondWord().trim();
				}// Do not use elseif here because it would prevent
				// the third declaration word from being added!
				if (splitbyDeclaration.getThirdWord() != null
						&& !splitbyDeclaration
						.getThirdWord().isEmpty()) {
					dec_plain_text = dec_plain_text + " "
						+ splitbyDeclaration.getThirdWord().trim();
				}

				// Have we found the Declaration in the native format
				// markup again?
				// Do not use match because point (.) could be
				// contained e.g. '1. Part' and would be
				// be interpreted as regex ., allowing to match
				// things that must not match.
				// TODO make possible to match things like 'Ue bung'
				// or special characters encoded.
				if (textBuffer.toString().replaceAll("(\r\n)+|[\r\n]+"
							, " ").replaceAll("[ ][ ]+", " ")
						.contains(dec_plain_text)
						/* For parts that are doubles of a
						previous part: */
						&& !isThisElementAlreadyTheDeclarationOfAnotherPart(o)
						) {
					/*
					This is redundant because as we stop here the
					sheetdraftElementsTraversed_index isn't incremented
					and points to exactly the following element:
					*/
					sheetdraftElementReachedWhenDeclarationFoundInNativeFormat
						= o;//.getParentNode();
					//	= sheetdraftElementsTraversed.get(sheetdraftElementsTraversed_index);//the root
					sheetdraftElementReachedWhenDeclarationFoundInNativeFormat_index
						= sheetdraftElementsTraversed_index;
					//this.sheetdraftElementReachedWhenDeclarationFoundInNativeFormat_depth
					//	= levelDepth;
					isDeclarationFound = true;
					return ;
				}
			}





			// =======2)
			// No pure text to add. Then we have to add some styling
			// elemens (standalone et alia).
			// Add additional formatting:

			// -------standalone elements
			if (o instanceof Br) {
				textBuffer.append(System.getProperty("line.separator"));
				//return;
			}
			else if (o instanceof Drawing) {

			}
			else if (o instanceof org.docx4j.wml.R.Separator) {//TODO clarify
				textBuffer.append(System.getProperty(" "));
				//return;
			}
			else if (o instanceof Tab
					|| o instanceof org.docx4j.wml.R.Ptab) {
				textBuffer.append(System.getProperty("\t"));
				//return;
			}



			// -------non-standalone elements
			// Paragraph, Header or Table only
			else if (o instanceof org.docx4j.wml.P
					|| o instanceof org.docx4j.wml.Hdr
					|| o instanceof org.docx4j.wml.Tbl) {
				textBuffer.append(System.getProperty("line.separator"));
				//return;
			}
			// P only
			if (o instanceof org.docx4j.wml.P) {
				org.docx4j.wml.P p = (org.docx4j.wml.P) o;

//				if (p.getPPr() != null
//						&& p.getPPr().getPStyle() != null) {
//				}

//				if (p.getPPr() != null && p.getPPr().getRPr() != null) {
//				}
				//getRunContent( p.getContent(p), parent, pdfParagraph);

//				if (parent instanceof Document) {
//					((Document)parent).add(pdfParagraph);
//				} else if (parent instanceof PdfPTable) {
//
//					((PdfPTable)parent).addCell(
//							new PdfPCell(pdfParagraph)
//					);
//				} else {
//					log.error("Trying to add paragraph to "
//					+ parent.getClass().getName());
//				}
//

				// LIST TODO - the following identifies a list.
				// List seems to be a paragraph?
				// http://stackoverflow.com/questions/7799585/how-to-append-new-list-item-to-a-list
				//public List<Object> apply(Object obj) {
				//if (obj instanceof org.docx4j.wml.P) {
					Object deepCopy = null;

//					if (p.getPPr() != null) {
//						if (p.getPPr().getPStyle() != null) {
//							if ((p.getPPr().getPStyle().getVal()
//									.equals("Akapitzlist"))
//									&& (akapListCounter < 10)) {
//
//								if ((p.getPPr().getPStyle() != null) {
//									if (((p.getPPr().getPStyle()
//											.getVal()
//											.equals("Akapitzlist"))) {
//										deepCopy = XmlUtils.deepCopy(o);
//										akapListCounter++;
//										int indexOf = wmlDocumentEl
//											.getBody().getContent()
//											.indexOf(obj);
//
//
//										List<Object> content
//											= ((org.docx4j.wml.P)
//											deepCopy).getContent();
//
//										for (Object el : content) {
//											System.out.println(
//													"class1:"
//													+ el.getClass()
//													.toString());
//											if (el instanceof
//													org.docx4j.wml.R) {
//												List<Object> subc
//												= ((org.docx4j.wml.R)
//												el).getContent();
//
//												for (Object r : subc) {
//													((javax.xml.bind.JAXBElement) r)
//													.setValue("tetetete");
//												}
//											}
//
//										}// end for
//
//
//										wmlDocumentEl.getBody()
//										.getContent().add(
//											indexOf + 1, deepCopy);
//
//
//									}
//								}// end get style
//
//							}
//						}
//					}
//					else {
//					}


				//}
				//	return null;
				//}



			}
			//else if (elementName.equals("text:note-citation")) {
			else if (o instanceof org.docx4j.wml.CTFootnotes) {
				textBuffer.append(" (");
			}
			//else if (elementName.equals("text:span")) {
			else if (o instanceof org.docx4j.wml.R) {
				textBuffer.append("");// if we append something here
				// (e.g. a space) then we get into serious trouble if
				// we want to find e.g. Uebung, but it's then Ue bung
				// what is possible as a R(un)/SPAN tag allows styling
				// of individual characters.
			}
			else if (o instanceof org.docx4j.wml.SdtBlock) {
				org.docx4j.wml.SdtBlock sdt = (org.docx4j.wml.SdtBlock) o;
				// Don't bother looking in SdtPr
//				traverseBlockLevelContent(sdt.getSdtContent().getContent(),
//						parent);
	//		}
	//		else if (o instanceof org.docx4j.wml.SdtContentBlock) {
	//
	//			org.docx4j.wml.SdtBlock sdt = (org.docx4j.wml.SdtBlock) o;
	//
	//			// Don't bother looking in SdtPr
	//
	//			traverseMainDocumentRecursive(sdt.getSdtContent()
	//					.getEGContentBlockContent(),
	//					fontsDiscovered, stylesInUse);

			}
			else if (o instanceof org.docx4j.wml.Tbl) {
				// If object is a Table Node, then dive deeper
				// to rows and along the columns.
				org.docx4j.wml.Tbl table = (org.docx4j.wml.Tbl) o;
//				List<Object> children = getChildren(table);
			}
			else if (o instanceof org.w3c.dom.Node) {
				// If Xerces is on the path, this will be an
				// org.apache.xerces.dom.NodeImpl; otherwise it will be
				// com.sun.org.apache.xerces.internal.dom.ElementNSImpl;

				// Ignore these, eg w:bookmarkStart
				//log.debug("not traversing into unhandled Node: "
				//		+ ((org.w3c.dom.Node)o).getNodeName());
			}
			else if (o instanceof JAXBElement) {
				//log.debug( "Encountered "
				//		+ ((JAXBElement) o).getDeclaredType().getName());
//				if (o instanceof javax.xml.bind.JAXBElement
//						&& (((JAXBElement)o).getName()
//						.getLocalPart().equals("commentReference")
//						|| ((JAXBElement)o).getName()
//						.getLocalPart().equals("commentRangeStart")
//						|| ((JAXBElement)o).getName()
//						.getLocalPart().equals("commentRangeEnd")
//						)) {
				System.out.println("JAXBElement: "
						+ ((JAXBElement<?>)o).getName().getLocalPart());
				//Child unwrapped = (Child)XmlUtils.unwrap(o);
				// TEXT
//				if (((JAXBElement)o).getName().getLocalPart()
//						.equals("t")) {
//					textBuffer.append((
//							(JAXBElement)((JAXBElement) o).getValue()
//						).getValue());
//				}
				//sheetdraftElementsTraversed
				//	.add((Child)XmlUtils.unwrap(o));

				//			if (((JAXBElement) o).getDeclaredType()
				//					.getName().equals(
				//					"org.docx4j.wml.P")) {
				//				org.docx4j.wml.P p = (org.docx4j.wml.P)
				//					((JAXBElement) o).getValue();

			}
			else if (o instanceof CommentReference
					||  o instanceof CommentRangeStart
					|| o instanceof CommentRangeEnd) {
				System.out.println(o.getClass().getName());
				//sheetdraftElementsTraversed.add((Child)o);
			}
			else {
				System.out.println( "UNEXPECTED: " + o.getClass()
						.getName() );
			}



			/* Text only nodes of deeper level/depth or better said
			 * its text-only contents will be added here: */
			// Had the children been created via the JAXBElement way?
			if (children == null) {
				/*List<Object>*/ children = getChildren(o);
				// Checks for ContentAccessor.getContent, other
				// elements' XML content and if Text then null!
			}
			if (children != null) {
				//Iterator<?> iterator = children.iterator();
				//while (iterator.hasNext()) {
				//	Object child = iterator.next();
				for (int i = 0; i < children.size(); i++) {


					Object child = children.get(i);


					// if its wrapped in javax.xml.bind.JAXBElement,
					// get its value; this is ok, provided the results
					// of the Callback won't be marshalled.
					// THEY WILL BE MARSHALLED FOR SAVING THE DOCUMENT!
					// SO AT LEAST IT IS REQUIRED TO unmarschalString
					// again. Anyway, we use remove function
					// that handles this temporary(?) unwrap.
					//child = XmlUtils.unwrap(child);
					// Not marshalled means it will not be saved
					// e.g. when copying this node.


					// Workaround for broken getParent (since 3.0.0):
					if (child instanceof Child) {
						if (o instanceof SdtBlock) {
							((Child)child).setParent(
								((SdtBlock)o).getSdtContent());
							/*
							getParent on eg a P in a SdtBlock
							should return SdtContentBlock, as
							illustrated by the following code:

							SdtBlock sdtBloc = Context
								.getWmlObjectFactory()
								.createSdtBlock();
							SdtContentBlock sdtContentBloc
								= Context.getWmlObjectFactory()
								.createSdtContentBlock();
							sdtBloc.setSdtContent(sdtContentBloc);
							P p = Context.getWmlObjectFactory()
								.createP();
							sdtContentBloc.getContent().add(p);
							String result = XmlUtils
								.marshaltoString(sdtBloc, true);
							System.out.println(result);
							SdtBlock rtp = (SdtBlock)XmlUtils
								.unmarshalString(result
								, Context.jc, SdtBlock.class);
							P rtr = (P)rtp.getSdtContent()
								.getContent().get(0);
							System.out.println(rtr.getParent()
								.getClass().getName() );

							Similarly, P is the parent of R;
							the p.getContent() list is not
							the parent.

							P p = Context.getWmlObjectFactory().createP();
							R r = Context.getWmlObjectFactory().createR();
							p.getContent().add(r);
							String result = XmlUtils
								marshaltoString(p, true);
							P rtp = (P)XmlUtils
								unmarshalString(result);
							R rtr = (R)rtp.getContent().get(0);
							System.out.println(rtr.getParent()
								getClass().getName() );
							*/
						}
						// TODO: More corrections?
						else {
							((Child)child).setParent(o);
						}
					}
					else if (child instanceof javax.xml.bind.JAXBElement
//							&& (((JAXBElement)child).getName()
//							.getLocalPart().equals("commentReference")
//							|| ((JAXBElement)child).getName()
//							.getLocalPart().equals("commentRangeStart")
//							|| ((JAXBElement)child).getName()
//							.getLocalPart().equals("commentRangeEnd"))
							) {
						((Child)((JAXBElement<?>)child).getValue())
							.setParent(/*XmlUtils.unwrap(*/ o /*)*/
						);//o := parent here
					}


					// Termination condition met?
					if (isDeclarationFound) {
						return ;// otherwise the last child will
						// always be the found one eventhough it may
						// already have been found in the first child
						// (text) node (because the loop otherwise
						// continues if we emerge from the child
						// where the declaration finally was found!
					}


					// Recursively process the child element
					//DOCX_travelDownUntilDeclarationFound(children.get(i), declaration, levelDepth + 1);
					++levelDepth;
					if (shouldTraverse(child)) { // true by default
						walkJAXBElements(child);
					}
					--levelDepth;

				}

			}/* if children != null -END */


			// -------non-standalone elements
			// Another line break to be inserted?
			if (o instanceof org.docx4j.wml.P
					|| o instanceof org.docx4j.wml.Hdr
					|| o instanceof org.docx4j.wml.Tbl) {
				textBuffer.append(System.getProperty("line.separator"));
				//return;
			}
			//else if (elementName.equals("text:note-citation")) {
			else if (o instanceof org.docx4j.wml.CTFootnotes) {
				textBuffer.append(") ");
			}
			//else if (elementName.equals("text:span")) {
			else if (o instanceof org.docx4j.wml.R) {
				// same as above at the opening R tag is true here too!
				textBuffer.append(""); // Better not append anything!
			}


			// => We reached the end, no more elements and still the
			// termination condition did not grib!
			// => No such element we looked for was found in this branch!
			return ;
		}



		/**
		 * Deletes non-part related XML content and the then no
		 * longer referenced refs.
		 *
		 * Called from Sheetdraft.java.
		 *
		 * @param sheetdraftDeepestCommonParentElement_index
		 * @param exception The deepest common parent element's child
		 * containing this part('s declaration).
		 * @throws Exception
		 */
		public void deleteAllChildrenOfExceptFor(
				int sheetdraftDeepestCommonParentElement_index)
				throws Exception {
			deleteAllChildrenOfExceptFor(
					sheetdraftDeepestCommonParentElement_index, null);
		}



		public void deleteAllChildrenOfExceptFor(
				int sheetdraftDeepestCommonParentElement_index
				, Part partSucceding) throws Exception {

			// As the elementsTraversed are equal for the part
			// and the sheetdraft the following is correcto so that
			// we can get to the deepest common parent element despite
			// aiming at this part's XML:
			Child candidate = (Child) sheetdraftElementsTraversed.get(sheetdraftDeepestCommonParentElement_index);
			if (candidate != ((Child)deepestAllPartsCommonParentElement_sChildContainingThisPart).getParent()) {
				System.out.print(Global
						.addMessage("DeleteAllChildrenNodesOfExceptFor"
							+ " discovered a discrepancy: Redebug this"
							+ " method!"
							, "danger")
				);
			}
			deleteAllChildrenOfExceptFor(
					candidate,
					// For all parts individually determined while
					// emerging up while looking for the deepest to
					// all parts common parent.(safer than
					// decrementing index)
					deepestAllPartsCommonParentElement_sChildContainingThisPart
					//<-- for each part determined while emerging
					// up while looking for the deepest to all
					// parts common parent element.
					/*
					nolongerTODO For not cleanly formatted documents
					check if this element is still available in any
					other part's traversed elements list.
					*/
					//, sheetdraftElementsTraversed.get(
					//	sheetdraftDeepestCommonParentElement_index + 1)
					, partSucceding
			);
		}



		public void removeAllSiblingsOf(Object elementNotToRemove
				, Part partAfterThis)
				throws Exception {
			/*
			XmlUtils.unwrap only unwraps if it is a JAXBElement.
			If it is already a real element node (unwrapped) this
			this same unchanged element is returned as it was given.
			*/
			if (XmlUtils.unwrap(elementNotToRemove) instanceof Child) {
				deleteAllChildrenOfExceptFor(
						((Child)XmlUtils.unwrap(elementNotToRemove))
						.getParent()
						, elementNotToRemove
						, partAfterThis
				);
			}
			//org.w3c.dom.Node
			else if (elementNotToRemove instanceof org.w3c.dom.Node) {
			}
		}



		public void deleteAllChildrenOfExceptFor(Object parentElement, Object exception, Part partAfterThis)
				throws Exception {

			if (parentElement == null) {
				System.out.print("'delete all children of except for'"
						+ " resulted in the parent being null!");
				return ;
			}

			/*
			First get the elements in the markup of this part's
			filesystem representation.
			*/

			// Start with root Element and find deepest common parent
			// element again in this file
			//getDeepestAllPartsCommonParentElement();
			//= findDeepestCommonParentElementEquivalentRecursively(
			//		doc.getRootElement()
			//		, sheetdraftDeepestCommonParentElement);


			List<Object> childNodes;
			childNodes = getChildren(XmlUtils.unwrap(parentElement));

			if (childNodes == null) {
				System.out.println("ERROR:"
						+ " deepestToAllPartsCommonParentElement"
						+ "/parentElement has no childNodes!"
						+ " deepestCommon: " + parentElement);
				return ;
			}

			// No follow up part given or this is the last one?
			if (partAfterThis == null) {
				// SOMEWHAT GUESS WHAT STILL BELONGS TO THE PART
				// IF NOT ALL CONTENT OF THIS PART IS CONTAINED
				// WITHIN ONE XML-TAG!

				// Remove all below/deeper than the deepest common
				// parent element but not the part xml markup:
				boolean keep_next_element_because_the_before_was_a_heading
					= false;
				//for (Element child
				//		: sheetdraftDeepestCommonParentElement
				//		.getChildren()) {
				for (int i = 0; i < childNodes.size(); i++) {
					Object o = childNodes.get(i);
					Child child = null;
					if (o instanceof Child/*org.w3c.dom.Node*/) {
						child = (Child) o;
					}
					else if (o instanceof JAXBElement) {
						// Alternatively more detailed deletion of
						// nodes is possible if
						//o.getValue() instanceof Tbl and a reaction
						// to it is used. Below works for both:
						if ( ((JAXBElement<?>) o).getValue()
								instanceof Child/*org.w3c.dom.Node*/) {
							child = (Child) ((JAXBElement<?>) o).getValue();
							//alternative: unwrap(); cases.
						}
						else {
							continue;
						}
					}
					else {
						System.out.println("No child node: o = " + o);
						continue; // no child node available
					}
					// Spare the exception, that is the part that
					// shall remain:
					if ( !child.equals(XmlUtils.unwrap(exception)) ) {

						/*
						IF deletion of references that are no longer
						referenced is NOT possible after
						having removed these main content XML nodes,
						THEN REFERENCES MUST BE FOLLOWED!
						*/

						// Stand alone elements better stay with the
						// document for now:
						if (child instanceof org.docx4j.wml.R.Separator) {
							continue;
						}
						else if (child instanceof org.docx4j.wml.R.Tab) {
							continue;
						}
						else if (child instanceof org.docx4j.wml.Br) {
							continue;
						}

						// Does this to a high probability belong the
						// the part declaration?
						if (keep_next_element_because_the_before_was_a_heading) {
							// This time it was not a heading neither
							// one of the standalone elements above?
							if (!(child instanceof Hdr)) {
							//if (!child.getNodeName().equals("text:h")) {
								// => No longer keep next few elements.
								keep_next_element_because_the_before_was_a_heading
									= false;
							}
							continue;
						}
						//child.getParentNode().removeChild(child);
						//this.deepestAllPartsCommonParentElement_sChildContainingThisPart.getParentNode()
						boolean deletedNode
						// WORKING BUT THE LOOP IS NOT NECESSARY AS
						// WE HAVE THE CORRESPONDING JAXBELEMENT
						// ALREADY: o !!
						//DocxUtils.remove(
						//		/*((ContentAccessor)
						//		deepestCommonParentElement)
						//		.getContent()*/
						//		childNodes, child);
							= childNodes.remove(o);

						if (deletedNode) {
							// The deleted node will be replaced
							// by the following childNodes, this
							// shift has to be taken into account:
							--i;
						}

	//					if (child instanceof org.odftoolkit.odfdom
	//							.dom.element.text.TextAElement) {
	//						this.deepestAllPartsCommonParentElement_sChildContainingThisPart
	//							.getParentNode()
	//							.removeChild((TextAElement)child);
	//					}
	//					else if (child instanceof org.odftoolkit.odfdom
	//							.dom.element.text.TextHElement) {
	//						((OfficeTextElement)(this
	//								.deepestAllPartsCommonParentElement_sChildContainingThisPart
	//								.getParentNode()))
	//								.removeChild((TextHElement)child);
	//					}
	//					else if (child instanceof org.odftoolkit.odfdom
	//							.dom.element.text.TextPElement) {
	//						this.deepestAllPartsCommonParentElement_sChildContainingThisPart
	//							.getParentNode()
	//							.removeChild((TextPElement)child);
	//					}
						/*
						For following references a recursive approach
						would be better suited:
						Otherwise references in children will be
						ignored as they are deleted at once.
						*/
						//deleteElementRecursively(child);
					}
					// Is this a heading? Then better keep the next
					// p(aragraph) too as an part
					// will not exist in a heading alone!?
					else {//child.getNodeName().equals("text:h")) {
						//<- else only now because if child equals
						// exception we wish to keep the followers too!
						keep_next_element_because_the_before_was_a_heading
							= true;
					}

				}

			}


			else {
				// USE THE GIVEN THIS PART FOLLOWING PART FOR
				// ACCESS TO ITS DECLARATION ELEMENT.
				boolean reachedThisPartDeclarationElement = false;
				boolean reachedNextPartDeclarationElement = false;
				//for (Element child : sheetdraftDeepestCommonParentElement.getChildren()) {
				for (int i = 0; i < childNodes.size(); i++) {
					Object o = childNodes.get(i);
					Child child = null;
					// DETERMINE REAL XML OBJECT (not the wrapper)
					if (o instanceof Child/*org.w3c.dom.Node*/) {
						child = (Child) o;
					}
					else if (o instanceof JAXBElement) {
						// Alternatively more detailed deletion of
						// nodes is possible if
						// o.getValue() instanceof Tbl
						// and a reaction to it is used.
						if ( ((JAXBElement<?>) o).getValue()
								instanceof Child/*org.w3c.dom.Node*/) {
							child = (Child) ((JAXBElement<?>) o)
								.getValue();
						}
						else {
							continue;
						}
					}
					else {
						continue;
					}


					// Reached this part but not the next one?
					if (reachedThisPartDeclarationElement
							&& !reachedNextPartDeclarationElement
							/* && !child.equals(this
							.deepestElement_sChild  performance not
							necessarily improved because we have too
							many more comparisons! */
							) {
						reachedNextPartDeclarationElement
						= checkIfNextPartAlreadyReached(o, child, partAfterThis);
					}
					// Spare the exception, that is the part
					// that shall remain:
					if (!child.equals(XmlUtils.unwrap(exception))
							// Because once we reached the follow up
							// part we can remove again.
							&& (!reachedThisPartDeclarationElement
								|| reachedNextPartDeclarationElement)
							) {

						/*
						IF deletion of references that are no longer
						referenced is NOT possible after having
						removed these main content XML nodes,
						THEN REFERENCES MUST BE FOLLOWED!
						*/

						// Stand alone elements better stay with the
						// document for now:
						if (child instanceof org.docx4j.wml.R.Separator) {
							continue;
						}
						else if (child instanceof org.docx4j.wml.R.Tab) {
							continue;
						}
						else if (child instanceof org.docx4j.wml.Br) {
							continue;
						}

						//else
						// Live list (by reference) changes take effect
						//child.getParentNode().removeChild(child);
						boolean deletedNode =
						// WORKING BUT THE LOOP IS NOT NECESSARY AS
						// WE HAVE THE CORRESPONDING JAXBELEMENT
						// ALREADY: o !!
						//DocxUtils.remove(
						//		/*((ContentAccessor)
						//			deepestCommonParentElement)
						//		.getContent()*/
						//		childNodes, child);
						childNodes.remove(o);
						// Attention: removing a node shifts all the
						// childNodes!
						// TODO is this true for docx DOM too?
						if (deletedNode) {
							--i;
						}
						/*
						For following references a recursive approach
						would be better suited: Otherwise references
						in children will be ignored as they are deleted
						at once.
						*/
						//deleteElementRecursively(child);
					}
					else {
						reachedThisPartDeclarationElement = true;
						/*
						Is the next part within the same branch
						as this part?
						Then we have reached begin and end of the
						elements to spare at once and only have to
						one element. The rest has to be removed.
						*/
						reachedNextPartDeclarationElement
						= checkIfNextPartAlreadyReached(
								o, child, partAfterThis);
					}

				}


			}


			/*

			part_textDocument.save(filelink);
			 */


		}



//		public boolean checkIfNextPartAlreadyReached(
//				Object o, Child child, Part partAfterThis) {
//			/*
//			Check whether next part's declaration element's
//			branch is already reached.
//			*/
//			// The simple case: Only within this level/depth.
//			if ( child.equals(XmlUtils.unwrap(partAfterThis
//					.deepestAllPartsCommonParentElement_sChildContainingThisPart))
//				) {
//				//reachedNextPartDeclarationElement = true;
//				// From this point it plays no role anymore what the
//				// value of reachedThisPartDeclarationElement is.
//				return true;
//			}
//			else if (o.equals(partAfterThis
//					.highestParentElementContainingThisPartOnly)) {
//				//reachedNextPartDeclarationElement = true;
//				return true;
//			}
//			// Look throughout the child elements for the other
//			// part highest only-one-part-containing element:
//			else {
//
//				boolean isPartWithinThisBranch;
//				isPartWithinThisBranch = false;
//
//				//Object child_schild = child;
//				//TODO: Perhaps better use recursion therefore put it
//				// into a TraversalUtil?
//				/*
//				The reason for taking the current approach is that
//				we examine the width first for not having to
//				examine branch after branch sequentially.
//				*/
//				List<Object> child_schildNodes;
//				List<List<Object>> child_schildNodesList;
//				child_schildNodesList = new ArrayList<List<Object>>();
//
//				int index = 0;
//				child_schildNodesList.add(getChildren(
//						XmlUtils.unwrap(o)));
//
//				while (!child_schildNodesList.isEmpty()
//						/*also possible: index > -1*/) {
//					child_schildNodes = child_schildNodesList
//						.get(index);
//
//					// Cancel condition propagation from inner loop
//					if (isPartWithinThisBranch) {
//						break;
//					}
//					if (child_schildNodes == null) {
//						continue;
//					}
//					// Look within these child element nodes whether
//					// other part is contained or not:
//					for (Object child_schild : child_schildNodes) {
//						//for (Object wayelement
//						//		: partAfterThis.wayTowardsRoot) {
//						if ( XmlUtils.unwrap(child_schild)
//								.equals(XmlUtils
//									.unwrap(partAfterThis
//									.highestParentElementContainingThisPartOnly)
//								) ) {//wayelement)) {
//							isPartWithinThisBranch = true;
//							break;
//						}
//
//						//}
//						List<Object> helper_childNodes;
//						helper_childNodes = getChildren(
//								XmlUtils.unwrap(child_schild));
//						if (helper_childNodes != null) {
//							child_schildNodesList.add(helper_childNodes);
//							++index;
//						}
//					}
//
//					// Now that we have treated this nodeChildren
//					// layer/level/depth:
//					child_schildNodesList.remove(child_schildNodes);
//					--index;
//				}
//				if (isPartWithinThisBranch) {
//					//reachedNextPartDeclarationElement = true;
//					return true;
//				}
//			}
//
//			return false;
//		}


	}



	/**
	 * Helper for allowing w3c and jaxb to use the same
	 * checkIfNextPartAlreadyReached function.
	 * @param nodeList
	 * @return An unordered List containing the ordered NodeList's nodes.
	 */
	public List<Object> nodeListOrdered2UnorderedList(
			org.w3c.dom.NodeList nodeList) {
		List<Object> list = new ArrayList<Object>();
		int nodeList_index = -1;
		while (++nodeList_index < nodeList.getLength()) {
			list.add( nodeList.item(nodeList_index) );
		}
		return list;
	}


	/**
	 *
	 * @param o		-- the parent node!?
	 * @param child -- the child node (for convenience?).
	 * @param partAfterThis
	 * @return true if next part already reached else false.
	 */
	public boolean checkIfNextPartAlreadyReached(Object o, Object child, Part partAfterThis) {
		/*
		Check if next part's declaration element's branch
		is already reached.
		*/
		// The simple case: Only within this level/depth.
		if (/*child instanceof Child && */child.equals(
					XmlUtils.unwrap(partAfterThis
						.deepestAllPartsCommonParentElement_sChildContainingThisPart))
				/*|| child instanceof org.w3c.dom.Node && not even
				necessary, the same input object is returned if unwrap
				couldn't detect it being of type JAXBElement. */
				) {
			//reachedNextPartDeclarationElement = true;
			// From this point on it plays no role anymore what the
			// value of reachedThisPartDeclarationElement is.
			return true;
		}
		else if (o.equals(partAfterThis.highestParentElementContainingThisPartOnly)) {
			//reachedNextPartDeclarationElement = true;
			return true;
		}
		// Look throughout the child elements for the other part
		// highest only-one-part-containing element:
		else {

			boolean isPartWithinThisBranch;
			isPartWithinThisBranch = false;

			//Object child_schild = child;
			// TODO: Perhaps better use recursion therefore put it
			// into a TraversalUtil?
			/*
			The reason for taking the current approach is that we
			examine the width first for not having to examine branch
			after branch sequentially.
			*/
			List<Object> child_schildNodes;
			List<List<Object>> child_schildNodesList;
			child_schildNodesList = new ArrayList<List<Object>>();

			int index = 0;
			if (child instanceof org.w3c.dom.Node) {
				child_schildNodesList.add( nodeListOrdered2UnorderedList( ((org.w3c.dom.Node) o).getChildNodes() )  );

			}
			else {
				child_schildNodesList.add(
						TraversalUtil.getChildrenImpl(
							XmlUtils.unwrap(o)));
			}
			while (!child_schildNodesList.isEmpty()
					/* also possible: index > -1 */) {
				child_schildNodes = child_schildNodesList.get(index);

				// Cancel condition propagation from inner loop
				if (isPartWithinThisBranch) {
					break;
				}
				if (child_schildNodes == null) {
					continue;
				}
				// Look within these child element nodes whether
				// other part is contained or not:
				for (Object child_schild : child_schildNodes) {
					//for (Object wayelement : partAfterThis
					//		.wayTowardsRoot) {
					if ( XmlUtils.unwrap(child_schild)
							.equals(
								XmlUtils.unwrap(partAfterThis
									.highestParentElementContainingThisPartOnly)
							) ) {//wayelement)) {
						isPartWithinThisBranch = true;
						break;
					}

					//}
					List<Object> helper_childNodes;
					if (child_schild instanceof org.w3c.dom.Node) {
						// w3c
						helper_childNodes
							= nodeListOrdered2UnorderedList(
									((org.w3c.dom.Node) child_schild)
									.getChildNodes() );
					}
					else {
						// jaxb
						helper_childNodes = TraversalUtil
							.getChildrenImpl(
									XmlUtils.unwrap(child_schild));
					}
					if (helper_childNodes != null) {
						child_schildNodesList.add(helper_childNodes);
						++index;
					}
				}

				// Now that we have treated this nodeChildren
				// layer/level/depth:
				child_schildNodesList.remove(child_schildNodes);
				--index;
			}
			if (isPartWithinThisBranch) {
				//reachedNextPartDeclarationElement = true;
				return true;
			}
		}

		return false;
	}


}







