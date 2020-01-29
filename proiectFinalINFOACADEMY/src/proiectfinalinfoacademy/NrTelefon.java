/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proiectfinalinfoacademy;

/**
 *
 * @author Robert
 */
public abstract class NrTelefon {
    protected long numar;

    public NrTelefon(long numar) {
       try{
           if(numar == 0 || numar < 0){
               
           }else{
               this.numar = numar;
           }
       }catch(IllegalArgumentException e){
           System.out.println("Numar de telefon invalid");
       }
    
    }
    
    
    
    
}
