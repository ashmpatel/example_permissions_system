package com.example.demo2.models;

public class PermissionUser {
    long userId;

    String userIdName;

    long hierarchyId;

    String fieldName;

    String inCondition;

    String addCondition;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserIdName() {
        return userIdName;
    }

    public void setUserIdName(String userIdName) {
        this.userIdName = userIdName;
    }

    public long getHierarchyId() {
        return hierarchyId;
    }

    public void setHierarchyId(long hierarchyId) {
        this.hierarchyId = hierarchyId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getInCondition() {
        return inCondition;
    }

    public void setInCondition(String inCondition) {
        this.inCondition = inCondition;
    }

    public String getAddCondition() {
        return addCondition;
    }

    public void setAddCondition(String addCondition) {
        this.addCondition = addCondition;
    }
}
