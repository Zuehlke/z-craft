class Enemy {

    constructor(x, y) {
        this.posX = x;
        this.posY = y;
    }

    isOccupied(x, y) {
        return enemies.some(enemy => (enemy.posX === x && enemy.posY === y));
    }

    moveLeft() {
        if (this.isOccupied(this.posX -1, this.posY)) {
            return;
        }

        this.posX = this.posX - 1;
        if (this.posX < 0) {
            this.posX = 0;
        }
    }

    moveRight() {
        if (this.isOccupied(this.posX +1, this.posY)) {
            return;
        }

        this.posX = this.posX + 1;
        if (this.posX > sizeX) {
            this.posX = sizeX;
        }
    }

    moveUp() {
        if (this.isOccupied(this.posX, this.posY-1)) {
            return;
        }

        this.posY = this.posY - 1;
        if (this.posY < 0) {
            this.posY = 0;
        }
    }

    moveDown() {
        if (this.isOccupied(this.posX, this.posY+1)) {
            return;
        }

        this.posY = this.posY + 1;
        if (this.posY > sizeY) {
            this.posY = sizeY;
        }
    }

    moveToPlayer(x,y){
        let distX = this.posX - x;
        let distY = this.posY - y;

        if (abs(distX) > abs(distY)){
            if(distX > 0) {
                this.moveLeft();
            } else {
                this.moveRight();
            }
        } else {
            if(distY > 0) {
                this.moveUp();
            } else {
                this.moveDown();
            }
        }
    }

    draw() {
        let c = color(0, 204, 255);
        fill(c);
        noStroke();
        rect(this.posX * 20, this.posY * 20, 20, 20);
    }
}
