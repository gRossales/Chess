package piece;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;
import view.HighLight;

public class Pawn extends Piece {
    private boolean double_step;
    public Pawn(char file, int rank, int color){
        
        super(file, rank, color);
        double_step = false;
        try {
            if(color == 0)
                this.image = ImageIO.read(new File("img/Pawn0.png"));
            else
                this.image = ImageIO.read(new File("img/Pawn1.png"));
        
        }
        catch (IOException e) {
        }
    }

    public boolean isDouble_step() {
        return double_step;
    }

    public void setDouble_step(boolean double_step) {
        this.double_step = double_step;
    }
  
    
    
    @Override
    public void draw(Graphics g) {
        this.g = g;
        this.g.drawImage(this.image, (((int) this.file) -65)*70 , (this.rank)*70, null);
    }

    @Override
    public ArrayList<HighLight> possibleMoves(HashMap<String, Piece> hash, boolean check_test) {
        int x, y;
        Piece p;
        ArrayList<HighLight> high;
        
        high = new ArrayList<HighLight>();
        p = null;
        x = (int)this.file - 65;
        y = this.rank;
        
        if(check_test == false)
            high.add(new HighLight((int)(this.file-65),this.rank));
        
        p = hash.get(""+((char)(x+1+65))+(y));
        if(p != null && p.getColor() != this.color && p instanceof Pawn && ((Pawn)p).isDouble_step()){
            if(this.color == 1)
                high.add(new HighLight(x+1,(y+1)));
            else
                high.add(new HighLight(x+1,(y-1)));
        }
        p = hash.get(""+((char)(x-1+65))+(y));
        if(p != null && p.getColor() != this.color && p instanceof Pawn && ((Pawn)p).isDouble_step()){
            if(this.color == 1)
                high.add(new HighLight(x-1,(y+1)));
            else
                high.add(new HighLight(x-1,(y-1)));
        }
        
        if(check_test == false){
            if(this.color == 1){//  if white
                if(y+1 < 8){
                    p = hash.get(""+((char)(x+65))+(y+1));
                    if(p == null){
                        high.add(new HighLight(x,(y+1)));
                        p = hash.get(""+((char)(x+65))+(y+2));
                        if(y == 1 && p == null){
                            high.add(new HighLight(x,(y+2)));
                        }
                    }

                    if(x+1 < 8){
                        p = hash.get(""+((char)(x+1 +65))+(y+1));
                        if(p != null && p.getColor() != this.color){
                            high.add(new HighLight(x+1,(y+1))); 
                        }
                    }
                    if(x-1 > -1){
                        p = hash.get(""+((char)(x-1 +65))+(y+1));
                        if(p != null && p.getColor() != this.color){
                            high.add(new HighLight(x-1,(y+1))); 
                        }
                    }
                }   
            }else{
                if(y-1 > -1){
                    p = hash.get(""+((char)(x+65))+(y-1));
                    if(p == null){
                        high.add(new HighLight(x,(y-1)));
                        p = hash.get(""+((char)(x+65))+(y-2));
                        if(y == 6 && p == null ){
                            high.add(new HighLight(x,(y-2)));
                        }
                    }
                    if(x+1 < 8){
                        p = hash.get(""+((char)(x+1 +65))+(y-1));
                        if(p != null && p.getColor() != this.color){
                            high.add(new HighLight(x+1,(y-1))); 
                        }
                    }
                    if(x-1 > -1){
                        p = hash.get(""+((char)(x-1 +65))+(y-1));
                        if(p != null && p.getColor() != this.color){
                            high.add(new HighLight(x-1,(y-1))); 
                        }
                    }
                }      
            }
        }else{
            if(this.color == 1){
               
                high.add(new HighLight(x-1,(y+1)));
                high.add(new HighLight(x+1,(y+1)));
            }else{
                high.add(new HighLight(x-1,(y-1)));
                high.add(new HighLight(x+1,(y-1)));
            }
        }
        
        return high;
    }
    
}
