package com.sdut.community.controller;

import com.sdut.community.dto.QuestionDTO;
import com.sdut.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 24699
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    private String question(@PathVariable(name = "id") Long id,
                            Model model) {
        QuestionDTO questionDTO = questionService.getById(id);
        // 累加阅读数
        model.addAttribute("question", questionDTO);
        questionService.incView(id);
        questionService.intCommentCount(id);
        return "question";
    }
}
