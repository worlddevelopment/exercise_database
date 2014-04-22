package docx4j_library;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.docx4j.XmlUtils;
import org.docx4j.openpackaging.contenttype.ContentTypeManager;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.io.Load;
import org.docx4j.openpackaging.io3.stores.ZipPartStore;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPart;
import org.docx4j.openpackaging.parts.WordprocessingML.FooterPart;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.openpackaging.parts.relationships.Namespaces;
import org.docx4j.openpackaging.parts.relationships.RelationshipsPart;
import org.docx4j.openpackaging.parts.relationships.RelationshipsPart.AddPartBehaviour;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.HeaderReference;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.PartName;

/**
 * http://www.simplethoughtsonline.com/2012/01/merging-word-documents-with-docx4j.html 

    Load our initial file in WordprocessingMLPackage (this is the file where we want to attach the rest of the files, so in the end they look as one)
    Create unique section template
    Reset sections (this will serve the purpose of removing all references from the existing template, remember that section defines page layout)
    Remove body section (we can add this in the end)
    Loop through the [files to be ] attach[ed] (if you do not have sections separating pages, you might add page breaks)
	    Copy relationships that you are interested in
	    Copy elements
	    If you do not want page breaks, then you can add empty section
	Add body section
    Reapply all headers and footers to empty sections

 */
public class MergeUtil {

	
	
	private enum SectionType { BODY; };
	private WordprocessingMLPackage target_wmlPackage;
	
	
	
	
    public void mergeDocxFiles(WordprocessingMLPackage target_wmlPackage, List<WordprocessingMLPackage> source_wmlPackages,
            String outputFile) throws Exception {
      
	   this.target_wmlPackage = target_wmlPackage;
	   //...
//      resetSections(target_wmlPackage);
//      //...
//      addEmptySection(target_wmlPackage, SectionType.PARAGRAPH);
      
      for (WordprocessingMLPackage source_wmlPackage : source_wmlPackages) {
          //...
          traverseAndCopyRelationships(source_wmlPackage.getPackage().getRelationshipsPart());
          traverseAndCopyElements(source_wmlPackage.getPackage().getRelationshipsPart(),
                  source_wmlPackage.getMainDocumentPart().getContent());
          //...

      }

//      addEmptySection(target_wmlPackage, SectionType.BODY);
////      addPageBreak();
//      assignHeaderFooterData(target_wmlPackage);
      //...               

  }

  //...

  private void addPageBreak() {
      System.out.println("Trying to add a page break.");
      org.docx4j.wml.P p = new org.docx4j.wml.P();
      org.docx4j.wml.R r = new org.docx4j.wml.R();
      org.docx4j.wml.Br br = new org.docx4j.wml.Br();
      br.setType(org.docx4j.wml.STBrType.PAGE);
      r.getContent().add(br);
      p.getContent().add(r);
      target_wmlPackage.getMainDocumentPart().addObject(p);
  }

  
  
  /**
   * 
   * @param rp
   * @throws InvalidFormatException
   */
  private void traverseAndCopyRelationships(RelationshipsPart rp) throws InvalidFormatException {
	  traverseAndCopyRelationships(rp, target_wmlPackage);
  }
  public static void traverseAndCopyRelationships(RelationshipsPart rp, WordprocessingMLPackage target_wmlPackage) throws InvalidFormatException {
	  
      for (Relationship r : rp.getRelationships().getRelationship()) {
          Part part = rp.getPart(r);
          if (part != null) {
              //...
              if (part instanceof org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage
//                      || part instanceof org.docx4j.openpackaging.parts.WordprocessingML.FooterPart
//                      || part instanceof org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart
            		  ) {
            	  
            	  //add, overwriting existing partName (i.e.target) (if more relationships shall target the same image, don't use this method)
            	  //target_wmlPackage.set
            	  target_wmlPackage.addTargetPart(part, AddPartBehaviour.REUSE_EXISTING);
            	  
              }
              
              if (part.getRelationshipsPart(false) != null) {
                  traverseAndCopyRelationships(part.getRelationshipsPart(false), target_wmlPackage);
              }
          }
      }
  }
  
