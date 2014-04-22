/**
 * 
 */
package aufgaben_db;

import java.util.ArrayList;
import java.util.List;

/**
 * Enum, die die unterstuetzten Dateiendungen auflistet.
 * 
 * 
 * @author Schweiner, Artiom, J. R.I.B.-Wein
 *
 */
public enum DocType {
	DOC(false)//(".doc")
	, DOCX//(".docx")
	, HTML//(".html")
	, ODT//(".odt")
	, PDF//(".pdf")
//	, RTF//(".rtf")
	, TEX//(".tex")
	, TXT//(".txt")
	;
	
//	 private String ending;
//	 private DocType(String c) {
//	 	ending = c;
//	 }
	
	
	/**
	 * The determination of which types lead where has to be solved dynamically too - somehow. 
	 * Probably the Registry of IConverter of our french colleagues can be reused/extended.
	 * 
	 * e.g. Each Converter (class?) must register its destination and source format in the DocType.
	 */
	private DocType[] typesThatLeadHere;//<-- as it can't change on runtime/is fix at compiletime.
	//private DocType[] typesThisConvertsTo;
	
	private static final short RESOLVE_CONVERSION_PATH_CONVERSION_COUNT_LIMIT = 20;/*DocType.length*/
	//<-- TODO check if high enough? 
	/**
	 * 
	 * @param from	- The conversion source. (what we have)
	 * @param to	- The conversion target. (what we want)
	 * @return
	 * 		The resolved path in the correct order (but reversed) which to follow (from 0 up to length)
	 *  	 		to get the target format from the source format.
	 * 		null if no path exists.
	 */
	public static DocType[] resolveConversionPath(DocType source_format, DocType target_format) {
		
		// 1. Start with the target format:
		PathElement startEl = target_format.new PathElement(target_format, null);//starting from here => hence from PathElement is undefined.
		
		// 2. follow edges recursively that lead to this target_type until the source format is found:
		//int path_index = 0;
		// The return value is double as the path array is given already as reference.
		PathElement finalPathElementThatWasFirstSuccessful = ((DocType)(startEl.node))
				.resolveConversionPathRecursively(startEl, source_format, target_format, 0 + 1);
		
		
		//sort out and fix the order if required:
		int conversion_index = -1;
		DocType[] conversions_to_perform = new DocType[RESOLVE_CONVERSION_PATH_CONVERSION_COUNT_LIMIT];
		//Follow from this successful path element (source_format) way back.
		//Extracting DocTypes on the way:
		PathElement wayEl = finalPathElementThatWasFirstSuccessful;
		/*int path_index = path.length;path is now a row of interlinked objects: PathElement.		
		while (--path_index > -1) {
			//skip path element slots that are not used, i.e. null:
		*/
		while ((wayEl = wayEl.from) != null) {
			//eventhough this is valid by design, let's check: //TODO clarify: check if it's clear or not?
			if (wayEl.node instanceof DocType) {
				//store the path element's underlaying format, i.e. its 'node' (allows generic usage):
				conversions_to_perform[++conversion_index] = (DocType)(wayEl.node);
			}
		}
		
		
		//hand over the result:
		return conversions_to_perform;
		
	}
	/**
	 * Here the @param to is omitted and this DocType is taken as the target, hence the starting point
	 * for our resolver algorithm. 
	 */
	public DocType[] resolveConversionPath(DocType from) {
		
		return DocType.resolveConversionPath(from, this);
		
	}
	
