import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Font;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import java.awt.image.BufferedImage;

public class Playing extends State implements StateMethods {

    private static final int BOARDX = 10; //Truc ngang
    private static final int BOARDY = 20; // Truc doc
    private static final int BLOCKSIZE = 30; //Kich thuoc moi cell

    private TetrisBlock currentBlock;
    private TetrisBlock nextBlock;
    private TetrisBlock[] blocks; //Mang chua cac khoi I J L O S T Z

    private Color[][] background; //Mang dung de luu vi tri mau cua cac khoi da roi

    private boolean gameOver;
    private boolean gamePause;
    private int score;
    
    private List<MenuButton> buttons;
    private BufferedImage pause;
    private BufferedImage stopPause;

    private BufferedImage sound;
	private BufferedImage home;
    private BufferedImage reset;


    private boolean last_update;

    public Playing(Game game) {
        super(game);
        background = new Color[BOARDY][BOARDX];
        loadImgs();
        loadButtons();
        gameOver = false;
        gamePause = true;
        last_update = getGame().getPlayMusic();
        this.score = 0;
        spawNextBlock();
        spawCurrentBlock();
    }

    private void loadImgs()
    {
        pause = LoadFile.loadImage("Pictetric/pause.png");
        stopPause = LoadFile.loadImage("Pictetric/stopPause.png");
        if(!getGame().getPlayMusic())
        {
            sound = LoadFile.loadImage("Pictetric/muteSound.png");
        }
        else{
            sound = LoadFile.loadImage("Pictetric/sound.png");
        }
		home = LoadFile.loadImage("Pictetric/home.png");
        reset = LoadFile.loadImage("Pictetric/playagain.png");
    }

    private void loadButtons()
    {
        buttons = new ArrayList<>();
        buttons.add(new MenuButton(GameState.Playing, pause, 310, 340, 85, 85, 0));
        buttons.add(new MenuButton(GameState.Playing, stopPause, 310, 340, 85, 85, 1));
        buttons.add(new MenuButton(GameState.Menu, home, 310, 450, 85, 85, 2));
        buttons.add(new MenuButton(GameState.Playing, reset, 405, 460, 85, 85, 3));
        buttons.add(new MenuButton(GameState.Playing, sound, 405, 340, 85, 85, 4));
    }

    public void drawMenuInGame(Graphics g)
    {
        for(int i = 0; i<buttons.size(); i++)
        {
            if(gamePause == false && i == 0)
                continue;
            if(gamePause == true && i == 1)
                continue;

            buttons.get(i).drawMenuButton(g);
        }
    }


    // Khoi tao cac khoi I T Z L S...
    public void initBlock() {
        blocks = new TetrisBlock[] { new IShape(), new LShape(),
                new JShape(), new OShape(), new SShape(),
                new TShape(), new ZShape() };
    }

    // Draw Table
    public void drawTable(Graphics g) {
        g.setColor(Color.BLACK);
        for (int row = 0; row < BOARDY; row++) {
            g.drawLine(0, row * BLOCKSIZE, BLOCKSIZE * BOARDX, BLOCKSIZE * row);
        }

        for (int col = 0; col <= BOARDX; col++) {
            g.drawLine(col * BLOCKSIZE, 0, col * BLOCKSIZE, getGame().getGameScreen().getHeight());
        }
    }

    public void drawTableScore(Graphics g) {
        g.setFont(new Font("Arial Black", Font.BOLD, 18));
        g.setColor(Color.BLACK);
        g.drawRect(320, 230, 85, 100);
        g.drawString("SCORE", 325, 250);
    }

    public void drawScore(Graphics g)
    {
        g.setFont(new Font("Arial Black", Font.BOLD, 18));
        g.setColor(Color.BLACK);
        g.drawString("" + this.score, 325, 295);
    }

    public void drawTableLevel(Graphics g) {
        g.setFont(new Font("Arial Black", Font.BOLD, 18));
        g.setColor(Color.BLACK);
        g.drawRect(405, 230, 75, 100);
        g.drawString("LEVEL", 410, 250);
    }

    public void drawLevel(Graphics g)
    {
        g.setFont(new Font("Arial Black", Font.BOLD, 18));
        g.setColor(Color.BLACK);
        g.drawString("" + getGame().getMenu().getLevel(), 435, 295);
    }

    public void gameOver(Graphics g)
    {
        if(gameOver)
        {
            BufferedImage gameOver = LoadFile.loadImage("Pictetric/gameover.png");
            g.drawImage(gameOver, 10, 100, 290, 400,null);
        }
    }

    public void gamePause(Graphics g)
    {
        if(gamePause == false && gameOver == false)
        {
            BufferedImage gamePause = LoadFile.loadImage("Pictetric/pauseGame.png");
            g.drawImage(gamePause, 10, 100, 280, 280,null);
        }
    }

    // Random block dau tien
    public void spawNextBlock() {
        initBlock();
        Random r = new Random();
        nextBlock = blocks[r.nextInt(blocks.length)];
    }

