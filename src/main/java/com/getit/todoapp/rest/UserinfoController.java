package com.getit.todoapp.rest;
import com.getit.todoapp.domain.Userinfo;
import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebJson(jsonObject = Userinfo.class)
@Controller
@RequestMapping("/userinfoes")
public class UserinfoController {
}
