package com.example.demo2.services;


import com.example.demo2.models.FieldNameFilterWithCodes;
import com.example.demo2.models.HierarchyLinks;
import com.example.demo2.models.MappingCode;
import com.example.demo2.models.PermissionUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TestRepo {
    @Autowired
    JdbcTemplate jdbctemplate;

    static class PermissionUserMapper implements RowMapper<PermissionUser> {

        public PermissionUser mapRow(ResultSet rs, int rownum) throws SQLException {
            PermissionUser user = new PermissionUser();
            user.setUserId(rs.getLong("id"));
            user.setHierarchyId(rs.getLong("hierarchy_id"));
            user.setFieldName(rs.getString("field_name"));
            user.setAddCondition(rs.getString("and_condition"));
            user.setInCondition(rs.getString("in_condition"));
            return user;
        }
    }

    static class UserHierarchyMapper implements RowMapper<HierarchyLinks> {

        public HierarchyLinks mapRow(ResultSet rs, int rownum) throws SQLException {
            return new HierarchyLinks(rs.getLong(1),rs.getLong(2),rs.getLong(3));
        }
    }

    static class UserBookCodeMapper implements RowMapper<MappingCode> {

        public MappingCode mapRow(ResultSet rs, int rownum) throws SQLException {
            return new MappingCode(rs.getLong("user_id"),rs.getLong("level_id"),rs.getString("mapping_code"));
        }
    }


    public List<PermissionUser> findUserByUserId(String userName) {
        return jdbctemplate.query("select ID, USER_ID, HIERARCHY_ID, FIELD_NAME, IN_CONDITION, AND_CONDITION from USERS where user_id= '" + userName + "' ", new PermissionUserMapper());
    }

    public List<PermissionUser> findUserByUserIdAndEntryHierarchyId(String userName, long entryHierarhcyId) {
        return jdbctemplate.query("select ID, USER_ID, HIERARCHY_ID, FIELD_NAME , AND_CONDITION, IN_CONDITION from USERS where user_id= '" + userName + "' " + " and hierarchy_id =  " + entryHierarhcyId, new PermissionUserMapper());
    }

    public List<MappingCode> getUserMappingCodesForEndHierarchyId(long userId, long levelId) {
        return jdbctemplate.query("select * from HIERARCHY_END_POINT where user_id= " + userId + " and level_id= " + levelId, new UserBookCodeMapper());
    }

    /**
     * Gets a single permission for a hierarchy for a user
     * @param userName
     * @param entryHierarchyId
     * @return
     */
    public FieldNameFilterWithCodes getUserPermissionTree(String userName, long entryHierarchyId) {
        PermissionUser user = findUserByUserIdAndEntryHierarchyId(userName, entryHierarchyId).get(0);
        // String fieldName = user.getFieldName();

        String fieldName = user.getFieldName();

        // long firstHierarchyLevelId = findUserByUserId(userName).get(0).getHierarchyId();
        HierarchyLinks oneLevelOfHierarchy = jdbctemplate.queryForObject("select user_id, current_level_id, next_level_id from HIERARCHY_LINKS where user_id=" + user.getUserId() +
                " and current_level_id = " + entryHierarchyId , new UserHierarchyMapper());
        boolean nextLevel = false;
        long currentLevelId = entryHierarchyId;

        if (oneLevelOfHierarchy != null) {
            nextLevel = true;
        } else {
            currentLevelId = oneLevelOfHierarchy.getCurrentLevel();
        }


        HierarchyLinks nextLevelInHierarchy = null;
        // walk the User Hierarchy tree by starting at the Current Level where the user is mapped to and then get the Next Level Id and start the walk from there for next level
        while (nextLevel) {

            try {
                nextLevelInHierarchy = jdbctemplate.queryForObject("select user_id, current_level_id, next_level_id from HIERARCHY_LINKS where user_id= " + user.getUserId()
                        + " and current_level_id = " + currentLevelId, new UserHierarchyMapper());
            } catch (EmptyResultDataAccessException ex) {
                nextLevel = false;
            }

            // if the tree has another level then walk down to the next level otherwise stop and return the current level
            if (nextLevelInHierarchy==null || nextLevelInHierarchy.getNextLevel()==0) {
                nextLevel = false;
            } else {
                currentLevelId = nextLevelInHierarchy.getNextLevel();
            }
        }

        // we now have the end of the tree walk in the hierarchy id
        // we need to get the list of book codes mapped to this end of the hierarchy id
        List<MappingCode> mappingCodes =  getUserMappingCodesForEndHierarchyId(user.getUserId(), currentLevelId);
        return new FieldNameFilterWithCodes(fieldName,mappingCodes ,user.getAddCondition(), user.getInCondition());
    }


    /**
     * Gets all permissions for user
     * @param userName
     * @return
     */
    public List<FieldNameFilterWithCodes> getAllPermissionsForUser(String userName) {
        List<PermissionUser> userEntryHierarchyLinks = findUserByUserId(userName);
        List<FieldNameFilterWithCodes> allPermissionsForUser = new ArrayList<>();

        // go through all the users entry hierarchy and get the permissions to make the list of all a user has access to
        for(PermissionUser permission: userEntryHierarchyLinks) {
            FieldNameFilterWithCodes onePermission = getUserPermissionTree(userName, permission.getHierarchyId());
            onePermission.setAndCondition(permission.getAddCondition());
            onePermission.setInCondition(permission.getInCondition());
            allPermissionsForUser.add(onePermission);
        }
        return allPermissionsForUser;
    }

}