    // Random block tiep theo
    public void spawCurrentBlock() {
        currentBlock = (TetrisBlock)nextBlock.clone();
        currentBlock.spaw(BOARDX);
        spawNextBlock();
        currentBlock.setSpeedLevel(getGame().getMenu().getLevel());
    }

    // Check Game Ket Thuc
    public boolean checkOver() {
        if (currentBlock.getY() < 0)
        {
            return true;
        }
        return false;
    }

    private boolean checkBottom() {
        //System.out.println(currentBlock.getBottomEdge());
        if (currentBlock.getBottomEdge() == BOARDY)
        {
            return true;
        }
            
        int[][] shape = currentBlock.getShape();
        int shapeX = currentBlock.getWidth(); // X
        int shapeY = currentBlock.getHeight(); // Y

        for (int x = 0; x < shapeX; x++) {
            for (int y = shapeY - 1; y >= 0; y--) {
                if (shape[y][x] != 0) {
                    int xPos = x + currentBlock.getX();
                    int yPos = y + currentBlock.getY() + 1;
                    // System.out.println(yPos + " " + yPos);
                    if (yPos < 0)
                        break;

                    if (background[yPos][xPos] != null)
                        return true;
                    break;
                }
            }
        }
        return false;
    }

    private boolean checkLeft() {
        if (currentBlock.getLeftEdge() == 0)
            return true;

        int[][] shape = currentBlock.getShape();
        int shapeX = currentBlock.getWidth();
        int shapeY = currentBlock.getHeight();

        for (int y = 0; y < shapeY; y++) {
            for (int x = 0; x < shapeX; x++) {
                if (shape[y][x] != 0) {
                    int xPos = x + currentBlock.getX() - 1;
                    int yPos = y + currentBlock.getY();

                    if (yPos < 0)
                        break;

                    if (background[yPos][xPos] != null)
                        return true;
                    break;
                }
            }
        }
        return false;
    }

    private boolean checkRight() {
        if (currentBlock.getRightEdge() == BOARDX)
            return true;

        int[][] shape = currentBlock.getShape();
        int shapeX = currentBlock.getWidth();
        int shapeY = currentBlock.getHeight();

        for (int y = 0; y < shapeY; y++) {
            for (int x = shapeX - 1; x >= 0; x--) {
                if (shape[y][x] != 0) {
                    int xPos = x + currentBlock.getX() + 1;
                    int yPos = y + currentBlock.getY();

                    if (yPos < 0)
                        break;

                    if (background[yPos][xPos] != null)
                        return true;
                    break;
                }
            }
        }

        return false;
    }

    // Draw Block
    public void drawCurrentBlock(Graphics g) {
        for (int row = 0; row < currentBlock.getHeight(); row++) {
            for (int col = 0; col < currentBlock.getWidth(); col++) {
                if (currentBlock.getShape()[row][col] == 1) {
                    int x = (currentBlock.getX() + col) * BLOCKSIZE;
                    int y = (currentBlock.getY() + row) * BLOCKSIZE;
                    drawGridSquare(g, currentBlock.getColor(), x, y);
                }
            }
        }
    }

    public void drawNextBlock(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect(320, 40, 150, 150);
        g.setFont(new Font("Arial Black", Font.BOLD, 18));
        g.drawString("NEXT", 370, 35);
        g.drawRect(320, 10, 150, 30);

        for (int row = 0; row < nextBlock.getHeight(); row++) {
            for (int col = 0; col < nextBlock.getWidth(); col++) {
                if (nextBlock.getShape()[row][col] == 1) {
                    int x = (col) * BLOCKSIZE + nextBlock.getHeight() * BLOCKSIZE / 2 + 320;
                    int y = (row) * BLOCKSIZE + 40 + nextBlock.getWidth() * BLOCKSIZE / 2;
                    drawGridSquare(g, nextBlock.getColor(), x, y);
                }
            }
        }
    }

    public void drawBackground(Graphics g) {
        if(currentBlock == null)
            return;
        Color color;
        for (int y = 0; y < BOARDY; y++) {
            for (int x = 0; x < BOARDX; x++) {
                color = background[y][x];
                if (color != null) {
                    int xPos = (x) * BLOCKSIZE;
                    int yPos = (y) * BLOCKSIZE;
                    drawGridSquare(g, color, xPos, yPos);
                }
            }
        }
    }

