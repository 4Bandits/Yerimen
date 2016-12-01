var app = require('express')();
var server = require('http').Server(app);
var io = require('socket.io')(server);
var self;

var players = [];
var powers = [];

server.listen(9000, function(){
	log("Yerimen Server started.");
});

io.on('connection', function(socket){

	//socket.emit('getPowers', powers);

	socket.on('addNewPlayer', function(data){
	    var info = getStartedInfo(socket);
        socket.emit('getStartedInfo', info);
	    addNewPlayer(socket, info.x, info.y, data.name, data.character);
	    socket.broadcast.emit("registerNewPlayer", { id: socket.id, character: data.character, x: info.x, y: info.y, name:data.name});
	});

	socket.on('playerMoved', function(data){
	    socket.broadcast.emit('playerMoved', data);
        updatePlayer(data);
	});

	socket.on('playerAttack', function(data){
        socket.broadcast.emit('playerAttack', data);
        powers.push(data);
	});

	socket.on('takeDamage', function(data){
        socket.broadcast.emit('takeDamage', data);
        updatePlayer(data);
	});

	socket.on('disconnect', function(){
		socket.broadcast.emit('playerDisconnected', { id: socket.id });
		log("Player with ID [" + socket.id + "] just logged out.");
		removePlayer(socket.id);
	});
});


function Player(id, x, y, health, direction, name, character){
    this.id = id;
    this.x = x;
    this.y = y;
    this.health = health;
    this.direction = direction;
    this.name = name;
    this.character = character;
};

function log(comment){
    console.log("[INFO | " + currentTimestamp() +"] " + comment);
};

function currentTimestamp() {
    var date = new Date();
    return date.toLocaleTimeString()
}

function addNewPlayer(socket, x, y, name, character) {
    players.push(new Player(socket.id, x,y, 100, 'right', name, character));
    log("Player with ID [" + socket.id + "] just logged in.");
}



function updatePlayer(data){
    forPlayer(data.id, function(player, index) {
       player.x = data.x;
       player.y = data.y;
       player.health = data.health;
       player.direction = data.direction;
    });
};

function removePlayer(playerID){
    forPlayer(playerID, function(player, index){
        players.splice(index, 1);
    });
};

function forPlayer(id, callback) {
    forEach(players, function(player, index) {
        if(player.id == id){
           callback(player, index);
        };
    });
}

function forEach(list, callback) {
    for(var i = 0; i < list.length; i++){
        callback(list[i], i);
    };
}

function getStartedInfo(socket){
    var vector2 = getDefaultPosition();
    return {
                id: socket.id,
                x: vector2.x,
                y: vector2.y,
                players,
                powers

           }
}


function Vector2d(x, y){
    this.x = x;
    this.y = y;
}

function getDefaultPosition(){
    var result;
    //todo implementar una forma dinamica
    switch(players.length){
        case 0:
            result = new Vector2d(400, 400);
        break;
        case 1:
            result = new Vector2d(2900, 2900);
        break;
        case 2:
            result = new Vector2d(2900, 400);
        break;
        case 3:
            result = new Vector2d(400, 2900);
        break;
        default:
        result = new Vector2d(1500, 1500);
        break
    }
    return result;
}
