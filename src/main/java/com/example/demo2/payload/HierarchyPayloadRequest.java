package com.example.demo2.payload;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class HierarchyPayloadRequest {
    @NotBlank
    @NotNull
    private String hierarchyName;

    public String getHierarchyName() {
        return hierarchyName;
    }

    public void setHierarchyName(String hierarchyName) {
        this.hierarchyName = hierarchyName;
    }
}