    public void drawGridSquare(Graphics g, Color color, int x, int y) {
        g.setColor(color);
        g.fillRect(x, y, BLOCKSIZE, BLOCKSIZE);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, BLOCKSIZE, BLOCKSIZE);
    }

    // Move
    public void moveBlockDown() {
        if (checkBottom()&&gamePause)// Cham Day
        {
            moveBlockBackground();
            clearLines();
            if(checkOver())
            {
                gameOver = true;
                return;
            }
            else
                spawCurrentBlock();
            return;
        }
        if(gamePause)
            currentBlock.moveDown();

        return;
    }

    public void moveBlockLeft() {
        if(gameOver == true)
            return;
        if(currentBlock == null)
            return;
        if (checkLeft())
            return;
        if(gamePause)
            currentBlock.moveLeft();
    }

    public void moveBlockRight() {
        if(gameOver == true && gamePause)
            return;
        if(currentBlock == null)
            return;
        if (checkRight())
            return;
        if(gamePause)
            currentBlock.moveRight();
    }

    // Co dinh khoi khi cham day
    private void moveBlockBackground() {
        if(gameOver == true)
            return;
        int[][] shape = currentBlock.getShape();
        int row = currentBlock.getHeight();
        int col = currentBlock.getWidth();

        int xPos = currentBlock.getX();
        int yPos = currentBlock.getY();
        Color color = currentBlock.getColor();

        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                if (shape[r][c] != 0) {
                    if(r+yPos < 0)
                        break;
                    background[r + yPos][c + xPos] = color; //Danh Dau Vi Tri Da Co Block
                }
            }
        }
    }

    // Clear
    public void clearLines() {
        boolean lines = true;
        int line = 0;
        for (int y = 0; y < BOARDY; y++) {
            lines = true;
            for (int x = 0; x < BOARDX; x++) {
                if (background[y][x] == null) {
                    lines = false; //Dong con o chua hoan thanh
                    break;
                }
            }
            if (lines == true) {
                line++;
                clearLine(y);
                shiftDown(y);
                clearLine(0);

            }
        }
        this.score += 10*line;
    }

    public void clearLine(int y) {
        for (int i = 0; i < BOARDX; i++) {
            background[y][i] = null;
        }
    }

    public void shiftDown(int y) {
        for (int yPos = y; yPos > 0; yPos--) {
            for (int x = 0; x < BOARDX; x++) {
                background[yPos][x] = background[yPos - 1][x];
            }
        }
    }

    // Rotate
    public void rotateShape() {
        if(!gamePause || gameOver)
            return;

        int[][] rotatedShape = null;

        rotatedShape = currentBlock.transposeMatrix(currentBlock.getShape());

        rotatedShape = currentBlock.reverseRows(rotatedShape);

        if ((currentBlock.getX() + rotatedShape[0].length > 10) || (currentBlock.getY() + rotatedShape.length > 20)) {
            return;
        }

        for (int row = 0; row < rotatedShape.length; row++) {
            for (int col = 0; col < rotatedShape[row].length; col++) {
                if (rotatedShape[row][col] != 0) {
                    if (currentBlock.getY() + row < 0)
                        break;
                    if (background[currentBlock.getY() + row][currentBlock.getX() + col] != null) {
                        return;
                    }
                }
            }
        }
        currentBlock.setShape(rotatedShape);
    }

    @Override
    public void update() {
        if(!getGame().getPlayMusic() && getGame().getPlayMusic() != last_update) {
            getGame().getMusic().stop();
            sound = LoadFile.loadImage("Pictetric/muteSound.png");
            buttons.get(4).setImg(sound);
        }
        else if(getGame().getPlayMusic() && getGame().getPlayMusic() != last_update){
            getGame().getMusic().start();
            sound = LoadFile.loadImage("Pictetric/sound.png");
            buttons.get(4).setImg(sound);
        }
        last_update = getGame().getPlayMusic();
    }

    @Override
    public void draw(Graphics g) {
        drawTableScore(g);
        drawScore(g);
        drawLevel(g);
        drawTableLevel(g);
        drawTable(g);
        drawMenuInGame(g);
    
        drawBackground(g);
        drawCurrentBlock(g);
        drawNextBlock(g);
        gameOver(g);
        moveBlockDown();
        gamePause(g);
    }

    @Override
    public void MouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for(MenuButton button: buttons)
        {
            if(isButton(e, button)){
                button.setMouseMove(true);
            }
            else{
                button.setMouseMove(false);
            }
        }
        
    }

    @Override
    public void MousePressed(MouseEvent e) {
        for(MenuButton button:buttons)
        {
            if(isButton(e, button))
            {
                button.setMousePressed(true);
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
                    if(button.getIndex() == 2)
                    {
                        button.applyGameState();
                    }
                    else if(button.getIndex() == 0 || button.getIndex() == 1)
                    {
                        gamePause = !gamePause;
                    }
                    else if(button.getIndex() == 3)
                    {
                        getGame().setPlaying(new Playing(getGame()));
                        
                    }
                    else if(button.getIndex() == 4)
                    {
                        getGame().setPlayMusic(!getGame().getPlayMusic());
                    }
                }
                break;
            }
        }
        resetButton();
    }

    public void resetButton()
    {
        for(MenuButton button:buttons)
            button.setMousePressed(false);
    }

    @Override
    public void KeyClicked(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void KeyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                // System.out.println("press");
                moveBlockLeft();
                break;
            case KeyEvent.VK_RIGHT:
                moveBlockRight();
                break;
            case KeyEvent.VK_UP:
                rotateShape();
                break;
            case KeyEvent.VK_DOWN:
                currentBlock.setSpeedUp();
                break;
        }

    }

    @Override
    public void KeyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            currentBlock.setSpeedNormal();
        }
        
    }

}
