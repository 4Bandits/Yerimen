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

    var startedInfo = getStartedInfo(socket);
    socket.emit('getStartedInfo', startedInfo);
	socket.emit('getEnemies', players);
	socket.emit('getPowers', powers);

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
		socket.broadcast.emit('playerDisconnected', { characterID: socket.id });
		log("Player with ID [" + socket.id + "] just logged out.");
		removePlayer(socket.id);
	});

	socket.on('notifyNewPlayer', function(data){
	    var name = data.name;
	    addNewPlayer(socket, data.x, data.y, name);
	    socket.broadcast.emit("newPlayer", { characterID: socket.id, characterSelected: name, x: data.x, y: data.y});
	});

});


function Player(characterID, x, y, health, direction, name){
    this.characterID = characterID;
    this.x = x;
    this.y = y;
    this.health = health;
    this.direction = direction;
    this.name = name;
};

function log(comment){
    console.log("[INFO | " + currentTimestamp() +"] " + comment);
};

function currentTimestamp() {
    var date = new Date();
    return date.toLocaleTimeString()
}

function addNewPlayer(socket, x, y, name) {
    players.push(new Player(socket.id, x,y, 100, 'right', name));
    log("Player with ID [" + socket.id + "] just logged in.");
}

function updatePlayer(data){
    forPlayer(data.characterID, function(player, index) {
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
        if(player.characterID == id){
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
                socketID: socket.id,
                positionX: vector2.x,
                positionY: vector2.y
           }
}

function Vector2d(x, y){
    this.x = x;
    this.y = y;
}

function getDefaultPosition(){
    var result;
    //todo implementar una forma dinamica
    switch(players.length +1){
        case 1:
            result = new Vector2d(400, 400);
        break;
        case 2:
            result = new Vector2d(2900, 2900);
        break;
        case 3:
            result = new Vector2d(2900, 400);
        break;
        case 4:
            result = new Vector2d(400, 2900);
        break;
        default:
        result = new Vector2d(1500, 1500);
        break
    }
    return result;
}
