/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customize_preferences;

/**
 * Class that holds all the interfaces of this application in static attributes. <br>
 * Attributes : <br>
 * - instance : attribute that stores a FrameInstanceHolder object; <br>
 * - clientsFrame : attribute that stores a ClientsGUI object; <br>
 * - providerFrame : attribute that stores a ProviderGUI object; <br>
 * - addClientFrame : attribute that stores a AddClientGUI object; <br>
 - detailClientFrame : attribute that stores a DetailClientGUI object; <br>
 * @author Robert
 */
public class FrameInstanceHolder {
    
    public static FrameInstanceHolder instance = new FrameInstanceHolder();
    private ClientsGUI clientsFrame = null;
    private ProviderGUI providerFrame = null;
    //private AddClientGUI addClientFrame = null;
    private DetailClientGUI detailClientFrame = null;
    private SimpleKMeansGUI simpleKMeansFrame = null;

    /**
     * Empty constructor that instantiates a new FrameInstanceHolder object;
     */
    public FrameInstanceHolder() {
        
    }

    /**
     * Setter method. <br>
     * @param clientsFrame a ClientsGUI object
     */
    public void setClientsFrame(ClientsGUI clientsFrame) {
        this.clientsFrame = clientsFrame;
    }
    /**
     * Setter method.
     * @param addClientFrame a addClientGUI object
     */
   // public void setAddClientFrame(AddClientGUI addClientFrame) {
      //  this.addClientFrame = addClientFrame;
   // }
    /**
     * Setter method
     * @param detailClientFrame a DetailClientGUI object;
     */
    public void setDetailClientFrame(DetailClientGUI detailClientFrame) {
        this.detailClientFrame = detailClientFrame;
    }
    /**
     * Setter method 
     * @param providerFrame a ProviderGUI object;
     */
    public void setProviderFrame(ProviderGUI providerFrame) {
        this.providerFrame = providerFrame;
    }

    public ClientsGUI getClientsFrame() {
        return clientsFrame;
    }
    /**
     * Getter method.
     * @return DetailClientGUI object or null
     */
    public DetailClientGUI getDetailClientFrame() {
        return detailClientFrame;
    }
    /**
     * Getter method
     * @return ProviderGUI or null;
     */
    public ProviderGUI getProviderFrame() {
        return providerFrame;
    }

    /**
     * Setter method
     * @param simpleKMeansFrame the SimpleKMeansGUI object
     */
    public void setSimpleKMeansFrame(SimpleKMeansGUI simpleKMeansFrame) {
        this.simpleKMeansFrame = simpleKMeansFrame;
    }

    /**
     * Getter method
     * @return the SimpleKMEansFrame object
     */
    public SimpleKMeansGUI getSimpleKMeansFrame() {
        return simpleKMeansFrame;
    }
    
    
    
}
