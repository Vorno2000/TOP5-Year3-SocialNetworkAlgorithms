/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment4;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Vorno
 */
public class Main {
    static Scanner input = new Scanner(System.in);
    
    public static void main(String[] args) {
        initialize init = new initialize();
    }
    //initializes all the questions and inputs. Then creates example and calls
    //all the projects methods
    static class initialize {
        public initialize() {
            boolean isValid = false;
            
            System.out.println("Which example would you like to use?(enter 1 or 2)\n(1)Example from discussion board (using names)\n(2)Made up example (using integers)");
            
            while(!isValid) {
                try {
                    String temp_entry = input.nextLine();
                    int user_entry = Integer.parseInt(temp_entry);
                    
                    switch(user_entry) {
                        case 1:
                            initFirst();
                            isValid = true;
                            break;
                        case 2:
                            initSecond();
                            isValid = true;
                            break;
                        default:
                            System.err.println("Enter 1 or 2");
                    }
                } catch(NumberFormatException ex) {
                    System.err.println("Enter 1 or 2");
                }
            }
        }
        
        //this is to initialize the discussion boards example
        public void initFirst() {
            ArrayList<Vertex> vertices = new ArrayList<Vertex>();
            Edge[] edges = new Edge[7];

            Vertex anna = new Vertex("Anna");
            Vertex bill = new Vertex("Bill");
            Vertex carl = new Vertex("Carl");
            Vertex dave = new Vertex("Dave");
            Vertex emma = new Vertex("Emma");
            Vertex fred = new Vertex("Fred");

            vertices.add(anna);
            vertices.add(bill);
            vertices.add(carl);
            vertices.add(dave);
            vertices.add(emma);
            vertices.add(fred);

            Edge annaToBill = new Edge(0.5, vertices.indexOf(anna), vertices.indexOf(bill));
            Edge annaToCarl = new Edge(0.4, vertices.indexOf(anna), vertices.indexOf(carl));
            Edge billToDave = new Edge(0.4, vertices.indexOf(bill), vertices.indexOf(dave));
            Edge carlToDave = new Edge(0.3, vertices.indexOf(carl), vertices.indexOf(dave));
            Edge carlToEmma = new Edge(0.5, vertices.indexOf(carl), vertices.indexOf(emma));
            Edge daveToEmma = new Edge(0.8, vertices.indexOf(dave), vertices.indexOf(emma));
            Edge emmaToFred = new Edge(0.7, vertices.indexOf(emma), vertices.indexOf(fred));

            edges[0] = annaToBill;
            edges[1] = annaToCarl;
            edges[2] = billToDave;
            edges[3] = carlToDave;
            edges[4] = carlToEmma;
            edges[5] = daveToEmma;
            edges[6] = emmaToFred;
            
            double[][] table = new double[6][6];
        
            for(int i = 0; i < edges.length; i++) {
                table[edges[i].getConnectionFrom()][edges[i].getConnectionTo()] = edges[i].getStrength();
                table[edges[i].getConnectionTo()][edges[i].getConnectionFrom()] = edges[i].getStrength();
            }
            
            start(table, vertices);
        }
        
