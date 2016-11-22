package com.yerimen.server;

import com.badlogic.gdx.math.Vector2;
import com.yerimen.json.PowerJsonBuilder;
import com.yerimen.players.Character;
import com.yerimen.players.Player;
import com.yerimen.players.PlayerFactory;
import com.yerimen.powers.FireBall;
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
import java.util.List;

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

    private void configSocketEvents() {
        socket
                .on("getStartedInfo", this::getStartedInfo)
                .on("newPlayer", this::newPlayer)
                .on("playerDisconnected", this::playerDisconnected)
                .on("playerMoved", this::playerMoved)
                .on("playerAttack", this::playerAttack);
    }


    private void newPlayer(Object[] args) {
        JSONObject data = (JSONObject) args[0];
        try {
            String newCharacterID = data.getString("characterID");
            String newCharacterSelected = data.getString("characterSelected");
            Double x = data.getDouble("x");
            Double y = data.getDouble("y");
            gameContent.addEnemy(newCharacterID, new Vector2(x.floatValue(), y.floatValue()), newCharacterSelected);
        } catch (JSONException e) {
            throw new RuntimeException("SocketIO - Adding new Character Error");
        }
    }

    private void playerDisconnected(Object[] args) {
        JSONObject data = (JSONObject) args[0];
        try {
            String characterID = data.getString("characterID");
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
                String name = args.getJSONObject(i).getString("name");
                String id = args.getJSONObject(i).getString("characterID");
                enemies.put(id, PlayerFactory.getCharacter(id,new Vector2(x,y),name));
               // cn.addEnemy(args.getJSONObject(i).getString("characterID"), new Vector2(x, y), name);
            }
            return enemies;
        } catch (JSONException e) {
            throw new RuntimeException("SocketIO - Get All Players Error");
        }
    }

    private void playerMoved(Object[] args) {
        JSONObject data = (JSONObject) args[0];
        try {
            String characterID = data.getString("characterID");
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
            String id = data.getString("socketID");
            float x =(float) data.getDouble("positionX");
            float y = (float)data.getDouble("positionY");

            Player mainPlayer = new Player(id, userInformation.getPlayerTexture(), userInformation.getPlayerTextureStatus(), new Vector2(x, y), PowerFactory.getPower(PowerType.Fireball));
            mainPlayer.addObserver(this);
            cn.SetPlayer(mainPlayer, getPlayersInServer(data.getJSONArray("players")));
        } catch (JSONException e) {
            throw new RuntimeException("SocketIO - Move Character Error");
        }
    }

    public void notifyNewPlayer(){
        socket.emit("notifyNewPlayer", this.getMainPlayerSelected());
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
            data.put("name", this.gameContent.getMainPlayer().getName());
            data.put("x", this.gameContent.getMainPlayer().getXPosition());
            data.put("y", this.gameContent.getMainPlayer().getYPosition());
            return data;
        } catch (Exception e) {
            throw new RuntimeException("Error - Json");
        }
    }
}
