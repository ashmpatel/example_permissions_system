package com.example.demo2.services;

import com.example.demo2.Repository.HierarchyRepository;
import com.example.demo2.excceptions.ResourceNotFoundException;
import com.example.demo2.models.FieldNameFilterWithCodes;
import com.example.demo2.models.MappingCode;
import com.example.demo2.models.PermissionUser;
import com.example.demo2.models.UserHierarchy;
import com.example.demo2.payload.HierarchyPayloadRequest;
import com.example.demo2.payload.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserHierarchyServiceImpl implements UserHierarchyService {
    @Autowired
    HierarchyRepository hierarchyRepository;

    @Autowired
    TestRepo jdbcRepo;

    private static String NOT_IN = " NOT IN ";
    private static String IN = " IN ";
    private static String EQUALS = " = ";

    private static String YES = "Y";

    private static String ALWAYS_TRUE = "1=1";

    private static String AND = " AND ";
    private static String OR = " OR ";
    private static String SPACE  = " ";

    private static String SINGLE_QUOTE_CHAR  = "'";
    private static String OPENING_BRACKET  = "(";
    private static String CLOSING_BRACKET  = ")";

    @Override
    public MessageResponse createHierarchy(HierarchyPayloadRequest hierarchyPayloadRequestRequest) {
        UserHierarchy newHierarchy = new UserHierarchy();
        newHierarchy.setHierarchyAlternateName(hierarchyPayloadRequestRequest.getHierarchyName());
        hierarchyRepository.save(newHierarchy);
        return new MessageResponse(String.valueOf(newHierarchy.getId()));

    }

    @Override
    public Optional<UserHierarchy> updateHierarchy(Integer hierarchyId, HierarchyPayloadRequest hierarchyPayloadRequestRequest) throws ResourceNotFoundException {
        Optional<UserHierarchy> userHierarchy = hierarchyRepository.findById(hierarchyId);
        if (userHierarchy.isEmpty()) {
            throw new ResourceNotFoundException("User Hierarchy Id", hierarchyId);
        } else {
            userHierarchy.get().setHierarchyAlternateName(hierarchyPayloadRequestRequest.getHierarchyName());
        }
        hierarchyRepository.save(userHierarchy.get());
        return userHierarchy;
    }


    @Override
    public void deleteHierarchy(Integer hierarchyId) throws ResourceNotFoundException {
        if (hierarchyRepository.findById(hierarchyId).get().equals(hierarchyId)){
            hierarchyRepository.deleteById(hierarchyId);
        }
        else throw new ResourceNotFoundException("User Hierarchy Id", hierarchyId);
    }

    @Override
    public UserHierarchy getASingleHierarchy(Integer hierarchyId) throws ResourceNotFoundException {
        return hierarchyRepository.findById(hierarchyId).orElseThrow(() -> new ResourceNotFoundException("User Hierarchy Id", hierarchyId));
    }

    @Override
    public List<UserHierarchy> getAllHierarchies() {
        return hierarchyRepository.findAll();
    }

    @Override
    public PermissionUser getSingleUserByName(String userName) {
        return jdbcRepo.findUserByUserId(userName).get(0);
    }

    @Override
    public FieldNameFilterWithCodes getHierarchyForUser(String userName, int entryHierarchyId) {
        FieldNameFilterWithCodes userMappingCodes = jdbcRepo.getUserPermissionTree(userName, entryHierarchyId);
        return userMappingCodes;
    }

    @Override
    public List<FieldNameFilterWithCodes> getUserAllPermissions(String userName) {
        List<FieldNameFilterWithCodes> userMappingCodes = jdbcRepo.getAllPermissionsForUser(userName);
        return userMappingCodes;
    }

    public String convertToSql(List<FieldNameFilterWithCodes>  userListOfPermissions) {

        // final list of permissions in list form then we AND them all together
        List<String> allPermissionSql= new ArrayList<>();

        // go through all the permissions
        for(FieldNameFilterWithCodes permission: userListOfPermissions) {
            // if there is just 1 value for filtering then use EQUALS otherwise IN operator
            String condition = EQUALS;
            List<MappingCode> mappingCodes = permission.getMappingCodes();
            if (mappingCodes.size()>1) {
                if (permission.getInCondition().equals(YES)) {
                    condition = IN;
                } else {
                    condition = NOT_IN;
                }
            }
            // keep adding the filtervalues to a list and then we join them with a comma and quote the filter values
            List<String> filterValues = new ArrayList<>();
            for (MappingCode filterValue: mappingCodes) {
                filterValues.add(filterValue.getMappingCode());
            }

            // add quotes and commans for each filtervalue for the IN list of the filter
            List<String> filterValuesQuoted = new ArrayList<>();
            for (String filterValue: filterValues) {
                filterValuesQuoted.add(SINGLE_QUOTE_CHAR.concat(filterValue).concat(SINGLE_QUOTE_CHAR));
            }
            String allFilterValuesForField = String.join(",", filterValuesQuoted);

            // if we used an IN operator, then put brackets around the IN values filter list
            String sql = permission.getFieldName().concat(condition);
            if (mappingCodes.size() > 1) {
                sql = sql.concat(OPENING_BRACKET).concat(allFilterValuesForField).concat(CLOSING_BRACKET);
            } else {
                sql = sql.concat(allFilterValuesForField);
            }

            if (permission.getAndCondition().equals(YES)) {
                sql = AND.concat(sql);
            } else {
                sql = OR.concat(sql);
            }
            allPermissionSql.add(sql);
        }
        // join all the different field filters using AND
        String finalSql =  String.join(SPACE, allPermissionSql);
        finalSql =  ALWAYS_TRUE + finalSql;
        return finalSql;

    }
}