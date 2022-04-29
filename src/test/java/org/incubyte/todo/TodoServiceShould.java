package org.incubyte.todo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TodoServiceShould {

    @Mock
    private TodoRepository todoRepository;

    Todo openTodo;
    Todo closeTodo;

    List<Todo> dummyTodos = new ArrayList<>();
    @BeforeEach
    public void init() {
        openTodo = new Todo();
        openTodo.setDone(false);
        openTodo.setId(1L);
        openTodo.setDescription("Open todo");
        dummyTodos.add(openTodo);

        closeTodo = new Todo();
        closeTodo.setDone(true);
        closeTodo.setId(2L);
        closeTodo.setDescription("Close todo");
        dummyTodos.add(closeTodo);

    }

    @Test
    public void save_todo_by_calling_repository() {
        Todo todo = new Todo();

        TodoService todoService = new TodoService(todoRepository);

        todoService.save(todo);

        verify(todoRepository).save(todo);

    }

    @Test
    public void find_todo_by_calling_repository() {

        TodoService todoService = new TodoService(todoRepository);
        long id = 100L;
        Optional<Todo> retrivedTodo = todoService.get(id);
        verify(todoRepository).findById(id);
    }

    @Test
    public void find_all_todos_by_calling_repository() {
        TodoService todoService = new TodoService(todoRepository);

        Iterable<Todo> todos = todoService.getAllTodos();

        verify(todoRepository).findAllOrderById();
    }

    @Test
    public void get_all_open_todos()
    {
        when(todoRepository.findAllOrderById()).thenReturn(dummyTodos);
        TodoService todoService = new TodoService(todoRepository);

        Iterable<Todo> todos = todoService.getAllTodosByFilter("Open");

        verify(todoRepository).findAllOrderById();

        Assertions.assertThat(todos).contains(openTodo);
    }

    @Test
    public void get_all_close_todos()
    {
        when(todoRepository.findAllOrderById()).thenReturn(dummyTodos);

        TodoService todoService = new TodoService(todoRepository);

        Iterable<Todo> todos = todoService.getAllTodosByFilter("Close");

        verify(todoRepository).findAllOrderById();

        Assertions.assertThat(todos).contains(closeTodo);
    }
}