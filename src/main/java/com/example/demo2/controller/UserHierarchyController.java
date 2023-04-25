package com.example.demo2.controller;

import com.example.demo2.models.FieldNameFilterWithCodes;
import com.example.demo2.models.PermissionUser;
import com.example.demo2.models.UserHierarchy;
import com.example.demo2.payload.HierarchyPayloadRequest;
import com.example.demo2.payload.MessageResponse;
import com.example.demo2.payload.MessageResponseListOfPermissions;
import com.example.demo2.payload.UserNamePayloadRequest;
import com.example.demo2.services.UserHierarchyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/userhierarchy")
public class UserHierarchyController {

    @Autowired
    UserHierarchyService userHierarchyService;

    @GetMapping("/all")
    public ResponseEntity<List<UserHierarchy>> getAllEmployees() {
        List<UserHierarchy> employees = userHierarchyService.getAllHierarchies();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<UserHierarchy> getHierarchyById(@PathVariable("id") Integer id) {
        UserHierarchy employee = userHierarchyService.getASingleHierarchy(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<MessageResponse> addEmployee(@RequestBody HierarchyPayloadRequest userHierarchy) {
        MessageResponse newEmployee = userHierarchyService.createHierarchy(userHierarchy);
        return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MessageResponse> updateEmployee(@PathVariable Integer id, @RequestBody HierarchyPayloadRequest userHierarchyPayload) {
        Optional<UserHierarchy> response = userHierarchyService.updateHierarchy(id, userHierarchyPayload);
        MessageResponse userHierarchyResponse = new MessageResponse(response.get().getHierarchyAlternateName());

        return new ResponseEntity<>(userHierarchyResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteHierarchyId(@PathVariable("id") Integer id) {
        userHierarchyService.deleteHierarchy(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/getUserByName")
    public ResponseEntity<MessageResponse> addEmployee(@RequestBody UserNamePayloadRequest userName) {
        PermissionUser user = userHierarchyService.getSingleUserByName(userName.getUserName());
        return new ResponseEntity<>(new MessageResponse(String.valueOf(user.getUserId())), HttpStatus.OK);
    }

    @PostMapping("/getUserPermissions")
    public ResponseEntity<MessageResponseListOfPermissions> getUserPermissions(@RequestBody UserNamePayloadRequest user) {
        FieldNameFilterWithCodes userListOfPermissions = userHierarchyService.getHierarchyForUser(user.getUserName(), user.getEntryHierarchyId());
        return new ResponseEntity<>(new MessageResponseListOfPermissions(userListOfPermissions), HttpStatus.OK);
    }

    @PostMapping("/getUserAllPermissions")
    public ResponseEntity<MessageResponseListOfPermissions> getUserAllPermissions(@RequestBody UserNamePayloadRequest user) {
        List<FieldNameFilterWithCodes>  userListOfPermissions = userHierarchyService.getUserAllPermissions(user.getUserName());
        return new ResponseEntity<>(new MessageResponseListOfPermissions<List<FieldNameFilterWithCodes>>(userListOfPermissions), HttpStatus.OK);
    }

    @PostMapping("/getUserAllPermissionsSql")
    public ResponseEntity<MessageResponse> getUserAllPermissionsSql(@RequestBody UserNamePayloadRequest user) {
         List<FieldNameFilterWithCodes>  userListOfPermissions = userHierarchyService.getUserAllPermissions(user.getUserName());
         String sql = userHierarchyService.convertToSql(userListOfPermissions);
         return new ResponseEntity<>(new MessageResponse(sql), HttpStatus.OK);
    }

}