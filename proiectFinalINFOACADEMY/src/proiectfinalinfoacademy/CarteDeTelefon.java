/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proiectfinalinfoacademy;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.JOptionPane;

import javax.swing.table.AbstractTableModel;


/**
 *
 * @author Robert
 */
public class CarteDeTelefon extends AbstractTableModel {
    
    protected List<Abonat> lista;

    public CarteDeTelefon(List<Abonat> lista) {
        this.lista = lista;
    }

    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Abonat a = lista.get(rowIndex);
        switch(columnIndex){
            case 0 : return a.getNume();
            case 1 : return a.getPrenume();
            case 2: return  a.getCnp();
            case 3: return a.getNrTel();
            default : return a.toString();
                
        }
        
    }

    @Override
    public String getColumnName(int column) {
        return new String[]{"Nume","Prenume","CNP","Nr Telefon"}[column];
    }
    public void ordoneaza(Criterii c){
        switch(c){
            case Dupa_Nume : 
                Collections.sort(lista, new Comparator<Abonat>(){

            @Override
            public int compare(Abonat o1, Abonat o2) {
                return o1.getNume().compareToIgnoreCase(o2.getNume());
            }
                } );
                break;
            case Dupa_Prenume : 
                Collections.sort( lista, new Comparator<Abonat>(){
            @Override
            public int compare(Abonat o1, Abonat o2) {
                return o1.getPrenume().compareToIgnoreCase(o2.getPrenume());
            }
            
                    
                });
                break;
            case Dupa_Cnp : 
                Collections.sort( lista, new Comparator<Abonat>(){

            @Override
            public int compare( Abonat o1, Abonat o2 ) {
                return (int)(o1.getCnp() - o2.getCnp());
            }
                
            });
                break;
            case Dupa_NrTel :
                Collections.sort( lista, new Comparator<Abonat>(){

            @Override
            public int compare(Abonat o1, Abonat o2) {
                return (int)(o1.getNrTel().hashCode() - o2.getNrTel().hashCode());       
            }
                } );
                break;       
        }
        fireTableDataChanged();
    }
}