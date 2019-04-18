package piece;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;
import view.HighLight;

public class Knight extends Piece {
    public Knight(char file, int rank, int color){
        super(file, rank, color);
        try {
            if(color == 0)
                this.image = ImageIO.read(new File("img/Knight0.png"));
            else
                this.image = ImageIO.read(new File("img/Knight1.png"));
        
        }
        catch (IOException e) {
        }
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
        
        if(x+1 < 8 && y+2 <8){
            p = hash.get(""+((char)(x+1 +65))+(y+2));
            if(p == null){
                high.add(new HighLight(x+1,y+2));
            }else if(p.getColor() != this.color){
                high.add(new HighLight(x+1,y+2));
            }else if(check_test == true){
                high.add(new HighLight(x+1,y+2));
            }
                
        }
        if(x-1 >-1  && y+2 <8){
            p = hash.get(""+((char)(x-1 +65))+(y+2));
            if(p == null){
                high.add(new HighLight(x-1,y+2));
            }else if(p.getColor() != this.color){
                 high.add(new HighLight(x-1,y+2));
            }else if(check_test == true){
                high.add(new HighLight(x-1,y+2));
            }
        }
        
        
        if(x+2 < 8 && y+1 <8){
            p = hash.get(""+((char)(x+2 +65))+(y+1));
            if(p == null){
                high.add(new HighLight(x+2,y+1));
            }else if(p.getColor() != this.color){
                high.add(new HighLight(x+2,y+1));                
            }else if(check_test == true){
                high.add(new HighLight(x+2,y+1));
            }
        }
        if(x-2 > -1 && y+1 <8){
            p = hash.get(""+((char)(x-2 +65))+(y+1));
            if(p == null){
                high.add(new HighLight(x-2,y+1));
            }else if(p.getColor() != this.color){
                high.add(new HighLight(x-2,y+1));
            }else if(check_test == true){
                high.add(new HighLight(x-2,y+1));
            }
        }
        
        
        if(x-2 > -1 && y-1 >-1){
            p = hash.get(""+((char)(x-2 +65))+(y-1));
            if(p == null){
                high.add(new HighLight(x-2,y-1));
            }else if(p.getColor() != this.color){
                high.add(new HighLight(x-2,y-1));
            }else if(check_test == true){
                high.add(new HighLight(x-2,y-1));
            }
        }
        if(x+2 < 8 && y-1 > -1){
            p = hash.get(""+((char)(x+2 +65))+(y-1));
            if(p == null){
                high.add(new HighLight(x+2,y-1));
            }else if(p.getColor() != this.color){
                high.add(new HighLight(x+2,y-1));
            }else if(check_test == true){
                high.add(new HighLight(x+2,y-1));
            }
        }
        
        if(x+1 < 8 && y-2 >-1){
            p = hash.get(""+((char)(x+1 +65))+(y-2));
            if(p == null){
                high.add(new HighLight(x+1,y-2));
            }else if(p.getColor() != this.color){
                high.add(new HighLight(x+1,y-2));
            }else if(check_test == true){
                high.add(new HighLight(x+1,y-2));
            }
        }
        if(x-1 > -1 && y-2 > -1){
            p = hash.get(""+((char)(x-1 +65))+(y-2));
            if(p == null){
                high.add(new HighLight(x-1,y-2));
            }else if(p.getColor() != this.color){
                high.add(new HighLight(x-1,y-2));
            }else if(check_test == true){
                high.add(new HighLight(x-1,y-2));
            }
        }
        
        return high;
    }
    
}
