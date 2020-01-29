/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customize_preferences;

import java.util.List;
import java.util.Map;
import javax.swing.table.AbstractTableModel;

/**
 * Class that acts like a model for allocation table from Provider interface. <br>
 * Attributes : <br>
 * - offers   : A list of offers that will be shown in the table <br>
 * @author Robert
 */
public class AllocationTableModel extends AbstractTableModel{  
    protected List<Offer> offers;
    
    /**
    * Instantiates a new AllocationTableModel object.
    */
    public AllocationTableModel() {
    }
    
    /**
    * Setter for offers attribute. <br>
    * @param offers a list of offers.
    */
    public void setOffers( List<Offer> offers ) {
        this.offers = offers;
    }
    /**
    * Getter that will return the objects offers. <br>
    * @return the offers list
    */
    public List<Offer> getOffers() {
        return offers;
    }
    
    /**
    * Override the parent method. Internally Will set the table row count. <br>
    * @return the size of offers list.
    */
    @Override
    public int getRowCount() {
        return offers.size();
    }
    /**
    * Override the parent method. Will set the table column count. <br>
    * @return 2 the number of column in the table.
    */
    @Override
    public int getColumnCount() {
        return 2;
    }
    /**
    * Override parent method. Will set the values of the table. In each cell( rowIndex, columnIndex ) it will set the value.
    * @param rowIndex the row index of the table <br>
    * @param columnIndex the column index of the table <br>
    * @return for each pair, row and column, will return the value that will be shown in that cell. <br>
    */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        List<Client> clients = DatabaseManager.instance.getClientsByOfferId(rowIndex + 1 );
        switch( columnIndex ){
           case 0 : return offers.get(rowIndex).getIdOferta();
           case 1 : return getClientsListAsString( clients );
           default : return "Empty";
       }
    }
    /**
    * Override parent method. Will set the column name when the table will be refreshed. <br>
    * @param column the column index 
    * @return "Oferta" and "Client/Clients" as column names.
    */
    @Override
    public String getColumnName(int column) {
        return new String[]{
            "Oferta",
            "Client/Clienti"}[column];
    }
    
    /**
    * Computes a string containing the id of the allocated client in a particular offer. <br>  
    * @param clients a clients list allocated to a offer
    * @return a string with client ids
    */
    private String getClientsListAsString( List<Client> clients ){
        String message = "";
        for( int i = 0; i<clients.size(); i++ ){
            Client client = clients.get(i);
            if( (i + 1) == clients.size() ){
                message += client.getIdClient();
            }else{
                message += client.getIdClient() + " , ";
            }
        }
        return message;
    }
}
