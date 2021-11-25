/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment4;

/**
 *
 * @author Vorno
 */
//edge class
public class Edge implements Comparable<Edge> {
    double strength;
    int connectionFrom;
    int connectionTo;

    public Edge() {
        
    }
    
    public Edge(double strength, int connectionFrom, int connectionTo) {
        this.strength = strength;
        this.connectionFrom = connectionFrom;
        this.connectionTo = connectionTo;
    }
    
    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    public int getConnectionFrom() {
        return connectionFrom;
    }

    public void setConnectionFrom(int connectionFrom) {
        this.connectionFrom = connectionFrom;
    }

    public int getConnectionTo() {
        return connectionTo;
    }

    public void setConnectionTo(int connectionTo) {
        this.connectionTo = connectionTo;
    }

    @Override
    public int compareTo(Edge o) {
        if(this.getStrength() < o.getStrength())
            return -1;
        else if(this.getStrength() > o.getStrength())
            return 1;
        else return 0;
    }
}
