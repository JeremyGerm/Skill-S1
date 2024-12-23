/**************************
 * Author: Christian Duncan (Starting Code)
 * Modified by: Dawit Kasy, Ella Berry, Jason Handrahan, Jeremy Wiening, Paul Zegarek
 * 
 * Fall 2024, CSC215
 * Given a graph of contact points between individuals, a list of infected individuals, 
 * and a distance D, determine how many individuals are with D contacts of an infected individual.
 */

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class ContactTracer {
    public static final String DEFAULT_NAME = "simple0.input";

    public static void main(String[] args) {
        String fileName = DEFAULT_NAME;

        if (args.length >= 1) {
            fileName = args[0];
        }

        processFile(fileName);
    }

    // Read in the problem and produce the output
    public static void processFile(String fileName) {
        try {
            String[] line;

            // Open up the file for parsing
            Scanner sc = new Scanner(new FileReader(fileName));

            // Get the number of names (IDs)
            int n = Integer.parseInt(sc.nextLine());

            HashMap<String, Integer> peopleId = new HashMap<>();

            Graph g = new Graph();

            for (int i = 0; i < n; i++) {
                String id = sc.nextLine();
                System.out.println("DEBUG: Node " + i + ": ID=" + id);

                // You will want to store this ID. 
                // Using a Hashmap, I would map ID to i, call it the id number.
                int idNum = i;
                peopleId.put(id, idNum);
                g.addVertex(idNum);
               
            }

            // You will probably want to create an undirected graph G with n nodes
            // Initially with no edges but add a method to add an edge between two nodes

        

            // System.out.println("//////////////////////////////////////////////////////");
            // System.out.println("HashMap elements" + peopleId);

            // Get the various connections
            int m = Integer.parseInt(sc.nextLine());
                   

            for (int e = 0; e < m; e++) {
                line = sc.nextLine().split(" ");
                String idA = line[0];
                String idB = line[1];
                //example idA:Alice, idB: Bob were in contact. now we must use the method to create a connection(edge) between them

            

                System.out.println("DEBUG: Contact between " + idA + " and " + idB);
                // You might want to get the id number of idA and idB from the Hashmap-
                // Then add the edge between idA and idB to the graph
                g.addEdge(peopleId.get(idB), peopleId.get(idA));;
            }

            // System.out.println("//////////////////////////////////////////////////////");
            //  g.printAdjacencyList();
            //   System.out.println("//////////////////////////////////////////////////////");

          



            // Get how many contacts have been infected and how far to report exposure from
            // an infected individual
            line = sc.nextLine().split(" ");
            int numInfected = Integer.parseInt(line[0]);
            int distance = Integer.parseInt(line[1]);

            ArrayList<String> infected = new ArrayList<String>(numInfected);
            for (int c = 0; c < numInfected; c++) {
                String idA = sc.nextLine();
                System.out.println("DEBUG: Infected: " + idA);
                infected.add(idA);
            }

                //find a way to find the distance from node peopleInfected.get(idA)

            HashMap<Integer, Boolean> exposed = new HashMap<>();
            for (String id : infected) {
                int startnode = peopleId.get(id);
                exposed.putAll(bfs(g, startnode, distance));                
            }
                
        System.out.println("Number of exposed individuals: " + exposed.size());
        } catch (IOException e) {
            System.err.println("Error reading in the graph: " + e.getMessage());
        }
        
        // Now process the information to get the results...
            // Use the Graph, infected list, and distance to get the result and print the number of
            // exposed individuals.
    }

    public static HashMap<Integer, Boolean> bfs(Graph g, int startNode, int maxDistance) {
        HashMap<Integer, Boolean> visited = new HashMap<>();
        Queue<int[]> queue = new LinkedList<>();

        queue.add(new int[]{startNode, 0});
        visited.put(startNode, true);

        while(!queue.isEmpty()) {
            int[] current = queue.poll();
            int node = current [0];
            int distance = current [1];

            if (distance >= maxDistance) {
                continue;
            }

            for (int neighbor : g.getAdjacent(node)) {
                if (!visited.containsKey(neighbor)) {
                    visited.put(neighbor, true);
                    queue.add(new int[]{neighbor, distance + 1});
                }
            }
        }


        return visited;
    }
}
