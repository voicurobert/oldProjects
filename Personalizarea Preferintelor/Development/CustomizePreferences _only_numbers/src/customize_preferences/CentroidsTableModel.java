/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customize_preferences;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Class that acts like a model for centroids table from Simple K Means Algorithm interface. <br>
 * Attributes : <br>
 * - centroids   : A list of centroids that will be shown in the table <br>
 * @author Robert
 */
public class CentroidsTableModel extends AbstractTableModel{

    protected List<Centroid> centroids;
    
    /**
     * Instantiates a new CentroidsTableModel object.
     */
    public CentroidsTableModel(){
        
    }

    /**
     * Setter method for centroids list
     * @param centroids a list of centroids
     */
    public void setCentroids(List<Centroid> centroids) {
        this.centroids = centroids;
    }

    /**
     * Getter methods
     * @return the centroids list
     */
    public List<Centroid> getCentroids() {
        return centroids;
    }
    
    /**
    * Override the parent method. Internally Will set the table row count. <br>
    * @return the size of centroids list.
    */
    @Override
    public int getRowCount() {
         return centroids.size();
    }

    /**
    * Override the parent method. Will set the table column count. <br>
    * @return 8 the number of column in the table.
    */
    @Override
    public int getColumnCount() {
        return 8;
    }

    /**
    * Override parent method. Will set the values of the table. In each cell( rowIndex, columnIndex ) it will set the value.
    * @param rowIndex the row index of the table <br>
    * @param columnIndex the column index of the table <br>
    * @return for each pair, row and column, will return the value that will be shown in that cell. <br>
    */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Centroid centroid = centroids.get(rowIndex);
        switch( columnIndex ){
            
            case 0 : return centroid.getVector()[0];
            case 1 : return centroid.getVector()[1];
            case 2 : return centroid.getVector()[2];
            case 3 : return centroid.getVector()[3];
            case 4 : return centroid.getVector()[4];
            case 5 : return centroid.getVector()[5];
            case 6 : return centroid.getVector()[6];
            case 7 : return centroid.getVector()[7];
            default : return "";
       }
    }
    
    
    /**
    * Override parent method. Will set the column name when the table will be refreshed. <br>
    * @param column the column index 
    * @return the column names.
    */
    @Override
    public String getColumnName(int column) {
        return new String[]{
            "Valoare 1",
            "Valoare 2",
            "Valoare 3",
            "Valoare 4",
            "Valoare 5",
            "Valoare 6",
            "Valoare 7",
            "Valoare 8"}[column];
    }
    
}
