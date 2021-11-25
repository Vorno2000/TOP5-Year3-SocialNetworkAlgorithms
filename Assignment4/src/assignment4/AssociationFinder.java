/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment4;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vorno
 */
//A summary can be found in AT
public class AssociationFinder<T> {
    static final int NO_PARENT = -1;
    static ArrayList<Integer> path = new ArrayList<>();
    static ArrayList<Double> distance = new ArrayList<>();
    
    public AssociationFinder() {
        
    }
    //Utilizes Dijkstras Algorithm to locate the shortest path and 
    //distance from one vertex to another
    public double findAssociation(double[][] weighted_table, int from, int to) {
        int n = weighted_table[0].length;
        double[] shortest_dist = new double[n];
        boolean[] visited = new boolean[n];
        int[] parents = new int[n];
        
        for(int i = 0; i < n; i++) {
            shortest_dist[i] = Double.MAX_VALUE;
            visited[i] = false;
        }
        
        shortest_dist[from] = 0;
        parents[from] = NO_PARENT;
        
        for(int i = 1; i < n; i++) {
            int pre = -1;
            double min = Double.MAX_VALUE;
            
            for(int v = 0; v < n; v++) {
                if(!visited[v] && shortest_dist[v] < min) {
                    pre = v;
                    min = shortest_dist[v];
                }
            }
            visited[pre] = true;
            for(int v = 0; v < n; v++) {
                double dist = weighted_table[pre][v];
                
                if(dist > 0 && ((min + dist) < shortest_dist[v])) {
                    parents[v] = pre;
                    shortest_dist[v] = min + dist;
                }
            }
        }
        distance.add(shortest_dist[to]);
        addPath(to, parents);
        
        return shortest_dist[to];
    }
    
    public void addPath(int i, int[] parents) {
        if(i == NO_PARENT)
            return;
        addPath(parents[i], parents);
        path.add(i);
    }
    
    public List<Double> getDist() {
        return distance;
    }
    
    public List<Integer> getPath() {
        return path;
    }
}

/*                 Anna Bill Carl Dave Emma Fred               Anna Bill Carl Dave Emma Fred
            Anna   0:0  0:1  0:2  0:3  0:4  0:5         Anna    0   0.5  0.4   0    0    0
            Bill   1:0  1:1  1:2  1:3  1:4  1:5         Bill   0.5   0    0   0.4   0    0
            Carl   2:0  2:1  2:2  2:3  2:4  2:5         Carl   0.4   0    0   0.3  0.5   0
            Dave   3:0  3:1  3:2  3:3  3:4  3:5         Dave    0   0.4  0.3   0   0.8   0
            Emma   4:0  4:1  4:2  4:3  4:4  4:5         Emma    0    0   0.5  0.8   0   0.7
            Fred   5:0  5:1  5:2  5:3  5:4  5:5         Fred    0    0    0    0   0.7   0
*/