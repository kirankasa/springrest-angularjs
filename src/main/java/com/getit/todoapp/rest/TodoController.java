package com.getit.todoapp.rest;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
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
	
	@Autowired
	private UserService userService;
    
	@Autowired
    TodoService todoService;


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

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json, @PathVariable("id") Long id) throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        JSONObject request=new JSONObject(json);
        Userinfo userinfo=userService.findUserinfo(Long.parseLong(request.get("userName").toString()));
        JSONObject userJsonObject=new JSONObject(userinfo.toJson());
        request.put("userName", userJsonObject);
        Todo todo = Todo.fromJsonToTodo(request.toString());
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
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (todo == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(todo.toJson(), headers, HttpStatus.OK);
    }

	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson(Authentication authentication) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
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
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (Todo todo: Todo.fromJsonArrayToTodoes(json)) {
            todoService.saveTodo(todo);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Long id) {
        Todo todo = todoService.findTodo(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (todo == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        todoService.deleteTodo(todo);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
}