	/**
	 * Attention: It might be confusing that we start from the target. This is the target, not source!
	 * @param source_format
	 * @return
	 */
	public PathElement resolveConversionPathRecursively(DocType source_format) {
		return resolveConversionPathRecursively(
				this.pathElementIfStartingFromHere, source_format, this, 0 + 1);
	}
	public PathElement resolveConversionPathRecursively(
			/*PathElement[] path*/PathElement previousPathElement/*path element we're coming from*/
			, DocType source_format, DocType target_format, int depth) {
		
		
		//termination condition met? have found the source conversion format (note: we started at target).
		if (previousPathElement.node.equals(source_format)) {
			//=> The tos/next path elements reachable from where we just come from can be omitted.
			return previousPathElement;
					//<--from each path element a way back is without branching!
					/* The source type now exists in the path and this element will be handed
					 * upwards until we are  needs 
					not being converted into. Or */
		}
		if (depth > RESOLVE_CONVERSION_PATH_CONVERSION_COUNT_LIMIT/* - 1 because we wish to include the termination condition check.*/) {
			return null;//<-- indicate that we found no path.
		}
		//else:
		
		
		//add this path element at this point of the path before adding the path elements this may lead to in the while loop.
		//path[depth] = new PathElement(this, path[depth - 1]);
		/*<-- this not already happens 
		in the for loop but it still overwrites other entries that may already reside at this level.
		
		It also is unnecessary as the path spans from PathElement to PathElement, so all possible
		(by branching reachable) 'to'/next PathElements are stored in the previous path element.
		 */
		
		//iterate all possible edges, i.e. formats, that lead to this type/format:
		for (DocType leading_here : this.typesThatLeadHere) {
			
			//new path element from this current path.
			PathElement pathEl = new PathElement(leading_here, previousPathElement);
			//path[depth].tos.add(pathEl);
			previousPathElement.tos.add(pathEl);
			
			//dive deeper:
			/*return */
			PathElement foundPathElementAtDestination /*<--could either be pathEl or the one of the pathelements that
			 		follow pathEl that was successful leading to the destination(source_format). */
			= resolveConversionPathRecursively(pathEl, source_format, target_format, depth + 1);
			if (foundPathElementAtDestination  != null) {
				/*=> Succeeded in finding a path someway down the path following pathEl.
				 => Hand it over to the callee. (further relaying it until it surfaces.) */
				return foundPathElementAtDestination;
				//previousPathElement.tos.remove(pathEl);
			}
			
			
		}
		
		return null;//previousPathElement;
		/*<--TODO should this be null or path/previousPathElement? -Null because if we don't 
		find anything in the while loop this means we have gone all possible path from here on and 
		were not successful in finding the destination (our source format). */
	}

	
	//could be used but it is not as it's even longer than the detailed expression it stands for: 
	private PathElement pathElementIfStartingFromHere = new PathElement(this, null);
	
	/**
	 * This class may be used for any Object/node to store in the mesh (nodes + edges)
	 * to figure a suitable path along the mesh network if the mesh is interlinked in one piece.
	 * The information necessary then is only are the start and end node. The way elements of the
	 * path are determined/resolved automatically if the example enum DocType is followed.  
	 */
	//Note: A path is non-branching. And that is what we need. Otherwise we have ambguity.
	public class PathElement {
		
		private /*DocType*/Object node;
		private PathElement from; //not conversion from (possible multiple), but where we came from (only one)!!
		private List<PathElement> tos = new ArrayList<PathElement>();
		/**
		 * As one path is a single thread if seen from target to source and this one-way path 
		 * is what we want, we only store from which PathElement we came from.
		 * Each PathElement may be used as often as from in any PathElement as desired. It's the branching
		 * and doesn't matter as we are interested the other way round which is is non-branching.
		 * If there is no from-PathElement then we have reached the start/where this path begins/began. 
		 */
		public PathElement(DocType node, PathElement from) {
			this.node = node;
			this.from = from;/*if we imagine walking along the path then this is the previous node path
			element we come from.*/
		}
		public boolean addReachableNextElement(PathElement to) {
			return this.tos.add(to);
		}
	}
	
	
	
	//======= DOC TYPE CONSTRUCTORS =======//
	
	 private boolean is_conversion_target;
	 private DocType() {
		 this(true);
	 }
	 private DocType(boolean is_conversion_target) {
		 this.is_conversion_target = is_conversion_target;
	 }
	 

	 public String getCode() {
	 	return "." + name();//ending;
	 }
	 
	 
	 
	//=======METHODEN --
	/* annotation by JRIBW: EVen though I think e.g. RTF(rtf)
	   is REDUNDANT! I have added all those following lines below */
	public String toString() {
	    return "." + this.name();//ending;
	}
	
	public String getEnding() {
	    return "." + name();//this.ending;
	}
	
	
	/**
	 * Signalises if a fileformat shall be converted to or not.  
	 * @return
	 */
	public boolean isConversionTarget() {
		return this.is_conversion_target;
	}
	
	
	public static DocType getByEnding(String ending) {
	    /*
	    System.out.println(name);
	    if (name.equalsIgnoreCase("title")) {
	        return Resource.Private;
	    }
	    */
	    if (ending != null) {
	        for (DocType cat : DocType.values()) {
	            if ((ending).equalsIgnoreCase(cat.name())) {
	                return cat;
	            }
	        }
	    }
	    return null;
	}
		
	
	 
	 
	 
	 
}
