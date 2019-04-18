package main;

import engine.GameEventHandler;
import view.DeskChessFrame;

public class DeskChess {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DeskChessFrame frame = new DeskChessFrame();
                frame.setVisible(true);
                new GameEventHandler(frame);
                
            }
        });
    }
    
}
