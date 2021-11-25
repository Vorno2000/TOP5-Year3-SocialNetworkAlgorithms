/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Vorno
 */

//https://www.techiedelight.com/disjoint-set-data-structure-union-find-algorithm/
public class DisjointSet {
    private Map<Integer, Integer> parent = new HashMap();
 
    // stores the depth of trees
    private Map<Integer, Integer> rank = new HashMap();
 
    // perform MakeSet operation
    public void makeSet(int[] universe)
    {
        // create `n` disjoint sets (one for each item)
        for (int i = 0; i < universe.length; i++)
        {
            parent.put(i, i);
            rank.put(i, 0);
        }
    }
 
    // Find the root of the set in which element `k` belongs
    public int Find(int k)
    {
        // if `k` is not the root
        if (parent.get(k) != k)
        {
            // path compression
            parent.put(k, Find(parent.get(k)));
        }
 
        return parent.get(k);
    }
 
    // Perform Union of two subsets
    public void Union(int a, int b)
    {
        // find the root of the sets in which elements
        // `x` and `y` belongs
        int x = Find(a);
        int y = Find(b);
 
        // if `x` and `y` are present in the same set
        if (x == y) {
            return;
        }
 
        // Always attach a smaller depth tree under the
        // root of the deeper tree.
        if (rank.get(x) > rank.get(y)) {
            parent.put(y, x);
        }
        else if (rank.get(x) < rank.get(y)) {
            parent.put(x, y);
        }
        else {
            parent.put(x, y);
            rank.put(y, rank.get(y) + 1);
        }
    }
    
    public ArrayList<Integer> inCluster(int k) {
        ArrayList<Integer> inCluster = new ArrayList<>();
        for(int i = 0; i < parent.size(); i++) {
            if(Find(parent.get(i)) == k)
                inCluster.add(i);
        }
        
        return inCluster;
    }
}
