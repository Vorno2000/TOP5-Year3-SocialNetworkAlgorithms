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
//vertex class
public class Vertex<T> {
    private final T data;
    
    public Vertex(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
