package com.yerimen.server;

import com.badlogic.gdx.math.Vector2;
import com.yerimen.players.Character;
import com.yerimen.players.Player;
import com.yerimen.players.CharacterStatus;
import com.yerimen.screen.GameContent;
import com.yerimen.textures.TextureManager;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server implements Observer{

    private Socket socket;
    private HashMap<String, Character> players;
    private GameContent gameContent;

    public Server(GameContent gameContent){
        this.players = new HashMap<>();
        this.gameContent = gameContent;
        this.connectSocket();
        this.configSocketEvents();
    }

    private void connectSocket(){
        try {
            socket = IO.socket("http://localhost:8080");
            socket.connect();
        } catch (Exception e){
            throw new RuntimeException("Connection Error!!!");
        }
    }

    private void configSocketEvents(){
        socket
            .on(Socket.EVENT_CONNECT, args -> this.connectionEvent())
            .on("newPlayer", this::newPlayer)
            .on("playerDisconnected", this::playerDisconnected)
            .on("getPlayers", this::getPlayersInServer)
            .on("playerMoved", this::playerMoved);
    }

    private void connectionEvent(){
        Player mainPlayer =new Player(TextureManager.getInstance().getWerewolfTexture(), TextureManager.getInstance().getWerewolfStatus() , new Vector2(0,0));
        this.gameContent.setMainPlayer(mainPlayer);
        mainPlayer.addObserver(this);
    }

    private void newPlayer(Object[] args){
        JSONObject data = (JSONObject) args[0];
        try {
             String newPlayerID = data.getString("id");
             players.put(newPlayerID, new Character(TextureManager.getInstance().getWerewolfTexture(), new CharacterStatus(), new Vector2(0,0)));
        } catch (JSONException e) {
            throw new RuntimeException("SocketIO - Adding new Character Error");
        }
    }

    private void playerDisconnected(Object[] args){
        JSONObject data = (JSONObject) args[0];
        try {
            String anotherPlayerID = data.getString("id");
            players.remove(anotherPlayerID);
        } catch (JSONException e) {
            throw new RuntimeException("SocketIO - Remove old Character Error");
        }
    }

    private void getPlayersInServer(Object[] args){
        JSONArray objects = (JSONArray) args[0];
        try {
            for(int i = 0; i < objects.length(); i++){
                float x = ((Double) objects.getJSONObject(i).getDouble("x")).floatValue();
                float y = ((Double) objects.getJSONObject(i).getDouble("y")).floatValue();
                Character anotherPlayer = new Character(TextureManager.getInstance().getWerewolfTexture(), new CharacterStatus(), new Vector2(x,y));
                players.put(objects.getJSONObject(i).getString("id"), anotherPlayer);
            }
        }catch (JSONException e){
            throw new RuntimeException("SocketIO - Get All Players Error");
        }
    }

    private void playerMoved(Object[] args){
        JSONObject data = (JSONObject) args[0];
        try {
            String playerId = data.getString("id");
            Double x = data.getDouble("x");
            Double y = data.getDouble("y");
            String direction = data.getString("direction");
            if(players.get(playerId) != null){
                players.get(playerId).move(x.floatValue(),y.floatValue());
                players.get(playerId).setDirection(direction);
            }
        } catch (JSONException e) {
            throw new RuntimeException("SocketIO - Move Character Error");
        }
    }

    public List<Character> getPlayers(){
        return new ArrayList<>(this.players.values());
    }

    public void update(JSONObject jsonObject){
        socket.emit("playerMoved", jsonObject);
    }

}
