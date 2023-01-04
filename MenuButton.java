import java.awt.image.BufferedImage;
import java.awt.Rectangle;


import java.awt.Graphics;

public class MenuButton {
    
    private GameState state;
    private BufferedImage img;
    private int xPos, yPos, width, height;

    private int index;
    private boolean mouseMove, mousePressed;

    private Rectangle bounds;

    public MenuButton(GameState state, BufferedImage img, int xPos, int yPos,int witdh, int height, int index)
    {
        this.state = state;
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = witdh;
        this.height = height;
        this.index = index;
        this.img = img;
        initBounds();
    }  

    private void initBounds() {
        bounds = new Rectangle(xPos, yPos, width, height);
    }

    
    public void drawMenuButton(Graphics g)
    {
        if(this.isMouseMove()) {
            g.drawImage(img, (int)(xPos - width * 0.1), (int)(yPos - height * 0.1), (int)(width * 1.2), (int)(height * 1.2), null);
        } else {
            g.drawImage(img, xPos, yPos, width, height, null);
        }	
    }

    public void setMousePressed(boolean mousePressed)
    {
        this.mousePressed = mousePressed;
    }

    public void setMouseMove(boolean mouseMove)
    {
        this.mouseMove = mouseMove;
    }

    public boolean isMousePressed()
    {
        return this.mousePressed;
    }
    public boolean isMouseMove()
    {
        return this.mouseMove;
    }

    public void resetMouse()
    {
        this.mouseMove = false;
        this.mousePressed = false;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void applyGameState()
    {
        
        GameState.state = state;
    }

    public int getXPos()
    {
        return this.xPos;
    }
    public int getYPos()
    {
        return this.yPos;
    }

    public int getIndex()
    {
        return this.index;
    }

    public GameState getState()
    {
        return state;
    }

    public void setImg(BufferedImage img)
    {
        this.img = img;
    }
}
