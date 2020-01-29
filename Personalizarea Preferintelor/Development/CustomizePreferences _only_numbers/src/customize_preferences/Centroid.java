/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customize_preferences;

import java.util.Random;

/**
 * Class that simulates a centroid. A centroid in our application is an object <br>
 * that has a vector of type double with 8 elements. <br>
 * Attributes : <br>
 * - vector   : attribute that contains those 8 elements.
 * @author Robert
 */
public class Centroid {
    private double[] vector = new double[8];
    
    /**
     * Empty constructor that will instantiate a new Centroid object.
    */
    public Centroid(){
        
    }
    /**
     * Getter method. <br>
     * @return the vector attribute.
    */
    public double[] getVector() {
        return vector;
    }
    /**
     * Setter method. <br>
     * @param vector is a vector of type double. It must have its elements set. <br>
    */
    public void setVector(double[] vector) {
        this.vector = vector;
    }
    
    /**
     * Method that calls a private method called computeCentroid(). <br>
    */
    protected void generateRandomData(){
        vector = computeCentroid();
    }
    /**
     * Private method that will generate a new vector of type double of length 8 with random values. <br>
     * This method calls the private method getRandomNumber() and will set a new value for our vector. <br>
     * @return the initialized vector with random values.
    */
    protected double[] computeCentroid(){
        double[] vector = new double[8];
        vector[0] = getRandomNumber(1, 10 );
        vector[1] = getRandomNumber(1, 10 );
        vector[2] = getRandomNumber(1, 10 );
        vector[3] = getRandomNumber(1, 10 );
        vector[4] = getRandomNumber(1, 10 );
        vector[5] = getRandomNumber(1, 10 );
        vector[6] = getRandomNumber(1, 10 );
        vector[7] = getRandomNumber(1, 10 );
        return vector;
    }
    
    /**
     * Private method that generates a random number of type double between two values. <br>
     * @param start integer value 
     * @param end integer value
     * @return the generated number between start and end values
    */
    protected double getRandomNumber( int start, int end ){
        double randomNumber = 0.0;
        Random rand = new Random();
        long range = ( long ) end - ( long ) start;
        long fraction = ( long ) ( range * rand.nextDouble() );
        randomNumber = fraction + start;
        return randomNumber;
    }
    /**
     * Override method that will return a string with the description of Centroid object.
    */
    public String toString(){
        return "Centroid values: " + " centroid[1] = " + this.vector[0] +
                " centroid[2] = " + this.vector[1] +
                " centroid[3] = " + this.vector[2] +
                " centroid[4] = " + this.vector[3] +
                " centroid[5] = " + this.vector[4] +
                " centroid[6] = " + this.vector[5] +
                " centroid[7] = " + this.vector[6] +
                " centroid[8] = " + this.vector[7];
    }
}
