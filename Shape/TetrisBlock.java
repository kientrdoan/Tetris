import java.awt.Color;

public class TetrisBlock implements Cloneable{
    
    private int[][] shape; //Tung Khoi
    private Color color;

    private int x, y;

    private int normal, fast;
	private int delay;
	
	private long now, lastTime;
	
	private boolean collision = false;
    
    public TetrisBlock(int[][] shape, Color color)
    {
        this.shape = shape; //Khoi Tao block
        this.color = color; //Khoi Tao Mau Sac Cho Block

		now = System.currentTimeMillis();
		lastTime = now;
    }


    //Khoi tao vi tri khi xuat hien
    public void spaw(int boardX)
    {
        y = -getHeight();
        x = (boardX-getWidth())/2;
    }

    public int[][] getShape() {
        return shape;
    }

    public void setShape(int[][] shapeIn) {
        this.shape = shapeIn;
    }

    public Color getColor() {
        return color;
    }

    public int getHeight()
    {
        return shape.length;
    }

    public int getWidth()
    {
        return shape[0].length;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void moveDown()
    {
        now = System.currentTimeMillis();
        if(now - lastTime > delay && !collision) {
			lastTime = now;
			this.y++;
        }
       
    }
    public void moveUp()
    {
        this.y--;
    }
    public void moveLeft()
    {
        this.x--;
    }
    public void moveRight()
    {
        this.x++;
    }

    //Rotate
    public int[][] transposeMatrix(int[][] matrix) {
        int[][] temp = new int[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                temp[j][i] = matrix[i][j];
            }
        }
        return temp;
    }

    public int[][] reverseRows(int[][] matrix) {

        int middle = matrix.length / 2;

        for (int i = 0; i < middle; i++) {
            int[] temp = matrix[i];

            matrix[i] = matrix[matrix.length - i - 1];
            matrix[matrix.length - i - 1] = temp;
        }

        return matrix;

    }


    public int getBottomEdge()
    {
        return this.y + getHeight();
    }
    public int getLeftEdge()
    {
        return this.getX();
    }
    public int getRightEdge()
    {
        return this.getX() + this.getWidth();
    }

    public void setSpeedLevel(int level)
    {
        normal = 600 - (int)(600*0.1*(level-1));
        fast = 50;
        delay = normal; //quyet dinh toc do roi xuong
    }

    public void setSpeedUp()
    {
        delay = fast;
    }
    public void setSpeedNormal()
    {
        delay = normal;
    }

    @Override
    public Object clone(){
        
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
}
