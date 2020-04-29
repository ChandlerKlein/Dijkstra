/*
 * CLASS: CS 4310
 * NAME: Chandler Klein
 * DATE: 04/17/2020
 * Assignment 6: Dijkstra's Shortest Path Algorithms
 */

package edu.wmich.cs4310.a6.chandlerklein;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph<T> {

	private List<Edge<T>> allEdges;
	private Map<Integer, Vertex<T>> allVertices;
	private Map<String, String> placesMap;

	public Graph() {
		placesMap = new HashMap<>();
		allEdges = new ArrayList<>();
		allVertices = new HashMap<>();
	}

	// Add an edge to the graph structure and adjacent vertices
	public void addEdge(int src, int dest, float weight, String description) {
		// Make a new vertex if the one specified in the edge
		// doesn't exist yet; otherwise, get it from allVertices
		Vertex<T> vertex1;
		if (allVertices.containsKey(src)) {
			vertex1 = allVertices.get(src);
		} else {
			vertex1 = new Vertex<T>(src);
			allVertices.put(src, vertex1);
		}
		Vertex<T> vertex2;
		if (allVertices.containsKey(dest)) {
			vertex2 = allVertices.get(dest);
		} else {
			vertex2 = new Vertex<T>(dest);
			allVertices.put(dest, vertex2);
		}

		// Create a new edge with the vertices specified
		Edge<T> edge = new Edge<T>(vertex1, vertex2, weight, description);

		// Add edge to List
		allEdges.add(edge);
		
		// Add edge and adjacent vertex to both source and destination,
		// since the graph edges are bi-directional
		vertex1.addAdjacentVertex(edge, vertex2);
		vertex2.addAdjacentVertex(edge, vertex1);
	}

	// Reads the USRoads/Road.txt file -- adds an edge to the graph
	// for each line
	public void parseRoads() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("USRoads/Road.txt"));
		String[] split;

		String line = br.readLine();

		while (line != null) {
			split = line.split(",");
			int startPlaceId = Integer.parseInt(split[0]);
			int endPlaceId = Integer.parseInt(split[1]);
			float roadLength = Float.parseFloat(split[2]);
			String description = split.length == 3 ? "" : split[3];

			addEdge(startPlaceId, endPlaceId, roadLength, description);
			line = br.readLine();
		}
		br.close();
	}

	// Stores value pairs from USRoads/Place.txt in a Map called placesMap
	// Includes pairs in form { Place Name : Place ID } as well as 
	// { Place ID : Place Name } to make searching easier.
	public int makePlacesMap(String src, String dst) throws IOException {
		int flag = 0;
		BufferedReader br = new BufferedReader(new FileReader("USRoads/Place.txt"));

		Map<String, String> map = new HashMap<>();

		String[] split;
		String line = br.readLine();

		while (line != null) {
			split = line.split(",");
			String placeId = split[0];
			String placeName = split[1];

			map.put(placeName, placeId);
			map.put(placeId, placeName);
			line = br.readLine();
		}
		// Set flag depending on whether source, destination, or both
		// are not valid places
		if (!map.containsKey(src)) {
			flag = 1;
		}
		if (!map.containsKey(dst)) {
			flag = 2;
		}
		if (!map.containsKey(src) && !map.containsKey(dst)) {
			flag = 3;
		}
		br.close();
		setPlacesMap(map);
		return flag;
	}

	public List<Edge<T>> getAllEdges() {
		return allEdges;
	}

	public Collection<Vertex<T>> getAllVertices() {
		return allVertices.values();
	}

	public Map<String, String> getPlacesMap() {
		return placesMap;
	}

	public void setPlacesMap(Map<String, String> placesMap) {
		this.placesMap = placesMap;
	}

	public Vertex<T> getVertex(int id) {
		return allVertices.get(id);
	}

}