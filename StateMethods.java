import java.awt.Graphics;

import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;

public interface StateMethods {
    public void update();
    public void draw(Graphics g);
    
    public void MouseClicked(MouseEvent e);
    public void mouseMoved(MouseEvent e);
    public void MousePressed(MouseEvent e);
    public void mouseReleased(MouseEvent e);

    public void KeyClicked(KeyEvent e);
    public void KeyPressed(KeyEvent e);
    public void KeyReleased(KeyEvent e);

}
