/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customize_preferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * Simple K Means algorithm.
 * This class will apply the Simple K Means algorithm over all 
 * clients from the database.
 * </p>
 * Attributes : <br>
 * - clusterNumber : attribute that will hold the parameter of Simple K Means algorithm and represents the cluster number that will be generated <br>
 * - clients : attribute that will hold a list of Client objects <br>
 * - offers : attribute that will hold a list of Offers
 * @author Robert
 */
public class SimpleKMeans {
    
    private int clusterNumber;
    private List<Client> clients;
    private List<Centroid> centroids = new ArrayList<>();
    private int nrOfSteps = 100;
    // integer is clientIndex(the same index from clients list ); Double is the result from euclidian distance; Integer is the centroid index
    private Map< Integer, HashMap< Double, Integer > > allocationMapping = new HashMap< Integer, HashMap< Double, Integer > >();
    // integer is the centroid index; List<Client> is the clients allocated to that centroid
    private Map< Integer, List< Client > > clusterAllocation = new HashMap< Integer, List<Client> >();
   
    
    public SimpleKMeans( ) {
       
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }


    public int getClusterNumber() {
        return clusterNumber;
    }

    public void setClusterNumber(int clusterNumber) {
        this.clusterNumber = clusterNumber;
    }

    public List<Client> getClients() {
        return clients;
    }


    public List<Centroid> getCentroids() {
        return centroids;
    }

    public Map<Integer, List<Client>> getClusterAllocation() {
        return clusterAllocation;
    }
    
    /**
     * <p>
     * This method is the starting point of our algorithm. <br>
     * The steps are: <br>
     * Step 1: with the number of clusters given, we calculate
     * the centroids - for each number of clusters we will have a centroid. 
     * For example if the number of clusters is 3 we will calculate 3 centroids.
     * @author Robert
     */
    public void startAlgorithm() {
        // Step 1: calculate random centroids
        for (int i = 1; i <= clusterNumber; i++) {
            Centroid centroid = new Centroid();
            centroid.generateRandomData();
            System.out.println(centroid.toString());
            DatastoreData.instance.getCentroids().add(centroid);
            centroids.add(centroid);
        }
        int nrOfClients = clients.size();
        int startIndex = 1;
        
        while ( startIndex <= nrOfSteps ) {
            for (int clientIndex = 0; clientIndex < nrOfClients; clientIndex++) {
                HashMap< Double, Integer > resultMapping = new HashMap<Double, Integer>();
                // get client vector values
                Client currentClient = clients.get( clientIndex );
                for (int centroidIndex = 0; centroidIndex < clusterNumber; centroidIndex++ ) {
                    Centroid centroid = centroids.get(centroidIndex);
                    // Step 2: calculate Euclidian distances from client values to centroid values
                    //double currentResult = euclidianDistance(currentClient, centroid );
                    double currentResult = minkowskiDistance(currentClient, centroid );
                    System.out.println( currentResult );
                    // add mapping for current centroid index and currentResult - we need this to 
                    // make allocation between client and min result
                    resultMapping.put( currentResult, centroidIndex );
                     if( allocationMapping.containsKey( clientIndex ) ){
                        allocationMapping.replace( clientIndex, resultMapping );
                    }else{
                        allocationMapping.put( clientIndex, resultMapping );
                    }
                }
                // make cluster allocation
                doClusterAllocation( clientIndex );
            }
            recalculateCentroids();
            startIndex++;
        }
    }
    
    private double euclidianDistance( Client client, Centroid centroid ){
        double result = 0.0;
        double[] clientValues = client.getVectorValues();
        for( int i = 0; i<8; i++ ){
            result += (double) Math.abs( Math.pow( clientValues[i] -  centroid.getVector()[i] , 2 ) );
        }
        return result;
    }
    
    private double manhattanDistance( Client client, Centroid centroid ){
        double result = 0.0;
        double[] clientValues = client.getVectorValues();
        for( int i = 0; i<8; i++ ){
            result += (double) Math.abs( clientValues[i] -  centroid.getVector()[i] );
        }
        return result;
    }
    
