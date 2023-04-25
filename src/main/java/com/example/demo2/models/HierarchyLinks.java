package com.example.demo2.models;

public class HierarchyLinks {
    long userId;
    long currentLevel;
    long nextLevel;

    public HierarchyLinks(long userId, long currentLevel, long nextLevel) {
        this.userId = userId;
        this.currentLevel = currentLevel;
        this.nextLevel = nextLevel;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(long currentLevel) {
        this.currentLevel = currentLevel;
    }

    public long getNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(long nextLevel) {
        this.nextLevel = nextLevel;
    }
}
