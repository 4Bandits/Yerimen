package com.yerimen.server;

import org.json.JSONObject;

public interface Observer {

    void update(JSONObject jsonObject);
}
