package com.getit.todoapp.domain;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import org.springframework.roo.addon.json.RooJson;

@RooJavaBean
@RooToString
@RooJpaEntity
@RooJson
public class Userinfo {

    /**
     */
    @NotNull
    @Column(unique = true)
    private String userName;

    /**
     */
    @NotNull
    @Column(unique = true)
    private String email;

    /**
     */
    private String firstName;

    /**
     */
    private String lastName;

    /**
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userName")
    private Set<Todo> todoes = new HashSet<Todo>();

    /**
     */
    @NotNull
    private String password;
}