        public void initSecond() {
            ArrayList<Vertex> vertices = new ArrayList<>();
            Edge[] edges = new Edge[12];
            
            Vertex one = new Vertex(1);
            Vertex two = new Vertex(2);
            Vertex three = new Vertex(3);
            Vertex four = new Vertex(4);
            Vertex five = new Vertex(5);
            Vertex six = new Vertex(6);
            Vertex seven = new Vertex(7);
            Vertex eight = new Vertex(8);
            
            vertices.add(one);
            vertices.add(two);
            vertices.add(three);
            vertices.add(four);
            vertices.add(five);
            vertices.add(six);
            vertices.add(seven);
            vertices.add(eight);
            
            Edge oneToTwo = new Edge(0.5, 0, 1);
            Edge oneToFive = new Edge(0.7, 0, 4);
            Edge twoToFive = new Edge(0.35, 1, 4);
            Edge twoToFour = new Edge(0.82, 1, 3);
            Edge fourToFive = new Edge(0.635, 3, 4);
            Edge twoToThree = new Edge(0.655, 1, 2);
            Edge threeToFour = new Edge(0.85, 2, 3);
            Edge threeToSeven = new Edge(0.7, 2, 6);
            Edge fiveToSeven = new Edge(0.9, 4, 6);
            Edge sixToSeven = new Edge(0.4, 5, 6);
            Edge sevenToEight = new Edge(0.275, 6, 7);
            Edge sixToEight = new Edge(0.37, 5, 7);
            
            edges[0] = oneToTwo;
            edges[1] = oneToFive;
            edges[2] = twoToFive;
            edges[3] = twoToFour;
            edges[4] = fourToFive;
            edges[5] = twoToThree;
            edges[6] = threeToFour;
            edges[7] = threeToSeven;
            edges[8] = fiveToSeven;
            edges[9] = sixToSeven;
            edges[10] = sevenToEight;
            edges[11] = sixToEight;
            
            double[][] table = new double[8][8];
            
            for(int i = 0; i < edges.length; i++) {
                table[edges[i].getConnectionFrom()][edges[i].getConnectionTo()] = edges[i].getStrength();
                table[edges[i].getConnectionTo()][edges[i].getConnectionFrom()] = edges[i].getStrength();
            }
            
            start(table, vertices);
        }
        
        public void start(double[][] table, ArrayList<Vertex> vertices) {
            AssociationTable AT = new AssociationTable(table, vertices);
            AT.printTable();
            AT.printWeightedTable();
        
            boolean isValid = false;
            
            while(!isValid) {
                System.out.println("Would you like to: \n(1)Find association between vertices\n(2)Find cliques\n(3)Find Clusters");
                try {
                    String temp_input = input.nextLine();
                    int user_input = Integer.parseInt(temp_input);
                    
                    if(user_input == 1) {
                        boolean selectionValid = false;
                        System.out.println("Select 2 from the given list by entering the number (x):");
                        for(int i = 0; i < vertices.size(); i++) {
                            System.out.println(vertices.get(i).getData()+"("+i+") ");
                        }
                        while(!selectionValid) {
                            try {
                                System.out.println("From: ");
                                int user_selection1 = input.nextInt();
                                System.out.println("To: ");
                                int user_selection2 = input.nextInt();
                                
                                if(user_selection1 < 0 || user_selection1 > vertices.size()-1)
                                    throw new Exception();
                                else if(user_selection2 < 0 || user_selection2 > vertices.size()-1)
                                    throw new Exception();
                                else
                                    selectionValid = true;
                                
                                System.out.println("Calculating Optimal Association Path...");
                                AT.findAssociation(vertices.get(user_selection1), vertices.get(user_selection2));
                            }catch(Exception e) {
                                System.err.println("Please enter the index of your selection (x)");
                                input.nextLine();
                            }
                        }
                        
                        isValid = true;
                    }
                    else if(user_input == 2) {
                        boolean validInput = false;
                        double threshold = 0.0;
                        
                        System.out.println("Please specify a threshold: ");
                        while(!validInput) {
                            try {
                                String threshold_str = input.nextLine();
                                threshold = Double.parseDouble(threshold_str);
                                
                                AT.findCliques(threshold);
                                
                                validInput = true;
                            } catch(NumberFormatException e) {
                                System.err.println("Please specify a threshold (eg: 0.5)");
                            }
                        }
                        
                        isValid = true;
                    }
                    else if(user_input == 3) {
                        AT.findClusters();
                        
                        isValid = true;
                    }
                    
                }catch (NumberFormatException ex) {
                    System.err.println("Please choose 1 or 2");
                }
            }
        }
    }
}
