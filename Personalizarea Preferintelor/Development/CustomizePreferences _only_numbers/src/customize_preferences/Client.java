/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customize_preferences;

/**
 * Class that simulates a client personalized preferences. <br>
 * Attributes  : <br>
 * - idClient  : represents the id from the database of this Client object. <br>
 * - criteriu1 : represents the value for Criteriu 1. <br>
 * - criteriu2 : represents the value for Criteriu 2. <br>
 * - criteriu3 : represents the value for Criteriu 3. <br>
 * - criteriu4 : represents the value for Criteriu 4. <br>
 * - criteriu5 : represents the value for Criteriu 5. <br>
 * - criteriu6 : represents the value for Criteriu 6. <br>
 * - criteriu7 : represents the value for Criteriu 7. <br>
 * - criteriu8 : represents the value for Criteriu 8. <br>
 * - idOferta  : reprezents the offer id from the database that is allocated to this Client object <br>
 * @author Robert
 */
public class Client {
    
    private int idClient;
    private double criteriu1;
    private double criteriu2;
    private double criteriu3;
    private double criteriu4;
    private double criteriu5;
    private double criteriu6;
    private double criteriu7;
    private double criteriu8;
    private int idOferta;

    /**
     * Empty constructor that will instantiate a new Client object.
     */
    public Client() {
    }
    /**
     * Setter method. Will set idClient attribute. <br>
     * @param idClient integer value.
     */
    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }
    /**
     * Setter method. Will set idOferta attribute. <br>
     * @param idOferta integer value.
     */
    public void setIdOferta(int idOferta) {
        this.idOferta = idOferta;
    }
    /**
     * Setter method. Will set criteriu1 attribute. <br>
     * @param criteriu1 double value;
     */
    public void setCriteriu1(double criteriu1) {
        this.criteriu1 = criteriu1;
    }
    /**
     * Setter method. Will set criteriu2 attribute. <br>
     * @param criteriu2 double value;
     */
    public void setCriteriu2(double criteriu2) {
        this.criteriu2 = criteriu2;
    }
    /**
     * Setter method. Will set criteriu3 attribute. <br>
     * @param criteriu3 double value;
     */
    public void setCriteriu3(double criteriu3) {
        this.criteriu3 = criteriu3;
    }
    /**
     * Setter method. Will set criteriu4 attribute. <br>
     * @param criteriu4 double value;
     */
    public void setCriteriu4(double criteriu4) {
        this.criteriu4 = criteriu4;
    }
    /**
     * Setter method. Will set criteriu5 attribute. <br>
     * @param criteriu5 double value;
     */
    public void setCriteriu5(double criteriu5) {
        this.criteriu5 = criteriu5;
    }
    /**
     * Setter method. Will set criteriu6 attribute. <br>
     * @param criteriu6 double value;
     */
    public void setCriteriu6(double criteriu6) {
        this.criteriu6 = criteriu6;
    }
    /**
     * Setter method. Will set criteriu7 attribute. <br>
     * @param criteriu7 double value;
     */
    public void setCriteriu7(double criteriu7) {
        this.criteriu7 = criteriu7;
    }
    /**
     * Setter method. Will set criteriu8 attribute. <br>
     * @param criteriu8 double value;
     */
    public void setCriteriu8(double criteriu8) {
        this.criteriu8 = criteriu8;
    }
    
    /**
     * Getter method. <br>
     * @return client id value.
     */
    public int getIdClient() {
        return idClient;
    }
    /**
     * Getter method. <br>
     * @return oferta id value.
     */
    public int getIdOferta() {
        return idOferta;
    }
    /**
     * Getter method. <br>
     * @return criteriu1 value.
     */
    public double getCriteriu1() {
        return criteriu1;
    }
    /**
     * Getter method. <br>
     * @return criteriu2 value.
     */
    public double getCriteriu2() {
        return criteriu2;
    }
    /**
     * Getter method. <br>
     * @return criteriu3 value.
     */
    public double getCriteriu3() {
        return criteriu3;
    }
    /**
     * Getter method. <br>
     * @return criteriu4 value.
     */
    public double getCriteriu4() {
        return criteriu4;
    }
    /**
     * Getter method. <br>
     * @return criteriu5 value.
     */
    public double getCriteriu5() {
        return criteriu5;
    }
    /**
     * Getter method. <br>
     * @return criteriu6 value.
     */
    public double getCriteriu6() {
        return criteriu6;
    }
    /**
     * Getter method. <br>
     * @return criteriu7 value.
     */
    public double getCriteriu7() {
        return criteriu7;
    }
    /**
     * Getter method. <br>
     * @return criteriu8 value.
     */
    public double getCriteriu8() {
        return criteriu8;
    }
    
    /**
     * Method that will compute, from the values of the criteria, a vector of type double with 8 elements. <br>
     * @return an 8 element vector of type double.
     */
    public double[] getVectorValues(){
        double[] values = new double[8];
        values[0] = (double) criteriu1;
        values[1] = (double) criteriu2;
        values[2] = (double) criteriu3;
        values[3] = (double) criteriu4;
        values[4] = (double) criteriu5;
        values[5] = (double) criteriu6;
        values[6] = (double) criteriu7;
        values[7] = (double) criteriu8;
        return values;
    }
    
    /**
     * Override method that will return a description of this Client object. <br>
     * @return a string.
     */
    public String toString(){
        return "ID Client: " + String.valueOf( idClient ) + " Criteriu 1: " + String.valueOf( criteriu1 ) +
                " Criteriu 2: " + String.valueOf( criteriu2 ) +
                " Criteriu 3: " + String.valueOf( criteriu3 ) + 
                " Criteriu 4: " + String.valueOf( criteriu4 ) + 
                " Criteriu 5: " + String.valueOf( criteriu5 ) + 
                " Criteriu 6: " + String.valueOf( criteriu6 ) + 
                " Criteriu 7: " + String.valueOf( criteriu7 ) + 
                " Criteriu 8: " + String.valueOf( criteriu8 ) + 
                " ID Oferta: " + String.valueOf( idOferta ) ;
    }
    
}