  /**
   * 
   * @param rp
   * @param content
   * @throws InvalidFormatException
   */
  private void traverseAndCopyElements(RelationshipsPart rp, List<Object> content) throws InvalidFormatException {
	  traverseAndCopyElements(rp, content, target_wmlPackage);
  }
  public static void traverseAndCopyElements(RelationshipsPart rp, List<Object> content, WordprocessingMLPackage target_wmlPackage)
		  throws InvalidFormatException {
	  
      for (Object o : content) {

          //...
    	  
    	  // Picture:
    	  if (o instanceof org.docx4j.dml.picture.Pic) {
		      Part imageRelationshipPart = findResourceById(rp
		    		  , ((org.docx4j.dml.picture.Pic) o).getBlipFill().getBlip()
		              //...
		              .getEmbed()
              );
		      /*Relationship */Part imagePart = findResourceByName(
		    		  target_wmlPackage.getPackage().getRelationshipsPart()
		    		  , imageRelationshipPart.partName.getName() /*part name is the unique pathToTheNodeFromWithinPackage e.g. /ppt/slides/slide<number>.xml
		    		   											   => If target is an image, then it's pointing e.g. to /word/media/<image>.jpg*/
    		  );
		      
		      // copy the image part over: (overwriting existing as perhaps this image is newer than the old residing in there)
		      target_wmlPackage.addTargetPart(imagePart);
		      
    	  }
    	  
          //... 
      }
      
  }
  
  
  
  

  
  
  
  //...       
  private static Part findResourceById(RelationshipsPart rp, String lastId) {
      for (Relationship r : rp.getRelationships().getRelationship()) {
    	  
    	  Part part = rp.getPart(r);

    	  if (r.getId().equals(lastId)) {
    		  return part;//.getPartName();
    	  }
    	  
    	  // recursion possible?
          if (part.getRelationshipsPart(false) != null) {
              Part foundPart = findResourceById(part.getRelationshipsPart(false), lastId); //<- recurse
              if (foundPart != null) {
            	  return foundPart; // relay it up!
              }
          }
          
      }
      return null;
  }

  
  
  
  private static /*Relationship*/ Part findResourceByName(RelationshipsPart rp, String imageName) {
      for (Relationship r : rp.getRelationships().getRelationship()) {
          Part part = rp.getPart(r);
          
          if (part.getPartName().getName().equals(imageName)) {
        	  return part;//r;
          }
          
          // recursion possible?
          if (part.getRelationshipsPart(false) != null) {
              Part foundPart = findResourceByName(part.getRelationshipsPart(false), imageName);
              if (foundPart != null) {
            	  return foundPart; // relay it up!
              }
          }
      }
      return null;
  }
  
  
  
  
  private String findRelationshipIdByTarget(RelationshipsPart rp, String target) throws InvalidFormatException {
	  for (Relationship r : rp.getRelationships().getRelationship()) {
          Part part = rp.getPart(r);
          
          if (r.getTarget().equals(target)) {
        	  return r.getId();
          }
          
          // recursion possible?
          if (part.getRelationshipsPart(false) != null) {
               String foundId = findRelationshipIdByTarget(part.getRelationshipsPart(false), target);
               if (foundId != null) {
            	   return foundId; // relay it up the recursion depth.
               }
          }
      }
      return null;
  }
  

  

  
  
  
  
  
  
  
  
  
  
  
  //...
  private void resetSections(WordprocessingMLPackage wordMLPackage) throws InvalidFormatException {
      org.docx4j.wml.Document doc = (org.docx4j.wml.Document) wordMLPackage.getMainDocumentPart().getJaxbElement();

      for (Object o : doc.getBody().getContent()) {
          if (o instanceof org.docx4j.wml.P) {
              if (((org.docx4j.wml.P) o).getPPr() != null) {
                  org.docx4j.wml.PPr ppr = ((org.docx4j.wml.P) o).getPPr();
                  if (ppr.getSectPr() != null) {
                      //...
                	  //TODO
                	  ppr.setPStyle(null);
                  }
              }
          }
      }

      wordMLPackage.getMainDocumentPart().getJaxbElement().getBody().setSectPr(null);
  }
  
  
  
  
  //...
  private org.docx4j.wml.SectPr.PgSz defaultSectionPgSz;
  private org.docx4j.wml.SectPr.PgMar defaultSectionPgMar;
  private org.docx4j.wml.SectPr.Type defaultSectionType;
  private org.docx4j.wml.CTDocGrid defaultSectionDocGrid;
  private org.docx4j.wml.CTColumns defaultSectionColumns;
  private org.docx4j.wml.ObjectFactory objectFactory;
  private void addEmptySection(WordprocessingMLPackage wordMLPackage, SectionType type) {
      if (type.equals(SectionType.BODY)) {
          org.docx4j.wml.SectPr sectPr = objectFactory.createSectPr();
          sectPr.setPgSz(this.defaultSectionPgSz);
          sectPr.setPgMar(this.defaultSectionPgMar);
          sectPr.setCols(this.defaultSectionColumns);
          sectPr.setDocGrid(this.defaultSectionDocGrid);
          // add:
          wordMLPackage.getMainDocumentPart().getJaxbElement().getBody().setSectPr(sectPr);
      } else {
          //...
      }
  }
  
  
  
  
  //...
  private void assignHeaderFooterData(WordprocessingMLPackage wordMLPackage) throws InvalidFormatException {
      org.docx4j.wml.Document doc = (org.docx4j.wml.Document) wordMLPackage.getMainDocumentPart().getJaxbElement();
      int sectionCounter = 0;
      wordMLPackage.getMainDocumentPart().getContent();

      HeaderPart headerPart = new HeaderPart();
      headerPart.setPackage(wordMLPackage);
      headerPart.setJaxbElement(objectFactory.createHdr());
      Relationship rHdr = wordMLPackage.getMainDocumentPart().addTargetPart(headerPart);
      
      FooterPart footerPart = new FooterPart();
      footerPart.setPackage(wordMLPackage);
      footerPart.setJaxbElement(objectFactory.createFtr());
      Relationship rFtr = wordMLPackage.getMainDocumentPart().addTargetPart(footerPart);
      
      for (Object o : doc.getBody().getContent()) {
          if (o instanceof org.docx4j.wml.P) {
              if (((org.docx4j.wml.P) o).getPPr() != null) {
                  org.docx4j.wml.PPr ppr = ((org.docx4j.wml.P) o).getPPr();
                  if (ppr.getSectPr() != null) {
                      //...
//                      if(!StringUtils.isEmpty(hr.getId())) {TODO
//                          ppr.getSectPr().getEGHdrFtrReferences().add(hr);
//                      }
                      //...
                  }
              }
          }
      }

      // TODO: This headerReference or another (from within the to be examined document)?
      HeaderReference hr = objectFactory.createHeaderReference();
      hr.setType(org.docx4j.wml.HdrFtrRef.DEFAULT);
      org.docx4j.wml.FooterReference fr = objectFactory.createFooterReference();
      fr.setType(org.docx4j.wml.HdrFtrRef.DEFAULT);
//TODO      hr.setId(findRelationshipIdByTarget(wordMLPackage.getRelationshipsPart(),  String.format("/word/header1_%d.xml", lastHeaderReference)));//TODO resolve targeted header.
//      fr.setId(findRelationshipIdByTarget(wordMLPackage.getRelationshipsPart(),  String.format("/word/footer1_%d.xml", lastHeaderReference)));

      if(!StringUtils.isEmpty(hr.getId()))
          wordMLPackage.getMainDocumentPart().getJaxbElement().getBody().getSectPr().getEGHdrFtrReferences().add(hr);
      if(!StringUtils.isEmpty(fr.getId()))
          wordMLPackage.getMainDocumentPart().getJaxbElement().getBody().getSectPr().getEGHdrFtrReferences().add(fr);
  }


  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  public static Map<String, String> rId_changesMap = new java.util.TreeMap<String, String>(); //<-- order plays a role here? TODO
  //for restoring / copying over the binary content.
  public static Map<Part, byte[]> source_alreadyAddedPart_oldNameToByteArrayMap = new HashMap<Part, byte[]>();
  public static List<RelationshipsPart> traversedRelationshipsParts = new ArrayList<RelationshipsPart>();
  public static Map<PartName, PartName> source_alreadyAddedPart_oldNameToNewNameMap = new HashMap<PartName, PartName>();
  public static List<Relationship> source_alreadyTreatedRelationships = new ArrayList<Relationship>();
  
  
  public static void traverseAndCopyRelationships(RelationshipsPart source_rp, WordprocessingMLPackage wmlPackage, RelationshipsPart target_rp, WordprocessingMLPackage source_wMLPac)
		  throws IOException, Docx4JException {
	  
	  // termination condition met?
	  if (source_rp == null || target_rp == null
//			  || source_rp.equals(target_rp)
			  // already traversed?
			  || traversedRelationshipsParts.contains(source_rp)) {
		  
		  return ; //<- no more recursion required as this rp's rp has already been recursed over (just this moment e.g.)
	  }
	  //else 
//	  traversedRelationshipsParts.add(source_rp);
	  source_rp.setPackage(source_wMLPac);
	  source_rp.setPackage(source_wMLPac);
	  
	  for (Relationship source_rel : source_rp.getRelationships().getRelationship() ) {
		  
			Part source_part_to_rel = source_rp.getPart(source_rel);
			if (source_part_to_rel == null) {
				if (target_rp.getPart(source_rel.getTarget()) == null) {
					// => this target does not exist anywhere.
					continue;
				}
				// is it one of those relationships where we reuse the existing? Because then we continue so that the source content that might use it, can use it. If we skipped instead, then the relationship might not get into target. But it should. Though it is strange that there was no corresponding target part then. Perhaps it should really be skipped. Or this is an auto-repair functionality? 
				else if (!source_rel.getType().equals(Namespaces.IMAGE)) {//TODO put into a list to not have to update at more than one place.
					source_part_to_rel = target_rp.getPart(source_rel.getTarget());
				}
				//else skip as IMAGES would be copied and we wish to avoid redundant content (in this case double images).
//				continue;
			}
			
			// this relationship already occurred once?
			if (source_alreadyTreatedRelationships.contains(source_rel)) {
				continue;
			}

			
			String source_rel_rId_old = source_rel.getId();
			// This rId already occurred? (however this can be possible?)
			if (rId_changesMap.containsKey(source_rel_rId_old)) {
				// => reuse the previously randomly generated one map entry and don't generate a new one randomly. 
				source_rel.setId(rId_changesMap.get(source_rel_rId_old));
			}
			else {
				setUniqueRelId(source_rel, wmlPackage /*where it should be unique*/);
				if (!source_rel_rId_old.equals(source_rel.getId())) {
					// store for updating relation ship id references in the content xml, i.e. objects.
					rId_changesMap.put(source_rel_rId_old, source_rel.getId());
				}
				
			}
			
			// or is not a supported relationship type (image)?
			if(!source_rel.getType().equals(Namespaces.IMAGE)//TODO put into a list to not have to update at more than one place.
					) {
				continue;//<--only copy images for now otherwise save complains about non-findable header...part.
			}
			
			
			PartName source_part_to_rel_partName_before_rename = source_part_to_rel.getPartName();
			// multiple rels pointing to the same target (part) are possible:
			// already added (and potentially renamed on the way, renaming twice might result in an error)?
			if (source_alreadyAddedPart_oldNameToNewNameMap.containsKey(source_part_to_rel_partName_before_rename)
					) {
				// => already added before => no need to add now again. Only the relationship has to be added.
				// => also implies the binary being put into the corresponding map if it existed.
//				source_partNameToFirstFinalRelPointintToItMap.get(source_part_to_rel);
				source_rel.setParent(target_rp);
				source_rel.setTarget(source_alreadyAddedPart_oldNameToNewNameMap.get(source_part_to_rel_partName_before_rename).getName());//.getURI());
				target_rp.addRelationship(source_rel);
				// register another source relationship that points to the target. (so now there should be two or more rels) 
				source_part_to_rel.getSourceRelationships().add(source_rel);
//				source_part_to_rel.setOwningRelationshipPart(target_rp); //<-- already done before.
				continue; //<--part already was added before.
			}
			else {
				// => first time that we encounter this part:
//TO BE ADDED LATER AS IT'S NOT CERTAIN WHICH NAME THE addPart WILL FINALLY USE.							source_alreadyAddedPart_oldNameToNewNameMap.put(source_part_to_rel_partName_before_rename, source_part_to_rel.getPartName());
				// not already added before/ is it not contained ? 
				if (source_alreadyAddedPart_oldNameToByteArrayMap.get(source_part_to_rel) == null
						// is it a binary part?
						&& source_part_to_rel instanceof BinaryPart) {
					
					// => store the binary data (as the target part could be renamed and then the resource could no longer be found as partByteArrays uses a the partName in relative form as a String which will not keep up the reference. TODO change to use PartName though those use the absolute partName. How to resolve that?).
					BinaryPart bp = ((BinaryPart) source_part_to_rel);

					// not already un allocated by the VM ? (as it's wrapped in a Reference<>)
					if ( bp.getBytes() != null ) {
						source_alreadyAddedPart_oldNameToByteArrayMap.put(source_part_to_rel, bp.getBytes());
					}
					else {
						// load from old, i.e. source package using the old original source part name:
						source_alreadyAddedPart_oldNameToByteArrayMap.put(
								source_part_to_rel
								, IOUtils.toByteArray(
										((ZipPartStore) source_wMLPac.getTargetPartStore()).loadPart(source_part_to_rel_partName_before_rename.getName()) 
									    )
						);
//						ByteArray byteArray = partByteArrays.get(
//			            		part.getPartName().getName().substring(1) );
//			            if (byteArray == null) throw new IOException("part '" + part.getPartName() + "' not found");
//			            bytes = byteArray.getBytes();
						
						/* Now before saving this is written back to the binaryPart to circumvent the loading and 'not finding' as the part has been renamed (and not even exists in the target package). */
					}
				}
				else {
					//else it can stay null as if it's no binary data or already added, then there is no need to add it anyway. 
					System.out.println("Avoided adding binary data twice. TODO check if better to choose the new value?");
				}
				
				
			}
			
			
			// 

			
			source_part_to_rel.setPackage(wmlPackage);
			
			
			// we take over images and rename those if they already exist in the target  (TODO only if content equal, resue existing):
			Relationship new_rel = null; //<-- instead of that we could insert source rel prior to adding the Part. then the existing would be reused but the content types are not updated and perhaps other dangerous things. 
			if (source_rel.getType().equals(Namespaces.IMAGE)) {
				
				source_part_to_rel.setPackage(source_wMLPac);//<-- very important! for operating on the correct package when adding/removing the part. 
				// Does the part name already exist in the target package?
				if (wmlPackage.getParts().getParts().get(source_part_to_rel.getPartName()) != null) {
					/* as we wish to add this part without overwriting one with the same pathTo, i.e.partName,
					   we check if the partName is given already (as this is the Map's key, so it's unique.)
					   => change if required. update all references (relationships).
					*/
					int loopCountLimit = 100;
					int number = 0;
					String[] str_parts = source_part_to_rel.getPartName().getName().split("[.]");
					String partName_old_base = str_parts[0];
					PartName partName_old = source_part_to_rel.getPartName();
					PartName partName_new = partName_old; //start with the old one.
					while (++number < loopCountLimit 
							//&& wmlPackage.getParts().getParts().containsKey(source_part_to_rel.getPartName())) {
							&& wmlPackage.getParts().getParts().get(partName_new) != null) {
						
						String partName_new_str;
						partName_new_str = partName_old_base + "__" + number;
						String pointAfterFirstRound = "";
						for (String str_part : str_parts) {
							// skip the first entry as this is treated above already.
							if (!pointAfterFirstRound.isEmpty()) {
								partName_new_str += pointAfterFirstRound + str_part;
							}
							pointAfterFirstRound = ".";
						}
						partName_new = new PartName(partName_new_str);
					}
					// still no unique name? => repeat with random number
					number = 0;
					while (++number < loopCountLimit 
							//&& wmlPackage.getParts().getParts().containsKey(source_part.getPartName())) {
							&& wmlPackage.getParts().getParts().get(partName_new) != null) {
						
						String partName_new_str;
						partName_new_str = partName_old_base + "__" + ((long)(Math.random() * 1000000));
						String pointAfterFirstRound = "";
						for (String str_part : str_parts) {
							// skip the first entry as this is treated above already.
							if (!pointAfterFirstRound.isEmpty()) {
								partName_new_str += pointAfterFirstRound + str_part;
							}
							pointAfterFirstRound = ".";
						}
						partName_new = new PartName(partName_new_str);
					}
					
					// now we hopefully have a unique partname. => rename the part:
					source_part_to_rel.setPartName(partName_new);
					
					// update the part's target part store binary data (that contains the entries' data):
					// put binary data into partByteArrays for later saving?
//					if (source_wMLPac.getTargetPartStore() instanceof ZipPartStore) {
//						if (!((ZipPartStore) source_wMLPac.getTargetPartStore()).partExists(partName_new.getName())) {
//							if (((ZipPartStore) source_wMLPac.getTargetPartStore()).partExists(partName_old.getName())) {
//								// then the old at least exists. Copy it over.
//								((ZipPartStore) source_wMLPac.getTargetPartStore()).partByteArray.put(
//										partName_new.getName()
//										,
//										((ZipPartStore) source_wMLPac.getTargetPartStore()).partByteArray.get(partName_old.getName())
//										
//							     );
//							}
//						}
//					}
					
				}
				//else the source part name is already unique in the target package. No rename required.
				
				source_part_to_rel.setPackage(wmlPackage);
				// Add the source part:
				String partName_relative = source_part_to_rel.getPartName().getName().substring(1);
				java.io.InputStream is = source_wMLPac.getSourcePartStore().loadPart(partName_relative);
				org.docx4j.openpackaging.Base attachmentPoint = wmlPackage;
//				new_rel = attachForeignPart(wmlPackage, attachmentPoint, source_wMLPac.getContentTypeManager(), is);
				
//						for (Relationship r : target_rp.getRelationships().getRelationship()) {
//							if (!target_rp.getRelationships().getRelationship().contains(source_r) ) {
					    // takes care of part name uniqueness renaming already.
						new_rel = /*target_rp*/wmlPackage.getMainDocumentPart().addTargetPart(source_part_to_rel, AddPartBehaviour.RENAME_IF_NAME_EXISTS, /*wmlPackage.getContentTypeManager(),*/ source_rel.getId());
//HERE A SHORTCUT IS EVENTUALLY SET THE REST IS EQUAL TO ABOVE									target_rp.addTargetPart(source_part_to_rel, AddPartBehaviour.RENAME_IF_NAME_EXISTS);
//							}
//						}
				
			}
			// for others we prefer reusing existing doubles:
//			else if (source_rel.getType().equals(Namespaces.STYLES)) {
//				source_part_to_rel.setPackage(wmlPackage);
//				// takes care of part name uniqueness renaming already:
//				new_rel = /*target_rp*/wmlPackage.getMainDocumentPart().addTargetPart(source_part_to_rel, AddPartBehaviour.REUSE_EXISTING, /*wmlPackage.getContentTypeManager(),*/ source_rel.getId());
////						target_rp.addTargetPart(source_part_to_rel, AddPartBehaviour.REUSE_EXISTING);
//				// now as the part could have been reused, fetch the part:
//				Part old_source_part_to_rel = source_part_to_rel;
//				source_part_to_rel = wmlPackage.getMainDocumentPart().getRelationshipsPart().getPart(new_rel);
//			}
			
			

			
			
			if (new_rel == null) {
				System.out.println("Docx4J: Merge docx: from addPart returned new_rel was " + new_rel + ". Using source_rel " + source_rel + " instead.");
				new_rel = source_rel;
				target_rp.addRelationship(new_rel);
				source_part_to_rel.getSourceRelationships().add(new_rel);
				source_part_to_rel.setOwningRelationshipPart(target_rp);
			}
			if (!source_alreadyAddedPart_oldNameToNewNameMap.containsKey(source_part_to_rel_partName_before_rename) ) {
				source_alreadyAddedPart_oldNameToNewNameMap.put(source_part_to_rel_partName_before_rename, source_part_to_rel.getPartName());
//DOUBLE see above							// is of type binary data? (or could we find/load the binary data?)
//				if (source_part_to_rel_bp != null
//						// not already added this information?
//						&& source_alreadyAddedPart_oldNameToByteArrayMap.get(source_part_to_rel) == null) {
//					source_alreadyAddedPart_oldNameToByteArrayMap.put(source_part_to_rel_partName_before_rename, source_part_to_rel_bp.getBytes());
//				}
//				else {
//					System.out.println("Couldn't find the binary data.");
//				}
			}
			else {
				System.out.println("Tried to queue renaming a source part with the same target twice or more often. That might give issues on save.");
			}
			
			source_part_to_rel.setPackage(wmlPackage); //<-- always the target. not the source one.
			// otherwise this things happen already in addPart which calls loadPart (only if not REUSING EXISTING).
			
			
			// was the desired id set?
			if (!new_rel.getId().equals(source_rel.getId())) {
				// override the above 'map put' or move the above here and call setUniqueRelId:
				rId_changesMap.put(source_rel_rId_old, new_rel.getId());
//						setUniqueRelId(new_rel, wmlPackage);
			}
			// key and value equal => no change?
			if (source_rel_rId_old.equals(new_rel.getId())) {
				//  => remove from changes list:
				rId_changesMap.remove(new_rel.getId());
			}
			
			
			// new relationship was already inserted into the relationshipPart.
			//target_rp.addRelationship(new_rel); new_rel.setParent(target_rp);
			
			//avoid source rel being copied if a new rel was created.
//			source_rel = new_rel; // it is deep copied, i.e. a new one is being build up in addPart.
			
			// recursion:
			if (source_part_to_rel.getRelationshipsPart(false) != null) {
				
//				traverseAndCopyRelationships(source_part_to_rel.getRelationshipsPart(), wmlPackage, target_rp, source_wMLPac);
			}
				 
		}
	  
  }
	  
  
  public static void attachForeignPart( WordprocessingMLPackage wordMLPackage, 
			org.docx4j.openpackaging.Base attachmentPoint,
			ContentTypeManager foreignCtm, 
			String resolvedPartUri, java.io.InputStream is) throws Exception{
		
		
		Part foreignPart = Load.getRawPart(is, foreignCtm,  resolvedPartUri, null);
			// the null means this won't work for an AlternativeFormatInputPart 
		attachmentPoint.addTargetPart(foreignPart);
		// Add content type
		ContentTypeManager packageCtm = wordMLPackage.getContentTypeManager();
		packageCtm.addOverrideContentType(foreignPart.getPartName().getURI(), foreignPart.getContentType());
		
		System.out.println("Attached foreign part: " + resolvedPartUri);
		
	}
  
  
  
  
  public static void traverseAndReplaceRelIdsUnique(WordprocessingMLPackage source_wMLPac, WordprocessingMLPackage wmlPackage)
		  throws JAXBException {

//		// 2) copy images and other resources
//		// copy parts. 
////		target_rootElement.getContent().addAll(source_rootElement.getContent());
//		Parts source_parts = source_wMLPac.getParts();
//		// copy into new list to avoid ConcurrentModificationException.
//		java.util.Iterator<Part> iter = source_parts.getParts().values().iterator();
////		for (Part source_part : source_parts.getParts().values()) {//<-- resulted in concurrent modification exception as we change the data in the live state.
//		while (iter.hasNext()) {
//			Part source_part = iter.next();
//			
//			// of any already existing part and the new part to be added's contents are not equal?
//			if (!wmlPackage.getParts().getParts().containsValue(source_part)) { 
//				
//				
//				/* check for relationship id collisions in all and not only target_part.relationships
//				 while the objects' rel ids are handled by docx4j and the object the rel is pointing to is stored as getPArent() directly.
//				 */
//				for (Relationship source_rel : source_part.getRelationshipsPart().getRelationships().getRelationship()) {
//					
//					// assuming within source_part_relationships it's already unique:
//					setUniqueRelId(source_rel, wmlPackage); 
//					
//				}
//				
//				
//				/* now that each in the source part contained relationship id is unique in context of the whole target package, i.e. its relationships.
//				   => add the source part to the target part. 
//				 */
//				wmlPackage.getParts().put(source_part);
//				
//				
//				// TODO Follow relationships to its object to see which objects have arrived in the content. (recursion).
//				// if required. currently not. we trust the object been added previously.
//				
//			}
//			else {
//				//=> the part contents (e.g. image) are equal.
//				//=> So we copy the relationships of the objects that pointed to that source part to the target part's relationshipPart that already exists:
//				// 1) find the target part in the target docx.
//				PartName target_partName = null;
//				Part target_part = null;
//				for (Part target_p : wmlPackage.getParts().getParts().values()) { //can't us the partName because we checked for the content being equal. I.e. the image squaresum or anything like that. TODO. checking for the partName is not enough, we don't want double/redundant content.
//					if (target_p.equals(source_part)) {
//						target_partName = target_p.getPartName();
//						target_part = target_p;
//						break; // we have found what we were looking for.
//					}
//				}
//				// 2) Copy over the relationships with the source objects to the new target part's relationships.
//				//    resolve relId conflicts within relationships on the way. Docx4j keeps the object directly instead of relId and will handle the changed relId when storing.
//				Map<Object, Integer> nodeCountMap = new HashMap<Object, Integer>();//<- init with zeroes. TODO make sure it's initialised with zeroes.
//				for (Relationship source_part_rel : source_part.getRelationshipsPart().getRelationships().getRelationship()) {
//					// a) update the relationship target part name.
//					source_part_rel.setTarget(target_partName.getName());
//					// no relationships to follow as each rel (with unique relId) has only one object.
//					// and NOT check for objects with same rsid (random number for increasing combining accuracy, not important for us and rather an obstacle)?
//					// find relationship's equivalent already in target existing object. Why? Could also be existing twice and still be equal. e.g. twice the same XML Node with content Aufgabe and the same properties, ... 
////WITHOUT RECURSION WON'T FIND ANYTHING ANYWAY							for (Object o : wmlPackage.getMainDocumentPart().getContent()) {
////						//TODO recursion! very costly. Recursion over parts would be preferable.
////						if ( XmlUtils.unwrap(o).equals(source_part_rel.getParent()/*getId()*/) ) {
////							System.out.println("Found equal already existing object: " + o + " == " + source_part_rel.getParent());
////							// count up for this object?
////							nodeCountMap.put(source_part_rel.getParent(), nodeCountMap.get(source_part_rel.getParent()) + 1);
////						}
////					}
//					
//					
//					// b) resolve relation ship id. change until unique.
//					/* check for relationship id collisions in all and not only target_part.relationships
//					 while the objects' rel ids are handled by docx4j and the object the rel is pointing to is stored as getPArent() directly.
//					 */
////					for (Relationship source_rel : source_part.getRelationshipsPart().getRelationships().getRelationship()) {
//						
//						// assuming within source_part_relationships it's already unique:
//						setUniqueRelId(source_part_rel, wmlPackage);
//						
////					}
//					
//					
//					// c) if it's at least existing/found in the target document.xml once?
////BETTER ALWAYS ADD IT							if (nodeCountMap.get(source_part_rel.getParent()) > 0) {
//						target_part.getRelationshipsPart().addRelationship(source_part_rel);
////					}
//				}
//			}
//			
//			
//		}
	  
	  
	  

		// 3) Append the source content to the target root element's content. DO THIS LAST TO HAVE TO UPDATE THE RELATIONSHIP IDS ON A SMALL SET OF NODES ONLY.
//		wmlPackage.getMainDocumentPart().getContent().addAll( source_wMLPac.getMainDocumentPart().getContent() );
//		target_rootElement.getContent().addAll(source_rootElement.getContent()); better use the main document part content live list.
		/* ADD OBJECTS FOR LOOP */
		boolean source_wmlPackage_will_be_saved_or_further_used = false;
		for (Object o : source_wMLPac.getMainDocumentPart().getContent()) {
			
			// update r:id="rId<number>" if it has to be changed.
			// iterate all rel ids that have been changed:
			for (String rId_old : MergeUtil.rId_changesMap.keySet()) { //<- if the changes map is empty, nothing to loop over.
				
				String rId_new = rId_changesMap.get(rId_old);
				
				
				// POSSIBILITY 1 -- most general. (an interface class RelationshipHolder were brilliant (like ContentAccessor)! sadly it not exists.)
				String rId = null;
				try {
					// try to access the potentially non-existent attribute rId <-- turned out not even to be enough, see pic(ture) example below:
//					String rId = o.getRelId();
				}
				catch (Exception e) {
					// do nothing, it has no attribute rId
					rId = null;
				}
				
				if (rId != null) {
//					o.setRelId(rId.replaceAll(rId_old, rId_new);
				}
				
				// POSSIBILITY 2 -- specific and redundant solution (non-dynamic, non-generic)
				if (o instanceof org.docx4j.dml.picture.Pic) {
					org.docx4j.dml.picture.Pic pic = ((org.docx4j.dml.picture.Pic)o);
					pic.getBlipFill().getBlip().setEmbed(
							pic.getBlipFill().getBlip().getEmbed().replaceAll(rId_old, rId_new)
					);
				}
				
				// ... TODO add more special cases
				
				
				
				
				/*
				// POSSIBILITY 3 -- more general as no instance check is required. But non-functional and not speedy performance.
				String o_marshalled = XmlUtils.marshaltoString(o);
				// Does the relationship id occur / being set in this xml object's attributes? 
				if (o_marshalled.contains(rId_old)) {
					// store back ..
					o = XmlUtils.unmarshalString( 
							// .. after replacing old by new relationship id:
							o_marshalled.replaceAll(rId_old, MergeUtil.rId_changesMap.get(rId_old))
					);
				}
				//else save some time by not having to unmarshal and storing back to object o which could be costly. If old relId occurs often this might be counterproductive than simply always replacing.  
				*/
				
			}
			
//			// TODO why should it not be contained twice? e.g. XML node with 'Untergang'? Had to be recursive anyway.
//			if (!wmlPackage.getMainDocumentPart().getContent().contains(o)) {
				if (source_wmlPackage_will_be_saved_or_further_used) {
					o = XmlUtils.deepCopy(o);
				}
				
				wmlPackage.getMainDocumentPart().getContent().add(/*XmlUtils.deepCopy(*/o/*)*/);//.addObject(o);<--handles styels but gives NullPointerException because default stylepart is not set.
//				// follow relationships. (already done above)
//				if (o instanceof RelationShipObjectType) {/*TODO just like ContentAccessor.*/
//					String oRelId = ((org.docx4j.wml.<RelationShipObjectType>)o).getRelId();
//					// corresponding part:
//					Part source_p = source_wMLPac.getMainDocumentPart().getRelationshipsPart().getPart(oRelId);
//
//					// before copying the part recursively, check for unique rel id rId:
//					// or don't copy and don't save the source package instead.
////					List<Parts> allParts = new ArrayList<Parts>();
////					allParts.add(mainDocumentPart.get)
//					for (Part existing_p : wmlPackage.getParts().getParts()) {
//						for (Relationship existing_rel : existing_p.getRelationshipsPart().getRelationships().getRelationship()) {								
//							if (oRelId.equals(existing_rel)) {
//								rId_changesMap.put(o_rId, findNew)
//							}
//						}
//					}
//				}
//					
//					
//					
//					
//			}
//			else {
//				// => add children to the already existing one.
//				// TODO consequences for source relationships? setParent(the equal already existing object?)
//				// TODO flag this object to  not to follow relationships where relId == o.relId? <-- wrong?
//			}
		} /* ADD OBJECTS FOR LOOP -END */
	  
	  
	  
	  
  }
  
  
  
  
  
