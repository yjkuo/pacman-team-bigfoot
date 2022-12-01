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
    score: 0,
    level: 1,
    bigDotTimeLeft: 0
};
let pacman = {
    position: {x: 50, y: 30},
    velocity: {x: 0, y: 0},
    dir: 2,
    state: false,
    isDead: false,
    deadFrame: 1
};
let flashIdx = 0;
let flashState = false;

let grassImg = new Image();
grassImg.src = './assets/grass.png';
grassImg.alt = "Image not found";

let forestImg = new Image();
forestImg.src = './assets/forest4.png';
forestImg.alt = "Image not found";

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
        c.fillText("Level " + gameState.level, canvas.width/2, 70);
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
            else if (item.name === "fruit") app.drawFruit(item.loc.x, item.loc.y);
        })
        pacman.state = !pacman.state;
        flashState = gameState.bigDotTimeLeft < 40 ? true : false;
        if (flashState) flashIdx = (flashIdx + 1) % 4;
        else flashIdx = (flashIdx + 1) % 2;
        if (pacman.isDead) {
            app.drawDeadPacman(pacman.position.x, pacman.position.y);
            pacman.deadFrame++;
            if (pacman.deadFrame > 12) {
                pacman.deadFrame = 12;
                if (gameState.lives == 0) gameOver();
            }
        } else {
            pacman.deadFrame = 1;
            app.drawPacman(pacman.position.x, pacman.position.y, pacman.state, pacman.dir);
            for (let i = 0; i < ghostsData.length; ++i) {
                app.drawGhost(ghostsData[i], pacman.state);
            }
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

    let drawPacman = function(row, column, animate, direction) {
        // let x = gameStartX + passageWidth * column + passageWidth / 2;
        // let y = gameStartY + passageWidth * row + passageWidth / 2;
        let x = gameStartX + row;
        let y = gameStartY + column;
        let img = animate? pacmanImg[0]: pacmanImg[1];
        let size = passageWidth - 5;
        c.save();
        c.translate(x,y);
        c.rotate(Math.PI / 2 * direction);
        c.drawImage(img, -size / 2, -size / 2, size, size);
        c.restore();
    };

    let drawDeadPacman = function(row, column) {
        let x = gameStartX + row;
        let y = gameStartY + column;
        let size = passageWidth - 5;
        c.save();
        c.translate(x,y);
        c.drawImage(deadPacmanImg[pacman.deadFrame-1], -size / 2, -size / 2, size, size);
        c.restore();
    };

    let drawGhost = function(ghostData, animate) {
        let ghostImg1;
        let ghostImg2;
        if (ghostData.isFlashing) {
            ghostImg1 = flashGhostImg[flashIdx];
            ghostImg2 = flashGhostImg[flashIdx];
        } else if (ghostData.isDead){
            ghostImg1 = deadGhostImg[ghostData.direction]
            ghostImg2 = deadGhostImg[ghostData.direction]
        } else {
            ghostImg1 = ghostImg[ghostData.color][ghostData.direction][0];
            ghostImg2 = ghostImg[ghostData.color][ghostData.direction][1];
        }
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
        let x = gameStartX + row;
        let y = gameStartY + column;
        c.drawImage(fruitImg, x - passageWidth/2, y - passageWidth/2, passageWidth - 5, passageWidth - 5);
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

    let gameOver = function() {
        gameStart = false;
        clearInterval(intervalID);
        intervalID = -1;
        c.fillStyle = "rgba(0, 0, 0, 0.5)";
        c.fillRect(0, 0, canvas.width, canvas.height);
        writeMessage("Game Over");
        setTimeout(initialize, 2000);
    }

    return {
        clear,
        writeMessage,
        drawBackground,
        drawGameBoard,
        drawPassageBlock,
        drawDot,
        drawPacman,
        drawDeadPacman,
        drawGhost,
        drawFruit,
        gamePause,
        dims: {height: canvas.height, width: canvas.width}
    }
}

function loadImg(src) {
    let img = new Image();
    img.src = src;
    return img;
}
function preloadImages() {
    window.pacmanImg = [
        loadImg('./assets/sprites/PacmanL1.png'),
        loadImg('./assets/sprites/PacmanL2.png')
    ];
    window.deadPacmanImg = [
        loadImg('./assets/sprites/DeadPacman1.png'),
        loadImg('./assets/sprites/DeadPacman2.png'),
        loadImg('./assets/sprites/DeadPacman3.png'),
        loadImg('./assets/sprites/DeadPacman4.png'),
        loadImg('./assets/sprites/DeadPacman5.png'),
        loadImg('./assets/sprites/DeadPacman6.png'),
        loadImg('./assets/sprites/DeadPacman7.png'),
        loadImg('./assets/sprites/DeadPacman8.png'),
        loadImg('./assets/sprites/DeadPacman9.png'),
        loadImg('./assets/sprites/DeadPacman10.png'),
        loadImg('./assets/sprites/DeadPacman11.png'),
        loadImg('./assets/sprites/DeadPacman12.png')
    ];
    window.ghostImg = {
        'orange': [
            [loadImg('./assets/sprites/OL1.png'), loadImg('./assets/sprites/OL2.png')],
            [loadImg('./assets/sprites/OU1.png'), loadImg('./assets/sprites/OU2.png')],
            [loadImg('./assets/sprites/OR1.png'), loadImg('./assets/sprites/OR2.png')],
            [loadImg('./assets/sprites/OD1.png'), loadImg('./assets/sprites/OD2.png')],
        ],
        'pink': [
            [loadImg('./assets/sprites/PL1.png'), loadImg('./assets/sprites/PL2.png')],
            [loadImg('./assets/sprites/PU1.png'), loadImg('./assets/sprites/PU2.png')],
            [loadImg('./assets/sprites/PR1.png'), loadImg('./assets/sprites/PR2.png')],
            [loadImg('./assets/sprites/PD1.png'), loadImg('./assets/sprites/PD2.png')],
        ],
        'blue': [
            [loadImg('./assets/sprites/BL1.png'), loadImg('./assets/sprites/BL2.png')],
            [loadImg('./assets/sprites/BU1.png'), loadImg('./assets/sprites/BU2.png')],
            [loadImg('./assets/sprites/BR1.png'), loadImg('./assets/sprites/BR2.png')],
            [loadImg('./assets/sprites/BD1.png'), loadImg('./assets/sprites/BD2.png')],
        ],
        'red': [
            [loadImg('./assets/sprites/RL1.png'), loadImg('./assets/sprites/RL2.png')],
            [loadImg('./assets/sprites/RU1.png'), loadImg('./assets/sprites/RU2.png')],
            [loadImg('./assets/sprites/RR1.png'), loadImg('./assets/sprites/RR2.png')],
            [loadImg('./assets/sprites/RD1.png'), loadImg('./assets/sprites/RD2.png')],
        ]
    }
    window.flashGhostImg = [
        loadImg('./assets/sprites/FlashA1.png'),
        loadImg('./assets/sprites/FlashA2.png'),
        loadImg('./assets/sprites/FlashB1.png'),
        loadImg('./assets/sprites/FlashB2.png')
    ]
    window.deadGhostImg = [
        loadImg('./assets/sprites/DeadGhostL.png'),
        loadImg('./assets/sprites/DeadGhostU.png'),
        loadImg('./assets/sprites/DeadGhostR.png'),
        loadImg('./assets/sprites/DeadGhostD.png')
    ]
}

window.onload = function() {
    app = createApp(document.querySelector("canvas"));
    // intervalID = setInterval(updateCanvas, 100);
    preloadImages();
    gameStart = true;
    $("#pause-btn").click(() => app.gamePause());
    $("#restart-btn").click(() => {
        gameState.lives = $("#lives-dropdown").val();
        gameState.ghosts = $("#ghost-dropdown").val();
        initialize();
    });
    $(".reset-game").change(function(){
        $(".reset-game").blur();
        initialize();
    });
    initialize();
};

function updateCanvas() {
    if (intervalID === -1) {
        return;
    }
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
    pacman.isDead = data.gameFreeze;
    gameState.lives = data.lives;
    ghostsData = data.ghosts;
    items = data.items;
    gameState.score = data.currentScore;
    gameState.level = data.levelCount;
    gameState.bigDotTimeLeft = data.bigDotTimeLeft;
    app.clear();
    app.drawGameBoard();
    if (data.nextLevelFreeze) {
        if (data.levelCount === 1)
            app.writeMessage("Level 2");
        else
            app.writeMessage("Game Won");
    }
}

function initialize() {
    clearInterval(intervalID);
    intervalID = -1;
    let payload = {
        numberOfGhosts: $("#ghost-dropdown").val(),
        lives: $("#lives-dropdown").val(),
        level: $("#level-dropdown").val()
    };
    $.get("/initialize", payload, function(data) {
        data = JSON.parse(data);
        handleGameData(data);
        app.writeMessage("Press any key to start");
    });
}
