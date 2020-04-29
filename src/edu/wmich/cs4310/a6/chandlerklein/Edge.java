/*
 * CLASS: CS 4310
 * NAME: Chandler Klein
 * DATE: 04/17/2020
 * Assignment 6: Dijkstra's Shortest Path Algorithms
 */

package edu.wmich.cs4310.a6.chandlerklein;

public class Edge<T> {

	private Vertex<T> src, dest;
	private float weight;
	private String description;

	public Edge(Vertex<T> src, Vertex<T> dest, float weight, String description) {
		setSrc(src);
		setDest(dest);
		setWeight(weight);
		setDescription(description);
	}

	public Vertex<T> getSrc() {
		return src;
	}

	public void setSrc(Vertex<T> src) {
		this.src = src;
	}

	public Vertex<T> getDest() {
		return dest;
	}

	public void setDest(Vertex<T> dest) {
		this.dest = dest;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String toString() {
		return "Edge [vertex1=" + src + ", vertex2=" + dest + ", " + "weight=" + weight + "," + " description="
				+ description + "]";
	}
}
