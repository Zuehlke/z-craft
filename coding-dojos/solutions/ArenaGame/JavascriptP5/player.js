class Player {

    constructor(x, y) {
        this.posX = x;
        this.posY = y;
    }

    moveLeft() {
        this.posX--;
        if (this.posX < 0) {
            this.posX = 0;
        }
    }

    moveRight() {
        this.posX++;
        if (this.posX > sizeX) {
            this.posX = sizeX;
        }
    }

    moveUp() {
        this.posY--;
        if (this.posY < 0) {
            this.posY = 0;
        }
    }

    moveDown() {
        this.posY = this.posY + 1;
        if (this.posY > sizeY) {
            this.posY = sizeY;
        }
    }

    draw() {
        const c = color(255, 204, 0);
        fill(c);
        noStroke();
        rect(this.posX * 20, this.posY * 20, 20, 20);
    }
}
