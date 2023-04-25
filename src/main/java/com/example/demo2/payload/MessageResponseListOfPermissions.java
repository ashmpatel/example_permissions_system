package com.example.demo2.payload;

import com.example.demo2.models.FieldNameFilterWithCodes;
import com.example.demo2.models.MappingCode;

import java.util.List;

public class MessageResponseListOfPermissions<T> {
    T userPermisisons;

    public MessageResponseListOfPermissions(T userPermisisons) {
        this.userPermisisons = userPermisisons;
    }

    public T getUserPermisisons() {
        return userPermisisons;
    }

    public void setUserPermisisons(T userPermisisons) {
        this.userPermisisons = userPermisisons;
    }
}
