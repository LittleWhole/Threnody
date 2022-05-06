package map;

import entities.units.player.Player;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;

public class GameMap extends TiledMap {

    private int tileId;
    private int layerIndex;
    private boolean solids[][];
    private Shape hitboxes[][];

    public GameMap(String ref) throws SlickException {
        super(ref, true);
        solids = new boolean[super.getWidth()][super.getHeight()];
        hitboxes = new Shape[super.getWidth()][super.getHeight()];
        tileId = 0;
        layerIndex = 0;
        super.g
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

    public void setRect(int i, int j, Shape hitbox) {
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
        layerIndex = this.getLayerIndex();
        tileId = 0;
        for(int i=0;i<this.getWidth();i++){
            for(int j=0;j<this.getHeight();j++){
                tileId = this.getTileId(j, i, layerIndex);
                g.draw(hitboxes[j][i]);
            }
        }
    }
    public boolean collidesWith(Shape s){
        for(int i=0;i<this.getWidth();i++){
            for(int j=0;j<this.getHeight(); j++){
                if(s.intersects(hitboxes[j][i]) && solids[j][i]){
                }
            }
        }
        return false;
    }

}
