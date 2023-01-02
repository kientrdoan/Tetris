import java.awt.event.MouseEvent;

public class State {
    private Game game;

    public State(Game game)
    {
        this.game = game;
    }

    public boolean isButton(MouseEvent e, MenuButton mb)
    {
        return mb.getBounds().contains(e.getX(), e.getY());
    }

    public Game getGame()
    {
        return game;
    }

    
    
}
