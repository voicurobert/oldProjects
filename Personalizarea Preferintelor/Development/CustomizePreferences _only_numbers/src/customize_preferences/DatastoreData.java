/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customize_preferences;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Robert
 */
public class DatastoreData {
    
    public static final DatastoreData instance = new DatastoreData();
    private List<Client> clients = new ArrayList<Client>();
    private List<Offer> offers = new ArrayList<Offer>();
    private List<Centroid> centroids = new ArrayList<Centroid>();
    
    public DatastoreData(){
        
    }

    public List<Centroid> getCentroids() {
        return centroids;
    }

    public List<Client> getClients() {
        return clients;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setCentroids(List<Centroid> centroids) {
        this.centroids = centroids;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }
    
    public void addCentroid( Centroid centroid ){
        this.centroids.add(centroid);
    }
    
    
    
}
