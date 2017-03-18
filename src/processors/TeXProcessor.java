package core;

import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.imageio.ImageIO;

// TODO use JEuclid instead. (scilab jlatexmath was introduced only temporarily).
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import converter.Converter;


/**
 * Utility for processing LaTeX files.
 *
 * @author Schweiner, Artijom, J.R.I.B., worlddevelopment
 *
 */
public class TeXProcessor {

/**
 * Cuts the given LaTeX sheetdraft with the help of a stored
 * DeclarationSet.
 *
 * @param sheetdraft
 * @return ArrayList of the split result (parts) with header
 * and \begin | \end{document}, and in theory directly compilable.
 * @throws IOException
 */
	public static ArrayList<Part> cutParts(Sheetdraft sheetdraft) throws IOException {

		System.out.println("TeXProcessor was called.");

		String headermixture = ""; // to be determined
//		ArrayList<String> allLines = new ArrayList<String>();
		String[] allTexLines = sheetdraft.getRawContent();//getTexText();
		int indexOfFirstIdentifier = 0;
		int indexOfLastIdentifier = 0 ;
		int indexOfBeginDoc = 0;
//		File cutPart;
		int indexOfFirstCut = 0;
		int indexOfLastCut = 0;
		boolean lastIdentifierFound = false;


		// ArrayList der zu schneidenen Aufgaben
		ArrayList<Part> outputTexParts = new ArrayList<Part>();

		// Have no declarations been found in the sheet?.
		if (sheetdraft.getDeclarationSet().declarations.size() == 0) {
			return outputTexParts;
		}

		// Using the content part heads create a list with the line
		// numbers at which to cut.
		ArrayList<Integer> linesToCut = new ArrayList<Integer>();

		HashMap<Integer, Declaration> lineDecReference
			= new HashMap<Integer, Declaration>();
		int ex_count_and_pos = 0;
		for (int i = 0; i < allTexLines.length; i++) {
			String singleLine = allTexLines[i];
				for (Declaration dec
						: sheetdraft.getDeclarationSet().declarations) {

					if (!dec.hasHead()) {
						continue;
					}
					//else

					if (dec.getHead() != null && singleLine != null) {
						System.out.println("Examining head: "
								+ dec.getHead().toString());

						if (singleLine.contains(dec.getHead()[0])
								&& singleLine.contains(dec.getHead()[1])
								&& singleLine.contains(dec.getHead()[2])
								||
								singleLine.contains(dec.getHead()[0])
								&& singleLine.contains(dec.getHead()[1])
								&& singleLine.contains(dec.getHead()[3])
								||
								singleLine.contains(dec.getHead()[0])
								&& singleLine.contains(dec.getHead()[2])
								&& singleLine.contains(dec.getHead()[3])
								||
								singleLine.contains(dec.getHead()[1])
								&& singleLine.contains(dec.getHead()[2])
								&& singleLine.contains(dec.getHead()[3])
								) {

							System.out.println("Found head: " + dec.getHead());
							Integer indexOfCut = i;
							while (!allTexLines[indexOfCut]
									.startsWith("\\begin")) {
								indexOfCut--;
							}
							System.out.println("Cut at line " + indexOfCut);
							linesToCut.add(indexOfCut);
							lineDecReference.put(indexOfCut, dec);
					}
				}
			}
		}

		// Read line with "\begin{document}"
		for (int i = 0; i < allTexLines.length; i++) {
			String singleLine = allTexLines[i];
			if (singleLine.startsWith("\\begin{docum")) {
				indexOfBeginDoc = i;
				System.out.println("begin{docum... found at line: "
						+ indexOfBeginDoc);
			}
		}
		// And store the header: (inclusive begin document)
		ArrayList<String> header = new ArrayList<String>();
		for (int i = 0; i < indexOfBeginDoc + 1; i++) {
			header.add(allTexLines[i]);
		}


		// Create an part from the content inbetween two heads.
		// Prepends and appends the header to every found part.
		// Store the found content parts.
		for (int i = 0; i < linesToCut.size() - 1; i++) {
			ArrayList<String> cutPart = new ArrayList<String>();
			// cut from current cut index to the next cut index.
			for (int j = linesToCut.get(i); j < linesToCut.get(i + 1); j++ ) {
				cutPart.add(allTexLines[j]);
			}
			// Prepend header to each TODO for performance add it first.
			for (int j = header.size() - 1; j > -1; j--) {
				cutPart.add(0, header.get(j));
			}
			// Append end
			cutPart.add(new String("\\end{document}"));

			// Convert to an array
			String[] partText = new String[cutPart.size()];
			for (int j = 0; j < partText.length; j++) {
				partText[j] = ersetzeUmlaute(cutPart.get(j));
			}
			Declaration dec = lineDecReference.get(linesToCut.get(i));
			// Creating file for it on harddrive in writeToHarddisk.

			// Increment here because we start with 1 instead of 0
			ex_count_and_pos++;

			// write to filesystem
			String new_ex_filelink = sheetdraft
				.getFilelinkForPartFromPosWithoutEnding(
					ex_count_and_pos, ex_count_and_pos)
				+ sheetdraft.getFileEnding();
			ReadWrite.write(partText, new_ex_filelink);

			// Create Part instance for eventual further handling.
//			Part loopPart = new Part(
//					new_ex_filelink
//					, dec
//					, partPlainText
//					//, partText
//					, headermixture
//			);
//			outputTexParts.add(loopPart);

		}

		System.out.println("*done* TeXProcessor: cutParts");
		return outputTexParts;
	}



	static void createImagesForParts(Sheetdraft sheetdraft) throws FileNotFoundException {
		createImagesForParts(sheetdraft.getAllParts().values());
	}

	static void createImagesForParts(Collection<Part> parts) throws FileNotFoundException {
		for (Part part : parts) {
			Converter.tex2image(part.filelink);
		}
	}



	/**
	 * Replaces all Umlauts and \u00DF with their latex representation.
	 * e.g. &Ouml; -> \"O, etc.
	 * TODO Add support for more languages (currently FRENCH).
	 *
	 * @param line
	 * @return The line with umlauts in LaTeX representation.
	 */
	private static String ersetzeUmlaute(String line) {

		String ergebnis = line.replaceAll("\u00D6", "\\\"O");
		//System.out.println(ergebnis);
		ergebnis = ergebnis.replaceAll("\u00F6", "\\\"o");
		//System.out.println(ergebnis);
		ergebnis = ergebnis.replaceAll("\u00DC", "\\\"U");
		//System.out.println(ergebnis);
		ergebnis = ergebnis.replaceAll("\u00FC", "\\\"u");
		//System.out.println(ergebnis);
		ergebnis = ergebnis.replaceAll("\u00C4", "\\\"A");
		//System.out.println(ergebnis);
		ergebnis = ergebnis.replaceAll("\u00E4", "\\\"a");
		//System.out.println(ergebnis);
		ergebnis = ergebnis.replaceAll("\u00DF", "sz");
		return ergebnis;
	}



}
