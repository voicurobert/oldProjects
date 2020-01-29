/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customize_preferences;

import java.awt.event.WindowEvent;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;

/**
 * CLass that opens a new window that will show a plot 
 * @author Robert
 */
public class DrawOffers extends ApplicationFrame{

    /**
     * Constructor - instantiates a new DrawOffers plot
     * @param title the title of the frame
     * @param chart a JFreeChart object containing the data that needs to be drawn in plot
     */
    public DrawOffers( String title, JFreeChart chart ) {
        super(title);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 400));
        setContentPane(chartPanel);
        
    }

    @Override
    public void windowClosing(WindowEvent event) {
        
    }

   
    
    
}
