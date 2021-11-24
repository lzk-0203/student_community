package com.sdut.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author 24699
 */
@Controller
public class CommentController {
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post() {
        return null;
    }
}
