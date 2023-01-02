import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Menu extends State implements StateMethods{



    private List<MenuButton> buttons;
    private BufferedImage backGround;
    private BufferedImage continuePlay;
	private BufferedImage newGame;
	private BufferedImage howtoplay;
    private BufferedImage quit;
    private BufferedImage sound;
    private BufferedImage selectLevel;
    private BufferedImage inform;

    private MenuButton btnSound;
    private MenuButton btnInform;
    private MenuButton btnContinue;
    private MenuButton btnNewGame;
    private MenuButton btnHowToPlay;
    private MenuButton btnQuit;

    private MenuButton btnSelectLv;
    private MenuButton btnPlay;
	private MenuButton btnCancel;
	private MenuButton btnLeft;
	private MenuButton btnRight;
    private MenuButton btnClose;

    private boolean played;
    private boolean choice_level;
    private boolean last_update;
    private boolean showInform;

    private int level;
    

    public Menu(Game game)
    {
        super(game);
        played = false;
        choice_level = false;
        showInform = false;
        last_update = getGame().getPlayMusic();
        level = 1;
        loadImg();
        loadButtons();
    }

    private void loadImg()
    {
        backGround = LoadFile.loadImage("Pictetric/bgm.png");
        continuePlay = LoadFile.loadImage("Pictetric/continue.png");
		newGame = LoadFile.loadImage("Pictetric/newgame.png");
		howtoplay = LoadFile.loadImage("Pictetric/howtoplay.png");
        quit = LoadFile.loadImage("Pictetric/quit.png");

        selectLevel = LoadFile.loadImage("Pictetric/selectLevel.png");

        inform = LoadFile.loadImage("Pictetric/chamHoi.jpg");

        if(!getGame().getPlayMusic()){
            sound = LoadFile.loadImage("Pictetric/muteSound.png");
        }
        else{
            sound = LoadFile.loadImage("Pictetric/sound.png");
        }
    }

    public int getLevel()
    {
        return this.level;
    }

    private void loadButtons()
    {
        buttons = new ArrayList<MenuButton>();

        //Khoi Tao MenuButton
        //infor - sound - continue - newgame - howtoplay - quit
        btnInform = new MenuButton(GameState.Menu, inform, 350, 10, 50, 50, 0);
        btnClose = new MenuButton(GameState.Menu, LoadFile.loadImage("Pictetric/close.png"), 252, 10, 39, 40, 1);
        btnSound = new MenuButton(GameState.Menu, sound, 425, 10, 50, 50, 2);
        
        btnContinue = new MenuButton(GameState.Playing, continuePlay,315, 270, 145, 70, 3);
        btnNewGame = new MenuButton(GameState.Menu, newGame,315, 350, 145, 70, 4);
        btnHowToPlay = new MenuButton( GameState.Guide, howtoplay,315, 430, 145, 70, 5);
        btnQuit  = new MenuButton(GameState.Menu, quit,315, 510, 145, 70, 6);

        //Khi nao nhan hien thi khong bat su kien nhap chinh no
        btnSelectLv = new MenuButton(GameState.Menu, selectLevel, 60, 50, 330, 236, 7);
        
        btnLeft = new MenuButton(GameState.Menu, LoadFile.loadImage("Pictetric/left.png"), 100, 140, 50, 50, 8);
		btnRight = new MenuButton(GameState.Menu, LoadFile.loadImage("Pictetric/right.png"), 300, 140, 50, 50, 9);
        btnPlay = new MenuButton(GameState.Playing, LoadFile.loadImage("Pictetric/play.png"), 85, 255, 128, 53, 10);
        btnCancel = new MenuButton(GameState.Menu, LoadFile.loadImage("Pictetric/cancel.png"), 225, 255, 128, 53, 11);

        buttons.add(btnInform);
        buttons.add(btnClose);
        buttons.add(btnSound);

        buttons.add(btnContinue);
        buttons.add(btnNewGame);
        buttons.add(btnHowToPlay);
        buttons.add(btnQuit);
        
        buttons.add(btnLeft);
        buttons.add(btnRight);
        buttons.add(btnPlay);
        buttons.add(btnCancel);

    }
   
    @Override
    public void update() {
        if(!getGame().getPlayMusic() && getGame().getPlayMusic() != last_update) {
            getGame().getMusic().stop();
            sound = LoadFile.loadImage("Pictetric/muteSound.png");
            buttons.get(2).setImg(sound);
        }
        else if(getGame().getPlayMusic() && getGame().getPlayMusic() != last_update){
            getGame().getMusic().start();
            sound = LoadFile.loadImage("Pictetric/sound.png");
            buttons.get(2).setImg(sound);
        }
        last_update = getGame().getPlayMusic();
    }

    @Override
    public void draw(Graphics g) 
    {
        new MenuButton(GameState.Menu, backGround,0, 0, 500, 630, 0).drawMenuButton(g);
        buttons.get(0).drawMenuButton(g);
        buttons.get(2).drawMenuButton(g);
        
        if(showInform)
        {
            new MenuButton(GameState.Menu, LoadFile.loadImage("Pictetric/TrKien.png"), 40, 10, 250, 250, 12).drawMenuButton(g);
            btnClose.drawMenuButton(g);
        }

        if(choice_level)
        {
            btnSelectLv.drawMenuButton(g);
            btnLeft.drawMenuButton(g);
            btnRight.drawMenuButton(g);
            btnPlay.drawMenuButton(g);
            btnCancel.drawMenuButton(g);
            g.setColor(Color.white);
			g.setFont(new Font("Comic Sans MS", Font.BOLD, 55));
			g.drawString("" + level, 210, 185);
        }


        if(!played && !choice_level)
        {
            for(int i = 4; i<=6; i++)
            {
                buttons.get(i).drawMenuButton(g);
            }
       }
       else if(played && choice_level == false)
       {
            for(int i = 3; i<=6; i++)
            {
                buttons.get(i).drawMenuButton(g);
            }
       }
    }

    @Override
    public void MouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        for(MenuButton button:buttons)
        {
            if(isButton(e, button) && button.getXPos() > 0 && button.getYPos() > 0)
            {
                button.setMouseMove(true);
            }
            else
            {
                button.setMouseMove(false);
            }
        }
        
    }

    @Override
    public void MousePressed(MouseEvent e) {

        if(choice_level)
        {
            
            for(int i = 7; i<11; i++)
            {
                if(isButton(e, buttons.get(i)))
                {
                    buttons.get(i).setMousePressed(true);
                }
            }
        }
        
        else
        { 
            for(int i = 0; i<=6; i++)
            {
                if(isButton(e, buttons.get(i)))
                {
                    buttons.get(i).setMousePressed(true);
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for(MenuButton button:buttons)
        {
            if(isButton(e, button))
            {
                if(button.isMousePressed())
                {
                    if(button.getIndex() == 0) //infor
                    {
                        showInform = true;
                        
                    }
                    if(button.getIndex() == 1) //closeshowinfo 
                    {
                        showInform = false;
                    }
                    if(button.getIndex() == 2) //sound
                    {
                        getGame().setPlayMusic(!getGame().getPlayMusic());
                    }
                    if(button.getIndex() == 3 && !choice_level && played) //Continue
                    {
                        button.applyGameState();
                        this.showInform = false;
                    }
                    if(button.getIndex() == 4 && !choice_level) //NewGame
                    {
                        this.choice_level = true;
                        this.showInform = false; 
                    }
                    if(button.getIndex() == 5 && !choice_level)//HowToPlay
                    {
                        button.applyGameState();
                    }
                    if(button.getIndex() == 6 &&  !choice_level) //Exit
                    {
                        System.exit(0);
                    }
                    if(button.getIndex() == 8) //left
                    {
                        if(this.level > 1)
                            this.level--;
                    }
                    if(button.getIndex() == 9) //right
                    {
                        if(this.level < 6)
                            this.level++;
                    }
                    if(button.getIndex() == 10) //Play
                    {
                        choice_level = false;
                        this.played = true;
                        button.applyGameState();
                        getGame().setPlaying(new Playing(getGame()));
                    }
                    if(button.getIndex() == 11) //Cancel
                    {
                        this.level = 1;
                        this.choice_level = false;
                    }
                    break;
                }
            }
        }
        resetButton();
    }

    public void resetButton()
    {
        for(MenuButton button:buttons)
        {
            button.resetMouse();
        }
    }

    @Override
    public void KeyClicked(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void KeyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            GameState.state = GameState.Playing;
        }
        
    }

    @Override
    public void KeyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }
    
    public BufferedImage getSound()
    {
        return this.sound;
    }

    public void setSound(BufferedImage sound)
    {
        this.sound = sound;
    }
}
