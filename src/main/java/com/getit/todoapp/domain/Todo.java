package com.getit.todoapp.domain;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.Enumerated;
import org.springframework.roo.addon.json.RooJson;
import javax.persistence.ManyToOne;

@RooJavaBean
@RooToString
@RooJpaEntity
@RooJson
public class Todo {

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

    public String toJson() {
        return new JSONSerializer().exclude("*.class").transform(new DateTransformer("MM/dd/yyyy"), Date.class).serialize(this);
    }

    public String toJson(String[] fields) {
        return new JSONSerializer().include(fields).exclude("*.class").serialize(this);
    }

    public static Todo fromJsonToTodo(String json) {
        return new JSONDeserializer<Todo>().use(null, Todo.class).deserialize(json);
    }

    public static String toJsonArray(Collection<Todo> collection) {
        return new JSONSerializer().exclude("*.class").transform(new DateTransformer("MM/dd/yyyy"), Date.class).serialize(collection);
    }

    public static String toJsonArray(Collection<Todo> collection, String[] fields) {
        return new JSONSerializer().include(fields).exclude("*.class").serialize(collection);
    }

    public static Collection<Todo> fromJsonArrayToTodoes(String json) {
        return new JSONDeserializer<List<Todo>>().use(null, ArrayList.class).use("values", Todo.class).deserialize(json);
    }

    /**
     */
    @ManyToOne
    private Userinfo userName;
}
