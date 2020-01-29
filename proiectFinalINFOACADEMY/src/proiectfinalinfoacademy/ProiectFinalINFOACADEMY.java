package proiectfinalinfoacademy;

import java.awt.*;
import javax.swing.*;
;


public class ProiectFinalINFOACADEMY extends JWindow {
    private int timp;

    public ProiectFinalINFOACADEMY(int timp) {
        this.timp = timp;
    }
    
    public void showSplash(){
        JPanel panel = (JPanel)getContentPane();
        panel.setBackground(Color.white);
        
        // Set the window's bounds, centering the window
        int width = 300;
        int height =300;
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width-width)/2;
        int y = (d.height-height)/2;
        setBounds(x,y,width,height);
        
        // Build the splash screen
        
        JLabel label = new JLabel(new ImageIcon(getClass().getResource("agenda.jpg")));
        
        JLabel nume = new JLabel("Voicu EduardRobert", JLabel.CENTER);
        nume.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(label, BorderLayout.CENTER);
        panel.add(nume, BorderLayout.SOUTH);
        
        // Display it
        setVisible(true);
        
        // Wait a little while, maybe while loading resources
        try{ 
            Thread.sleep(timp); 
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        
        setVisible(false);
        new GUI().setVisible(true);
    }
   
    public static void main(String[] args) {
        ProiectFinalINFOACADEMY p = new ProiectFinalINFOACADEMY(2000);
        p.showSplash();
       
    }
}
