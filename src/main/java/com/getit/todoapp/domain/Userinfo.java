package com.getit.todoapp.domain;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import org.springframework.roo.addon.json.RooJson;

@Entity
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

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	@Version
    @Column(name = "version")
    private Integer version;

	public Long getId() {
        return this.id;
    }

	public void setId(Long id) {
        this.id = id;
    }

	public Integer getVersion() {
        return this.version;
    }

	public void setVersion(Integer version) {
        this.version = version;
    }

	public String getUserName() {
        return this.userName;
    }

	public void setUserName(String userName) {
        this.userName = userName;
    }

	public String getEmail() {
        return this.email;
    }

	public void setEmail(String email) {
        this.email = email;
    }

	public String getFirstName() {
        return this.firstName;
    }

	public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

	public String getLastName() {
        return this.lastName;
    }

	public void setLastName(String lastName) {
        this.lastName = lastName;
    }

	public Set<Todo> getTodoes() {
        return this.todoes;
    }

	public void setTodoes(Set<Todo> todoes) {
        this.todoes = todoes;
    }

	public String getPassword() {
        return this.password;
    }

	public void setPassword(String password) {
        this.password = password;
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public String toJson(String[] fields) {
        return new JSONSerializer().include(fields).exclude("*.class").serialize(this);
    }

	public static Userinfo fromJsonToUserinfo(String json) {
        return new JSONDeserializer<Userinfo>().use(null, Userinfo.class).deserialize(json);
    }

	public static String toJsonArray(Collection<Userinfo> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static String toJsonArray(Collection<Userinfo> collection, String[] fields) {
        return new JSONSerializer().include(fields).exclude("*.class").serialize(collection);
    }

	public static Collection<Userinfo> fromJsonArrayToUserinfoes(String json) {
        return new JSONDeserializer<List<Userinfo>>().use(null, ArrayList.class).use("values", Userinfo.class).deserialize(json);
    }
}
