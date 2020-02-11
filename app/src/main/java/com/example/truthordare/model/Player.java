package com.example.truthordare.model;

import com.example.truthordare.utils.Utils;

public class Player {
    public static final String KEY_NAME = "player_name";
    public int id;
    public String playerName;

    public Player(int id, String playerName) {
        this(playerName);
        this.id = id;
    }

    public Player(String playerName) {
        this.playerName = playerName;
    }

    public String getPrefName() {
        return Utils.SCORE_PREFIX + this.id;
    }

    public String toString() {
        return this.playerName;
    }
}
