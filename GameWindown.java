import javax.swing.JFrame;

public class GameWindown extends JFrame
{
    public GameWindown(GameScreen gameScreen)
    {
        setTitle("EX5: Game Tetris");
        setSize(500, 630);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE); //Jframe dung chuong trinh se tat theo
        setLocationRelativeTo(null); //Hien Thi Giua Man Hinh Windown
        add(gameScreen);

    } 
}
