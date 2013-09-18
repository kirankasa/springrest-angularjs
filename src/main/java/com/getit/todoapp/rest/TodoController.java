package com.getit.todoapp.rest;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.getit.todoapp.domain.Todo;

@RooWebJson(jsonObject = Todo.class)
@Controller
@RequestMapping("/todoes")
public class TodoController {

	@RequestMapping(method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteAllTodos() {
		List<Todo> todos = todoService.findAllTodoes();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		for (Todo todo : todos) {
			todoService.deleteTodo(todo);
		}

		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}
}
