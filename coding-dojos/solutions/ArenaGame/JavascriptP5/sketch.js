const sizeX = 39;
const sizeY = 19;
const numEnemies = 11;

let player;
let win = false;
let turns = 0;

let enemies = [];
let board = [];

// noinspection JSUnusedGlobalSymbols
function setup() {
    createCanvas(800, 480);
    player = new Player(floor(random(40)), floor(random(20)));

    for (let i = 0; i < numEnemies; i++) {
        enemies.push(new Enemy(floor(random(40)), floor(random(20))))
    }

    for (let i = 0; i < 40; i++) {
        for (let j = 0; j < 20; j++) {
            let c = color(0, 0, 122);
            if ((i + j) % 2 === 1) {
                c = color(0, 0, 200);
            }

            board.push(new BoardTile(c, i, j));
        }
    }
}

// noinspection JSUnusedGlobalSymbols
function draw() {
    background(255);
    board.forEach(boardTile => boardTile.draw());
    enemies.forEach(enemy => enemy.draw());
    player.draw();
    if (win) {
        textSize(200);
        fill(0, 200, 153);
        text("You Win", 30, 260);
        textSize(50);
        fill(0, 200, 153);
        text("in " + turns + " turns", 300, 330);
    }
}

// noinspection JSUnusedGlobalSymbols
function keyPressed() {
    if (keyCode === LEFT_ARROW) {
        player.moveLeft();
        moveEnemies();
    } else if (keyCode === RIGHT_ARROW) {
        player.moveRight();
        moveEnemies();
    } else if (keyCode === UP_ARROW) {
        player.moveUp();
        moveEnemies();
    } else if (keyCode === DOWN_ARROW) {
        player.moveDown();
        moveEnemies();
    } else if (keyCode === 32) {
        moveEnemies();
    }

    updateBoard();
}

function moveEnemies() {
    if (!win) {
        turns++
    }
    enemies.forEach(enemy => enemy.moveToPlayer(player.posX, player.posY));
}

function updateBoard() {
    enemies = enemies.filter(enemy => !(enemy.posX === player.posX && enemy.posY === player.posY));

    if (enemies.length === 0) {
        win = true;
    }
}
