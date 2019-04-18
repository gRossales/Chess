package engine;

import piece.Bishop;
import piece.King;
import piece.Knight;
import piece.Pawn;
import piece.Piece;
import piece.Queen;
import piece.Rook;

 
public class Log {
    private javax.swing.JTextArea text;
    private int move_number;
    public Log(javax.swing.JTextArea text) {
        this.text = text;
        text.append("   White \t Black");
        move_number =1;
    }
    
    public void castlingLog(char x){
        if(x == 'B')
            text.append("0-0");
        else
            text.append("0-0-0");
    }
    
    public void enPassantLog(char x,int y,char last){
        x+=32;
        y++;
        last+=32;
        text.append(""+last+"x"+x+y+"e.p");
    }
    
    public void promotionLog(Piece selected,char x,int y){
        x+=32;
        y++;
        text.append(""+x+y+pieceNotation(selected));
        
    }
    
    public void normalMoveLog(Piece selected,char x,int y){
        x+=32;
        y++;
        text.append(""+pieceNotation(selected)+x+y);
    }
    
    public void captureLog(Piece selected,char x,int y,char last){
        x+=32;
        y++;
        last+=32;
        if(!(selected instanceof Pawn))
            text.append(""+pieceNotation(selected)+"x"+x+y);
        else
            text.append(""+last+"x"+x+y);
    }
    
    public void lineBegining(){
        text.append(move_number+". ");
    }
    
    public void newLine(){
        text.append("\n");
    }
    public void newTab(){
        text.append("\t");
    }

    public void check(){
        text.append("+");
    }
    
    public void checkMate(){
        text.append("+ \n");
    }
    
    public void setMove_number(int move_number) {
        this.move_number = move_number;
    }
 
    public void whiteVictory(){
        text.append("               0-1");
    }
    
    public void blackVictory(){
        text.append("               1-0");
    }
    
    public char pieceNotation(Piece p){
        char c = 0;
        if(p  instanceof King){
            c ='K';
        }else if(p instanceof Queen){
            c ='Q';
        }else if(p instanceof Bishop){
            c ='B';
        }else if(p instanceof Knight){
            c ='N';
        }else if(p instanceof Rook){
            c ='R';
        }
        return c;
    }
    
}
