package com.example.demo2.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserNamePayloadRequest {
    @NotBlank
    @NotNull
    private String userName;
    private int entryHierarchyId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String hierarchyName) {
        this.userName = hierarchyName;
    }

    public int getEntryHierarchyId() {
        return entryHierarchyId;
    }

    public void setEntryHierarchyId(int entryHierarchyId) {
        this.entryHierarchyId = entryHierarchyId;
    }
}