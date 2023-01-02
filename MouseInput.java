import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
public class MouseInput implements MouseListener, MouseMotionListener{

    private GameScreen gameScreen;

    public MouseInput(GameScreen gameScreen)
    {
        this.gameScreen = gameScreen;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //gameScreen.setPos(e.getX(), e.getY());
        switch(GameState.state)
        {
            case Menu:
                gameScreen.getGame().getMenu().mouseMoved(e);
                break;
            case Playing:
                gameScreen.getGame().getPlaying().mouseMoved(e);
        }
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch(GameState.state)
        {
            case Menu:
                gameScreen.getGame().getMenu().MouseClicked(e);
                break;
            case Playing:
                gameScreen.getGame().getPlaying().MouseClicked(e);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch(GameState.state)
        {
            case Menu:
                gameScreen.getGame().getMenu().MousePressed(e);;
                break;
            case Playing:
                gameScreen.getGame().getPlaying().MousePressed(e);;
        }
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch(GameState.state)
        {
            case Menu:
                gameScreen.getGame().getMenu().mouseReleased(e);
                break;
            case Playing:
                gameScreen.getGame().getPlaying().mouseReleased(e);
        }
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    
}
