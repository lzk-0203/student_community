package com.sdut.community.controller;

import com.sdut.community.mapper.QuestionMapper;
import com.sdut.community.mapper.UserMapper;
import com.sdut.community.model.Question;
import com.sdut.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 24699
 */
@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("pro_title") String pro_title,
            @RequestParam("description") String description,
            @RequestParam("tags") String tags,
            HttpServletRequest request,
            Model model) {
        Question question = new Question();
        question.setTitle(pro_title);
        question.setDescription(description);
        question.setTags(tags);

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtCreate(question.getGmtCreate());

        questionMapper.create(question);
        return "redirect:/";
    }
}
