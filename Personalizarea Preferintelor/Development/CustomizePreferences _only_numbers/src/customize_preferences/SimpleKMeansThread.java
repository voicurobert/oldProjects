/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customize_preferences;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that makes offers for clients based on Simple K Means algorithm in a thread. <br>
 * Attributes : <br>
 * - autoIterate : a boolean flag that indicates whether the algorithm runs on a button press or not <br>
 * - thread : a Thread object <br>
 * - suspend : a boolean flag that indicates if the thread is suspended or not <br>
 * - clusterNumber : int value indicating the number of clusters <br>
 * - clients : a list of Client objects <br>
 * - centroids : a list of Centroid objects <br>
 * - nrOfSteps : the number of steps to iterate <br>
 * - allocationMapping : a structure that holds the allocation mapping between the clients, the calculated distance and the centroid <br>
 * - clusterAllocation : a structure that holds the allocation from each step between the clients and the centroids <br> 
 * @author Robert
 */
public class SimpleKMeansThread implements Runnable {

    private boolean autoIterate;
    private Thread thread;
    private boolean suspend = true;
    private int clusterNumber;
    private List<Client> clients;
    private List<Centroid> centroids = new ArrayList<>();
    private int nrOfSteps;
    // integer is clientIndex(the same index from clients list ); Double is the result from euclidian distance; Integer is the centroid index
    private Map< Integer, HashMap< Double, Integer>> allocationMapping = new HashMap< Integer, HashMap< Double, Integer>>();
    // integer is the centroid index; List<Client> is the clients allocated to that centroid
    private Map< Integer, List< Client>> clusterAllocation = new HashMap< Integer, List<Client>>();

    
    /**
     * Constructor that instantiates a new SimpleKMeans threaded object
     */
    public SimpleKMeansThread() {

    }

    /**
     * Getter
     * @return allocationMapping 
     */
    public Map<Integer, HashMap<Double, Integer>> getAllocationMapping() {
        return allocationMapping;
    }

    /**
     * Getter
     * @return centroids list 
     */
    public List<Centroid> getCentroids() {
        return centroids;
    }

    /**
     * Getter 
     * @return clients list 
     */
    public List<Client> getClients() {
        return clients;
    }

    /**
     * Getter
     * @return clusterAllocation
     */
    public Map<Integer, List<Client>> getClusterAllocation() {
        return clusterAllocation;
    }

    /**
     * Getter
     * @return clusterNumber 
     */
    public int getClusterNumber() {
        return clusterNumber;
    }

    /**
     * Getter
     * @return nrOfSteps 
     */
    public int getNrOfSteps() {
        return nrOfSteps;
    }

    /**
     * Setter
     * @param centroids a list of Centroid objects
     */
    public void setCentroids(List<Centroid> centroids) {
        this.centroids = centroids;
    }
    /**
     * Setter
     * @param clients a list of Client objects
     */
    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    /**
     * Setter 
     * @param clusterNumber a int that indicates the number of clusters/offers
     */
    public void setClusterNumber(int clusterNumber) {
        this.clusterNumber = clusterNumber;
    }

    /**
     * Setter
     * @param nrOfSteps a int number that indicates the number of steps for algorithm
     */
    public void setNrOfSteps(int nrOfSteps) {
        this.nrOfSteps = nrOfSteps;
    }

