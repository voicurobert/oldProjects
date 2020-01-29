/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customize_preferences;

import java.awt.Color;

/**
 * Class that will open a window for viewing details about client and his associated offer
 * @author Robert
 */
public class DetailClientGUI extends javax.swing.JFrame {
    
    protected Client client;
    private int index;
    
    /**
     * Constructor - instantiates a new JFrame object
     * @param client a client object 
     * @param index  the client id 
     */
    public DetailClientGUI( Client client, int index ) {
        getContentPane().setBackground( new Color(200, 100, 100) );
        this.client = client;
        this.index = index;
        initComponents();
        setComponentsValues();
    }

    /**
     * Getter method
     * @return the client id 
     */
    public int getIndex() {
        return index;
    }

    /**
     * Setter method
     * @param index the client id
     */
    public void setIndex(int index) {
        // if index != this.index, setComponentValues()
        if( this.index != index ){
            this.index = index;
            setComponentsValues();
        }
        
    }
    
    /**
     * Setter method
     * @param client the client object 
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Getter method
     * @return the client
     */
    public Client getClient() {
        return client;
    }
    
    /**
     * Set the values for this Frame. Sets client and offer, if exists, details
     */
    public void setComponentsValues(){
        if( client != null ){
            // set text on client labels - the values of preferences 
            clientAgentTitleLabel.setText( "Agent Client " + String.valueOf( client.getIdClient() ) );
            clientValue1Label.setText( String.valueOf( client.getCriteriu1() ) );
            clientValue2Label.setText( String.valueOf( client.getCriteriu2() ) );
            clientValue3Label.setText( String.valueOf( client.getCriteriu3() ) );
            clientValue4Label.setText( String.valueOf( client.getCriteriu4() ) );
            clientValue5Label.setText( String.valueOf( client.getCriteriu5() ) );
            clientValue6Label.setText( String.valueOf( client.getCriteriu6() ) );
            clientValue7Label.setText( String.valueOf( client.getCriteriu7() ) );
            clientValue8Label.setText( String.valueOf( client.getCriteriu8() ) );
            
            // if client has id_oferta setted then set values to offer's values
            int offerId = client.getIdOferta();
            if( offerId != 0 ){
                // this client has an offer associated => show info
                // get the record from database, oferte table, where id_oferta = offerId
                Offer offer = DatabaseManager.instance.getOfferForId(offerId);
                offerNrValueLabel.setText( String.valueOf( offer.getIdOferta() ));
                offerValue1Label.setText( String.valueOf( offer.getCriteriu1() ));
                offerValue2Label.setText( String.valueOf( offer.getCriteriu2() ));
                offerValue3Label.setText( String.valueOf( offer.getCriteriu3() ));
                offerValue4Label.setText( String.valueOf( offer.getCriteriu4() ));
                offerValue5Label.setText( String.valueOf( offer.getCriteriu5() ));
                offerValue6Label.setText( String.valueOf( offer.getCriteriu6() ));
                offerValue7Label.setText( String.valueOf( offer.getCriteriu7() ));
                offerValue8Label.setText( String.valueOf( offer.getCriteriu8() ));
            }
        }else{
            System.out.println("client null ");
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        clientAgentTitleLabel = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        offerValue1Label = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        offerValue2Label = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        offerValue3Label = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        offerValue4Label = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        offerValue5Label = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        offerValue6Label = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        offerValue7Label = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        offerValue8Label = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        offerNrValueLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        clientValue1Label = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        clientValue2Label = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        clientValue3Label = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        clientValue4Label = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        clientValue5Label = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        clientValue6Label = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        clientValue7Label = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        clientValue8Label = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        clientAgentTitleLabel.setFont(new java.awt.Font("Consolas", 1, 18)); // NOI18N
        clientAgentTitleLabel.setText("Agent Client");

        jPanel1.setBackground(new java.awt.Color(200, 100, 100));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Oferta primita de la Agentul Furnizor", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Consolas", 1, 12))); // NOI18N

        jLabel17.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        jLabel17.setText("Criteriu 1");

        offerValue1Label.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        offerValue1Label.setText("Valoare 1");

        jLabel19.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        jLabel19.setText("Criteriu 2");

        offerValue2Label.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        offerValue2Label.setText("Valoare 2");

        jLabel21.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        jLabel21.setText("Criteriu 3");

        offerValue3Label.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        offerValue3Label.setText("Valoare 3");

        jLabel23.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        jLabel23.setText("Criteriu 4");

        offerValue4Label.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        offerValue4Label.setText("Valoare 4");

        jLabel25.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        jLabel25.setText("Criteriu 5");

        offerValue5Label.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        offerValue5Label.setText("Valoare 5");

        jLabel27.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        jLabel27.setText("Criteriu 6");

        offerValue6Label.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        offerValue6Label.setText("Valoare 6");

        jLabel29.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        jLabel29.setText("Criteriu 7");

        offerValue7Label.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        offerValue7Label.setText("Valoare 7");

        jLabel31.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        jLabel31.setText("Criteriu 8");

        offerValue8Label.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        offerValue8Label.setText("Valoare 8");

        jLabel33.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        jLabel33.setText("Numar Oferta");

        offerNrValueLabel.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        offerNrValueLabel.setText("Valoare");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel29)
                                .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.TRAILING)))
                        .addGap(82, 82, 82)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(offerValue7Label)
                            .addComponent(offerValue6Label)
                            .addComponent(offerValue8Label))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel23)
                                    .addComponent(jLabel25))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(82, 82, 82)
                                        .addComponent(offerValue5Label))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(offerValue4Label))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel33)
                                    .addComponent(jLabel19))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(offerValue2Label)
                                    .addComponent(offerNrValueLabel)
                                    .addComponent(offerValue1Label)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(offerValue3Label)))
                        .addGap(74, 74, 74))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(offerNrValueLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(offerValue1Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(offerValue2Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(offerValue3Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(offerValue4Label, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(offerValue5Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(offerValue6Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(offerValue7Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(offerValue8Label))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(200, 100, 100));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Preferintele Agentului Client", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Consolas", 1, 12))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Criteriu 1");

        clientValue1Label.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        clientValue1Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        clientValue1Label.setText("Valoare 1");

        jLabel3.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Criteriu 2");

        clientValue2Label.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        clientValue2Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        clientValue2Label.setText("Valoare 2");

        jLabel5.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Criteriu 3");

        clientValue3Label.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        clientValue3Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        clientValue3Label.setText("Valoare 3");

        jLabel7.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Criteriu 4");

        clientValue4Label.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        clientValue4Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        clientValue4Label.setText("Valoare 4");

        jLabel2.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Criteriu 5");

        clientValue5Label.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        clientValue5Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        clientValue5Label.setText("Valoare 5");

        jLabel11.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Criteriu 6");

        clientValue6Label.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        clientValue6Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        clientValue6Label.setText("Valoare 6");

        jLabel13.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Criteriu 7");

        clientValue7Label.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        clientValue7Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        clientValue7Label.setText("Valoare 7");

        jLabel15.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Criteriu 8");

        clientValue8Label.setFont(new java.awt.Font("Consolas", 1, 12)); // NOI18N
        clientValue8Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        clientValue8Label.setText("Valoare 8");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                        .addComponent(clientValue8Label))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(clientValue7Label))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(clientValue6Label))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(clientValue5Label))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(clientValue4Label))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(clientValue3Label))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(clientValue2Label))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(clientValue1Label)))
                .addGap(52, 52, 52))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(clientValue1Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(clientValue2Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(clientValue3Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(clientValue4Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(clientValue5Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(clientValue6Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(clientValue7Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(clientValue8Label))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
            .addGroup(layout.createSequentialGroup()
                .addGap(280, 280, 280)
                .addComponent(clientAgentTitleLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(clientAgentTitleLabel)
                .addGap(61, 61, 61)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(61, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        FrameInstanceHolder.instance.setDetailClientFrame(null);
    }//GEN-LAST:event_formWindowClosed

    

  
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel clientAgentTitleLabel;
    private javax.swing.JLabel clientValue1Label;
    private javax.swing.JLabel clientValue2Label;
    private javax.swing.JLabel clientValue3Label;
    private javax.swing.JLabel clientValue4Label;
    private javax.swing.JLabel clientValue5Label;
    private javax.swing.JLabel clientValue6Label;
    private javax.swing.JLabel clientValue7Label;
    private javax.swing.JLabel clientValue8Label;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel offerNrValueLabel;
    private javax.swing.JLabel offerValue1Label;
    private javax.swing.JLabel offerValue2Label;
    private javax.swing.JLabel offerValue3Label;
    private javax.swing.JLabel offerValue4Label;
    private javax.swing.JLabel offerValue5Label;
    private javax.swing.JLabel offerValue6Label;
    private javax.swing.JLabel offerValue7Label;
    private javax.swing.JLabel offerValue8Label;
    // End of variables declaration//GEN-END:variables
}
