/*
 * CLASS: CS 4310
 * NAME: Chandler Klein
 * DATE: 04/17/2020
 * Assignment 6: Dijkstra's Shortest Path Algorithms
 * 
 * This program finds and prints out the shortest path between two
 * places on a graph of US road network using Dijkstra's shortest-path
 * algorithm. 
 * 
 * Each line in Road.txt represents one road segment
 * Each line in Place.txt represents a place
 */

package edu.wmich.cs4310.a6.chandlerklein;

import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws IOException {
		
		/* 
		 * A couple of examples: 
		 * MIKALAMAZOO N -> MIANN ARBOR N
		 * WAFORKS N -> FLFLORIDA CITY SE
		 */ 

		// Gets the source name and destination name from the user
	 	String[] input = getInput();
	
		Graph<Integer> graph = new Graph<>();
	
		// makePlacesMap creates a Map for the places in USRoads/Place.txt
		// If the source and/or destination are not present in the file,
		// checkFlag will print out an error message
		int flag = graph.makePlacesMap(input[0], input[1]);
		checkFlag(flag);
	
		// Used to measure run time
		long start = System.currentTimeMillis();
	
		// Reads USRoads/Road.txt and adds all the edges to the graph
		graph.parseRoads();
	
		// Dijkstra object takes in the graph with all of its edges and
		// map of places, and printPath takes in the source and destination
		// strings
		Dijkstra dsp = new Dijkstra(graph);
		dsp.printPath(input[0], input[1]);
	
		// Print time to execute in milliseconds
		long end = System.currentTimeMillis();
		printTime(start, end);
	}

	// Get name of source and destination from the user, returns 2 strings
	public static String[] getInput() {

		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter the source name:\n$ ");
		String src = scanner.nextLine();

		System.out.print("Enter the destination name:\n$ ");
		String dst = scanner.nextLine();

		scanner.close();
		return new String[] { src, dst };
	}

	// Check the flag returned Graph method makePlacesMap
	// If flag is 0, the source and destination exist
	// If flag is 1, the source provided by the user was not valid
	// If flag is 2, the destination provided by the user was not valid
	// If flag is 3, source and destination provided by the user were not valid
	public static void checkFlag(int flag) {
		switch (flag) {
			case 0:
				break;
			case 1:
				System.out.println(">> Invalid source name.\n   Please try again.");
				System.exit(0);
			case 2:
				System.out.println(">> Invalid destination name.\n   Please try again.");
				System.exit(0);
			case 3:
				System.out.println(">> Invalid source and destination name.\n   "
						+ "Please try again.");
				System.exit(0);
		}
	}

	// Print the time to execute the program in milliseconds
	public static void printTime(long start, long end) {
		long timeElapsed = end - start;
		System.out.println("\n\n\n---------------------------\nProgram executed in: " + 
				timeElapsed + " ms");
	}
}