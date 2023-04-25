package com.example.demo2.services;


import com.example.demo2.models.FieldNameFilterWithCodes;
import com.example.demo2.models.MappingCode;
import com.example.demo2.models.PermissionUser;
import com.example.demo2.models.UserHierarchy;
import com.example.demo2.payload.HierarchyPayloadRequest;
import com.example.demo2.payload.MessageResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface UserHierarchyService {
    MessageResponse createHierarchy(HierarchyPayloadRequest employeeRequest);

    Optional<UserHierarchy> updateHierarchy(Integer hierarchyId, HierarchyPayloadRequest employeeRequest);

    void deleteHierarchy(Integer employeeId);

    UserHierarchy getASingleHierarchy(Integer hierarchyId);

    List<UserHierarchy> getAllHierarchies();

    PermissionUser getSingleUserByName(String name);

    FieldNameFilterWithCodes getHierarchyForUser(String userName, int entryHierarchyId);

    List<FieldNameFilterWithCodes> getUserAllPermissions(String userName);

    String convertToSql(List<FieldNameFilterWithCodes>  userListOfPermissions);

}
