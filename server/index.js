var app = require('express')();
var server = require('http').Server(app);
var io = require('socket.io')(server);

var players = [];
var powers = [];

server.listen(9000, function(){
	log("Yerimen Server started.");
});

io.on('connection', function(socket){
    socket.emit('getSocketID', {socketID: socket.id})
	socket.emit('getEnemies', players);
	socket.emit('getPowers', powers);

	socket.broadcast.emit("newPlayer", { characterID: socket.id });

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


	loginNewPlayer(socket);
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
    var hour = leadingZero(date.getHours());
    var minutes = leadingZero(date.getMinutes());
    var seconds = leadingZero(date.getSeconds());
    return hour + ":" + minutes + ":" + seconds;
}

function leadingZero(string) {
    return ("0" + string).slice(-2);
};

function loginNewPlayer(socket) {
    players.push(new Player(socket.id, 0,0, 100, 'right', 'werewolf'));
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
