package piece;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;
import view.HighLight;



public class King extends Piece{
    private boolean check;
    private boolean moved;
    public King(char file, int rank, int color){
        super(file, rank, color);
        check = false;
        moved = false;
        try {
            if(color == 0){
                this.image = ImageIO.read(new File("img/King0.png"));
            } else{
                this.image = ImageIO.read(new File("img/King1.png"));
            }
        }
        catch (IOException e) {
        }
        
    }
    public void setCheck(boolean check){
        this.check = check;
    }
    
    public boolean getCheck(){
        return this.check;
    }

    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    
    
    
    public void draw(Graphics g) {
        this.g = g;
        this.g.drawImage(this.image, (((int) this.file) -65)*70 , (this.rank)*70, null);
    }

    @Override
    public ArrayList<HighLight> possibleMoves(HashMap<String, Piece> hash, boolean check_test) {
        int x, y;
        Piece p;
        ArrayList<HighLight> high;
        ArrayList<HighLight> high_check;
        
        high = new ArrayList<HighLight>();
        high_check = null;
        p = null;
        x = (int)this.file - 65;
        y = this.rank;
        
        if(!check){
            if(this.moved == false && hash.get("A"+y) instanceof Rook){
                if(((Rook)hash.get("A"+y)).isMoved() == false && !(hash.containsKey("B"+y))
                        && !(hash.containsKey("C"+y))){
                    high.add(new HighLight(x-2,y));
                }
            } 
            if(this.moved == false && hash.get("H"+y) instanceof Rook){ 
                if(((Rook)hash.get("H"+y)).isMoved() == false && !(hash.containsKey("E"+y))
                        && !(hash.containsKey("F"+y))){
                    high.add(new HighLight(x+2,y));
                }
            }
        }
        if(x+1 <8){
            p = hash.get(""+((char)(x+1+65))+y);
            if(p == null){
                high.add(new HighLight(x+1,y));
            }else if(p.getColor() != this.color){
                high.add(new HighLight(x+1,y));
            }
        }
        if(x-1 >-1){
            p = hash.get(""+((char)(x-1+65))+y);
            if(p == null){
                high.add(new HighLight(x-1,y));
            }else if(p.getColor() != this.color){
                high.add(new HighLight(x-1,y));
            }
        }
        if(y+1 <8){
            p = hash.get(""+((char)(x+65))+(y+1));
            if(p == null){
                high.add(new HighLight(x,y+1));
            }else if(p.getColor() != this.color){
                high.add(new HighLight(x,y+1));
            }
        }
        if(y-1 >-1){
            p = hash.get(""+((char)(x+65))+(y-1));
            if(p == null){
                high.add(new HighLight(x,(y-1)));
            }else if(p.getColor() != this.color){
                high.add(new HighLight(x,(y-1)));
            }
        }
        if(x+1 <8){
            p = hash.get(""+((char)(x+1+65))+y);
            if(p == null){
                high.add(new HighLight(x+1,y));
            }else if(p.getColor() != this.color){
                high.add(new HighLight(x+1,y));
            }
        }
        if(x+1 < 8 && y+1 < 8){
            p = hash.get(""+((char)(x+1+65))+(y+1));
            if(p == null){
                high.add(new HighLight(x+1,(y+1)));
            }else if(p.getColor() != this.color){
                high.add(new HighLight(x+1,(y+1)));
            }
        }
        if(x-1 > -1 && y-1 > -1){
            p = hash.get(""+((char)(x-1+65))+(y-1));
            if(p == null){
                high.add(new HighLight(x-1,(y-1)));
            }else if(p.getColor() != this.color){
                high.add(new HighLight(x-1,(y-1)));
            }
        }
        if(x-1 > -1 && y+1 < 8){
            p = hash.get(""+((char)(x-1+65))+(y+1));
            if(p == null){
                high.add(new HighLight(x-1,(y+1)));
            }else if(p.getColor() != this.color){
                high.add(new HighLight(x-1,(y+1)));
            }
        }
        if(x+1 < 8 && y-1 > -1){
            p = hash.get(""+((char)(x+1+65))+(y-1));
            if(p == null){
                high.add(new HighLight(x+1,(y-1)));
            }else if(p.getColor() != this.color){
                high.add(new HighLight(x+1,(y-1)));
            }
        }
        
        high.add(new HighLight((int)(this.file-65),this.rank));
        
        if(check_test == true){
            for(HighLight hl: high){
                for(Piece piece: hash.values()){
                    if((piece.getColor() != this.color) && (!(piece instanceof King))){
                        high_check = piece.possibleMoves(hash,true);
                    }else if((piece.getColor() != this.color) && (piece instanceof King)){
                        high_check = piece.possibleMoves(hash,false);
                    }
                    if(high_check != null){
                        for(HighLight hl_check: high_check){
                            if(hl_check.hashMapCode().contentEquals(hl.hashMapCode())){ 
                                hl.setStr("red");
                            }
                        }
                        high_check.clear();
                    }
                }
                
               
            }
            high = HighLight.notRedEx(high, x, y);
        }
        
        
        return high;
    }
    
    public static boolean IsCheck(King king, ArrayList<HighLight> high){
        int x = (int)(king.file-65);
        int y = king.rank;
        
        if(high != null){
            for(HighLight h : high){
            
                if(h != null && x == h.getX() && y == h.getY()){
                    king.setCheck(true);
                    return king.getCheck();
                }
            }
            
        }
        king.setCheck(false);
        return king.getCheck();
        
    }
    
}
