package view;

import piece.Piece;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JPanel;

public class Board extends JPanel {
    private Graphics2D g2;
    private HashMap<String,Piece> pieces;
    private ArrayList<HighLight> highlights; 
    private HighLight high=null;
    private Piece p=null;

    public Piece getP() {
        return p;
    }

    public void setP(Piece p) {
        this.p = p;
    }
    
    
    
    public ArrayList<HighLight> getHighlights() {
        return highlights;
    }

    public void setHighlights(ArrayList<HighLight> highlights) {
        this.highlights = highlights;
    }

    public HighLight getHigh() {
        return high;
    }

    public void setHigh(HighLight high) {
        this.high = high;
    }
    
    
    public void setG2(Graphics2D g2) {
        this.g2 = g2;
    }
    
    public Graphics2D getG2() {
        return g2;
    }
    
    public HashMap<String,Piece> getPieces() {
        return pieces;
    }

    public void setPieces(HashMap<String,Piece> pieces) {
        this.pieces = pieces;
    }



    public Board() {
        super();
    }

    @Override //sobrescrita do metodo paintComponent da classe JPanel
    protected void paintComponent(Graphics g) {
       
        
        setG2((Graphics2D)g);
        this.g2.setBackground(Color.LIGHT_GRAY);
        this.g2.setColor(Color.DARK_GRAY);
        super.paintComponent(g2);         
        float maxWidth=this.getWidth();
        float maxHeight=this.getHeight();
        float boardSize = (maxWidth < maxHeight) ? maxWidth : maxHeight;
        int spotSize = Math.round(boardSize/8.0f);
                  
        for(int i = 0; i<8; ++i){
            for(int j = 0; j<8; ++j){
                //varia a cor do quadrante
                if(g2.getColor() == Color.LIGHT_GRAY) g2.setColor(Color.DARK_GRAY);
                else g2.setColor(Color.LIGHT_GRAY);

                //Desenha o tabuleiro
                g2.fillRect(i*spotSize,j*spotSize,(i*spotSize)+spotSize, (j*spotSize)+spotSize);
            }

            if(g2.getColor() == Color.LIGHT_GRAY) g2.setColor(Color.DARK_GRAY);
            else g2.setColor(Color.LIGHT_GRAY);
        }
        
        
        if(high != null)
            high.draw(g2);
        for(Piece p: pieces.values()){
                p.draw(g);       
        }
        for(HighLight h: highlights){
            if(h != null){
                h.draw(g2);
            }
        }
        
        if(p != null)
            p.draw(g2);
        
    }    
}
