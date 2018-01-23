class BoardTile {

    constructor(color, posX, posY) {
        this.color = color;
        this.posX = posX;
        this.posY = posY;
    }

    draw() {
        fill(this.color);
        noStroke();
        rect(this.posX * 20, this.posY * 20, 20, 20);
    }
}
