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
import com.yerimen.screens.game.GameHud;
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
    private GameHud gameHud;
    private ConnectionScreen connectionScreen;

    public Server(String serverUrl, UserInformation userInformation, ConnectionScreen connectionScreen) {
        try {
            this.socket = IO.socket(serverUrl);
            this.configSocketEvents();
            this.socket.connect();
            this.userInformation = userInformation;
            this.connectionScreen = connectionScreen;
        } catch (Exception e) {
            throw new RuntimeException("Connection Error!");
        }
    }

    public void setGameContent(GameContent gameContent) {
        this.gameContent = gameContent;
        gameContent.setServer(this);
    }

    private void configSocketEvents() {
        socket
                .on("getStartedInfo", this::getStartedInfo)
                .on("registerNewPlayer", this::registerNewPlayer)
                .on("playerDisconnected", this::playerDisconnected)
                .on("playerMoved", this::playerMoved)
                .on("playerAttack", this::playerAttack)
                .on("showNotification", this::showNotification);
    }

    private void showNotification(Object[] args) {
        JSONObject data = (JSONObject) args[0];
        try {
            String notification = data.getString("notification");
            this.gameHud.showNotification(notification);
        } catch (JSONException e) {
            throw new RuntimeException("SocketIO - Showing Notification Error");
        }
    }

    private void registerNewPlayer(Object[] args) {
        JSONObject data = (JSONObject) args[0];
        try {
            String id = data.getString("id");
            String userName = data.getString("name");
            String character = data.getString("character");
            Double x = data.getDouble("x");
            Double y = data.getDouble("y");
            gameContent.addEnemy(id, new Vector2(x.floatValue(), y.floatValue()), character,userName);
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
                String character = args.getJSONObject(i).getString("character");
                String id = args.getJSONObject(i).getString("id");
                String name = args.getJSONObject(i).getString("name");
                enemies.put(id, PlayerFactory.getCharacter(id, new Vector2(x, y), character,name));
            }
            return enemies;
        } catch (JSONException e) {
            throw new RuntimeException("SocketIO - Get All Players Error");
        }
    }

    private void playerMoved(Object[] args) {
        JSONObject data = (JSONObject) args[0];
        try {
            String characterID = data.getString("id");
            Double x = data.getDouble("x");
            Double y = data.getDouble("y");
            int health = data.getInt("health");
            String direction = data.getString("direction");
            gameContent.updateCharacter(characterID, x.floatValue(), y.floatValue(), direction, health);
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
            float x = (float) data.getDouble("x");
            float y = (float) data.getDouble("y");

            Player mainPlayer = new Player(id, userInformation.getUsername(), userInformation.getPlayerTexture(), userInformation.getPlayerTextureStatus(), new Vector2(x, y), PowerFactory.getPower(PowerType.Fireball), userInformation.getUsername());
            mainPlayer.addObserver(this);
            connectionScreen.setPlayer(mainPlayer, getPlayersInServer(data.getJSONArray("players")));
        } catch (JSONException e) {
            throw new RuntimeException("SocketIO - Move Character Error");
        }
    }

    public void notifyMyLogin() {
        socket.emit("addNewPlayer", this.getMainPlayerSelected());
        this.showNotification(this.userInformation.getUsername() + " just logged in.");
    }

    public void update(JSONObject jsonObject) {
        socket.emit("playerMoved", jsonObject);
    }

    public void update(String notification) {
        this.showNotification(notification);
    }

    public void update(Power power) {
        gameContent.addPower(power);
        socket.emit("playerAttack", power.toJson());
    }

    @Override
    public void updateKill() {
        this.showNotification("A player was killed.");
    }

    @Override
    public void updateSkillChanged(String newSkill) {

    }

    public void destroyPower(Power power) {
        socket.emit("destroyPower", power.toJson());
    }

    public JSONObject getMainPlayerSelected() {
        JSONObject data = new JSONObject();
        try {
            data.put("name", this.userInformation.getUsername());
            data.put("character", this.userInformation.getCharacter());
            return data;
        } catch (Exception e) {
            throw new RuntimeException("Error - Json");
        }
    }

    public void showNotification(String notification) {
        this.socket.emit("showNotification", this.notificationAsJson(notification));
    }

    private JSONObject notificationAsJson(String notification) {
        JSONObject newNotification = new JSONObject();
        try {
            newNotification.put("notification", notification);
            return newNotification;
        } catch (JSONException e) {
            throw new RuntimeException("Error - JSON Notification");
        }
    }

    public void setGameHud(GameHud gameHud) {
        this.gameHud = gameHud;
    }
}
