import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
public class Guide extends State implements StateMethods {
    private BufferedImage guide;
    private MenuButton btnGuide;

    public Guide(Game game) {
        super(game);
        guide = LoadFile.loadImage("Pictetric/guide.png");
        btnGuide = new MenuButton(GameState.Menu, guide, 10, 100, 470, 490, 0);


}

    @Override
    public void update() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void draw(Graphics g) {
        g.drawString("Enter to back menu", 10, 50);
        btnGuide.drawMenuButton(g);
        
    }

    @Override
    public void MouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void MousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void KeyClicked(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void KeyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            GameState.state = GameState.Menu;
        }
        
    }

    @Override
    public void KeyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }
}