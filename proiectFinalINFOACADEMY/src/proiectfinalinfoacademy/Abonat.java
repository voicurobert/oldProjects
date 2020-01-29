/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proiectfinalinfoacademy;

import javax.swing.JOptionPane;

/**
 *
 * @author Robert
 */
public class Abonat {
    private String nume;
    private String prenume;
    private long cnp;
    private NrTelefon nrTel;

    public Abonat(String nume, String prenume, long cnp, NrTelefon nrTel) {
        if(nume.length() == 0 || nume.matches("[a-zA-Z]")){
            throw new IllegalArgumentException("nume invalid");
            
        }else if(prenume.length() == 0 || prenume.matches("[a-zA-Z]")){
            throw new IllegalArgumentException("prenume invalid");
        }else if(cnp < 0 ){
            throw new IllegalArgumentException("cnp invalid");
        }else if(nrTel.equals(null)){
            throw new IllegalArgumentException("numar de telefon invalid");
        }else{
            this.nume = nume;
            this.prenume = prenume;
            this.cnp = cnp;
            this.nrTel = nrTel;
        }
            
        
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public long getCnp() {
        return cnp;
    }

    public void setCnp(long cnp) {
        this.cnp = cnp;
    }

    public NrTelefon getNrTel() {
        return nrTel;
    }

    public void setNrTel(NrTelefon nrTel) {
        this.nrTel = nrTel;
    }
    
    public String toString(){
        String rezultat = nume + prenume + cnp;
        return rezultat;
    }

    @Override
    public boolean equals(Object obj) {
        Abonat a = (Abonat) obj;
        if(a.cnp == this.cnp){
            return true;
        }else{
            return false;
        }
            
    }
    
    
    
    
    
    
}
