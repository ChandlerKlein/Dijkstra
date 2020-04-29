/*
 * CLASS: CS 4310
 * NAME: Chandler Klein
 * DATE: 04/17/2020
 * Assignment 6: Dijkstra's Shortest Path Algorithms
 */

package edu.wmich.cs4310.a6.chandlerklein;

import java.util.Map;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Dijkstra {

	private Map<Vertex<Integer>, Vertex<Integer>> parent;
	private Graph<Integer> graph;

	public Dijkstra(Graph<Integer> graph) {
		this.graph = graph;
		parent = new HashMap<>();
	}

	// Finds the shortest path from source to all other vertices
	// The resulting Map with distances to other vertices is returned
	// This function also adds to a parent Map so path can be traced
	public Map<Vertex<Integer>, Float> shortestPath(Graph<Integer> graph, Vertex<Integer> sourceVertex) {

		// Utilized BinaryMinHeap (priority queue) source given in report
		BinaryMinHeap<Vertex<Integer>> minHeap = new BinaryMinHeap<>();
		Map<Vertex<Integer>, Float> distance = new HashMap<>();

		// All of the vertices of the graph are added to the min-heap
		for (Vertex<Integer> vertex : graph.getAllVertices()) {
			minHeap.add(Integer.MAX_VALUE, vertex);
		}

		// Decreases distance to source to 0 since we start there
		minHeap.decrease(sourceVertex, 0);

		// Add the source vertex to the final Map returned
		distance.put(sourceVertex, (float) 0);

		// The parent of the source vertex should be null to indicate
		// that it has been reached
		parent.put(sourceVertex, null);

		
		while (!minHeap.isEmpty()) {
			// Remove vertex with minimum distance - called current
			BinaryMinHeap<Vertex<Integer>>.Node heapNode = minHeap.extractMinNode();
			Vertex<Integer> current = heapNode.key;

			distance.put(current, heapNode.weight);

			// For all neighbors of current vertex
			for (Edge<Integer> edge : current.getEdges()) {
				Vertex<Integer> adjacent = getVertexForEdge(current, edge);

				if (!minHeap.containsData(adjacent)) {
					continue;
				}
				
				// The "Relaxation" step
				// Relax the edge if a shorter path is found
				// The new parent is put into the Map
				float newDistance = distance.get(current) + edge.getWeight();

				if (minHeap.getWeight(adjacent) > newDistance) {
					minHeap.decrease(adjacent, newDistance);
					parent.put(adjacent, current);
				}
			}
		}
		return distance;
	}

	public void printPath(String src, String dst) {
		// Start by getting the source and destination vertex
		int srcId = Integer.parseInt(graph.getPlacesMap().get(src));
		int dstId = Integer.parseInt(graph.getPlacesMap().get(dst));
		Vertex<Integer> srcVertex = graph.getVertex(srcId);
		Vertex<Integer> dstVertex = graph.getVertex(dstId);

		// Destination vertex is stored for later
		Vertex<Integer> finalDist = dstVertex;

		Map<Vertex<Integer>, Float> distance = shortestPath(graph, srcVertex);

		List<String> path = new LinkedList<>();

		System.out.printf("Searching from %d(%s) to %d(%s)\n", srcId, src, dstId, dst);

		// If the parent of the destination vertex is null, 
		// that means it was never able to be reached by the source
		// in the shortestPath method
		if (parent.get(dstVertex) == null) {
			System.out.println("\n>> Destination can't be traveled to by car." + "\n   Please try again.");
			System.exit(0);
		}

		// While there is still a valid path, we continue 
		while (parent.get(dstVertex) != null) {
			int current = dstVertex.getId();
			dstVertex = parent.get(dstVertex);
			int parentId = dstVertex.getId();

			String desc = "";
			float weight = (float) 0.0;

			// Find the right edge that both the current vertex and parent
			// are connected so we can print
			for (Edge<Integer> edge : dstVertex.getEdges()) {
				if ((edge.getSrc().getId() == parentId && edge.getDest().getId() == current)
						|| (edge.getSrc().getId() == current && edge.getDest().getId() == parentId)) {
					desc = edge.getDescription();
					weight = edge.getWeight();
					break;
				}
			}

			// This string represents one line of the final output in the console
			// Shows the id and name of each place, description, and length
			// The string is added to the path
			String direction = String.format("%d(%s) -> %d(%s), %s, %.2f mi.\n", parentId,
					graph.getPlacesMap().get(String.valueOf(parentId)), current,
					graph.getPlacesMap().get(String.valueOf(current)), desc, weight);
			path.add(direction);

			if (dstVertex.getId() == srcId) {
				break;
			}
		}
		// The list with strings is reversed since we went backwards
		// from the destination vertex to the source vertex when saving
		Collections.reverse(path);

		// Print the final output path with step numbers
		// as well as the final distance, which uses value saved earlier
		int i = 1;
		for (String str : path) {
			System.out.printf("\t%2d: %s", i, str);
			i++;
		}
		System.out.printf("It takes %.2f miles from %d(%s) to %d(%s).\n", distance.get(finalDist), srcId, src, dstId,
				dst);

	}

	public Vertex<Integer> getVertexForEdge(Vertex<Integer> v, Edge<Integer> e) {
		return e.getSrc().equals(v) ? e.getDest() : e.getSrc();
	}

	public Map<Vertex<Integer>, Vertex<Integer>> getParent() {
		return parent;
	}

	public void setParent(Map<Vertex<Integer>, Vertex<Integer>> parent) {
		this.parent = parent;
	}
}
