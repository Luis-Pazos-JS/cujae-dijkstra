package org.example.dijsktra;

import java.util.*;

public class Menu {
    Scanner scanner = new Scanner(System.in);
    public Menu(){
        int options;
        System.out.println("-------------- Menu de Grafo -------------");
        System.out.println("1 : Crear grafo aleatorio");
        System.out.println("2 : Crear grafo definido");

        options = scanner.nextInt();
        scanner.nextLine();
        Graph<String> myGraph = new Graph<>();

        if(options == 2){
        myGraph.addVertex("V1");
        myGraph.addVertex("V2");
        myGraph.addVertex("V3");
        myGraph.addVertex("V4");
        myGraph.addVertex("V5");

        myGraph.addEdge("V1", "V2", 5);
        myGraph.addEdge("V1", "V3", 7);
        myGraph.addEdge("V1", "V4", 8);
        myGraph.addEdge("V1", "V5", 6);
        myGraph.addEdge("V3", "V2", 9);
        myGraph.addEdge("V3", "V2", 5);
        myGraph.addEdge("V3", "V6", 5);
        myGraph.addEdge("V2", "V1", 8);
        

        }
        System.out.println("1 : anadir arista");
        System.out.println("2 : anadir vertice");
        System.out.print("3 : mostrar grafo");


        // Verificar que la entrada es un número
        if (scanner.hasNextInt()) {
            options = scanner.nextInt();
            scanner.nextLine();  // Consumir el salto de línea pendiente
        } else {
            System.out.println("Entrada no válida.");
            return;
        }
        

        if(options == 2){
            
        }
        if(options == 3){
            show(myGraph);
        }
        
    }

    private void show(Graph<String> graph){
        int nVector = graph.vCant;

        String[][] table = new String[nVector + 1][nVector + 1];
        for (int i = 0; i < nVector + 1; i++) {
            for (int j = 0; j < nVector + 1; j++) {
                table[i][j] = "----";
            }
        }
        table[0][0] = "VERT";

        for (int i = 0; i < nVector; i++) {
            var vertex = graph.getVertexs().get(i);
            table[0][i + 1] = vertex.getInfo();
            table[i + 1][0] = vertex.getInfo();
        }
    }
}
