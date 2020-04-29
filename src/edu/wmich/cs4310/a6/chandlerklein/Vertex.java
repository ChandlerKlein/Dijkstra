/*
 * CLASS: CS 4310
 * NAME: Chandler Klein
 * DATE: 04/17/2020
 * Assignment 6: Dijkstra's Shortest Path Algorithms
 */

package edu.wmich.cs4310.a6.chandlerklein;

import java.util.List;
import java.util.ArrayList;

public class Vertex<T> {

	private int id;
	private T data;
	private List<Edge<T>> edges;
	private List<Vertex<T>> adjacencies;

	public Vertex(int id) {
		setId(id);
		edges = new ArrayList<>();
		adjacencies = new ArrayList<>();
	}

	public void addAdjacentVertex(Edge<T> edge, Vertex<T> vertex) {
		edges.add(edge);
		adjacencies.add(vertex);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public List<Edge<T>> getEdges() {
		return edges;
	}

	public void setEdges(List<Edge<T>> edges) {
		this.edges = edges;
	}

	public List<Vertex<T>> getAdjacencies() {
		return adjacencies;
	}

	public void setAdjacencies(List<Vertex<T>> adjacencies) {
		this.adjacencies = adjacencies;
	}

	@Override
	public String toString() {
		return String.valueOf(id);
	}

}
