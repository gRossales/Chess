package piece;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import view.HighLight;

public abstract class Piece {
    protected char file;        //columns A-H
    protected int rank;         //rows 1-8
    protected int color;//White or Black
    protected BufferedImage image;
    protected Graphics g;

    public Graphics getG() {
        return g;
    }
    
    public Piece(char file, int rank, int color){
        this.file = file;
        this.rank = rank;
        this.color = color;
        this.image = null;
    }
    
    public void setFile(char file){
        this.file = file;
    }
    public void setRank(int rank){
        this.rank = rank;
    }
    public void setColor(int color){
        this.color = color;
        
    }

    public char getFile() {
        return file;
    }

    public int getRank() {
        return rank;
    }
    
    public int getColor() {
        return color;
    }
    
    public String getkey(){
        String str = (""+this.getFile()+this.getRank());
        return str;
    }
    
    public boolean move(char file, int rank, ArrayList<HighLight> high){
        if(this.file != file || this.rank != rank){
            for(HighLight h: high){
                if(((h.getX() == (int)(file -65)) && (h.getY() == rank)) && h.getStr().contentEquals("green")){
                    this.file = file;
                    this.rank = rank;
                    return true;
                }
            }
        }
       return false;
    }
    public abstract void draw(Graphics g);
    public abstract ArrayList<HighLight> possibleMoves(HashMap<String,Piece> hash, boolean check_test);
    
}             

