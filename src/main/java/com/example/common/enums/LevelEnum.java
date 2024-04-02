package com.example.common.enums;

public enum LevelEnum {
    POOR("贫困户"),
    NORMAL("普通户");

    public String level;

    LevelEnum(String level) {
        this.level = level;
    }

}
