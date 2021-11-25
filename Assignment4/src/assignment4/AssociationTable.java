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
//Association Table is the base class that calls all other classes
//the main method creates AT which allows it to call all other classes through this one
public class AssociationTable {
    double[][] table;
    double[][] weighted_table;
    ArrayList<Vertex> vertices;
    AssociationFinder AF;
    
    public AssociationTable() {
        table = new double[0][0];
        weighted_table = new double[0][0];
        vertices = new ArrayList<>();
        AF = new AssociationFinder();
    }
    
    public AssociationTable(double[][] table, ArrayList<Vertex> vertices) {
        this.vertices = vertices;
        this.table = table;
        weighted_table = new double[table[0].length][table[0].length];
        setWeightedTable();
        AF = new AssociationFinder();
    }
    
    public double[][] getTable() {
        return table;
    }
    
    public void setTable(double[][] table) {
        this.table = table;
    }
    
    public final void setWeightedTable() {
        for(int i = 0; i < table[0].length; i++) {
            for(int j = 0; j < table[0].length; j++) {
                weighted_table[i][j] = table[i][j];
                
                if(weighted_table[i][j] != 0) {
                    weighted_table[i][j] = (-Math.log(table[i][j]));
                    weighted_table[i][j] = (Math.round(weighted_table[i][j] * 1000d)/1000d);
                }
            }
        }
    }
    //this finds the association between 2 vertices
    //this shows the path, the strength of the path and the distance
    public void findAssociation(Vertex from, Vertex to) {
        AF.findAssociation(weighted_table, vertices.indexOf(from), vertices.indexOf(to));
        
        List<Integer> path = AF.getPath();
        for(int i = 0; i < path.size(); i++) {
            System.out.print(vertices.get(path.get(i)).getData());
            if(i < path.size()-1)
                System.out.print(" > ");
            else
                System.out.println("");
        }
        
        List<Double> dist = AF.getDist();
        System.out.println("Shortest Length: "+(Math.round(dist.get(0)*1000d)/1000d));
        
        System.out.println("Strength: "+(Math.round(Math.exp(dist.get(0))*1000d)/1000d));
    }
    
    //this finds the cliques and maximal cliques in the table
    public void findCliques(double threshold) {
        CliqueFinger CF = new CliqueFinger();
        
        ArrayList<int[]> maximum_clique = new ArrayList<>();
        
        ArrayList<String> cliques_str = CF.findCliques(table, threshold);
        ArrayList<int[]> cliques = new ArrayList<>();
        
        for(int i = 0; i < cliques_str.size(); i++) {
            String clique = cliques_str.get(i);
            String[] clique_strArr = clique.split(" : ");
            int[] clique_vertices = new int[clique_strArr.length];
            
            for(int j = 0; j < clique_strArr.length; j++) {
                clique_vertices[j] = Integer.parseInt(clique_strArr[j]);
            }
            
            cliques.add(clique_vertices);
        }
        
        for(int i = 0; i < cliques.size(); i++) {
            if(maximum_clique.isEmpty()) {
                maximum_clique.add(cliques.get(i));
            }
            else if(maximum_clique.get(0).length < cliques.get(i).length) {
                int n = maximum_clique.size();
                for(int j = 0; j < n; j++)
                    maximum_clique.remove(0);
                
                maximum_clique.add(cliques.get(i));
            }
            else if(maximum_clique.get(0).length == cliques.get(i).length) {
                maximum_clique.add(cliques.get(i));
            }
        }
        
        System.out.println("\nMaximal Cliques: ");
        for(int i = 0; i < maximum_clique.size(); i++) {
            int[] clique = maximum_clique.get(i);
            
            for(int j = 0; j < clique.length; j++)
                System.out.print(vertices.get(clique[j]).getData()+" ");
            
            System.out.println("");
        }
        System.out.println("");
        
        System.out.println("All Cliques: ");
        for(int i = 0; i < cliques.size(); i++) {
            int[] curr_clique = cliques.get(i);
            
            for(int j = 0; j < curr_clique.length; j++)
                System.out.print(vertices.get(curr_clique[j]).getData()+" ");
            
            System.out.println("");
        }
    }
    
    //although not fully implemented, this is to find the clusters
    public void findClusters() {
        ClusterFinder CF = new ClusterFinder(weighted_table, AF);
        CF.findClusters(table);
    }
    
    //this is to print the n x n table
    public void printTable() {
        System.out.println("Print optimized for name example");
        for(int i = 0; i < vertices.size(); i++) {
            if(i == 0)
                System.out.print("\n       ");
            System.out.print(vertices.get(i).getData()+" ");
        }
        
        for(int i = 0; i < vertices.size(); i++) {
            for(int j = 0; j < vertices.size(); j++) {
                if(j == 0)
                    System.out.print("\n"+vertices.get(i).getData()+"  ");
                System.out.print("  "+table[i][j]);
            }
        }
        System.out.println("");
    }
    
    //this is to print the weighted table or the table that has distance instead of strength
    public void printWeightedTable() {
        System.out.println("\nPrint optimized for name example");
        for(int i = 0; i < vertices.size(); i++) {
            if(i == 0)
                System.out.print("\n       ");
            System.out.print(vertices.get(i).getData()+"   ");
        }
        
        for(int i = 0; i < vertices.size(); i++) {
            for(int j = 0; j < vertices.size(); j++) {
                if(j == 0)
                    System.out.print("\n"+vertices.get(i).getData()+"   ");
                if(weighted_table[i][j] == 0.0)
                    System.out.print(weighted_table[i][j]+"    ");
                else
                    System.out.print(weighted_table[i][j]+"  ");
            }
        }
        System.out.println("\n");
    }
}

/*                 Anna Bill Carl Dave Emma Fred        
            Anna   0:0  0:1  0:2  0:3  0:4  0:5
            Bill   1:0  1:1  1:2  1:3  1:4  1:5
            Carl   2:0  2:1  2:2  2:3  2:4  2:5
            Dave   3:0  3:1  3:2  3:3  3:4  3:5
            Emma   4:0  4:1  4:2  4:3  4:4  4:5
            Fred   5:0  5:1  5:2  5:3  5:4  5:5
*/