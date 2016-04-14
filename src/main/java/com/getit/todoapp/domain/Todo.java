package com.getit.todoapp.domain;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.Enumerated;
import org.springframework.roo.addon.json.RooJson;
import javax.persistence.ManyToOne;

@Entity
@RooJavaBean
@RooToString
@RooJpaEntity
@RooJson
public class Todo {
    private static final String CLASS_EXTENSION = "*.class";

    /**
     */
    @NotNull
    private String name;

    /**
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date targetDate;

    /**
     */
    private Boolean isCompleted;

    /**
     */
    @Enumerated
    private Priority priority;
    
    /**
     */
    @ManyToOne
    private Userinfo userName;

    public String toJson() {
        return new JSONSerializer().exclude(CLASS_EXTENSION).transform(new DateTransformer("MM/dd/yyyy"), Date.class).serialize(this);
    }

    public String toJson(String[] fields) {
        return new JSONSerializer().include(fields).exclude(CLASS_EXTENSION).serialize(this);
    }

    public static Todo fromJsonToTodo(String json) {
        return new JSONDeserializer<Todo>().use(null, Todo.class).deserialize(json);
    }

    public static String toJsonArray(Collection<Todo> collection) {
        return new JSONSerializer().exclude(CLASS_EXTENSION).transform(new DateTransformer("MM/dd/yyyy"), Date.class).serialize(collection);
    }

    public static String toJsonArray(Collection<Todo> collection, String[] fields) {
        return new JSONSerializer().include(fields).exclude(CLASS_EXTENSION).serialize(collection);
    }

    public static Collection<Todo> fromJsonArrayToTodoes(String json) {
        return new JSONDeserializer<List<Todo>>().use(null, ArrayList.class).use("values", Todo.class).deserialize(json);
    }

    

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

	public String getName() {
        return this.name;
    }

	public void setName(String name) {
        this.name = name;
    }

	public Date getTargetDate() {
        return this.targetDate;
    }

	public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

	public Boolean getIsCompleted() {
        return this.isCompleted;
    }

	public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

	public Priority getPriority() {
        return this.priority;
    }

	public void setPriority(Priority priority) {
        this.priority = priority;
    }

	public Userinfo getUserName() {
        return this.userName;
    }

	public void setUserName(Userinfo userName) {
        this.userName = userName;
    }
}
