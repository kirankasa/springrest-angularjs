package com.getit.todoapp.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.getit.todoapp.domain.Todo;
import com.getit.todoapp.domain.Userinfo;
import com.getit.todoapp.service.TodoService;
import com.getit.todoapp.service.UserService;


@RooWebJson(jsonObject = Todo.class)
@Controller
@RequestMapping("/todoes")
public class TodoController {
    private static final String APPLICATION_JSON = "application/json";
    private static final String CONTENT_TYPE = "Content-Type";
	
	@Autowired
	private UserService userService;
    
	@Autowired
    private TodoService todoService;


	@RequestMapping(method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity<String> deleteAllTodos(Authentication authentication) {
		 User user=(User) authentication.getPrincipal(); 
		 List<Todo> todos = todoService.findTodosByUserName(user.getUsername());
		HttpHeaders headers = new HttpHeaders();
		headers.add(CONTENT_TYPE, APPLICATION_JSON);
		for (Todo todo : todos) {
			todo.setUserName(null);
			todoService.deleteTodo(todo);
		}
		return new ResponseEntity<String>(headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json, @PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(CONTENT_TYPE, APPLICATION_JSON);
        Todo todo = Todo.fromJsonToTodo(json.toString());
        if (todoService.updateTodo(todo) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") Long id) {
        Todo todo = todoService.findTodo(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add(CONTENT_TYPE, "application/json; charset=utf-8");
        if (todo == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(todo.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson(Authentication authentication) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(CONTENT_TYPE, "application/json; charset=utf-8");
        User user=(User) authentication.getPrincipal(); 
        List<Todo> result = todoService.findTodosByUserName(user.getUsername());
        return new ResponseEntity<String>(Todo.toJsonArray(result), headers, HttpStatus.OK);
    }

	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json,Authentication authentication) {
        Todo todo = Todo.fromJsonToTodo(json);
        User user=(User) authentication.getPrincipal(); 
        Userinfo userinfo=userService.findByUserName(user.getUsername()).get(0);
        todo.setUserName(userinfo);
        todoService.saveTodo(todo);
        HttpHeaders headers = new HttpHeaders();
        headers.add(CONTENT_TYPE, APPLICATION_JSON);
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (Todo todo: Todo.fromJsonArrayToTodoes(json)) {
            todoService.saveTodo(todo);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(CONTENT_TYPE, APPLICATION_JSON);
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Long id,Authentication authentication) {
        
        User user=(User) authentication.getPrincipal();
        Todo todo = todoService.findTodoByUserNameAndId(user.getUsername(),id);
        HttpHeaders headers = new HttpHeaders();
        headers.add(CONTENT_TYPE, APPLICATION_JSON);
        if (todo == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        todo.setUserName(null);
        todoService.deleteTodo(todo);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
}
