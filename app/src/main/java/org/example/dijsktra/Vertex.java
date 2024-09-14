package org.example.dijsktra;

import java.util.LinkedList;
import java.util.Iterator;

public class Vertex<T>{
    public T info;
    public LinkedList<Edge<T>> edgeList;

    public Vertex(T info) {
		super();
		this.info = info;
		this.edgeList = new LinkedList<Edge<T>>();
	}

    public boolean deleteEdge(Vertex<T> ver){
        for(Iterator<Edge<T>> itr = edgeList.iterator(); itr.hasNext();){
            Edge<T> edge = itr.next();
            if( edge.getDestiny().equals(ver)){
                itr.remove();
                return true;
            }
        }
        return false;
    }
    public boolean isAdjacent(Vertex<T> ver){
        for(Iterator<Edge<T>> itr = edgeList.iterator(); itr.hasNext();){
            Vertex<T> vertex = itr.next().getDestiny();
            if(vertex.equals(ver))
                return true;
        }
        return false;
    }
    
    public LinkedList<Edge<T>> getEdgeList() {return edgeList;}
    public T getInfo(){return info;}
    
    public boolean addEdge(Vertex<T> dest, int w){
        boolean result = false;
        if(w > -1){
            for(Iterator<Edge<T>> itrE = edgeList.iterator(); itrE.hasNext() && !result;){
                Edge<T> edge = itrE.next();
                if(edge.getDestiny().getInfo().equals(dest.getInfo())){
                    result = true;
                }
            }
            if (!result){
                edgeList.add(new Edge<>(dest, w));
                result = true;
            }
        }
        return result;
    }
    public void setInfo(T inf){this.info = inf;}
    public void setEdge(Edge<T> edg) {edgeList.add(edg);}
}
