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
    private double[] numericVector = new double[5];
    private String[] stringVector = new String[3];
    
    /**
     * Empty constructor that will instantiate a new Centroid object.
    */
    public Centroid(){
        
    }
    /**
     * Getter method. <br>
     * @return the numeic vector attribute.
    */
    public double[] getNumericVector() {
        return numericVector;
    }

    /**
     * Getter method <br>
     * @return the string vector attribute
     */
    public String[] getStringVector() {
        return stringVector;
    }
    
    
    /**
     * Setter method. <br>
     * @param vector is a vector of type double. It must have its elements set. <br>
    */
    public void setNumericVector(double[] vector) {
        this.numericVector = vector;
    }
   
    /**
     * Setter method
     * @param stringVector is a vector of strings 
     */
    public void setStringVector(String[] stringVector) {
        this.stringVector = stringVector;
    }
    
    
    /**
     * Method that calls a private method called computeCentroid(). <br>
    */
    protected void generateRandomData(){
        generateNumericData();
        generateStringData();
        
    }
    
    /**
     * Generates numeric random data
     */
    private void generateNumericData(){
        numericVector = computeNumericCentroid();
    }
    
    /**
     * Generates string data
     */
    private void generateStringData(){
        stringVector = computeStringCentroid();
    }
    
    /**
     * Private method that will generate a new vector of type double of length 8 with random values. <br>
     * This method calls the private method getRandomNumber() and will set a new value for our vector. <br>
     * @return the initialized vector with random values.
    */
    protected double[] computeNumericCentroid(){
        double[] vector = new double[5];
        vector[0] = getRandomNumber(1, 10 );
        vector[1] = getRandomNumber(1, 10 );
        vector[2] = getRandomNumber(1, 10 );
        vector[3] = getRandomNumber(1, 10 );
        vector[4] = getRandomNumber(1, 10 );
        return vector;
    }
    
    protected String[] computeStringCentroid(){
        String[] values = new String[3];
        int random1 = (int)getRandomNumber(1, StringValues.instance.getSetValoriCriteriu1().length );
        int random2 = (int)getRandomNumber(1, StringValues.instance.getSetValoriCriteriu2().length );
        int random3 = (int)getRandomNumber(1, StringValues.instance.getSetValoriCriteriu3().length );
        values[0] = StringValues.instance.getSetValoriCriteriu1()[random1];
        values[1] = StringValues.instance.getSetValoriCriteriu2()[random2];
        values[2] = StringValues.instance.getSetValoriCriteriu3()[random3];
        return values;
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
        return "Centroid values: " + " centroid[1] = " + this.stringVector[0] +
                " centroid[2] = " + this.stringVector[1] +
                " centroid[3] = " + this.stringVector[2] +
                " centroid[4] = " + this.numericVector[0] +
                " centroid[5] = " + this.numericVector[1] +
                " centroid[6] = " + this.numericVector[2] +
                " centroid[7] = " + this.numericVector[3] +
                " centroid[8] = " + this.numericVector[4];
    }
}
