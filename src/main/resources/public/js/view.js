'use strict';

var app;
let gameStartX = 120;
let gameStartY = 90;
let width = 560;
let passageWidth = 20;
let intervalID = -1;
let items = [];
let gameStart = false;
let latestDirection = 2;
let gameState = {
    lives: 3,
    ghosts: 4,
    score: 0
};
let pacman = {
    position: {x: 50, y: 30},
    velocity: {x: 0, y: 0},
    dir: 2,
    state: false
};

let grassImg = new Image();
grassImg.src = './assets/grass.png';
grassImg.alt = "Image not found";

let forestImg = new Image();
forestImg.src = './assets/forest4.png';
forestImg.alt = "Image not found";

let pacmanImg1 = new Image();
pacmanImg1.src = './assets/sprites/PacmanL1.png';
let pacmanImg2 = new Image();
pacmanImg2.src = './assets/sprites/PacmanL2.png';

let ghosts = ['OL', 'PL', 'BL', 'RL'];
let ghostColorCode = {
    'orange': 'OL',
    'pink' : 'PL',
    'blue' : 'BL',
    'red' : 'RL'
}
let ghostsData = [];
let fruitImg = new Image();
fruitImg.src = './assets/sprites/Fruit1.png';

const layout = [
    [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,],
    [1,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,1,],
    [1,0,1,1,1,0,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,0,1,],
    [1,0,1,1,1,0,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,1,1,1,0,1,],
    [1,0,1,1,1,0,0,0,0,0,1,1,1,1,0,1,1,1,1,1,1,1,0,0,0,0,0,1,],
    [1,0,1,1,1,0,1,1,1,0,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,0,1,],
    [1,0,1,1,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0,1,],
    [1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,0,1,],
    [1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,],
    [1,1,1,1,1,1,0,1,1,1,1,1,0,1,1,0,1,1,1,1,1,0,1,1,1,1,1,1,],
    [1,1,1,1,1,1,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1,1,1,1,1,],
    [1,1,1,1,1,1,0,1,1,0,1,1,1,2,2,1,1,1,0,1,1,0,1,1,1,1,1,1,],
    [1,1,1,1,1,1,0,1,1,0,1,2,2,2,2,2,2,1,0,1,1,0,1,1,1,1,1,1,],
    [4,4,4,4,4,4,0,0,0,4,1,2,2,2,2,2,2,1,0,0,0,0,4,4,4,4,4,4,],
    [1,1,1,1,1,1,0,1,1,4,1,2,2,2,2,2,2,1,4,1,1,0,1,1,1,1,1,1,],
    [1,1,1,1,1,1,0,1,1,4,1,1,1,1,1,1,1,1,4,1,1,0,1,1,1,1,1,1,],
    [1,1,1,1,1,1,0,1,1,4,1,1,1,1,1,1,1,1,4,1,1,0,1,1,1,1,1,1,],
    [1,0,0,0,0,0,0,0,0,4,4,4,4,4,4,4,4,4,4,0,0,0,0,0,0,0,0,1,],
    [1,0,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,0,1,],
    [1,0,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,0,1,],
    [1,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,0,1,],
    [1,0,1,1,1,1,1,0,0,0,0,0,1,1,0,0,0,0,0,1,0,0,0,0,0,0,0,1,],
    [1,0,1,1,1,1,1,0,1,1,1,0,0,0,0,1,1,1,0,1,1,1,1,0,1,1,0,1,],
    [1,0,0,0,0,0,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,1,1,0,1,1,0,1,],
    [1,0,1,1,1,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,1,1,0,1,1,0,1,],
    [1,0,1,1,1,1,1,0,1,1,1,0,1,1,0,1,1,1,0,1,1,1,1,0,1,1,0,1,],
    [1,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,1,],
    [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1]
]

