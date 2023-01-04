import java.awt.Graphics;


import javax.swing.JPanel;


public class GameScreen extends JPanel{
    private MouseInput mouseInput;
    private Game game;
    
    public GameScreen(Game game)
    {
        this.game = game;
        mouseInput = new MouseInput(this);
        addKeyListener(new Input(this));
        addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g); //Ve Khi Khoi Tao
  
    }

    //Su dung cho class Input va MouseInput
    public Game getGame()
    {
        return game;
    }
}