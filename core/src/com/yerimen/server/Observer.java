package com.yerimen.server;

import com.yerimen.powers.Power;
import org.json.JSONObject;

public interface Observer {

    void update(JSONObject jsonObject);
    void update(Power power);
    void updateKill();
    void updateSkillChanged(String newSkill);
}