function createApp(canvas) {
    let c = canvas.getContext("2d");

    let drawBackground = function() {
        // c.fillStyle = 'green';
        // c.fillRect(gameStartX, gameStartY, width, width);
        c.drawImage(forestImg, 0, 0, 800, 660)
        c.font = "35px Ariel";
        c.fillStyle = "white";
        c.textAlign = "left";
        c.fillText("Score: " + gameState.score, 20, 30);
        c.fillText("Lives: " +  gameState.lives, 20, 70);
        c.font = "bold 40px Ariel";
        c.textAlign = "center";
        c.fillStyle = "white";
        c.fillText("Level 1", canvas.width/2, 70);
        c.drawImage(grassImg, gameStartX, gameStartY, width, width)
    };

    let detectCollision = function() {
        if (layout[pacman.position.x + pacman.velocity.x][pacman.position.y + pacman.velocity.y] == 1) return true;
        return false;
    }
    let drawGameBoard = function() {
        drawBackground();
        for (let i = 0; i < layout.length; i++) {
            for (let j = 0; j < layout.length; j++) {
                if (layout[i][j] !== 1) {
                    app.drawPassageBlock(i, j);
                }
            }
        }
        items.forEach(item => {
            if (item.name === "smallDot") app.drawDot(item.loc.x, item.loc.y, 3, "white");
            else if (item.name === "bigDot") app.drawDot(item.loc.x, item.loc.y, 6, "white");
        })
        pacman.state = !pacman.state;

        app.drawPacman(pacman.position.x, pacman.position.y, pacman.state, pacman.dir);
        for (let i = 0; i < ghostsData.length; ++i) {
            app.drawGhost(ghostsData[i], pacman.state);
        }
        // app.drawFruit(1, 12);
        // app.drawFruit(20, 4);

        // White line at ghost area door
        c.fillStyle = 'white';
        c.fillRect(gameStartX + 13 * passageWidth, gameStartY + 11 * passageWidth, 2 * passageWidth, 2);

    }

    let drawDot = function(row, column, radius, color) {
        // let x = gameStartX + passageWidth * column + passageWidth / 2;
        // let y = gameStartY + passageWidth * row + passageWidth / 2;
        let x = gameStartX + row;
        let y = gameStartY + column;
        c.fillStyle = color;
        c.beginPath();
        c.arc(x, y, radius, 0, 2 * Math.PI, false);
        c.closePath();
        c.fill();
    };

    let drawSprite = function(image, row, column, rotate=0.) {
        let x = gameStartX + passageWidth * column;
        let y = gameStartY + passageWidth * row;
        c.save();
        c.translate(x,y);
        c.rotate(rotate);
        c.drawImage(image, 0, 0, passageWidth - 5, passageWidth - 5);
        c.restore();
    };

    let drawPacman = function(row, column, animate, direction) {
        // let x = gameStartX + passageWidth * column + passageWidth / 2;
        // let y = gameStartY + passageWidth * row + passageWidth / 2;
        let x = gameStartX + row;
        let y = gameStartY + column;
        let img = animate? pacmanImg1: pacmanImg2;
        let size = passageWidth - 5;
        c.save();
        c.translate(x,y);
        c.rotate(Math.PI / 2 * direction);
        c.drawImage(img, -size / 2, -size / 2, size, size);
        c.restore();
    };

    let drawGhost = function(ghostData, animate) {
        let ghostImg1 = new Image();
        ghostImg1.src = './assets/sprites/' + ghostColorCode[ghostData.color] + '1.png';
        let ghostImg2 = new Image();
        ghostImg2.src = './assets/sprites/' + ghostColorCode[ghostData.color] + '2.png';
        let x = gameStartX + ghostData.loc.x;
        let y = gameStartY + ghostData.loc.y;
        let img = animate? ghostImg1: ghostImg2;
        c.save();
        c.translate(x,y);
        let size = passageWidth - 5;
        c.drawImage(img, -size / 2, -size / 2, size, size);
        c.restore();
    };

    let drawFruit = function(row, column) {
        let x = gameStartX + passageWidth * column;
        let y = gameStartY + passageWidth * row;
        c.drawImage(fruitImg, x, y, passageWidth - 5, passageWidth - 5);
    };

    let clear = function() {
        c.clearRect(0,0, canvas.width, canvas.height);
    };

    let drawPassageBlock = function (row, column) {
        let yVal = gameStartY + passageWidth * row;
        let xVal = gameStartX + passageWidth * column;
        c.fillStyle = '#5C4033';
        c.fillRect(xVal, yVal, passageWidth, passageWidth);
    }

    let writeMessage = function (message) {
        c.fillStyle = "rgba(0, 0, 0, 0.5)";
        c.fillRect(0, 0, canvas.width, canvas.height);
        c.font = "50px Ariel";
        c.fillStyle = "white";
        c.textAlign = "center";
        c.fillText(message, canvas.width / 2, canvas.height / 2);
    }

    let gamePause = function() {
        if (gameStart) {
            gameStart = false;
            $("#pause-btn-img").attr('src', './assets/PlayBtn.png');
            clearInterval(intervalID);
            intervalID = -1;
            c.fillStyle = "rgba(0, 0, 0, 0.5)";
            c.fillRect(0, 0, canvas.width, canvas.height);
            writeMessage("Paused");
            // c.font = "50px Ariel";
            // c.fillStyle = "white";
            // c.textAlign = "center";
            // c.fillText("Paused", canvas.width / 2, canvas.height / 2);
        } else {
            intervalID = setInterval(updateCanvas, 100);
            gameStart = true;
            $("#pause-btn-img").attr('src', './assets/pauseBtn.png');
        }
    }

    return {
        clear,
        writeMessage,
        drawBackground,
        drawGameBoard,
        drawPassageBlock,
        drawDot,
        drawPacman,
        drawGhost,
        drawFruit,
        gamePause,
        dims: {height: canvas.height, width: canvas.width}
    }
}


