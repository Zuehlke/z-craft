import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.*;

public class Arena {
    private final int width;
    private final int height;
    private Map<Point, Creature> positions = new HashMap<>();
    private Point playerPosition;
    private Random rnd = new Random();
    private int turns = 0;

    public Arena(int width, int height, int numEnemies){
        this.width = width;
        this.height = height;
        Point playerPosition = new Point(rnd.nextInt(width), rnd.nextInt(height));
        this.playerPosition = playerPosition;
        positions.put(playerPosition, Creature.PLAYER);
        for(int i=0; i<numEnemies; i++){
            Point enemyPosition;
            do{
                enemyPosition = new Point(rnd.nextInt(width), rnd.nextInt(height));
            } while(positions.containsKey(enemyPosition));
            positions.put(enemyPosition, Creature.ENEMY);

        }
    }

    public void display(JPanel[][] array){
        for(int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                array[j][i].setBackground(Color.GRAY);
            }
        }
        for(Map.Entry<Point, Creature> entry : positions.entrySet()){
            array[entry.getKey().y] [entry.getKey().x].setBackground(entry.getValue().color);
        }
        if (positions.size()==1){
            JOptionPane.showMessageDialog(null,"You won in "+turns+" turns!");
        }
    }

    public void moveEnemy(Point point){
       int xDistance = playerPosition.x - point.x ;
       int yDistance = playerPosition.y - point.y;
       Point newpos = new Point(point);
       if(Math.abs(xDistance)<Math.abs(yDistance)){
           newpos.translate(0, (int) Math.signum(yDistance));
       } else {
           newpos.translate((int) Math.signum(xDistance),0);
       }
       if(positions.get(newpos)==Creature.ENEMY){
           return;
       }
       positions.remove(point);
       positions.put(newpos,Creature.ENEMY);
       if(newpos.equals(playerPosition)){
           JOptionPane.showMessageDialog(null, "You lost in " + turns + " turns.");
       }
    }

    public void move(int keyCode) {
        Point nextPosition = getNextPosition(keyCode);
        movePlayer(nextPosition);
        positions.keySet().stream()
                .sorted(Comparator.comparing(playerPosition::distance))
                .skip(1)
                .forEach(this::moveEnemy);
        turns++;
    }

    private void movePlayer(Point nextPosition) {
        if(nextPosition==null){
            return;
        }
        if(positions.containsKey(nextPosition)){
            if(positions.get(nextPosition)==Creature.ENEMY){
                positions.remove(nextPosition);
            } else {
                return;
            }
        } else {
            positions.remove(playerPosition);
            positions.put(nextPosition, Creature.PLAYER);
            playerPosition = nextPosition;
        }
    }

    private Point getNextPosition(int keyCode) {
        Point nextPosition = new Point(playerPosition);
        switch (keyCode){
            case KeyEvent.VK_LEFT:
                nextPosition.x = Math.max(0,playerPosition.x-1);
                break;
            case KeyEvent.VK_UP:
                nextPosition.y = Math.max(0,playerPosition.y-1);
                break;
            case KeyEvent.VK_RIGHT:
                nextPosition.x = Math.min(width-1,playerPosition.x+1);
                break;
            case KeyEvent.VK_DOWN:
                nextPosition.y = Math.min(height-1,playerPosition.y+1);
                break;
            case KeyEvent.VK_SPACE:
                return null;
        }
        return nextPosition;
    }
}
