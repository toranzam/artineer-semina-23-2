package com.artineer.artineersemina232.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String showIndex() {
        return "index";
    }

    @GetMapping("/todolist")
    public String showTodoList(){
        return "/todoList/index";
    }

    @GetMapping("calendar")
    public String showCalender() {
        return "/calendar/index";
    }

}