    /**
     * Setter
     * @param autoIterate boolean flag indicating if the algorithm should run itself
     */
    public void setAutoIterate(boolean autoIterate) {
        this.autoIterate = autoIterate;
    }

    
    /**
     * Override method from Thread. Here the Simple K Means algorithm starts. Algorithm steps: <br>
     * Step 1 : calculate the distance between each value from clients to centroids. The distance can be calculated using Euclidian distance, Chebysev distance. Manhattan distance or Minkowski distance. <br>
     * Step 2 : do cluster allocation based on information of client, calculated distance and centroid. This information is saved in allocationMapping structure. <br>
     * Step 3 : recalculate centroids based on cluster allocation. For every clients that was associated with a centroid recalculate the centroid values.
     * Step 4 : Make step 1, 2 and 3 as many times as nrOfSteps. <br>
     */
    @Override
    public void run() {
        for (int i = 0; i < nrOfSteps; i++) {
            System.out.println("Iteration: " + i );
            try {
                if (i == nrOfSteps - 1) {
                    computeOperationsForLastIteration();
                    FrameInstanceHolder.instance.getSimpleKMeansFrame().disableNextButton();
                }
                int nrOfClients = clients.size();
                for (int clientIndex = 0; clientIndex < nrOfClients; clientIndex++) {
                    HashMap< Double, Integer> resultMapping = new HashMap<Double, Integer>();
                    // get client vector values
                    Client currentClient = clients.get(clientIndex);
                    for (int centroidIndex = 0; centroidIndex < clusterNumber; centroidIndex++) {
                        Centroid centroid = centroids.get(centroidIndex);
                        // Step 2: calculate Euclidian distances from client values to centroid values
                        double currentResult = euclidianDistance(currentClient, centroid);
                        // System.out.println(currentResult);
                        // add mapping for current centroid index and currentResult - we need this to 
                        // make allocation between client and min result
                        resultMapping.put(currentResult, centroidIndex);
                        if (allocationMapping.containsKey(clientIndex)) {
                            allocationMapping.replace(clientIndex, resultMapping);
                        } else {
                            allocationMapping.put(clientIndex, resultMapping);
                        }
                    }
                    // make cluster allocation
                    doClusterAllocation(clientIndex);
                }
                recalculateCentroids();
                System.out.println("finnished iteration : " + i );
                
                // here we can set values to trees, we have the simplekmeans gui in frameinstace holder
                FrameInstanceHolder.instance.getSimpleKMeansFrame().setAllocationMappingTree();
                FrameInstanceHolder.instance.getSimpleKMeansFrame().setClusterAllocationTree();
                if (autoIterate == false) {
                    Thread.sleep(100);
                    synchronized (this) {
                        if (suspend) {
                            wait();
                        }
                    }
                }
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * This method start our thread and our algorithm
     */
    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * Suspends the thread until "Next" button is pressed.
     */
    public void suspend() {
        suspend = true;
        // notify();
    }

    /**
     * Resumes the thread when the "Next" button is pressed.
     */
    synchronized void resume() {
        suspend = false;
        notify();
    }
    
    /**
     * Realize action when the thread is at the last iteration. Inserts offers in database, oferte table, updated Clients with its associated offer id and refreshes the tables from ProviderGUI Frame.
     */
    private void computeOperationsForLastIteration() {
        this.thread.interrupt();
        // insert(last iteration centroids) offers in database
        for (Centroid centroid : centroids) {
            Offer offer = new Offer(centroid);
            DatabaseManager.instance.insertOffer(offer);
        }
        // update clients with offer id
        for (int centroidIndex = 0; centroidIndex < centroids.size(); centroidIndex++) {
            List<Client> clients = clusterAllocation.get(centroidIndex);
            if (clients != null) {
                Offer offer = new Offer(centroids.get(centroidIndex));
                for (Client client : clients) {
                    DatabaseManager.instance.updateClient(client, offer);
                }
            }
        }
        // refresh provider tables
        FrameInstanceHolder.instance.getProviderFrame().refreshOffersTable();
        FrameInstanceHolder.instance.getProviderFrame().refreshAllocationTable();
        if (FrameInstanceHolder.instance.getClientsFrame() != null) {
            FrameInstanceHolder.instance.getClientsFrame().clientsModel.fireTableDataChanged();
        }
    }
    
    /**
     * Makes the allocation for client index with the associated centroid. <br>
     * For the given client index return the minimum distance from allocationMapping and save this information in clusterAllocation. <br>
     * @param clientIndex 
     */
    private void doClusterAllocation( int clientIndex ){
        // from allocationMapping calculate min value for clientIndex
        try {
            HashMap< Double, Integer> resultMapping = allocationMapping.get(clientIndex);
            double[] resultVector = new double[clusterNumber];
            int i = 0;
            for (Double result : resultMapping.keySet()) {
                resultVector[i] = result;
                i++;
            }
            Arrays.sort(resultVector);
            double minValue = resultVector[0];
            int centroidIndex = resultMapping.get(minValue);
            if ( clusterAllocation.containsKey( centroidIndex ) ) {
                // we allready have the list of clients, so add the client with clientIndex
                List<Client> associatedClients = clusterAllocation.get(centroidIndex);
                Client currentClient = clients.get(clientIndex);
                boolean ok = true;
                for ( Client c : associatedClients ) {
                    if ( c.getIdClient() == currentClient.getIdClient() ) {
                        ok = false;
                    }
                }
                if ( ok ) {
                    clusterAllocation.get(centroidIndex).add(currentClient);
                }
            } else {
                // create a new List, and add the client
                List<Client> allocatedClients = new ArrayList<Client>();
                allocatedClients.add(clients.get(clientIndex));
                clusterAllocation.put(centroidIndex, allocatedClients);
            }
        }catch( NullPointerException e ){
            System.out.println("exception: " + e.getMessage() );
        }
    }
    
    /**
     * Recalculates centroid values with its associated clients. It makes a sum of values and then divide that sum to the number of associated clients.
     */
    private void recalculateCentroids(){
        // for each cluster number, recalculate centroids
        for (int centroidIndex = 0; centroidIndex < centroids.size(); centroidIndex++) {
            // get clients list
            if (clusterAllocation.containsKey(centroidIndex)) {
                Centroid centroid = centroids.get(centroidIndex);
                List<Client> allocatedClients = clusterAllocation.get(centroidIndex);
                double[] newValues = new double[8];
                for (int i = 0; i < 8; i++) {
                    newValues[i] = roundValue( calculateNewCentroidData(i + 1, allocatedClients) );
                }
                centroid.setVector(newValues);
                centroids.add(centroidIndex, centroid);
               // System.out.println("recalculated: " + centroid.toString());
                centroids.remove(centroidIndex + 1);
            }
        }
    }
    
    /**
     * Does the actual calculation of value between a centroid object and the associated clients.
     * @param index a int value from 0 to 7
     * @param allocatedClients a list of Client objects
     * @return 
     */
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
    
    /**
     * Does the Euclidian distance between a Client and a Centroid
     * @param client a Client object
     * @param centroid a Centroid object
     * @return a result of type double 
     */
    private double euclidianDistance( Client client, Centroid centroid ){
        double result = 0.0;
        double[] clientValues = client.getVectorValues();
        for( int i = 0; i<8; i++ ){
            result += (double) Math.abs( Math.pow( clientValues[i] -  centroid.getVector()[i] , 2 ) );
        }
        return result;
    }
    
    /**
     * Does the Manhattan distance between a Client and a Centroid
     * @param client a Client object
     * @param centroid a Centroid object
     * @return a result of type double 
     */
    private double manhattanDistance( Client client, Centroid centroid ){
        double result = 0.0;
        double[] clientValues = client.getVectorValues();
        for( int i = 0; i<8; i++ ){
            result += (double) Math.abs( clientValues[i] -  centroid.getVector()[i] );
        }
        return result;
    }
    
    /**
     * Does the Chebysev distance between a Client and a Centroid
     * @param client a Client object
     * @param centroid a Centroid object
     * @return a result of type double 
     */
    private double chebyshevDistance( Client client, Centroid centroid ){
        double[] result = new double[8];
        double[] clientValues = client.getVectorValues();
        for( int i = 0; i<8; i++ ){
            result[ i ] = (double) Math.abs( clientValues[i] -  centroid.getVector()[i] );
        }
        Arrays.sort( result );
        return (double) result[ result.length - 1];
    }
    
    /**
     * Does the Minkowski distance between a Client and a Centroid
     * @param client a Client object
     * @param centroid a Centroid object
     * @return a result of type double 
     */
    private double minkowskiDistance( Client client, Centroid centroid ){
        double result = 0.0;
        double[] clientValues = client.getVectorValues();
        for( int i = 0; i<8; i++ ){
            result += (double) Math.abs( Math.pow( clientValues[i] -  centroid.getVector()[i] , 3 ) );
        }
        return (double) Math.pow( result, 1/3 );
    }
    
    /**
     * Rounds the value to #.## format
     * @param value a double value
     * @return the formated result
     */
    private final double roundValue( double value ){
        double newValue = 0.0;
        DecimalFormat df = new DecimalFormat("#.##");
        newValue = Double.valueOf( df.format( value ));
        return newValue;
    }
}
