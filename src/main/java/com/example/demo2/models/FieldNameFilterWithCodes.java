package com.example.demo2.models;

import java.util.List;

public class FieldNameFilterWithCodes {

    String fieldName;
    List<MappingCode> mappingCodes;
    String inCondition;
    String andCondition;

    public FieldNameFilterWithCodes(String fieldName, List<MappingCode> mappingCodes, String inCondition, String andCondition) {
        this.fieldName = fieldName;
        this.mappingCodes = mappingCodes;
        this.inCondition = inCondition;
        this.andCondition = andCondition;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public List<MappingCode> getMappingCodes() {
        return mappingCodes;
    }

    public void setMappingCodes(List<MappingCode> mappingCodes) {
        this.mappingCodes = mappingCodes;
    }

    public String getInCondition() {
        return inCondition;
    }

    public void setInCondition(String inCondition) {
        this.inCondition = inCondition;
    }

    public String getAndCondition() {
        return andCondition;
    }

    public void setAndCondition(String andCondition) {
        this.andCondition = andCondition;
    }
}
