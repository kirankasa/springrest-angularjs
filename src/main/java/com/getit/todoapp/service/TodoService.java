package com.getit.todoapp.service;
import com.getit.todoapp.domain.Todo;

import java.util.List;

import org.springframework.roo.addon.layers.service.RooService;

@RooService(domainTypes = { com.getit.todoapp.domain.Todo.class })
public interface TodoService {

	public abstract long countAllTodoes();


	public abstract void deleteTodo(Todo todo);


	public abstract Todo findTodo(Long id);


	public abstract List<Todo> findAllTodoes();


	public abstract List<Todo> findTodoEntries(int firstResult, int maxResults);


	public abstract void saveTodo(Todo todo);


	public abstract Todo updateTodo(Todo todo);

	public abstract List<Todo> findTodosByUserName(String userName);
	
	public abstract Todo findTodoByUserNameAndId(String userName,Long id);
}
