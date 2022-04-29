package org.incubyte.todo;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
public class TodoControllerTest {

    @Inject
    @Client("/")
    HttpClient httpClient;


    @Test
    public void create_a_todo_object_with_default_state_not_done_and_save_it_in_db() {
        Todo todo = new Todo();
        todo.setDescription("Remember to hydrate");


        Todo savedTodo = this.httpClient.toBlocking().retrieve(HttpRequest.POST("/todos", todo), Argument.of(Todo.class));

        assertThat(savedTodo.getDescription()).isEqualTo("Remember to hydrate");
        assertThat(savedTodo.isDone()).isFalse();
        assertThat(savedTodo.getId()).isPositive();

        Todo retrievedTodo = this.httpClient.toBlocking().retrieve(HttpRequest.GET("/todos/" + savedTodo.getId()), Argument.of(Todo.class));

        Assertions.assertThat(retrievedTodo.getId()).isEqualTo(savedTodo.getId());
        Assertions.assertThat(retrievedTodo.getDescription()).isEqualTo(savedTodo.getDescription());
        Assertions.assertThat(retrievedTodo.isDone()).isEqualTo(savedTodo.isDone());
    }


    @Test
    public void get_all_the_todos_from() {

        Todo todo1 = new Todo();
        todo1.setDescription("I need to do homework");

        Todo todo2 = new Todo();
        todo2.setDescription("I need to have a bath");

        Todo todo3 = new Todo();
        todo3.setDescription("I need to kill abdul");

        Todo savedTodo1 = this.httpClient.toBlocking().retrieve(HttpRequest.POST("/todos", todo1), Argument.of(Todo.class));
        Todo savedTodo2 = this.httpClient.toBlocking().retrieve(HttpRequest.POST("/todos", todo2), Argument.of(Todo.class));
        Todo savedTodo3 = this.httpClient.toBlocking().retrieve(HttpRequest.POST("/todos", todo3), Argument.of(Todo.class));


        List<Todo> retrivedTodoList = httpClient.toBlocking().retrieve(
                HttpRequest.GET("todos/"), Argument.listOf(Todo.class));

        Assertions.assertThat(retrivedTodoList).containsExactly(savedTodo1, savedTodo2, savedTodo3);
    }

    @Test
    public void get_todos_by_status()
    {
        //Arrange
        Todo todo1 = new Todo();
//        todo1.setId(1L);
        todo1.setDescription("I need to do homework");
        todo1.setDone(false);

        Todo todo2 = new Todo();
//        todo2.setId(2L);
        todo2.setDescription("I need to have a bath");
        todo2.setDone(true);

        //Act
        Todo savedTodo1 = this.httpClient.toBlocking().retrieve(HttpRequest.POST("/todos", todo1), Argument.of(Todo.class));
        Todo savedTodo2 = this.httpClient.toBlocking().retrieve(HttpRequest.POST("/todos", todo2), Argument.of(Todo.class));

        //Assert
        List<Todo> retrivedOpenTodoList = httpClient.toBlocking().retrieve(
                HttpRequest.GET("todos/open"), Argument.listOf(Todo.class));

        Assertions.assertThat(retrivedOpenTodoList).containsExactly(savedTodo1);

        List<Todo> retrivedCloseTodoList = httpClient.toBlocking().retrieve(
                HttpRequest.GET("todos/close"), Argument.listOf(Todo.class));

        Assertions.assertThat(retrivedCloseTodoList).containsExactly(savedTodo2);

    }

}
