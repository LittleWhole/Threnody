package map;

import core.Main;
import entities.units.player.Player;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;

public class GameMap extends TiledMap {

    private int tileId;
    private int layerIndex;
    private final boolean[][] solids;
    private final Polygon[][] hitboxes;

    public GameMap(String ref) throws SlickException {
        super(ref, true);
        solids = new boolean[super.getWidth()][super.getHeight()];
        hitboxes = new Polygon[super.getWidth()][super.getHeight()];
        generateHitboxes();
        tileId = 0;
        layerIndex = 0;
    }

    public void generateHitboxes()   {
        for(int i = 0; i < hitboxes.length; i++)    {
            for(int j = 0; j < hitboxes[i].length; j++)    {
                if(getTileId(i,j,1) != 0)   {
                    if(getTileProperty(getTileId(i,j,1), "solid", "false").equals("true"))  {
                        hitboxes[i][j] = new Polygon();
                        hitboxes[i][j].addPoint(tileWidth/2f,tileHeight*1.5f);
                        hitboxes[i][j].addPoint(tileWidth,((float)Math.sin(Math.PI/6)*tileWidth));
                        hitboxes[i][j].addPoint(tileWidth/2f,((float)Math.cos(Math.PI/3)*tileHeight));
                        hitboxes[i][j].addPoint(0 ,((float)Math.sin(Math.PI/6)*tileWidth));
                        hitboxes[i][j].setClosed(true);

                        hitboxes[i][j].setCenterX((Main.getScreenWidth()*0.54f)-((j-i)*tileWidth/2f));
                        hitboxes[i][j].setCenterY((((Main.getScreenHeight()*0.72f))-(this.width*tileHeight/2f))+((i+j)*tileHeight/2f));
                    }
                }
            }
        }
    }

    public void updateHitboxes(float dx, float yx)    {
        for(int i = 0; i < hitboxes.length; i++)    {
            for(int j = 0; j < hitboxes[i].length; j++)    {

                if(hitboxes[i][j] != null)   {
                    hitboxes[i][j].setX(hitboxes[i][j].getX()+dx);
                    hitboxes[i][j].setY(hitboxes[i][j].getY()+yx);
                }
            }
        }
    }


    public void render(Player plr) {
        for (int i = 0; i < super.getLayerCount(); i++) super.render((int) (-plr.getX()),(int)(-plr.getY()), i);
    }

    public int getTileId() {
        return tileId;
    }

    public int getLayerIndex() {
        return layerIndex;
    }

    public boolean[][] getSolids() {
        return solids;
    }

    public Shape[][] getHitboxes() {
        return hitboxes;
    }
    public Shape getSingleHitbox(int i, int j){
        return hitboxes[i][j];
    }

    public void setTileId(int tileId) {
        this.tileId = tileId;
    }

    public void setLayerIndex(String layerName) {
        this.layerIndex = super.getLayerIndex(layerName);
    }

    public void setSolid(int i, int j, boolean solid) {
        this.solids[j][i] = solid;
    }

    public void setHitbox(int i, int j, Polygon hitbox) {
        this.hitboxes[j][i] = hitbox;
    }

    //other methods
    public void printSolidMatrix(){
        System.out.println("SOLID MATRIX");
        for(int i=0;i<super.getWidth();i++){
            for(int j=0;j<super.getHeight();j++){
                System.out.print(solids[j][i]+" ");
            }
            System.out.println();
        }
    }

    public void drawDebugRects(Graphics g){
        g.setColor(new Color(255,0,0,0.5f));
        layerIndex = this.getLayerIndex();
        tileId = 0;
        for(int i=0;i<this.getWidth();i++){
            for(int j=0;j<this.getHeight();j++){
                tileId = this.getTileId(j, i, layerIndex);
                if(hitboxes[j][i] != null) {
                    g.fill(hitboxes[j][i]);
                }
            }
        }
    }
    public boolean collidesWith(Shape s){
        for(int i=0;i<this.getWidth();i++){
            for(int j=0;j<this.getHeight(); j++){
                if(hitboxes[j][i] != null) {
                    if (s.intersects(hitboxes[j][i]) && solids[j][i]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
