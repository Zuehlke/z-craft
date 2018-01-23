import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.border.BevelBorder;

public class Main {
    private static final int ROWS = 10;
    private static final int COLS = ROWS;
    private static final int NUM_ENEMIES = 8;

    private static JPanel[][] panelArray = new JPanel[COLS][ROWS];

    public static void main(String[] args) {
        JFrame frame = new JFrame("Arena");
        frame.setLayout(new GridLayout(ROWS, COLS));
        for(int i=0;i<COLS; i++){
            for(int j=0;j<ROWS; j++){
                JPanel comp = new JPanel();
                comp.setBorder(new BevelBorder(BevelBorder.RAISED));
                frame.add(comp);
                panelArray[i][j]= comp;
            }
        }
        Arena arena = new Arena(ROWS, COLS, NUM_ENEMIES);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setBounds(0, 0, 500, 500);
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                arena.move(e.getKeyCode());
                arena.display(panelArray);
            }
        });
        frame.setVisible(true);
        arena.display(panelArray);
    }
}
