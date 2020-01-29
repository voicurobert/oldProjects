/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customize_preferences;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Class that acks like a model for offers table from Provider interface. <br>
 * Attributes: <br>
 * - offers : a list of offers <br>
 * @author Robert
 */
public class OffersTableModel extends AbstractTableModel{
    protected List<Offer> offers;
    
    /**
     * Constructor - instantiates a new OffersTableModel
     */
    public OffersTableModel(){
        
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public List<Offer> getOffers() {
        return offers;
    }
    
    /**
     * Override method. Internally will set the table row count. <br>
     * @return the size of offers list.
     */
    @Override
    public int getRowCount() {
        return offers.size();
    }

    /**
     * Override method. Will set the table column count. <br>
     * @return 9, the number of columns.
     */
    @Override
    public int getColumnCount() {
         return 9;
    }

    /**
     * Override method. Will set the table cells values. <br>
     * @param rowIndex the row index starting from 0; <br>
     * @param columnIndex the column index; <br>
     * @return the value that will be set in cell(rowIndex, columnIndex)
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
       Offer offer = offers.get(rowIndex);
       switch( columnIndex ){
           case 0 : return offer.getIdOferta();
           case 1 : return offer.getCriteriu1();
           case 2 : return offer.getCriteriu2();
           case 3 : return offer.getCriteriu3();
           case 4 : return offer.getCriteriu4();
           case 5 : return offer.getCriteriu5();
           case 6 : return offer.getCriteriu6();
           case 7 : return offer.getCriteriu7();
           case 8 : return offer.getCriteriu8();
           default : return offer.toString();
       }
    }
    
    /**
     * Override method. Will set the column names after table refresh. <br>
     * @param column the column index 
     * @return the column names 
     */
    @Override
    public String getColumnName(int column) {
        return new String[]{
            "Oferta",
            "Criteriu 1",
            "Criteriu 2",
            "Criteriu 3",
            "Criteriu 4",
            "Criteriu 5",
            "Criteriu 6",
            "Criteriu 7",
            "Criteriu 8"}[column];
    }
    
}
