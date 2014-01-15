package com.getit.todoapp.service;

import com.getit.todoapp.domain.Todo;
import com.getit.todoapp.repository.TodoRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TodoServiceImpl implements TodoService {

	@Autowired
    TodoRepository todoRepository;

	public long countAllTodoes() {
        return todoRepository.count();
    }

	public void deleteTodo(Todo todo) {
        todoRepository.delete(todo);
    }

	public Todo findTodo(Long id) {
        return todoRepository.findOne(id);
    }

	public List<Todo> findAllTodoes() {
        return todoRepository.findAll();
    }

	public List<Todo> findTodoEntries(int firstResult, int maxResults) {
        return todoRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveTodo(Todo todo) {
        todoRepository.save(todo);
    }

	public Todo updateTodo(Todo todo) {
        return todoRepository.save(todo);
    }

	@Override
	public List<Todo> findTodosByUserName(String userName) {
		
		return todoRepository.findTodosByUserName(userName);
	}
	
	@Override
	public Todo findTodoByUserNameAndId(String userName,Long id) {
		
		return todoRepository.findTodoByUserNameAndId(userName,id);
	}
}
