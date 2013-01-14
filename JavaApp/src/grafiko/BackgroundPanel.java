package grafiko;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


/*  Every object of this class is a panel
    with background the image which is
    loacated in patth*/
class BackgroundPanel extends JPanel {
    
   private Image bg;
   
   public BackgroundPanel(String path) {
        bg = new ImageIcon(path).getImage();
    }
    
    public void paintComponent(Graphics g) {
        g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
    }
    
}