  /**
	 * Assuming within the source relationships it's already unique.
	 * Just give both the source and target wml packages to be safe.
	 * @param source_rel
	 * @param uniqueWithin_wmlPackage
	 * @return
	 */
	public static boolean setUniqueRelId(Relationship rel, WordprocessingMLPackage uniqueWithin_wmlPackage) {
		
		boolean unique = false; //<--assumption. set to true once we reach the end of the inner loop without breaking due to finding a double. (as the id has to be unique.) so we restart until it's unique.
		int loopCountLimit = 1000;//Integer.MAX_VALUE - 1;
		int number = 0;
		while (!unique && ++number < loopCountLimit) {
			
			boolean allDifferent = true;
			
			List<RelationshipsPart> allRelationshipParts = new ArrayList<RelationshipsPart>();
			allRelationshipParts.add(uniqueWithin_wmlPackage.getRelationshipsPart());
			allRelationshipParts.add(uniqueWithin_wmlPackage.getMainDocumentPart().getRelationshipsPart());
			
			int allRelationshipParts_index = -1;
			while (++allRelationshipParts_index < allRelationshipParts.size()) {
				for (Relationship existing_rel : allRelationshipParts.get(allRelationshipParts_index)
						.getRelationships().getRelationship()) {
					
					if (rel.getId().equals(existing_rel.getId())
							// we reuse existing. 
//							&& !rel.getTarget().equals(existing_rel.getTarget())
							) {//<- one object can have more relationships, so don't check for source_rel.getParent().equals(target_rel.getParent())
						allDifferent = false;
						// => rename source rel id:
						String oldId = rel.getId();
						rel.setId( oldId + "__" + ((long) (Math.random() * (Integer.MAX_VALUE - 1))) );
						break; //<-
						//TODO update references? how? if required? do objects automatically know it's been changed? perhaps been handled in rel.setId()?
						
					}
					
				}
				if (!allDifferent) {
					break; //=> restart innerloop to not miss relationships. All have to be reexamined.
				}
			}
			// were all relationships 'carrying' :) different ids?
			if (allDifferent) {
				// stop the looping. we have set a id which seems to be unique.
				unique = true;
			}
			//else restart again the inner loop, setting a new random id.
			
		}
		
		return unique;
		
	}
  
    

  
}
