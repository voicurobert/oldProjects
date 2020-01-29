/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customize_preferences;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Class that acks like a model for clients table from Client interface and Provider interface. <br>
 * Attributes : <br>
 * - clients  : a list of clients.
 * @author Robert
 */
public class ClientsTableModel extends AbstractTableModel{
    
    protected List<Client> clients;

    /**
     * Empty constructor that will instantiate a new ClientsTableModel
     */
    public ClientsTableModel() {
    }

    /**
     * Setter method. Set the clients list. <br>
     * @param clients a list of clients
     */
    public void setClients( List<Client> clients ) {
        this.clients = clients;
    }
    /**
     * Getter method. <br>
     * @return the clients attribute.
     */
    public List<Client> getClients() {
        return clients;
    }
    
    /**
     * Override method. Internally will set the table row count. <br>
     * @return the size of clients list.
     */
    @Override
    public int getRowCount() {
        return clients.size();
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
       Client client = clients.get(rowIndex);
       switch( columnIndex ){
           case 0 : return client.getIdClient();
           case 1 : return client.getCriteriu1();
           case 2 : return client.getCriteriu2();
           case 3 : return client.getCriteriu3();
           case 4 : return client.getCriteriu4();
           case 5 : return client.getCriteriu5();
           case 6 : return client.getCriteriu6();
           case 7 : return client.getCriteriu7();
           case 8 : return client.getCriteriu8();
           default : return client.toString();
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
            "ID Client",
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
