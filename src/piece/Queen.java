package piece;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;
import view.HighLight;

public class Queen extends Piece{
    public Queen(char file, int rank, int color){
        super(file, rank, color);
        try {
            if(color == 0)
                this.image = ImageIO.read(new File("img/Queen0.png"));
            else
                this.image = ImageIO.read(new File("img/Queen1.png"));
        
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
    public ArrayList<HighLight> possibleMoves(HashMap<String, Piece> hash,boolean check_test) {
        int x, y;
        Piece p;
        ArrayList<HighLight> high;
        
        high = new ArrayList<HighLight>();
        p = null;
        x = (int)this.file - 65;
        y = this.rank;

        if(check_test == false)
            high.add(new HighLight((int)(this.file-65),this.rank));
        
        for(int i = x+1;i<8 ;i++){
            p = hash.get(""+((char)(i+65))+y);
            if(p != null && check_test == false){
                if(p.getColor() != this.color){
                    high.add(new HighLight(i,y));
                }
                break;
            }else if(p != null && check_test != false){
                if(p.getColor() != this.color && (p instanceof King)){
                    high.add(new HighLight(i,y));
                }else{
                    high.add(new HighLight(i,y));
                    break;
                }
            }
            high.add(new HighLight(i,y)); 
        }
        for(int i = y+1;i<8 ;i++){
            p = hash.get(""+((char)(x+65))+i);
            if(p != null && check_test == false){
                if(p.getColor() != this.color){
                    high.add(new HighLight(x,i));
                }
                break;
            }else if(p != null && check_test != false){
                if(p.getColor() != this.color && p instanceof King){
                    high.add(new HighLight(x,i));
                }else{
                    high.add(new HighLight(x,i));
                    break;
                }
            }
            high.add(new HighLight(x,i));
            
        }
        for(int i = x-1;i>-1 ;i--){
            p = hash.get(""+((char)(i+65))+y);
            if(p != null && check_test == false){
                if(p.getColor() != this.color){
                    high.add(new HighLight(i,y));
                }
                break;
            }else if(p != null && check_test != false){
                if(p.getColor() != this.color && p instanceof King){
                    high.add(new HighLight(i,y));
                }else{
                    high.add(new HighLight(i,y));
                    break;
                }
            }
            high.add(new HighLight(i,y));
            
        }
        for(int i = y-1;i>-1 ;i--){
            p = hash.get(""+((char)(x+65))+i);
            if(p != null && check_test == false){
                if(p.getColor() != this.color){
                    high.add(new HighLight(x,i));
                }
                break;
            }else if(p != null && check_test != false){
                if(p.getColor() != this.color && p instanceof King){
                    high.add(new HighLight(x,i));
                }else{
                    high.add(new HighLight(x,i));
                    break;
                }
            }
            high.add(new HighLight(x,i));
            
        }
        for(int i = x+1 , j = y+1; i < 8 && j < 8; i++, j++){
            p = hash.get(""+((char)(i+65))+j);
            if(p != null && check_test == false){
                if(p.getColor() != this.color){
                    high.add(new HighLight(i,j));
                }
                break;
            }else if(p != null && check_test != false){
                if(p.getColor() != this.color && p instanceof King){
                    high.add(new HighLight(i,j));
                }else{
                    high.add(new HighLight(i,j));
                    break;
                }
            }
            high.add(new HighLight(i,j));
        }
        for(int i = x-1 , j = y-1; i >-1 && j > -1; i--, j--){
            p = hash.get(""+((char)(i+65))+j);
            if(p != null && check_test == false){
                if(p.getColor() != this.color){
                    high.add(new HighLight(i,j));
                }
                break;
            }else if(p != null && check_test != false){
                if(p.getColor() != this.color && p instanceof King){
                    high.add(new HighLight(i,j));
                }else{
                    high.add(new HighLight(i,j));
                    break;
                }
            }
            high.add(new HighLight(i,j));
        }
        for(int i = x+1 , j = y-1; i < 8 && j > -1; i++, j--){
            p = hash.get(""+((char)(i+65))+j);
            if(p != null && check_test == false){
                if(p.getColor() != this.color){
                    high.add(new HighLight(i,j));
                }
                break;
            }else if(p != null && check_test != false){
                if(p.getColor() != this.color && p instanceof King){
                    high.add(new HighLight(i,j));
                }else{
                    high.add(new HighLight(i,j));
                    break;
                }
            }
            high.add(new HighLight(i,j));
        }
        for(int i = x-1 , j = y+1; i > -1 && j < 8; i--, j++){
            p = hash.get(""+((char)(i+65))+j);
            if(p != null && check_test == false){
                if(p.getColor() != this.color){
                    high.add(new HighLight(i,j));
                }
                break;
            }else if(p != null && check_test != false){
                if(p.getColor() != this.color && p instanceof King){
                     high.add(new HighLight(i,j));
                }else{
                    high.add(new HighLight(i,j));
                    break;
                }
            }
            high.add(new HighLight(i,j));
        }
        
        return high;
    }
}
