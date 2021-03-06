/*
 * The purpose of this class is to create and organize a winner list in the form of a XML file that users can search and view
 * Codes were modified from XML assignment, XMLApplication class lines 104-120 (display method), 127-155 (search method), 204-225 (file method), 231-247 (writeFile method)
 * Created by Yolanda Yu
 * Last Modified on 06/15/2017
 */

//imports of Leaderboard class
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import nu.xom.*;

public class Leaderboard extends Player {

	// private fields of Leaderboard class
	private static Elements scores;
	private static Element winnerList = new Element("winnersList");
	private static Document doc = new Document(winnerList);
	private static File myWinnerList = new File("myWinnerList.xml");

	public static void addWinnerOne() {
		file(); // Calls file() to pull up and read the corresponding document

		Element Player = new Element("Player");
		winnerList.appendChild(Player); // winnerList is the parent of Player

		Element Name = new Element("Name");
		Name.appendChild(playerOne);
		Player.appendChild(Name); // Name is a sub-element of Player

		String Count = Integer.toString(countOne); // converts the number of
													// steps player one took to
													// win into String form
		Element Steps = new Element("Steps");
		Steps.appendChild(Count); // putting Count in Steps
		Player.appendChild(Steps); // Steps is a sub-element of Player

		String endtime = Long.toString(endTime / 1000); // converts the amount
														// of time player one
														// took to win into
														// String form
		Element Time = new Element("Time");
		Time.appendChild(endtime); // putting endtime in Time
		Player.appendChild(Time); // Time is a sub-element of Player

		// writes the information into the document, with the newly added
		// winners too
		writeFile();
	}

	public static void addWinnerTwo() {
		file(); // Calls file() to pull up and read the corresponding document

		Element Player = new Element("Player");
		winnerList.appendChild(Player); // winnerList is the parent of Player

		Element Name = new Element("Name");
		Name.appendChild(playerTwo);
		Player.appendChild(Name); // Name is a sub-element of Player

		String Count = Integer.toString(countTwo); // converts the number of
													// steps player two took to
													// win into String form
		Element Steps = new Element("Steps");
		Steps.appendChild(Count); // putting Count in Steps
		Player.appendChild(Steps); // Steps is a sub-element of Player

		String endtime = Long.toString(endTime / 1000); // converts the amount
														// of time player two
														// took to win into
														// String form
		Element Time = new Element("Time");
		Time.appendChild(endtime); // putting endtime in Time
		Player.appendChild(Time); // Time is a sub-element of Player
		// writes the information into the document, with the newly added
		// winners too
		writeFile();
	}

	// This method reads then builds on to the XML file containing the root and
	// all of its child elements
	private static void file() {

		// build a file containing everything
		Builder builder = new Builder();

		try {
			doc = builder.build(myWinnerList);
			winnerList = doc.getRootElement();
			scores = winnerList.getChildElements(); // add everything to the
													// scores root
		}

		// If the try block cannot be executed properly due to errors, it will
		// output an according message to the user
		catch (IOException e) {
			System.out.println("Error: " + e);
		} catch (ParsingException e) {
			System.out.println("Error: " + e);
		}
	}

	// This method either creates a new XML file or searches for one that
	// already exist with the title. If it does exist, it takes the original
	// file, copies everything and save it in a new XML file with the new
	// winners added. Replacing/overriding the old document with the new one
	private static void writeFile() {
		// try-catch block that either finds or creates a XML file with a
		// specific name, if an error occurs then catch it and display an error
		// message to the user
		try {
			FileWriter xmlfile = new FileWriter(myWinnerList);
			BufferedWriter writer = new BufferedWriter(xmlfile);
			writer.write(doc.toXML());
			writer.close();
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
	}

	// This method searches the winner list and outputs information on any
	// player the user is searching for
	public static void search() {
		file(); // Calls file() to pull up and read the corresponding document

		// Initialize a new scanner that gets the player's name the user wants
		// to search for
		System.out.println("What player do you want to search for?");
		Scanner three = new Scanner(System.in);
		String playerName = three.nextLine();

		// a count for every time the player's name is found in the winner list
		int correct = 0;

		// run through the entire list and output the desired player's
		// information if found
		for (int list = 0; list < scores.size(); list++) {

			// to check if the user's input matches any player's name in the
			// winner list
			if (scores.get(list).getFirstChildElement("Name").getValue()
					.equals(playerName)) {
				// print it out to the console
				System.out.println(scores.get(list).toXML());
				correct++;
			}
		}
		// if no player name matches what the user is searching for, then the
		// system outputs "This player is not found"
		if (correct == 0) {
			System.out.println("This player is not found");
		}
	}

	// This method sorts the winner list and finds the highest score player
	// (lowest amount of steps)
	public static void sort() {
		file(); // Calls file() to pull up and read the corresponding document
		
		int iMin = 0; // starts off with 0
		
		// Go through the whole list
		for (int i = 1; i < scores.size(); i++) {

			// Compares every value with the smallest value to check if it's
			// smaller
			if (scores
					.get(i)
					.getFirstChildElement("Steps")
					.getValue()
					.compareTo(
							scores.get(iMin).getFirstChildElement("Steps")
									.getValue()) > 0) {
				iMin = i; // if true, set iMin as the new lowest value's
							// position
			}
		}
		System.out.println("\n"
				+ "The current highest score on the leaderboard is: ");
		System.out.println(scores.get(iMin).toXML()); // outputs all information
														// on the highest score
														// player
	}

	// formatting and displaying the winner list (XML file) by using serializer,
	// using try-catch to get rid of any exceptions and holes in the system
	public static void display() {
		file(); // Calls file() to pull up and read the corresponding document

		// Formats the winner list (XML file) into a certain frame and outputs
		// it
		try {
			Serializer serializer = new Serializer(System.out);
			serializer.setIndent(4);
			serializer.setMaxLength(64);
			serializer.write(doc);
		}
		// Output an error message to the user if the try block cannot be
		// executed properly
		catch (IOException ex) {
			System.err.println(ex);
		}
	}
}
