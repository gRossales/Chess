package engine;

import piece.Queen;
import piece.King;
import piece.Pawn;
import piece.Knight;
import piece.Bishop;
import piece.Piece;
import piece.Rook;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import view.*;

public class GameEventHandler implements MouseListener, MouseMotionListener{
    private DeskChessFrame frame;
    private Board board;
    public  Player player;
    private HashMap<String,Piece> hash_map;
    private ArrayList<HighLight> high_light_list;
    private ArrayList<HighLight> high_check_list;
    
    private Piece selected;
    private Piece lastplayed;
    private String selectedKey;
    private King wking;
    private King bking;
    private Log log;
    
    public GameEventHandler(DeskChessFrame frame) {
        hash_map = new HashMap <String,Piece> ();
        high_light_list = new ArrayList <HighLight> ();
        high_check_list = new ArrayList <HighLight> ();
        setFrame(frame);
        setBoard(getFrame().getBoard());
        board.addMouseListener(this);//Adiciona evento de mouse ao Painel
        board.addMouseMotionListener(this);//Adiciona evento de mouse ao Painel
        player = new Player();
        this.frame.repaint();
        createPieces();
        board.setPieces(hash_map);
        board.setHighlights(high_light_list);
        selected = null;

        lastplayed = selected;
        Player.move_number = 0;
        log = new Log(frame.getjTextArea1());
        frame.setStr("Black");
  
    }

    public DeskChessFrame getFrame() {
        return frame;
    }

    public void setFrame(DeskChessFrame frame) {
        this.frame = frame;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public HashMap<String, Piece> getList() {
        return hash_map;
    }

    public void setList(HashMap<String,Piece> list) {
        this.hash_map = list;
    }
    
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        char x = ((char)(e.getX()/69+65));
        int y = e.getY()/69;
        boolean move;
       
        if (selected != null){
            move = selected.move(((char)(e.getX()/69+65)), y,high_light_list);
            
            if(move == true && !(putInCheck()) && !(stillCheck(x,y))){
            
              
                if(player.getColor() == 1){
                    log.newLine();
                    log.setMove_number(Player.getMoveNumber());
                    log.lineBegining();
                }else{
                    log.newTab();
                }
                
                
                if(enPassant( x, y)){
                    log.enPassantLog(x, y,selectedKey.charAt(0));
                    //Log en Passant 
                }else
                if(castling(x,y)){
                    log.castlingLog(x);
                    //Log Roque Castling
                }else
                if(normalMove(x,y)){ // normal move
                     log.captureLog(selected, x, y,selectedKey.charAt(0));
                    //normal log
                }
                if(promotion(x,y)){
                    log.promotionLog(selected, x, y);
                     //log Promotion
                   
                }else{
                     log.normalMoveLog(selected, x, y);
                }
                     
                changeStats();
                alertOrClose();
                
            
            }   
            selected = null;
            high_light_list.clear();
        }else if(selected == null){       
            select(x,y);
        }
        frame.repaint();
       
        
       
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        board.setHigh(new HighLight(e.getX()/69, e.getY()/69,"black"));
        frame.repaint();
    }
    
    
    public void createPieces(){ 
        char letter;
        int color = 1; //WHITE

        for(int i = 0; i < 8; i++){
            letter = 'A';
            if(i > 1){      
                color = 0;//BLACK
            }
            for(int j = 0; j < 8; j++){
                setPieces(i,letter,color);
                ++letter;
            }
        }
        
    }
    
