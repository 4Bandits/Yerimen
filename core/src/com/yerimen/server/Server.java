package com.yerimen.server;

import com.badlogic.gdx.math.Vector2;
import com.yerimen.json.PowerJsonBuilder;
import com.yerimen.players.Character;
import com.yerimen.players.Player;
import com.yerimen.players.PlayerFactory;
import com.yerimen.powers.Power;
import com.yerimen.powers.PowerFactory;
import com.yerimen.powers.PowerType;
import com.yerimen.screens.game.ConnectionScreen;
import com.yerimen.screens.game.GameContent;
import com.yerimen.user.UserInformation;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Server implements Observer {

    private UserInformation userInformation;
    private Socket socket;
    private GameContent gameContent;
    private ConnectionScreen cn;

    public Server(String serverUrl, UserInformation userInformation, ConnectionScreen cn) {
        try {
            this.socket = IO.socket(serverUrl);
            this.configSocketEvents();
            this.socket.connect();
            this.userInformation = userInformation;
            this.cn = cn;
        } catch (Exception e) {
            throw new RuntimeException("Connection Error!");
        }
    }

    public void setGameContent(GameContent gameContent){
        this.gameContent = gameContent;
        gameContent.setServer(this);
    }
    public UserInformation getUserInformation(){
        return userInformation;
    }

    private void configSocketEvents() {
        socket
                .on("getStartedInfo", this::getStartedInfo)
                .on("registerNewPlayer", this::registerNewPlayer)
                .on("playerDisconnected", this::playerDisconnected)
                .on("playerMoved", this::playerMoved)
                .on("playerAttack", this::playerAttack)
                .on("getUsernames", this::getUsernames);
    }


    private void registerNewPlayer(Object[] args) {
        JSONObject data = (JSONObject) args[0];
        try {
            String id = data.getString("id");
            String userName= data.getString("username");
            String character = data.getString("character");
            Double x = data.getDouble("x");
            Double y = data.getDouble("y");
            gameContent.addEnemy(id, new Vector2(x.floatValue(), y.floatValue()), character,args);
            gameContent.registerEnemyUsername(id,userName);
        } catch (JSONException e) {
            throw new RuntimeException("SocketIO - Adding new Character Error");
        }
    }

    private void playerDisconnected(Object[] args) {
        JSONObject data = (JSONObject) args[0];
        try {
            String characterID = data.getString("id");
            gameContent.removeEnemy(characterID);
        } catch (JSONException e) {
            throw new RuntimeException("SocketIO - Remove Character Error");
        }
    }

    private HashMap<String, Character> getPlayersInServer(JSONArray args) {
        HashMap<String, Character> enemies = new HashMap<>();

        try {
            for (int i = 0; i < args.length(); i++) {
                float x = ((Double) args.getJSONObject(i).getDouble("x")).floatValue();
                float y = ((Double) args.getJSONObject(i).getDouble("y")).floatValue();
                String name = args.getJSONObject(i).getString("character");
                String id = args.getJSONObject(i).getString("id");
                enemies.put(id, PlayerFactory.getCharacter(id,new Vector2(x,y),name));
            }
            return enemies;
        } catch (JSONException e) {
            throw new RuntimeException("SocketIO - Get All Players Error");
        }
    }
    private void getUsernames(Object[] args) {
        JSONObject data= (JSONObject)args[0];
        try {
                String id = data.getString("id");
                String username = data.getString("username");
                gameContent.registerEnemyUsername(id, username);

        } catch (JSONException e) {
            throw new RuntimeException("SocketIO - Get All Usernames Error");
        }
    }

    private void playerMoved(Object[] args) {
        JSONObject data = (JSONObject) args[0];
        try {
            String characterID = data.getString("id");
            Double x = data.getDouble("x");
            Double y = data.getDouble("y");
            String direction = data.getString("direction");
            gameContent.moveEnemy(characterID, x.floatValue(), y.floatValue(), direction);
        } catch (JSONException e) {
            throw new RuntimeException("SocketIO - Move Character Error");
        }
    }

    private void playerAttack(Object[] args) {
        JSONObject data = (JSONObject) args[0];
        Power power = new PowerJsonBuilder(data).buildObject();
        gameContent.addPower(power);
    }


    private void getStartedInfo(Object[] args) {
        JSONObject data = (JSONObject) args[0];
        try {
            String id = data.getString("id");
            float x =(float) data.getDouble("x");
            float y = (float)data.getDouble("y");

            Player mainPlayer = new Player(id, userInformation.getPlayerTexture(), userInformation.getPlayerTextureStatus(), new Vector2(x, y), PowerFactory.getPower(PowerType.Fireball));
            mainPlayer.addObserver(this);
            cn.SetPlayer(mainPlayer, getPlayersInServer(data.getJSONArray("players")));

        } catch (JSONException e) {
            throw new RuntimeException("SocketIO - Move Character Error");
        }
    }

    public void notifyMyLogin(){
        socket.emit("addNewPlayer", this.getMainPlayerSelected());

    }

    public void update(JSONObject jsonObject) {
        socket.emit("playerMoved", jsonObject);

    }

    public void update(Power power) {
        gameContent.addPower(power);
        socket.emit("playerAttack", power.toJson());
    }

    public void destroyPower(Power power) {
        socket.emit("destroyPower", power.toJson());
    }

    public JSONObject getMainPlayerSelected() {
        JSONObject data = new JSONObject();
        try {
            data.put("name", this.userInformation.getUsername());
            data.put("character", this.userInformation.getCharacter());
            data.put("username",this.userInformation.getUsername());
            return data;
        } catch (Exception e) {
            throw new RuntimeException("Error - Json");
        }
    }
}
