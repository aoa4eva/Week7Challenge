package me.afua.week7challenge;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class AppRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String roleName;

    @ManyToMany(mappedBy = "roles")
    Set<AppUser> users;

    public AppRole() {
        this.users = new HashSet<>();
    }


    //DON'T FORGET TO INSTANTIATE THE HASHSETS IN TEH OVERLOADED CONSTRUCTOR!!
    public AppRole(String roleName) {
        this.roleName = roleName;
        this.users = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<AppUser> getUsers() {
        return users;
    }

    public void setUsers(Set<AppUser> users) {
        this.users = users;
    }
}
