package org.example.dijsktra;

import java.util.*;

import com.google.common.base.Objects;




public class Graph<T> {
    
    private LinkedList<Vertex<T>> listVertex;
    private LinkedList<Edge<T>> listEdges;
    public int vCant, eCant;
    
    public Graph(){
        listVertex = new LinkedList<>();
        listEdges = new LinkedList<>();
        vCant = 0;
        eCant = 0;
    }
    public void addVertex(T info){ listVertex.add(new Vertex<T>(info)); vCant++;};
    private void addVertex(Vertex<T> vertex){ listVertex.add(vertex); vCant++;};
    public void addEdge(T oring, T dest, int w) {
        
        Vertex<T> tail = findVertex(oring);
        Vertex<T> head = findVertex(dest);
    
        if (tail == null) {
            tail = new Vertex<T>(oring);
            addVertex(tail);
        }
    
        if (head == null) {
            head = new Vertex<T>(dest);
            addVertex(head);
        }
    
        tail.addEdge(head, w);
        eCant++;
    }
    
    public Vertex<T> findVertex(T info) {
        for (Vertex<T> vertex : listVertex) {
            if (vertex.getInfo().equals(info)) {
                return vertex;
            }
        }
        return null;
    }
    public int posVertex(T info){
        int pos = 0;
        for (Vertex<T> vertex : listVertex) {
            if (vertex.getInfo().equals(info)) {
                return pos;
            }
            pos++;
        }
        return -1;
    }
    
    public LinkedList<Vertex<T>> getVertexs(){return listVertex;}
    public LinkedList<Edge<T>> getEdges(){return listEdges;}

    public Map<Vertex<T>, List<Vertex<T>>> dijkstra(T init){
        Map<Vertex<T>, Integer> distances = new HashMap<>();
        Map<Vertex<T>, Vertex<T>> previus = new HashMap<>();
        
        PriorityQueue<Vertex<T>> pq = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        Vertex<T> startVertex = findVertex(init);

        for (Vertex<T> vertex : listVertex) {
            if (vertex.equals(startVertex)) {
                distances.put(vertex, 0);
            } else {
                distances.put(vertex, Integer.MAX_VALUE);
            }
            pq.add(vertex);
        }

        while (!pq.isEmpty()) {
            Vertex<T> current = pq.poll();

            for(Edge<T> edge : current.getEdgeList()){
                Vertex<T> neighbor = edge.getDestiny();
                int newDist = distances.get(current) + edge.getWeight();

                if(newDist < distances.get(neighbor)){
                    distances.put(neighbor, newDist);
                    previus.put(neighbor, current);
                    pq.remove(neighbor);
                    pq.add(neighbor);
                }

            }
        }

        Map<Vertex<T>, List<Vertex<T>>> path = new HashMap<>();
        for(Vertex<T> end : listVertex){
            path.put(end, reconstructionList(previus, startVertex, end));
        }

        return path;
}
    

    private List<Vertex<T>> reconstructionList(Map<Vertex<T>, Vertex<T>> previous, Vertex<T> start, Vertex<T> end){
        List<Vertex<T>> path = new LinkedList<>();
        
        if (!previous.containsKey(end) && !start.equals(end)) return path;

        for (Vertex<T> vertex = end; vertex != null; vertex = previous.get(vertex))
            path.add(0, vertex);
        
        return path;
    }


    public void initializeGraph(int numVertices, int numEdges) {
        Random random = new Random();

        
        for (int i = 1; i <= numVertices; i++) {
            addVertex((T) ("V" + i));
        }

        // Agregar m aristas aleatorias con pesos aleatorios entre 1 y 10
        for (int i = 0; i < numEdges; i++) {
            int originIndex = random.nextInt(numVertices);  // Índice aleatorio para el vértice de origen
            int destIndex = random.nextInt(numVertices);    // Índice aleatorio para el vértice de destino

            if (originIndex != destIndex) {  // Evitar lazos
                T origin = listVertex.get(originIndex).getInfo();
                T dest = listVertex.get(destIndex).getInfo();
                int weight = random.nextInt(10) + 1;  // Peso aleatorio entre 1 y 10
                addEdge(origin, dest, weight);
            }
        }
    }
}


/* public void addEdge(T oring, T dest, int w){
        for(Iterator<Edge<T>> itr = listEdges.iterator(); itr.hasNext();){
            Edge<T> edge = itr.next();
            if(edge.getDestiny().getInfo().hashCode() == dest.hashCode()
            && edge.getOrigin().getInfo().hashCode() == oring.hashCode())
                return;
        }

        Edge<T> edge = new Edge<>(new Vertex<T>(dest),w);
        eCant++;
        for(Iterator<Vertex<T>> itr = listVertex.iterator(); itr.hasNext();){
            Vertex<T> org = itr.next();
            if(org.getInfo().hashCode() == oring.hashCode())
                edge.setOrigin(org);
                return;
        }
        edge.setOrigin(new Vertex<T>(oring));
    } */