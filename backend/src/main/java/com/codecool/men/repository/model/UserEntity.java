package com.codecool.men.repository.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity

public class UserEntity {
    // egy user-hez több note is tartozhat
    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String password;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Note> notes;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<ShoppingListItem> shoppingListItems;

//    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
//    @CollectionTable(name = "user_roles") // Ez létrehoz egy külön táblát a role-oknak
//    @Enumerated(EnumType.STRING) // Ez tárolja az enum értékeket mint String
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<RoleEntity> roles;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }
    public boolean addRole(RoleEntity role){
       return roles.add(role);
    }

    public List<ShoppingListItem> getShoppingListItems() {
        return shoppingListItems;
    }

    public void addShoppingListItems(ShoppingListItem shoppingListItems) {
        this.shoppingListItems.add(shoppingListItems);
    }

    public void removeShoppingListItems(ShoppingListItem shoppingListItems) {
        this.shoppingListItems.remove(shoppingListItems);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", notes=" + notes +
                ", roles=" + roles +
                '}';
    }
}
