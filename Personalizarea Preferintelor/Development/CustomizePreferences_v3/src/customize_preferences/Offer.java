/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customize_preferences;

import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Class that simulates an offer <br>
 * Attributes: <br>
 * - idOferta  : represents the id from the database of this Offer object. <br>
 * - criteriu1 : represents the value for Criteriu 1. <br>
 * - criteriu2 : represents the value for Criteriu 2. <br>
 * - criteriu3 : represents the value for Criteriu 3. <br>
 * - criteriu4 : represents the value for Criteriu 4. <br>
 * - criteriu5 : represents the value for Criteriu 5. <br>
 * - criteriu6 : represents the value for Criteriu 6. <br>
 * - criteriu7 : represents the value for Criteriu 7. <br>
 * - criteriu8 : represents the value for Criteriu 8. <br>
 * @author Robert
 */
public class Offer {
    
    private int idOferta;
    private String criteriu1;
    private String criteriu2;
    private String criteriu3;
    private double criteriu4;
    private double criteriu5;
    private double criteriu6;
    private double criteriu7;
    private double criteriu8;
    
    /**
     * Constructor - instantiates a new Offer object
     */
    public Offer(){
        
    }
    
    /**
     * Constructor - instantiates a new Offer object based on a Centroid object
     * @param centroid a centroid object
     */
    public Offer( Centroid centroid ){
        this.criteriu1 = centroid.getStringVector()[0];
        this.criteriu2 = centroid.getStringVector()[1];
        this.criteriu3 = centroid.getStringVector()[2] ;
        this.criteriu4 = roundValue( centroid.getNumericVector()[0] );
        this.criteriu5 = roundValue( centroid.getNumericVector()[1] );
        this.criteriu6 = roundValue( centroid.getNumericVector()[2] );
        this.criteriu7 = roundValue( centroid.getNumericVector()[3] );
        this.criteriu8 = roundValue( centroid.getNumericVector()[4] );
        
    }

    
    public void setIdOferta(int idOferta) {
        this.idOferta = idOferta;
    }

    public void setCriteriu1(String criteriu1) {
        this.criteriu1 = criteriu1;
    }

    public void setCriteriu2(String criteriu2) {
        this.criteriu2 = criteriu2;
    }

    public void setCriteriu3(String criteriu3) {
        this.criteriu3 = criteriu3;
    }

    public void setCriteriu4(double criteriu4) {
        this.criteriu4 = criteriu4;
    }

    public void setCriteriu5(double criteriu5) {
        this.criteriu5 = criteriu5;
    }

    public void setCriteriu6(double criteriu6) {
        this.criteriu6 = criteriu6;
    }

    public void setCriteriu7(double criteriu7) {
        this.criteriu7 = criteriu7;
    }

    public void setCriteriu8(double criteriu8) {
        this.criteriu8 = criteriu8;
    }

    public int getIdOferta() {
        return idOferta;
    }

    public String getCriteriu1() {
        return criteriu1;
   
    }

    public String getCriteriu2() {
        return criteriu2;
    }

    public String getCriteriu3() {
        return criteriu3;
    }

    public double getCriteriu4() {
        String value = Double.toString(criteriu4);
        if( value.endsWith("0")){
            return Integer.valueOf( value.split( Pattern.quote("."))[0]) ;
        }else{
            return criteriu4;
        }
    }

    public double getCriteriu5() {
        String value = Double.toString(criteriu5);
        if( value.endsWith("0")){
            return Integer.valueOf( value.split( Pattern.quote("."))[0]) ;
        }else{
            return criteriu5;
        }
    }

    public double getCriteriu6() {
        String value = Double.toString(criteriu6);
        
        if( value.endsWith("0")){
            return Integer.valueOf( value.split( Pattern.quote("."))[0]) ;
        }else{
            return criteriu6;
        }
    }

    public double getCriteriu7() {
        String value = Double.toString(criteriu7);
         
        if( value.endsWith("0")){
            return Integer.valueOf( value.split( Pattern.quote("."))[0]) ;
        }else{
            return criteriu7;
        }
    }

    public double getCriteriu8() {
        String value = Double.toString(criteriu8);
        if( value.endsWith("0")){
            return Integer.valueOf( value.split( Pattern.quote("."))[0]) ;
        }else{
            return criteriu8;
        }
    }
    
    /**
     * Rounds a double value into #.## format
     * @param value the double value that will be formated
     * @return the formated value
     */
    public final double roundValue( double value ){
        double newValue = 0.0;
        DecimalFormat df = new DecimalFormat("#.##");
        newValue = Double.valueOf( df.format( value ));
        return newValue;
    }
    
    /**
     * Override method that will return a description of this Client object. <br>
     * @return a string.
     */
    public String toString(){
        return "ID Client: " + String.valueOf( idOferta ) + " Criteriu 1: " + String.valueOf( criteriu1 ) +
                " Criteriu 2: " + String.valueOf( criteriu2 ) +
                " Criteriu 3: " + String.valueOf( criteriu3 ) + 
                " Criteriu 4: " + String.valueOf( criteriu4 ) + 
                " Criteriu 5: " + String.valueOf( criteriu5 ) + 
                " Criteriu 6: " + String.valueOf( criteriu6 ) + 
                " Criteriu 7: " + String.valueOf( criteriu7 ) + 
                " Criteriu 8: " + String.valueOf( criteriu8 );
    }
}
