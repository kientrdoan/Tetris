import java.awt.Graphics;
import javax.sound.sampled.Clip;

public class Game implements Runnable
{
    private GameWindown gameWindown;
    private GameScreen gameScreen;

    private Menu menu;
    private Playing playing;
    private Guide guide;

    private Thread thread;

    private final int FPS = 120;
    private final int UP_FPS = 200;
    private boolean playMusic;
    private Clip music;
    
    public Game()
    {
        playMusic = true;
        init();
        this.gameScreen = new GameScreen(this);
        this.gameWindown = new GameWindown(gameScreen);
        gameScreen.requestFocus();
        startGame();

    }

    public void startGame()
    {
        thread = new Thread(this);
        thread.start();
    }
    public void init()
    {
        music = LoadFile.LoadSound("Pictetric/music.wav");
        music.loop(Clip.LOOP_CONTINUOUSLY);
        this.menu = new Menu(this);
        this.playing = new Playing(this);
        this.guide = new Guide(this);
    }

    public void update()
    {
        switch(GameState.state)
        {
            case Menu:
                menu.update();
                break;
            case Playing:
                playing.update();
                break;
        }
    }

    public void render(Graphics g)
    {
        switch(GameState.state)
        {
            case Menu:
                menu.draw(g);
                break;
            case Guide:
                guide.draw(g);
                break;
            case Playing:
                //System.out.println(GameState.state);
                playing.draw(g);
                break;
        }
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0/FPS;
        double timePerUpdate = 1000000000.0/UP_FPS;

        long previousTime = System.nanoTime();

        int frame = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double delU = 0;
        double delF = 0;

        while(true)
        {
            long current = System.nanoTime();

            delU += (current - previousTime)/timePerUpdate;
            delF += (current - previousTime)/timePerFrame;
            // System.out.println(delU + " " + delF);

            previousTime = current;

            if(delU >= 1)
            {
                update();
                updates++;
                delU--;
            }

            if(delF >= 1)
            {
                gameScreen.repaint();
                frame++;  
                delF--; 
            }

            if(System.currentTimeMillis() - lastCheck >= 1000)
            {
                lastCheck = System.currentTimeMillis();
                frame = 0;
                updates = 0;
            }
        }
    }

    public Menu getMenu()
    {
        return menu;
    }
    public Playing getPlaying()
    {
        return playing;
    }
    public Guide getGuide()
    {
        return guide;
    }

    public GameScreen getGameScreen()
    {
        return this.gameScreen;
    }

    public void setPlaying(Playing playing)
    {
        this.playing = playing;
    }

    public boolean getPlayMusic()
    {
        return this.playMusic;
    }

    public void setPlayMusic(boolean playMusic)
    {
        this.playMusic = playMusic;
    }

    public Clip getMusic()
    {
        return this.music;
    }

}
