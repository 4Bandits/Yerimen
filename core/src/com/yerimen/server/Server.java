package com.yerimen.server;

import com.badlogic.gdx.math.Vector2;
import com.yerimen.json.PowerJsonBuilder;
import com.yerimen.players.Player;
import com.yerimen.powers.FireBall;
import com.yerimen.powers.Power;
import com.yerimen.powers.PowerFactory;
import com.yerimen.screens.game.GameContent;
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
            this.configSocketEvents();
            this.socket.connect();
            this.userInformation = userInformation;
        } catch (Exception e) {
            throw new RuntimeException("Connection Error!");
        }
    }

    public void connect(GameContent gameContent) {
        this.gameContent = gameContent;
        gameContent.setServer(this);
        this.connectionEvent();
    }

    private void configSocketEvents() {
        socket
                .on("getStartedInfo", this::getStartedInfo)
                .on("newPlayer", this::newPlayer)
                .on("playerDisconnected", this::playerDisconnected)
                .on("getEnemies", this::getPlayersInServer)
                .on("playerMoved", this::playerMoved)
                .on("playerAttack", this::playerAttack);
    }

    private void connectionEvent() {
        Player mainPlayer = new Player("", userInformation.getPlayerTexture(), userInformation.getPlayerTextureStatus(), new Vector2(0, 0), PowerFactory.getPower("fireball"));
        this.gameContent.setMainPlayer(mainPlayer);
        mainPlayer.addObserver(this);
    }

    private void newPlayer(Object[] args) {
        JSONObject data = (JSONObject) args[0];
        try {
            String newCharacterID = data.getString("characterID");
            gameContent.addEnemy(newCharacterID);
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

    private void getPlayersInServer(Object[] args) {
        JSONArray objects = (JSONArray) args[0];
        try {
            for (int i = 0; i < objects.length(); i++) {
                float x = ((Double) objects.getJSONObject(i).getDouble("x")).floatValue();
                float y = ((Double) objects.getJSONObject(i).getDouble("y")).floatValue();
                gameContent.addEnemy(objects.getJSONObject(i).getString("characterID"), new Vector2(x, y));
            }
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
            this.gameContent.getMainPlayer().setCharacterID(data.getString("socketID"));

            Double x = data.getDouble("positionX");
            Double y = data.getDouble("positionY");
            this.gameContent.getMainPlayer().setPosition(x.floatValue(), y.floatValue());
        } catch (JSONException e) {
            throw new RuntimeException("SocketIO - Move Character Error");
        }
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

}