    private double chebyshevDistance( Client client, Centroid centroid ){
        double[] result = new double[8];
        double[] clientValues = client.getVectorValues();
        for( int i = 0; i<8; i++ ){
            result[ i ] = (double) Math.abs( clientValues[i] -  centroid.getVector()[i] );
        }
        Arrays.sort( result );
        return (double) result[ result.length - 1];
    }
    
    private double minkowskiDistance( Client client, Centroid centroid ){
        double result = 0.0;
        double[] clientValues = client.getVectorValues();
        for( int i = 0; i<8; i++ ){
            result += (double) Math.abs( Math.pow( clientValues[i] -  centroid.getVector()[i] , 3 ) );
        }
        return (double) Math.pow( result, 1/3 );
    }
    
    
    private void doClusterAllocation( int clientIndex ){
        // from allocationMapping calculate min value for clientIndex
        HashMap< Double, Integer > resultMapping = allocationMapping.get( clientIndex );
        if( resultMapping == null ){
            System.out.println("!!!!!!!!!!!!!!result mapping null in doClusterAllocation method WHY!!!!!!!!!!!!");
            return;
        }
        double[] resultVector = new double[ clusterNumber ];
        int i = 0;
        for( Double result : resultMapping.keySet() ){
            System.out.println("result: " + result);
            resultVector[ i ] = result;
            i++;
        }
        Arrays.sort( resultVector );
        double minValue = resultVector[0];
        if( resultVector == null ){
            System.out.println("!!!!!!!!!!!result vector null in doClusterAllocation method WHY !!!!!!!!!!");
            return;
        }
        System.out.println("min: " + minValue);
        int centroidIndex = resultMapping.get( minValue );
        if( clusterAllocation.containsKey( centroidIndex ) ){
            // we allready have the list of clients, so add the client with clientIndex
            List<Client> associatedClients = clusterAllocation.get( centroidIndex );
           
            Client currentClient = clients.get( clientIndex );
            boolean ok = true;
            for( Client c : associatedClients ){
                if( c.getIdClient() == currentClient.getIdClient() ){
                    ok = false;
                }
            }
            if( ok ){
                clusterAllocation.get( centroidIndex ).add( currentClient );
            }
        }else{
            // create a new List, and add the client
             List<Client> allocatedClients = new ArrayList<Client>();
            allocatedClients.add(clients.get(clientIndex));
            clusterAllocation.put(centroidIndex, allocatedClients);
            /*
            for (int j = 0; j < clusterNumber; j++) {
                if (centroidIndex != j) {
                    List<Client> allClients = clusterAllocation.get(j);
                    if (allClients != null) {
                        for (Client client : allClients) {
                            if (client.getIdClient() == clients.get(clientIndex).getIdClient()) {
                                allClients.remove(client);
                            }
                        }
                    }
                }
            }
                    */
        }
    }
    
    private void recalculateCentroids(){
        // for each cluster number, recalculate centroids
        for (int centroidIndex = 0; centroidIndex < centroids.size(); centroidIndex++) {
            // get clients list
            if (clusterAllocation.containsKey(centroidIndex)) {
                Centroid centroid = centroids.get(centroidIndex);
                List<Client> allocatedClients = clusterAllocation.get(centroidIndex);
                double[] newValues = new double[8];
                for (int i = 0; i < 8; i++) {
                    newValues[i] = calculateNewCentroidData(i + 1, allocatedClients);
                }
                centroid.setVector(newValues);
                centroids.add(centroidIndex, centroid);
                centroids.remove(centroidIndex + 1);
            }

        }
    }
    
    private double calculateNewCentroidData( int index, List<Client> allocatedClients ){
        double result = 0.0;
        for( Client client : allocatedClients ){
            switch( index ){
                case 1 : result += client.getCriteriu1();
                    break;
                case 2 : result += client.getCriteriu2();
                    break;
                case 3 : result += client.getCriteriu3();
                    break;
                case 4 : result += client.getCriteriu4();
                    break;
                case 5 : result += client.getCriteriu5();
                    break;
                case 6 : result += client.getCriteriu6();
                    break;
                case 7 : result += client.getCriteriu7();
                    break;
                case 8 : result += client.getCriteriu8();
                    break;
                default : result += 0.0;
                    break;
            }
        }
        return result/allocatedClients.size();
    }
        
}