    public void setPieces(int i, char letter, int color){
        if(i == 1 || i == 6){                                               //Pawn
            this.hash_map.put(""+letter+i,new Pawn(letter,i,color));
        }else if((i == 0 || i == 7) && (letter == 'A' || letter == 'H')){   //Rook
            this.hash_map.put(""+letter+i,new Rook(letter,i,color));
        }else if((i == 0 || i == 7) && (letter == 'B' || letter == 'G')){   //Knight
            this.hash_map.put(""+letter+i,new Knight(letter,i,color));
        }else if((i == 0 || i == 7) && (letter == 'C' || letter == 'F')){   //Bishop
            this.hash_map.put(""+letter+i,new Bishop(letter,i,color));
        }else if((i == 0 && letter == 'E') || (i == 7 && letter == 'E')){   //Queen
            this.hash_map.put(""+letter+i, new Queen(letter,i,color));
        }else if((i == 0 && letter == 'D') || (i == 7 && letter == 'D')){//King
            if(color == 1){
                wking = new King(letter,i,color);
                this.hash_map.put(""+letter+i,wking);
            }else{
                bking = new King(letter,i,color);
                this.hash_map.put(""+letter+i,bking);
            }
            System.out.println(""+letter+i);

        }


    }
    
    public King whichKing(){
        if(player.getColor() == 1){
            return wking;
        }
        return bking;
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
    
    public boolean isCheckMate(ArrayList<HighLight> high_list , King king){
        ArrayList<HighLight> high_piece_list = null;
        ArrayList<HighLight> k ;
        k =  king.possibleMoves(hash_map, true);
        if(k.size() == 1){
            for(HighLight highLight: high_list){
                for(Piece p: hash_map.values()){
                    if(p.getColor() == player.getColor())
                        if(p instanceof King){
                            high_piece_list = p.possibleMoves(hash_map, true);
                        }else{
                            high_piece_list = p.possibleMoves(hash_map, false);
                        }
                    if(high_piece_list != null)
                        for(HighLight high: high_piece_list){
                            if(high.getX() == highLight.getX() && high.getY() == highLight.getY())
                                return false;
                        }
                }
            }

            return true;
        }
        return false;
    }
    
    public Piece promote(char file,int rank,int color){
        Piece p;
        if(frame.getjComboBox1().getSelectedIndex() == 0){
            p = new Queen(file,rank,color);
        }else if(frame.getjComboBox1().getSelectedIndex() == 1){
            p = new Bishop(file,rank,color);
        }else if(frame.getjComboBox1().getSelectedIndex() == 2){
            p = new Knight(file,rank,color);
        }else{
            p = new Rook(file,rank,color);
        }
        return p;
    }
    
    public boolean promotion(char x,int y){
        if(selected instanceof Pawn && ((y == 7 && player.getColor() == 1) || (y == 0 && player.getColor() == 0))){
            hash_map.remove(""+x+y);
            selected = promote(selected.getFile(),selected.getRank(),player.getColor());
            hash_map.put(""+x+y,selected);
            board.setP(selected);
            return true;
        }
        return false;
    }
    
    
    
    
    
    public boolean enPassant(char x,int y){
        if(selected instanceof Pawn && Math.abs(((selectedKey.charAt(1)- 48) -y)) == 2){
            ((Pawn)(selected)).setDouble_step(true);
        }else if(selected instanceof Pawn && lastplayed instanceof Pawn && ((Pawn)lastplayed).isDouble_step()
           && selected.getFile() == lastplayed.getFile() && (selected.getRank() == lastplayed.getRank() + 1 
           || selected.getRank() == lastplayed.getRank() - 1)){
            if(player.getColor() == 0){    
                y++;
                hash_map.remove(""+x+y);
                y--;
            }else{
                y--;
                hash_map.remove(""+x+y);
                y++;
            }
            hash_map.put(""+x+y, hash_map.remove(selectedKey));
            
            return true;
        }
        return false;
    }
    
    public boolean castling(char x,int y){
        if(selected instanceof Rook){
            ((Rook)selected).setMoved(true);
        }else if(selected instanceof King && ((King)selected).isMoved() == false){
            ((King)selected).setMoved(true);
            if(Math.abs(selected.getFile() - selectedKey.charAt(0)) == 2){
                System.out.println(Math.abs(selected.getFile() - selectedKey.charAt(0))+"\n");
            }
                if(x == 'B'){
                    x = 'C';
                    hash_map.put(""+x+y, hash_map.remove(""+'A'+y));
                    hash_map.get(""+x+y).setFile(x);
                    x = 'B';
                }else if(x == 'F'){
                    x = 'E';
                    hash_map.put(""+x+y, hash_map.remove("H"+y));
                    hash_map.get(""+x+y).setFile(x);
                    x = 'F';
                }
                hash_map.put(""+x+y, hash_map.remove(selectedKey));
                return true;
        }
        return false;
    }
    
    public boolean normalMove(char x,int y){
        if(hash_map.containsKey(""+x+y)){
            hash_map.remove(""+x+y);
            hash_map.put(""+x+y, hash_map.remove(selectedKey));
            return true;
        }
        hash_map.put(""+x+y, hash_map.remove(selectedKey));
        return false;
    }
    
    public void changeStats(){
        frame.setStr(player.getStr());
        player.changePlayer();
        
        frame.getJlabel4().setText(player.getStr()+"'s Turn");
        char c = selectedKey.charAt(1);                             //aumenta o indice no log
        c++;
        selectedKey = selectedKey.replace(selectedKey.charAt(1), c);

        c = selectedKey.charAt(0);
        c = Character.toLowerCase(c);
        selectedKey = selectedKey.replace(selectedKey.charAt(0), c);

        //Reset condition;
        if(lastplayed instanceof Pawn && ((Pawn)lastplayed).isDouble_step())
            ((Pawn)lastplayed).setDouble_step(false);

        lastplayed = selected;
    }
    
    public void alertOrClose(){
        high_check_list.clear();
        for(Piece p: hash_map.values()){
            
            if(p.getColor()!=player.getColor() && King.IsCheck(whichKing(), p.possibleMoves(hash_map,true)) == true){
                log.check();
                high_check_list.clear();

                high_check_list.addAll(HighLight.highLightFromTo(p.getFile() -65, p.getRank(),whichKing().getFile()-65 , whichKing().getRank()));
                frame.repaint();
                System.out.println(player.getColor());
                if(!isCheckMate(high_check_list,whichKing())){
                    JOptionPane.showMessageDialog(frame, "CHECK");
                    break;
                }else{
                    log.checkMate();
                    if(player.getColor() == 1)
                        log.whiteVictory();
                    else
                        log.blackVictory();
                    player.changePlayer();
                    JOptionPane.showMessageDialog(frame,"CHECK MATE, "+player.getStr() +" won");
                    System.out.println("End game");
                    System.exit(0);
                }

            }
        }
    
    }
    
    public void select(char x,int y){
      if (hash_map.containsKey(""+x+y) && hash_map.get(""+x+y).getColor() == player.getColor()){
                selected = hash_map.get(""+x+y);
                board.setP(selected);
                if(selected instanceof King)
                    high_light_list.addAll(selected.possibleMoves(hash_map,true));
                else
                    high_light_list.addAll(selected.possibleMoves(hash_map,false));
                selectedKey = ""+x+y;
            }  
    }
    
    public boolean putInCheck(){
        char x1 = selectedKey.charAt(0) ,x2 = whichKing().getFile();
        int y1 = selectedKey.charAt(1)-48,y2 = whichKing().getRank();
        if(selected instanceof King){
            return false;
        }
        
        Piece p=null;
        if(x1 == x2 && selected.getFile() != x1){           //reta x
            if(y1 > y2 ){
               for(int i= y1-1;i>-1;i--){
                   if(hash_map.containsKey(""+x1+i)){
                        p = hash_map.get(""+x1+i);
                        if(p.getColor() == player.getColor() && !(p instanceof King)){
                            return false;
                        }else if(p instanceof King){
                            break;
                        }
                   }
               }
               for(int i = y1+1; i < 8; i++){
                    if(hash_map.containsKey(""+x1+i)){
                        p = hash_map.get(""+x1+i);
                        if(p.getColor() == player.getColor()){
                            return false;
                        }else if(p.getColor() != player.getColor() && (p instanceof Rook || p instanceof Queen)){
                            JOptionPane.showMessageDialog(frame, "You can't put your king on check ");
                            selected.setFile(x1);
                            selected.setRank(y1);
                            return true;
                        }
                    }
                }
            }else{
                for(int i= y1+1;i<8;i++){
                   if(hash_map.containsKey(""+x1+i)){
                        p = hash_map.get(""+x1+i);
                        if(p.getColor() == player.getColor() && !(p instanceof King)){
                            return false;
                        }else if(p instanceof King){
                            break;
                        }
                   }
               }
                for(int i = y1-1;i > -1; i--){
                    if(hash_map.containsKey(""+x1+i)){
                        p = hash_map.get(""+x1+i);
                        if(p.getColor() == player.getColor()){
                            return false;
                        }else if(p.getColor() != player.getColor() && (p instanceof Rook || p instanceof Queen)){
                            selected.setFile(x1);
                            selected.setRank(y1);
                            JOptionPane.showMessageDialog(frame, "You can't put your king on check ");
                            return true;
                        }
                    }
                }
        
            }
        }else if(y1 == y2 && selected.getRank() != y1){                         //reta y
            if(x1 > x2 ){
                for(int i= x1-1;i> 64;i--){                                     //pro lado do rei
                   if(hash_map.containsKey(""+(char)i+y1)){
                        p = hash_map.get(""+(char)i+y1);
                        if(p.getColor() == player.getColor() && !(p instanceof King)){
                            return false;
                        }else if(p instanceof King){
                            break;
                        }
                   }
               }
               for(int i = x1+1; i < 73; i++){
                    if(hash_map.containsKey(""+(char)i+y1)){
                        p = hash_map.get(""+(char)i+y1);
                        if(p.getColor() == player.getColor()){
                            return false;
                        }else if(p.getColor() != player.getColor() && (p instanceof Rook|| p instanceof Queen)){
                            selected.setFile(x1);
                            selected.setRank(y1);
                            JOptionPane.showMessageDialog(frame, "You can't put your king on check ");
                            return true;
                        }
                    }
                }
            }else{
                for(int i= x1+1;i<73;i++){                                      //pro lado do rei
                   if(hash_map.containsKey(""+(char)i+y1)){
                        p = hash_map.get(""+(char)i+y1);
                        if(p.getColor() == player.getColor() && !(p instanceof King)){
                            return false;
                        }else if(p instanceof King){
                            break;
                        }
                        
                   }
               }
                for(int i = x1-1;i > 64; i--){
                    if(hash_map.containsKey(""+(char)i+y1)){
                        p = hash_map.get(""+(char)i+y1);
                        if(p.getColor() == player.getColor()){
                            return false;
                        }else if(p.getColor() != player.getColor() && (p instanceof Rook || p instanceof Queen)){
                            selected.setFile(x1);
                            selected.setRank(y1);
                            JOptionPane.showMessageDialog(frame, "You can't put your king on check ");
                            return true;
                        }
                    }
                }
        
            }
        }else if(Math.abs(x1-x2) == Math.abs(y1-y2) ){//diagonais
            if(x1 - x2 > 0 && y1 -y2 > 0 && !(selected.getFile() - x2 == selected.getRank() - y2)){
                for(int i=x1-1,j=y1-1; i > 64 && j > -1 ;i--,j--){
                    if(hash_map.containsKey(""+(char)i+j)){
                        p = hash_map.get(""+(char)i+j);
                        if(p.getColor() == player.getColor() && !(p instanceof King)){
                            return false;
                        }else if(p instanceof King){
                            break;
                        }
                    }
                }
                for(int i=x1+1,j=y1+1; i < 73 && j < 8 ;i++,j++){
                    if(hash_map.containsKey(""+(char)i+j)){
                        p = hash_map.get(""+(char)i+j);
                        if(p.getColor() == player.getColor()){
                            return false;
                        }else if(p.getColor() != player.getColor() && (p instanceof Bishop || p instanceof Queen)){
                            selected.setFile(x1);
                            selected.setRank(y1);
                            JOptionPane.showMessageDialog(frame, "You can't put your king on check ");
                            
                            return true;
                        }
                    }
                }
            }else if(x1 - x2 < 0 && y1 -y2 < 0 && !(selected.getFile() - x2 == selected.getRank() - y2)){
                for(int i=x1+1,j=y1+1; i < 73 && j < 8 ;i++,j++){
                    if(hash_map.containsKey(""+(char)i+j)){
                        p = hash_map.get(""+(char)i+j);
                        if(p.getColor() == player.getColor() && !(p instanceof King)){
                            return false;
                        }else if(p instanceof King){
                            break;
                        }
                    }
                }
                for(int i=x1-1,j=y1-1; i > 64 && j > -1 ;i--,j--){
                    if(hash_map.containsKey(""+(char)i+j)){
                        p = hash_map.get(""+(char)i+j);
                        if(p.getColor() == player.getColor()){
                            return false;
                        }else if(p.getColor() != player.getColor() && (p instanceof Bishop || p instanceof Queen)){
                            selected.setFile(x1);
                            selected.setRank(y1);
                            JOptionPane.showMessageDialog(frame, "You can't put your king on check ");
                            return true;
                        }
                    }
                }
            }else if(x1 - x2 > 0 && y1 -y2 < 0 && !(selected.getFile() - x2 == (selected.getRank() - y2)*(-1))){
                for(int i=x1-1,j=y1+1; i > 64 && j < 8 ;i--,j++){
                     if(hash_map.containsKey(""+(char)i+j)){
                        p = hash_map.get(""+(char)i+j);
                        if(p.getColor() == player.getColor() && !(p instanceof King)){
                            return false;
                        }else if(p instanceof King){
                            break;
                        }
                    }
                }
                for(int i=x1+1,j=y1-1; i < 73 && j> -1 ;i++,j--){
                     if(hash_map.containsKey(""+(char)i+j)){
                        p = hash_map.get(""+(char)i+j);
                        if(p.getColor() == player.getColor()){
                            return false;
                        }else if(p.getColor() != player.getColor() && (p instanceof Bishop || p instanceof Queen)){
                            selected.setFile(x1);
                            selected.setRank(y1);
                            JOptionPane.showMessageDialog(frame, "You can't put your king on check ");
                            return true;
                        }
                    }
                }
            }else if(x1 - x2 < 0 && y1 -y2 > 0 && !(selected.getFile() - x2 == (selected.getRank() - y2)*(-1))){
                for(int i=x1+1,j=y1-1; i < 73 && j> -1 ;i++,j--){
                    if(hash_map.containsKey(""+(char)i+j)){
                        p = hash_map.get(""+(char)i+j);
                        if(p.getColor() == player.getColor() && !(p instanceof King)){
                            return false;
                        }else if(p instanceof King){
                            break;
                        }
                    }
                }
                for(int i=x1-1,j=y1+1; i > 64 && j < 8 ;i--,j++){
                     if(hash_map.containsKey(""+(char)i+j)){
                        p = hash_map.get(""+(char)i+j);
                        if(p.getColor() == player.getColor()){
                            return false;
                        }else if(p.getColor() != player.getColor() && (p instanceof Bishop || p instanceof Queen)){
                            selected.setFile(x1);
                            selected.setRank(y1);
                            JOptionPane.showMessageDialog(frame, "You can't put your king on check ");
                            return true;
                        }
                    }
                }
            }
        }
         return false;
    }
    
    public boolean stillCheck(char x,int y){
        //ver se o movimento tirou o rei do check
        //se nao tirou mandar warning
        char x1 = selectedKey.charAt(0) ;
        int y1 = selectedKey.charAt(1)-48;
        
        if(selected instanceof King){
            return false;
        }
        
        if(high_check_list.size() > 0){
            for(HighLight h: high_check_list){
                if(h.getX() == (int)x-65 && h.getY() == y){
                    return false;        
                }
            }
            selected.setFile(x1);
            selected.setRank(y1);
            JOptionPane.showMessageDialog(frame, "You can't leave your king on check ");
            return true;
        }
        return false;
    }
    
    
}
    
