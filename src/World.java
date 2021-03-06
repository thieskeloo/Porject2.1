import java.awt.*;

public class World {

    public static int width,height;
    private int spawnX,spawnY;
    private int[][] tiles;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private Entity.Manager entityManager;
    public int tileWidth, tileHeight;
    private Handler handler;
    public Player ball;
    public Physics engine;
    private double vectorx = 0;
    private double vectory = 0;
    private int allowedDraw=500;

    public World(Handler handler,String path){
        loadWorld(path);
        this.handler=handler;
        this.tileWidth = (int)((double) Tile.TILE_WIDTH/ (double) (1920.0/screenSize.width));
        this.tileHeight = (int)((double) Tile.TILE_HEIGHT/ (double) (1080.0/screenSize.height));
        System.out.println(screenSize);
    }

    public void init(){
        this.ball = new Player(handler, Assets.ball,450, 450, 16, 16);
    }

    public void tick(){
        this.ball.tick();
    }

    public void render(Graphics g){
        for(int y=0;y<height;y++){
          for(int x=0;x<width;x++){
              getTile(x,y).render(g,x*tileWidth,y*tileHeight);//A loop that is getting the current tile from the getTile method in order to
          }                                                                         //render it ...Draws the map
        }
        g.setColor(Color.BLUE);
       /* if(Math.sqrt(Math.pow(this.handler.getMouseManager().getMouseX(),2)+(Math.pow(this.handler.getMouseManager().getMouseY(),2)))-(ball.x + ball.width/2)>500){
            double angle = Math.atan(this.handler.getMouseManager().getMouseY()/this.handler.getMouseManager().getMouseX());
            vectory=allowedDraw*Math.sin(angle);
            vectorx=allowedDraw*Math.cos(angle);
            g.drawLine((int) (ball.x + ball.width / 2), (int) (ball.y + ball.height / 2),(int) (ball.x+ball.width/2)+500,(int)(ball.y + ball.height / 2)+500);
        }
        else {*/
            g.drawLine((int) (ball.x + ball.width / 2), (int) (ball.y + ball.height / 2), this.handler.getMouseManager().getMouseX(), this.handler.getMouseManager().getMouseY());
       // }
        this.ball.render(g);
    }

    public Tile getTile(int x,int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) {                     //if the tile is something outside our map , it will assume it is water so we can
            return Tile.waterTile;                        //restart the game
        }
        Tile t = Tile.tiles[tiles[x][y]];
        if (t == null) {
            return Tile.waterTile;
        }
        return t;
    }

/*
it is reading from the path(our text document) each number should be divided by a space , first row : 2 integers , height/width of the map
second row 2 integers: coords where our ball will spawn( we will add that later and when we add we have to uncomment spawnX and spawnY and instead of +2 change to +4)
third row : here is the map , as much integers as we set the width and as many rows as we set the height
 */
    private void loadWorld(String path){
        String file = Util.loadFileAsString(path);
        String[] tokens = file.split("\\s+");
        this.width = Util.parseInt(tokens[0]);
        this.height=Util.parseInt(tokens[1]);
        System.out.println("worldHeight = "+this.height);
       // spawnX= Util.parseInt(tokens[2]);
        //spawnY=Util.parseInt(tokens[3]);

        tiles=new int[width][height];
        for(int y=0;y<height;y++) {
            for(int x =0 ; x<width;x++) {
                tiles[x][y]=Util.parseInt(tokens[(x+y*width)+2]);//later change to +4 when spawn is added
            }
        }
    }


    //Getters and setters
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }

}
