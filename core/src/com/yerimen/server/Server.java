package com.yerimen.server;

import com.badlogic.gdx.math.Vector2;
import com.yerimen.json.PowerJsonBuilder;
import com.yerimen.players.Player;
import com.yerimen.powers.Power;
import com.yerimen.screens.game.GameContent;
import com.yerimen.textures.TextureManager;
import com.yerimen.user.UserInformation;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Server implements Observer {

    private UserInformation userInformation;
    private Socket socket;
    private GameContent gameContent;

    public void attemptConnectionTo(String serverUrl, UserInformation userInformation) {
        try {
            this.socket = IO.socket(serverUrl);
            this.socket.connect();
            this.userInformation = userInformation;
        } catch (Exception e) {
            throw new RuntimeException("Connection Error!");
        }
    }

    public void connect(GameContent gameContent) {
        this.gameContent = gameContent;
        this.configSocketEvents();
        this.connectionEvent();
    }

    private void configSocketEvents() {
        socket
            .on("newPlayer", this::newPlayer)
            .on("playerDisconnected", this::playerDisconnected)
            .on("getEnemies", this::getPlayersInServer)
            .on("playerMoved", this::playerMoved)
            .on("playerAttack", this::playerAttack);
    }

    private void connectionEvent() {
        Player mainPlayer = new Player(userInformation.getPlayerTexture(), userInformation.getPlayerTextureStatus(), new Vector2(0, 0));
        this.gameContent.setMainPlayer(mainPlayer);
        mainPlayer.addObserver(this);
    }

    private void newPlayer(Object[] args) {
        JSONObject data = (JSONObject) args[0];
        try {
            String newPlayerID = data.getString("id");
            gameContent.addEnemy(newPlayerID);
        } catch (JSONException e) {
            throw new RuntimeException("SocketIO - Adding new Character Error");
        }
    }

    private void playerDisconnected(Object[] args) {
        JSONObject data = (JSONObject) args[0];
        try {
            String playerID = data.getString("id");
            gameContent.removeEnemy(playerID);
        } catch (JSONException e) {
            throw new RuntimeException("SocketIO - Remove Character Error");
        }
    }

    private void getPlayersInServer(Object[] args) {
        JSONArray objects = (JSONArray) args[0];
        try {
            for (int i = 0; i < objects.length(); i++) {
                float x = ((Double) objects.getJSONObject(i).getDouble("x")).floatValue();
                float y = ((Double) objects.getJSONObject(i).getDouble("y")).floatValue();
                gameContent.addEnemy(objects.getJSONObject(i).getString("id"), new Vector2(x, y));
            }
        } catch (JSONException e) {
            throw new RuntimeException("SocketIO - Get All Players Error");
        }
    }

    private void playerMoved(Object[] args) {
        JSONObject data = (JSONObject) args[0];
        try {
            String playerId = data.getString("id");
            Double x = data.getDouble("x");
            Double y = data.getDouble("y");
            String direction = data.getString("direction");
            gameContent.moveEnemy(playerId, x.floatValue(), y.floatValue(), direction);
        } catch (JSONException e) {
            throw new RuntimeException("SocketIO - Move Character Error");
        }
    }

    private void playerAttack(Object[] args) {
        JSONObject data = (JSONObject) args[0];
        Power power = new PowerJsonBuilder(data).buildObject();
        gameContent.addPower(power);
    }

    public void update(JSONObject jsonObject) {
        socket.emit("playerMoved", jsonObject);
    }

    public void update(Power power) {
        //power.setId(this.getNextId());
        gameContent.addPower(power);
        socket.emit("playerAttack", power.toJson());
    }

}
