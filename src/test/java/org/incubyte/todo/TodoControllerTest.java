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
class TodoControllerTest {

    @Inject
    @Client("/")
    HttpClient httpClient;


    @Test
    void todo_crud_and_filter() {
        Todo todo1 = new Todo();
        todo1.setDescription("Remember to hydrate");
        todo1.setDone(false);

        Todo todo2 = new Todo();
        todo2.setDescription("TDD");
        todo2.setDone(true);


        Todo savedTodo1 = this.httpClient.toBlocking().retrieve(HttpRequest.POST("/todos", todo1), Argument.of(Todo.class));
        Todo savedTodo2 = this.httpClient.toBlocking().retrieve(HttpRequest.POST("/todos", todo2), Argument.of(Todo.class));

        assertThat(savedTodo1.getDescription()).isEqualTo("Remember to hydrate");
        assertThat(savedTodo1.isDone()).isFalse();
        assertThat(savedTodo1.getId()).isPositive();

        Todo retrievedTodo = this.httpClient.toBlocking().retrieve(HttpRequest.GET("/todos/" + savedTodo1.getId()), Argument.of(Todo.class));

        Assertions.assertThat(retrievedTodo.getId()).isEqualTo(savedTodo1.getId());
        Assertions.assertThat(retrievedTodo.getDescription()).isEqualTo(savedTodo1.getDescription());
        Assertions.assertThat(retrievedTodo.isDone()).isEqualTo(savedTodo1.isDone());

        List<Todo> retrivedTodoList = httpClient.toBlocking().retrieve(
            HttpRequest.GET("todos/"), Argument.listOf(Todo.class));

        Assertions.assertThat(retrivedTodoList).containsExactly(savedTodo1, savedTodo2);

        List<Todo> retrivedOpenTodoList = httpClient.toBlocking().retrieve(
                HttpRequest.GET("todos/open"), Argument.listOf(Todo.class));

        Assertions.assertThat(retrivedOpenTodoList).containsExactly(savedTodo1);

        List<Todo> retrivedCloseTodoList = httpClient.toBlocking().retrieve(
                HttpRequest.GET("todos/close"), Argument.listOf(Todo.class));

        Assertions.assertThat(retrivedCloseTodoList).containsExactly(savedTodo2);
    }

}
