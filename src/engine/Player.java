package engine;

public class Player {
    private String str;
    private int color;
    static int move_number;
    public Player(){
        str = "White";
        color = 1;
    }
    
    public void changePlayer(){
        if(str.compareTo("White") == 0){
            str = "Black";
            color = 0;
        }else{
            str = "White";
            color = 1;
        }
    }
    
    public int getColor(){
        return color;
    }
    
    public String getStr(){
        return str;
    }
    
    public static int getMoveNumber(){
        move_number++;
        return move_number;
    }

}

