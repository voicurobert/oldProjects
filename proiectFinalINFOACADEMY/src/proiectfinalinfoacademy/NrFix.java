/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proiectfinalinfoacademy;

/**
 *
 * @author Robert
 */
public class NrFix extends NrTelefon{

    public NrFix(long numar) {
        super(numar);
    }

   public String toString(){
       return "0" + super.numar;
   }

   
    
    
}
