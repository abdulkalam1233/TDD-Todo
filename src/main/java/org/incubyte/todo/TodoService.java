package org.incubyte.todo;

import jakarta.inject.Singleton;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Singleton
public class TodoService {
    private final TodoRepository todoRepository;
    private final HashMap<String, Boolean> filter;

    public TodoService(TodoRepository todoRepository) {

        this.todoRepository = todoRepository;

        this.filter = new HashMap<String, Boolean>();
        this.filter.put("Open", false);
        this.filter.put("Close", true);
    }

    public Todo save(Todo todo) {
        return todoRepository.save(todo);
    }

    public Optional<Todo> get(long id) {
        return todoRepository.findById(id);
    }

    public Iterable<Todo> getAllTodos() {
        return todoRepository.findAllOrderById();
    }

    public Iterable<Todo> getAllTodosByFilter(String status) {
        Iterable<Todo> todos = getAllTodos();

        List<Todo> result = StreamSupport.stream(todos.spliterator(), false)
                .filter(todo -> todo.isDone() == filter.get(status))
                .collect(Collectors.toList());
        return result;
    }
}
