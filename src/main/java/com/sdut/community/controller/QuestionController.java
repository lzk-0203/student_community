package com.sdut.community.controller;

import com.sdut.community.dto.CommentDTO;
import com.sdut.community.dto.QuestionDTO;
import com.sdut.community.enums.CommentTypeEnum;
import com.sdut.community.model.Question;
import com.sdut.community.service.CommentService;
import com.sdut.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author 24699
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    private String question(@PathVariable(name = "id") Long id,
                            Model model) {
        QuestionDTO questionDTO = questionService.getById(id);
        // 获取评论列表
        List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION.getType());
        // 放入Model
        model.addAttribute("question", questionDTO);
        // 累加阅读数
        questionService.incView(id);
        model.addAttribute("comments", comments);
        // 相关问题
        List<Question> relevantQuestions = questionService.getRelevantQuestions(id);
        model.addAttribute("relevantQuestions", relevantQuestions);
        return "question";
    }

    @GetMapping("/delete/{id}")
    private String delete(@PathVariable(name = "id") Long id) {
        questionService.deleteQuestionById(id);
        return "redirect:/";
    }
}
