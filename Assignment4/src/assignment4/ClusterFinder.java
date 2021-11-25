/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Vorno
 */

//Not completed but was close. this clusters all the vertices in the graph
public class ClusterFinder {
    double[][] table;
    double[][] weighted_table;
    DisjointSet ds;
    AssociationFinder AF;
    
    public ClusterFinder(double[][] weighted_table, AssociationFinder AF) {
        ds = new DisjointSet();
        this.weighted_table = weighted_table;
        this.AF = AF;
    }
    
    public void findClusters(double[][] table) {
        this.table = table;
        
        int[] universe = new int[table[0].length];
        ds.makeSet(universe);
        
        ArrayList<Edge> weighted_graph = createWeightedGraph(table);
        System.out.println("Joining : "+weighted_graph.get(0).getConnectionTo()+" -> "+weighted_graph.get(0).getConnectionFrom());
        ds.Union(weighted_graph.get(0).getConnectionTo(), weighted_graph.get(0).getConnectionFrom());
        ArrayList<ArrayList<Integer>> clusters_pre = new ArrayList<>();
            
        for(int j = 0; j < table[0].length; j++) {
            ArrayList<Integer> temp = ds.inCluster(j);

            if(!temp.isEmpty())
                clusters_pre.add(temp);
        }
        
        System.out.println(clusters_pre);
        
        for(int i = 0; i < table[0].length-2; i++) {
            ArrayList<ArrayList<Integer>> clusters = new ArrayList<>();
            
            for(int j = 0; j < table[0].length; j++) {
                ArrayList<Integer> temp = ds.inCluster(j);
                
                if(!temp.isEmpty())
                    clusters.add(temp);
            }
            
            double[][] prox_matrix = new double[clusters.size()][clusters.size()];
            
            double lowest_dist = 0.0;
            
            int idxJ=-1, idxV=-1;
            
            for(int j = 0; j < clusters.size(); j++) {
                for(int v = j+1; v < clusters.size(); v++) {
                    double temp;
                    
                    if(clusters.get(j).size() > 1)
                        temp = distClusterToCluster(clusters.get(j), clusters.get(v))+longestDistance(clusters.get(j));
                    else if(clusters.get(v).size() > 1) 
                        temp = distClusterToCluster(clusters.get(j), clusters.get(v))+longestDistance(clusters.get(v));
                    else
                        temp = distClusterToCluster(clusters.get(j), clusters.get(v));
                    
                    prox_matrix[j][v] = temp;
                    prox_matrix[v][j] = temp;
                    
                    if(lowest_dist == 0.0) {
                        lowest_dist = temp;
                        idxJ = j;
                        idxV = v;
                    }
                    else if(temp < lowest_dist+longestDistance(clusters.get(v))) {
                        lowest_dist = temp;
                        idxJ = j;
                        idxV = v;
                    }
                }
            }
            int cluster1 = clusters.get(idxJ).get(0);
            int cluster2 = clusters.get(idxV).get(0);
            
            System.out.println("Joining : "+ds.Find(cluster1)+" -> "+ds.Find(cluster2));
            ds.Union(ds.Find(cluster1), ds.Find(cluster2));
            
            clusters = new ArrayList<>();
            
            for(int j = 0; j < table[0].length; j++) {
                ArrayList<Integer> temp = ds.inCluster(j);
                
                if(!temp.isEmpty())
                    clusters.add(temp);
            }
            
            System.out.println(clusters);
        }
        
    }
    
    public ArrayList<Edge> createWeightedGraph(double[][] table) {
        ArrayList<Edge> weighted_graph = new ArrayList<>();
        int idx = 1;
        for(int i = 0; i < table.length-1; i++) {
            for(int j = idx; j < table[0].length; j++) {
                if(table[i][j] != 0) {
                    Edge newEdge = new Edge(-Math.log(table[i][j]), i, j);
                    weighted_graph.add(newEdge);
                }
            }
            idx++;
        }
        
        Collections.sort(weighted_graph, new Comparator<Edge>() {
            public int compare(Edge o1, Edge o2) {
                return o1.compareTo(o2);
            }
        });
        
        return weighted_graph;
    }
    
    public double longestDistance(ArrayList<Integer> list) {
        double longestDist = 0.0;
        
        if(list.size() > 1) {
            for(int i = 0; i < list.size()-1; i++) {
                for(int j = i+1; j < list.size(); j++) {
                    double compare = 0;
                    if(list.get(i)-list.get(j) == 1 || list.get(i)-list.get(j) == -1)
                        compare = AF.findAssociation(weighted_table, list.get(i), list.get(j));
                    if(compare > longestDist)
                        longestDist = compare;
                }
            }
            
            return longestDist;
        }
        else return 0.0;
    }
    
    public double distClusterToCluster(ArrayList<Integer> cluster1, ArrayList<Integer> cluster2) {
        double dist = 0.0;
        for(int i = 0; i < cluster1.size(); i++) {
            for(int j = 0; j < cluster2.size(); j++) {
                double temp = distance(cluster1.get(i), cluster2.get(j));
                if(dist == 0.0)
                    dist = temp;
                else if(temp < dist) {
                    dist = temp;
                }
            }
        }
        
        return dist;
    }
    
    public double distance(int from, int to) {
        return AF.findAssociation(weighted_table, from, to);
    }
}
                                                                                        //1
/*                 Anna Bill Carl Dave Emma Fred               Anna   Bill   Carl   Dave   Emma   Fred   
            Anna   0:0  0:1  0:2  0:3  0:4  0:5         Anna   0.0    0.693  0.916  0.0    0.0    0.0    5 / 0
            Bill   1:0  1:1  1:2  1:3  1:4  1:5         Bill   0.693  0.0    0.0    0.916  0.0    0.0    4 / 1
            Carl   2:0  2:1  2:2  2:3  2:4  2:5         Carl   0.916  0.0    0.0    1.204  0.693  0.0    3 / 2
            Dave   3:0  3:1  3:2  3:3  3:4  3:5       [ Dave   0.0    0.916  1.204  0.0    0.223  0.0    2 / 3
            Emma   4:0  4:1  4:2  4:3  4:4  4:5      1[ Emma   0.0    0.0    0.693  0.223  0.0    0.357  1 / 4
            Fred   5:0  5:1  5:2  5:3  5:4  5:5         Fred   0.0    0.0    0.0    0.0    0.357  0.0    
*/

/*

        0       1       2       3       4
0       0       0.223   0.223   0       0
1       0.223   0       0       0.223   0
2       0.223   0       0       0.223   0
3       0.223   0.223   0.223   0.223   0.223
4       0       0       0       0.223   0

*/