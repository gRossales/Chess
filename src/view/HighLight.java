package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class HighLight {
    private int x;
    private int y;
    private Graphics2D g2;
    String str;
    
    public HighLight(int x, int y) {
        this.x = x;
        this.y = y;
        str = "green";
    }

    public HighLight(int x, int y,String str) {
        this.x = x;
        this.y = y;
        this.str = str;
    }
    
    public void setStr(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }
    
    public Graphics getG() {
        return g2;
    }

    public void setG(Graphics2D g) {
        this.g2 = g;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    public String hashMapCode(){
        String str = ""+getX()+getY();
        return str;
    }
    
    public void draw(Graphics2D g){
        setG(g);
        setHighLightColor(this.str);
        this.g2.setStroke(new BasicStroke(5));
        this.g2.drawRoundRect(x*70 +2, y*70 +2, 65, 65, 10, 10);
    }
    
    public void setHighLightColor(String str){
        if(str.contentEquals("red")){
            this.g2.setColor(Color.RED);
        }else if(str.contentEquals("green")){
            this.g2.setColor(Color.GREEN);  
        }else if(str.contentEquals("black")){
            this.g2.setColor(Color.BLACK);  
        }else if(str.contentEquals("white")){
            this.g2.setColor(Color.WHITE);  
        }else if(str.contentEquals("blue")){
            this.g2.setColor(Color.BLUE);  
        }else if(str.contentEquals("orange")){
            this.g2.setColor(Color.ORANGE);  
        }else if(str.contentEquals("pink")){
            this.g2.setColor(Color.PINK);  
        }else if(str.contentEquals("cyan")){
            this.g2.setColor(Color.CYAN);  
        }
    }

    public static ArrayList<HighLight> highLightFromTo(int x1 ,int y1,int x2,int y2){
        ArrayList<HighLight> h = new ArrayList<>();
        
        int i,j;
        
        
        if(x1 == x2){ //torre ou rainha
            if(y1 > y2 ){
        
               for(i = y1; i > y2; i--){
                    h.add(new HighLight(x1,i));
                }
            }else{
                for(i = y1;i < y2; i++){
                    h.add(new HighLight(x1,i));
                }
        
            }
            
            
        }else if(y1 == y2){
           if(x1>x2 ){
               for(i = x1; i > x2; i--){
                    h.add(new HighLight(i,y1));
                }
        
           }else{
               for(i = x1;i < x2; i++){
                    h.add(new HighLight(i,y1));
                }
        
           }
            
        }else if(Math.abs(x1-x2) == Math.abs(y1-y2)){
            if(x1 - x2 > 0 && y1 -y2 > 0){
                for(i=x1,j=y1; i > x2 && j> y2 ;i--,j--){
                     h.add(new HighLight(i,j));
                }
            }else if(x1 - x2 < 0 && y1 -y2 < 0){
                for(i=x1,j=y1; i < x2 && j< y2 ;i++,j++){
                     h.add(new HighLight(i,j));
                }
            }else if(x1 - x2 > 0 && y1 -y2 < 0){
                for(i=x1,j=y1; i > x2 && j< y2 ;i--,j++){
                     h.add(new HighLight(i,j));
                }
            }else if(x1 - x2 < 0 && y1 -y2 > 0){
                for(i=x1,j=y1; i < x2 && j> y2 ;i++,j--){
                     h.add(new HighLight(i,j));
                }
            }
        
        }else{
            h.add(new HighLight(x1,y1));
        }
        return h;
        
        
    }
    
    public static ArrayList<HighLight> notRedEx(ArrayList<HighLight> list, int x ,int y){
        ArrayList<HighLight> novo = new ArrayList<>();
        
        for(HighLight h:list){
            if(h.getStr().contentEquals("green") || (h.getX() == x && h.getY() == y))
                novo.add(h);
        }
        
        return novo;
    }
    
}
