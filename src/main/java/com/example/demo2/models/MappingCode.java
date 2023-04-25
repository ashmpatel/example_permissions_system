package com.example.demo2.models;

public class MappingCode {

    long userId;
    long levelId;
    String mappingCode;

    public MappingCode(long userId, long levelId, String mappingCode) {
        this.userId = userId;
        this.levelId = levelId;
        this.mappingCode = mappingCode;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getLevelId() {
        return levelId;
    }

    public void setLevelId(long levelId) {
        this.levelId = levelId;
    }

    public String getMappingCode() {
        return mappingCode;
    }

    public void setMappingCode(String mappingCode) {
        this.mappingCode = mappingCode;
    }
}
