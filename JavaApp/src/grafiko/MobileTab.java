package grafiko;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class MobileTab extends JPanel{
    
    private JLabel deviceLabel;
    private JComboBox deviceBox;
    Device device[];
    
     
    
    MobileTab() {
        deviceLabel = new JLabel("Mobiles");
        add(deviceLabel,BorderLayout.NORTH);     
        
    }
    
}