window.onload = function() {
    app = createApp(document.querySelector("canvas"));
    // intervalID = setInterval(updateCanvas, 100);
    gameStart = true;
    $("#pause-btn").click(() => app.gamePause());
    $("#restart-btn").click(() => {
        gameState.lives = $("#lives-dropdown").val();
        gameState.ghosts = $("#ghost-dropdown").val();
        initialize();
    });
    initialize();
};

function updateCanvas() {
    let payload = {
        direction: latestDirection,
    };
    $.post("/update", payload, function(data) {
        data = JSON.parse(data);
        handleGameData(data);
    });
}

addEventListener('keydown', (e) => {
    if (intervalID === -1) intervalID = setInterval(updateCanvas, 100);
    switch(e.key) {
        case 'ArrowRight':
            // pacman.dir = 2;
            // pacman.velocity.y = 1;
            latestDirection = 2;
            break;
        case 'ArrowLeft':
            // pacman.dir = 0;
            // pacman.velocity.y = -1;
            latestDirection = 0;
            break;
        case 'ArrowUp':
            // pacman.dir = 1;
            // pacman.velocity.x = -1;
            latestDirection = 1;
            break;
        case 'ArrowDown':
            // pacman.dir = 3;
            // pacman.velocity.x = 1;
            latestDirection = 3;
            break;
    }
});

addEventListener('keyup', (e) => {
    switch(e.key) {
        case 'ArrowRight':
        case 'ArrowLeft':
            pacman.velocity.y = 0;
            break;
        case 'ArrowUp':
        case 'ArrowDown':
            pacman.velocity.x = 0;
            break;
    }
})

function handleGameData(data) {
    pacman.position.x = data.pacman.loc.x;
    pacman.position.y = data.pacman.loc.y;
    pacman.dir = data.pacman.direction;
    ghostsData = data.ghosts;
    items = data.items;
    gameState.score = data.currentScore;
    app.clear()
    app.drawGameBoard();
}

function initialize() {
    console.log("initialize called")
    let payload = {
        numberOfGhosts: $("#ghost-dropdown").val(),
        lives: $("#lives-dropdown").val()
    };
    $.get("/initialize", payload, function(data) {
        data = JSON.parse(data);
        console.log(data);
        handleGameData(data);
        app.writeMessage("Press any key to start");
    });
}
