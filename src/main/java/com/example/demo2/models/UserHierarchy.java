package com.example.demo2.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class UserHierarchy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String hierarchyAlternateName;
    @Enumerated(EnumType.STRING)
    private Departments department;

    public UserHierarchy(){}
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getHierarchyAlternateName() {
        return hierarchyAlternateName;
    }
    public void setHierarchyAlternateName(String firstName) {
        this.hierarchyAlternateName = firstName;
    }


    @Override
    public String toString() {
        return "UserHierarchy{" +
                "id=" + id +
                ", hierarchyAlternateName='" + hierarchyAlternateName +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserHierarchy hierarchy = (UserHierarchy) o;
        return Integer.compare(hierarchy.id, id) == 0 && Objects.equals(department, hierarchy.department) &&  Objects.equals(hierarchyAlternateName, hierarchy.hierarchyAlternateName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hierarchyAlternateName, department);
    }
}