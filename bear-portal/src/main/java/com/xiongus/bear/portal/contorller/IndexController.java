package com.xiongus.bear.portal.contorller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/portal")
public class IndexController {

    @RequestMapping("")
    public Object getIndex(){
        return "portal";
    }

}
