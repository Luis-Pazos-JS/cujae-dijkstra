package org.example.dijsktra;

public class Edge<T> {
    protected Vertex<T> destiny, origin;
	private int weight;
	public Edge(Vertex<T> vertex) { this.destiny = vertex; }
	public Edge(Vertex<T> vertex, int w) { this.destiny = vertex; weight = w;}
	
	public Vertex<T> getDestiny() { return destiny; }
	public Vertex<T> getOrigin() { return origin; }
	public int getWeight(){return weight;}

	public void setDestiny(Vertex<T> vertex) {this.destiny = vertex;}
	public void setOrigin(Vertex<T>  vertex) {this.destiny = vertex;}
	public void setWeight(int w){this.weight = w;}
}
