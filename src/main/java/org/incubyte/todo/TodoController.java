package org.incubyte.todo;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

import java.util.Optional;

@Controller("/todos")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }


    @Get(value = "/")
    public Iterable<Todo> getAllTodos() {
        return todoService.getAllTodos();
    }

    @Post("/")
    public Todo save(Todo todo) {
        return todoService.save(todo);
    }

    @Get("/open")
    public Iterable<Todo> getAllOpenTodos() {
        return todoService.getAllTodosByFilter("Open");
    }

    @Get("/close")
    public Iterable<Todo> getAllCloseTodos() {
        return todoService.getAllTodosByFilter("Close");
    }


    @Get("/{id}")
    public Optional<Todo> findById(long id) {
        return todoService.get(id);
    }

}
