package org.example.dijsktra;

import java.util.*;

public class Graph<T> {

    private LinkedList<Vertex<T>> listVertex;

    private HashMap<
        Vertex<T>,
        HashMap<Vertex<T>, Pair<Integer, LinkedList<Vertex<T>>>>
    > dijkstraCache;
    private boolean isChangedCache;

    public int vCant, eCant;

    public Graph() {
        listVertex = new LinkedList<>();
        vCant = 0;
        eCant = 0;
        dijkstraCache = new HashMap<>();
        isChangedCache = false;
    }

    public void addVertex(T info) {
        this.addVertex(new Vertex<T>(info));
    }

    private void addVertex(Vertex<T> vertex) {
        listVertex.add(vertex);
        vCant++;
        isChangedCache = true;
    }

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
        isChangedCache = true;
    }

    public Vertex<T> findVertex(T info) {
        for (Vertex<T> vertex : listVertex) {
            if (vertex.getInfo().equals(info)) {
                return vertex;
            }
        }
        return null;
    }

    public int posVertex(T info) {
        int pos = 0;
        for (Vertex<T> vertex : listVertex) {
            if (vertex.getInfo().equals(info)) {
                return pos;
            }
            pos++;
        }
        return -1;
    }

    public LinkedList<Vertex<T>> getVertexs() {
        return listVertex;
    }

    public HashMap<Vertex<T>, Pair<Integer, LinkedList<Vertex<T>>>> dijkstra(
        T init
    ) {
        Vertex<T> startVertex = findVertex(init);
        // TODO: check if startVertex is null, raise exception
        if (startVertex == null) {
            return new HashMap<>();
        }

        if (!isChangedCache && dijkstraCache.containsKey(startVertex)) {
            return dijkstraCache.getOrDefault(startVertex, new HashMap<>());
        }

        var path = new HashMap<
            Vertex<T>,
            Pair<Integer, LinkedList<Vertex<T>>>
        >();
        Map<Vertex<T>, Integer> distances = new HashMap<>();
        Map<Vertex<T>, Vertex<T>> previus = new HashMap<>();

        PriorityQueue<Vertex<T>> pq = new PriorityQueue<>(
            Comparator.comparingInt(distances::get)
        );

        for (Vertex<T> vertex : listVertex) {
            if (vertex.equals(startVertex)) {
                distances.put(vertex, 0);
                //path.put(vertex, new Pair<>(0, new LinkedList<>()));
            } else {
                distances.put(vertex, Integer.MAX_VALUE);
                //path.put(
                //    vertex,
                //    new Pair<>(Integer.MAX_VALUE, new LinkedList<>())
                //);
            }
            pq.add(vertex);
        }

        while (!pq.isEmpty()) {
            Vertex<T> current = pq.poll();

            for (Edge<T> edge : current.getEdgeList()) {
                Vertex<T> neighbor = edge.getDestiny();
                int dist = distances.get(current) + edge.getWeight();

                if (dist < distances.get(neighbor)) {
                    distances.put(neighbor, dist);
                    previus.put(neighbor, current);
                    pq.remove(neighbor);
                    pq.add(neighbor);
                }
            }
        }

        for (Vertex<T> end : listVertex) {
            var reconstructed = reconstructionList(previus, startVertex, end);
            path.put(end, new Pair<>(distances.get(end), reconstructed));
        }

        dijkstraCache.put(startVertex, path);
        isChangedCache = false;
        return path;
    }

    private LinkedList<Vertex<T>> reconstructionList(
        Map<Vertex<T>, Vertex<T>> previous,
        Vertex<T> start,
        Vertex<T> end
    ) {
        var path = new LinkedList<Vertex<T>>();

        if (!previous.containsKey(end) && !start.equals(end)) return path;

        for (
            Vertex<T> vertex = end;
            vertex != null;
            vertex = previous.get(vertex)
        ) path.add(vertex);

        return path;
    }

    public void initializeGraph(int numVertices, int numEdges) {
        Random random = new Random();

        for (int i = 1; i <= numVertices; i++) {
            addVertex((T) ("V" + i));
        }

        // Agregar m aristas aleatorias con pesos aleatorios entre 1 y 10
        for (int i = 0; i < numEdges; i++) {
            int originIndex = random.nextInt(numVertices); // Índice aleatorio para el vértice de origen
            int destIndex = random.nextInt(numVertices); // Índice aleatorio para el vértice de destino

            if (originIndex != destIndex) { // Evitar lazos
                T origin = listVertex.get(originIndex).getInfo();
                T dest = listVertex.get(destIndex).getInfo();
                int weight = random.nextInt(10) + 1; // Peso aleatorio entre 1 y 10
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
