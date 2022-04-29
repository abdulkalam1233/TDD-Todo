package org.incubyte.todo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TodoControllerShould {

    @Mock
    private TodoService todoService;


    @Test
    public void call_service_to_save_todo() {
        TodoController todoController = new TodoController(todoService);

        Todo todo = new Todo();
        todoController.save(todo);

        verify(todoService).save(todo);
    }

    @Test
    public void call_service_to_find_todo_by_id() {
        TodoController todoController = new TodoController(todoService);
        long id = 99L;

        Optional<Todo> todo = todoController.findById(id);

        verify(todoService).get(id);
    }

    @Test
    public void call_service_to_get_all_the_todos() {
        TodoController todoController = new TodoController(todoService);

        Iterable<Todo> todos =  todoController.getAllTodos();

        verify(todoService).getAllTodos();
    }

    @Test
    public void get_all_open_todos()
    {
        TodoController todoController = new TodoController(todoService);

        Iterable<Todo> todos =  todoController.getAllOpenTodos();

        verify(todoService).getAllTodosByFilter("Open");
    }

    @Test
    public void get_all_close_todos()
    {
        TodoController todoController = new TodoController(todoService);

        Iterable<Todo> todos =  todoController.getAllCloseTodos();

        verify(todoService).getAllTodosByFilter("Close");
    }
}
