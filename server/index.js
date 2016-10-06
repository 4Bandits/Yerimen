var app = require('express')();
var server = require('http').Server(app);
var io = require('socket.io')(server);
var players = [];
var powers = [];

server.listen(8080, function(){
	log("Server is running...");
});

io.on('connection', function(socket){
    log("Player connected. ID: "+ socket.id)

	socket.emit('getEnemies', players);
	socket.emit('getPowers', powers);

	socket.broadcast.emit("newPlayer", { id: socket.id });

	socket.on('playerMoved', function(data){
	    data.id = socket.id;
	    socket.broadcast.emit('playerMoved', data);
        updatePlayer(data);
	});

	socket.on('playerAttack', function(data){
        socket.broadcast.emit('playerAttack', data);
        powers.push(data);
	});

	socket.on('takeDamage', function(data){
	    data.id = socket.id;
        socket.broadcast.emit('takeDamage', data);
        updatePlayer(data);
	});

	socket.on('disconnect', function(){
		socket.broadcast.emit('playerDisconnected', { id: socket.id });
		removePlayer(socket.id);

		log("Player disconnected. ID: "+ socket.id)
	});

	players.push(new player(socket.id, 0, 0, 100, 'right', 'werewolf'));
});

function player(id, x, y, health, direction, name){
    this.id = id;
    this.x = x;
    this.y = y;
    this.health = health;
    this.direction = direction;
    this.name = name;
};

function updatePlayer(data) {
    withPlayerOfIdDo(data.id, function(player) {
        player.x = data.x;
        player.y = data.y;
        player.health = data.health;
        player.direction = data.direction;
    })
};

function removePlayer(playerID) {
    withPlayerOfIdDo(playerID, function(index, player) {
        players.splice(index, 1);
    })
};

function withPlayerOfIdDo(id, callback) {
    for(var i = 0; i < players.length; i++){
        var currentPlayer = players[i];

        if(currentPlayer.id == id){
            callback(i, currentPlayer);
        };
    };
}

function log(comment) {
    console.log(comment);
};