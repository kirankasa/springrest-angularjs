package com.getit.todoapp.rest;
import com.getit.todoapp.domain.Todo;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebJson(jsonObject = Todo.class)
@Controller
@RequestMapping("/todoes")
public class TodoController {
}
