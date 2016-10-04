var app = require('express')();
var server = require('http').Server(app);
var io = require('socket.io')(server);
var players = [];
var powers = [];

server.listen(8080, function(){
	log("Server is running...");
});

io.on('connection', function(socket){
	socket.emit('getPlayers', players);
	socket.emit('getPowers', powers);
	socket.broadcast.emit("newPlayer", { id: socket.id });
	socket.on('playerMoved', function(data){
	    data.id = socket.id;
	    socket.broadcast.emit('playerMoved', data);
        updatePlayer(data);
       // log("player moved x: " +  data.x  + " y: " + data.y)
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
	});
	players.push(new player(socket.id, 0,0, 100, 'right', 'werewolf'));
	log("player "+ socket.id)
});

function player(id, x, y, health, direction, name){
    this.id = id;
    this.x = x;
    this.y = y;
    this.health = health;
    this.direction = direction;
    this.name = name;
};

function log(comment){
    console.log(comment);
};

function updatePlayer(data){
    for(var i = 0; i < players.length; i++){
        if(players[i].id == data.id){
            players[i].x = data.x;
            players[i].y = data.y;
            players[i].health = data.health;
            players[i].direction = data.direction;
        };
    };
};

function removePlayer(playerID){
    for(var i = 0; i < players.length; i++){
        if(players[i].id == playerID){
            players.splice(i, 1);
        };
    };
};
