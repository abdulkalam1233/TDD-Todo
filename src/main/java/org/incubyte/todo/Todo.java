package org.incubyte.todo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Todo {
    @Id
    @GeneratedValue
    private long id;

    public void setId(long id) {
        this.id = id;
    }

    private String description;
    private boolean done;

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return done;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return id == todo.id && done == todo.done && description.equals(todo.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, done);
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
