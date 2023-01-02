import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Input implements KeyListener {

    private GameScreen gameScreen;

    public Input(GameScreen gameScreen)
    {
        this.gameScreen = gameScreen;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(GameState.state)
        {
            case Menu:
                gameScreen.getGame().getMenu().KeyPressed(e);
                break;
            case Guide:
                gameScreen.getGame().getGuide().KeyPressed(e);
                break;
            case Playing:
                gameScreen.getGame().getPlaying().KeyPressed(e);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(GameState.state)
        {
            case Menu:
                gameScreen.getGame().getMenu().KeyReleased(e);
                break;
            case Guide:
                gameScreen.getGame().getGuide().KeyPressed(e);
                break;
            case Playing:
                gameScreen.getGame().getPlaying().KeyReleased(e);;
                break;
        }
        
    }
    
}
