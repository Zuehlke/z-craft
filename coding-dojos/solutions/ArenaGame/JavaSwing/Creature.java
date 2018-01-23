import java.awt.*;

enum Creature {
    PLAYER(Color.BLUE), ENEMY(Color.RED);

    Creature(Color c){
        this.color = c;
    }

    final Color color;
}
