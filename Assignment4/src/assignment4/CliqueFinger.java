/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment4;

import java.util.ArrayList;

/**
 *
 * @author Vorno
 */
//this is to find cliques in the graph
public class CliqueFinger {
    double[][] table;
    int n;
    ArrayList<String> cliques;
    double threshold;
    
    public CliqueFinger() {
        cliques = new ArrayList<>();
    }
    
    public ArrayList<String> findCliques(double[][] table, double threshold) {
        this.threshold = threshold;
        this.table = table;
        n = table[0].length;
        
        boolean[] R = new boolean[table[0].length];
        boolean[] P = new boolean[table[0].length];
        boolean[] X = new boolean[table[0].length];
        
        for(int i = 0; i < P.length; i++)
            P[i] = true;
        
        findCliquesRec(R, P, X);
        
        return cliques;
    }
    
    /*
        implemented using https://algosdotorg.wordpress.com/maximal-cliquesbronkerbosch-without-pivot-java/
        as a guide but tried to implement my own version using boolean arrays as efficiently as possible. 
    */
    public void findCliquesRec(boolean[] R, boolean[] P, boolean[] X) {
        if((isEmpty(P)) && (isEmpty(X))) {
            String current_clique = "";
            int counter = 0;
            
            for(int i = 0; i < R.length; i++) {
                if(R[i] == true) {
                    counter++;
                    if(current_clique.equals(""))
                        current_clique += i;
                    else 
                        current_clique += " : "+i;
                }
            }
            if(counter >= 2)
                cliques.add(current_clique);

            return;
        }
        
        for(int i = 0; i < P.length; i++) {
            if(P[i] == true) {
                R[i] = true;
                findCliquesRec(R, intersect(P, getNeighbors(i)), intersect(X, getNeighbors(i)));
                R[i] = false;
                P[i] = false;
                X[i] = true;
            }
        }
    }
    
    public boolean[] intersect(boolean[] array_1, boolean[] array_2) {
        boolean[] new_array = new boolean[array_1.length];
        
        for(int i = 0; i < array_1.length; i++) {
            if(array_1[i] == true && array_2[i] == true)
                new_array[i] = true;
        }
        
        return new_array;
    }
    
    public boolean[] union(boolean[] array_1, boolean[] array_2) {
        boolean[] new_array = new boolean[array_1.length];
        
        for(int i = 0; i < array_1.length; i++) {
            if(array_1[i] == true || array_2[i] == true)
                new_array[i] = true;
        }
        
        return new_array;
    }
    
    public boolean[] getNeighbors(int i) {
        boolean[] neighbors = new boolean[n];
        
        for(int j = 0; j < n; j++) {
            if(table[i][j] != 0) {
                if(table[i][j] >= threshold)
                    neighbors[j] = true;
            }
        }
            
        return neighbors;
    }
    
    public boolean isEmpty(boolean[] arr) {
        for(boolean i : arr)
            if(i == true) return false;
        
        return true;
    }
}