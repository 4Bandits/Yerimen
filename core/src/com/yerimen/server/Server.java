package com.yerimen.server;

import com.badlogic.gdx.math.Vector2;
import com.yerimen.json.PowerJsonBuilder;
import com.yerimen.players.MainPlayer;
import com.yerimen.players.Player;
import com.yerimen.players.PlayerStatus;
import com.yerimen.powers.Power;
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
    private HashMap<String, Player> players;
    private List<Power> powers;
    private GameContent gameContent;

    public Server(GameContent gameContent){
        this.players = new HashMap<>();
        this.powers = new ArrayList<>();
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
            .on("playerMoved", this::playerMoved)
            .on("playerAttack", this::playerAttack);

    }

    private void connectionEvent(){
        MainPlayer mainPlayer =new MainPlayer(TextureManager.getInstance().getWerewolfTexture(), TextureManager.getInstance().getWerewolfStatus() , new Vector2(0,0));
        this.gameContent.setMainPlayer(mainPlayer);
        mainPlayer.addObserver(this);
    }

    private void newPlayer(Object[] args){
        JSONObject data = (JSONObject) args[0];
        try {
             String newPlayerID = data.getString("id");
             players.put(newPlayerID, new Player(TextureManager.getInstance().getWerewolfTexture(), new PlayerStatus(), new Vector2(0,0)));
        } catch (JSONException e) {
            throw new RuntimeException("SocketIO - Adding new Player Error");
        }
    }

    private void playerDisconnected(Object[] args){
        JSONObject data = (JSONObject) args[0];
        try {
            String anotherPlayerID = data.getString("id");
            players.remove(anotherPlayerID);
        } catch (JSONException e) {
            throw new RuntimeException("SocketIO - Remove old Player Error");
        }
    }

    private void getPlayersInServer(Object[] args){
        JSONArray objects = (JSONArray) args[0];
        try {
            for(int i = 0; i < objects.length(); i++){
                float x = ((Double) objects.getJSONObject(i).getDouble("x")).floatValue();
                float y = ((Double) objects.getJSONObject(i).getDouble("y")).floatValue();
                Player anotherPlayer = new Player(TextureManager.getInstance().getWerewolfTexture(), new PlayerStatus(), new Vector2(x,y));
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
                players.get(playerId).setPosition(x.floatValue(),y.floatValue());
                players.get(playerId).setDirection(direction);
            }
        } catch (JSONException e) {
            throw new RuntimeException("SocketIO - Move Player Error");
        }
    }

    private void playerAttack(Object[] args){
        JSONObject data = (JSONObject) args[0];
        Power power = new PowerJsonBuilder(data).buildObject();
        this.powers.add(power);
    }

    public List<Player> getPlayers(){
        return new ArrayList<>(this.players.values());
    }

    public List<Power> getPowers(){
        return new ArrayList<>(this.powers);
    }

    public void update(JSONObject jsonObject){
        socket.emit("playerMoved", jsonObject);
    }

    public void update(Power power){
        //power.setId(this.getNextId());
        this.powers.add(power);
        socket.emit("playerAttack", power.toJson());
    }

}
