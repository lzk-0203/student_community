package com.sdut.community.service;

import com.github.pagehelper.PageHelper;
import com.sdut.community.dto.PaginationDTO;
import com.sdut.community.dto.QuestionDTO;
import com.sdut.community.exception.CustomizeErrorCode;
import com.sdut.community.exception.CustomizeException;
import com.sdut.community.mapper.QuestionMapper;
import com.sdut.community.mapper.UserMapper;
import com.sdut.community.model.Question;
import com.sdut.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 24699
 */
@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(String search, Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();

        if (StringUtils.isNotBlank(search)) {
            String[] tags = StringUtils.split(search, " ");
            List<Question> questions = new ArrayList<>();
            for (String tag : tags) {
                questions.addAll(questionMapper.getRelevant(tag));
            }

            List<QuestionDTO> questionDTOS = new ArrayList<>();
            for (Question question : questions) {
                User user = userMapper.selectByPrimaryKey(question.getCreator());
                QuestionDTO questionDTO = new QuestionDTO();
                // 快速通过名字反射进行拷贝
                BeanUtils.copyProperties(question, questionDTO);
                questionDTO.setUser(user);
                questionDTOS.add(questionDTO);
            }
            paginationDTO.setQuestionDTOs(questionDTOS);
            return paginationDTO;
        }

        Integer totalCount = questionMapper.count();
        paginationDTO.setPagination(totalCount, page, size);

        if (page < 1) {
            page = 1;
        }
        if (page > paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }

        // pageHelper
        PageHelper.startPage(page, size);
        List<Question> questions = questionMapper.list();

        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            // 快速通过名字反射进行拷贝
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        paginationDTO.setQuestionDTOs(questionDTOS);
        return paginationDTO;
    }

    public PaginationDTO getQuestionsByUserId(Long userId ,Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.countByUserId(userId);
        paginationDTO.setPagination(totalCount, page, size);

        if (page < 1) {
            page = 1;
        }
        if (page > paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }

        // pageHelper
        PageHelper.startPage(page, size);
        List<Question> questions = questionMapper.list();

        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            // 快速通过名字反射进行拷贝
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        paginationDTO.setQuestionDTOs(questionDTOS);
        return paginationDTO;
    }

    public QuestionDTO getById(Long id) {
        Question question = questionMapper.getQuestionById(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        } else {
            Question NewQuestion = questionMapper.getQuestionById(question.getId());
            NewQuestion.setTitle(question.getTitle());
            NewQuestion.setDescription(question.getDescription());
            NewQuestion.setTags(question.getTags());
            NewQuestion.setGmtModified(System.currentTimeMillis());
            NewQuestion.setFile(question.getFile());
            int result = questionMapper.update(NewQuestion);
            if (result != 1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(Long id) {
        Question question = questionMapper.getQuestionById(id);
        questionMapper.updateByView(question);
    }

    public List<Question> listByViewCount() {
        List<Question> questionsByView = questionMapper.getQuestionsByView();
        return questionsByView;
    }

    public List<Question> getRelevantQuestions(Long id) {
        Question showQuestion = questionMapper.getQuestionById(id);
        String Tag = showQuestion.getTags();
        String[] tags = Tag.split(" ");
        List<Question> questionsByRelevant = new ArrayList<>();

        for (String tag : tags) {
            questionsByRelevant.addAll(questionMapper.getRelevant(tag));
        }
        return questionsByRelevant;
    }

    public void deleteQuestionById(Long id) {
        questionMapper.deleteById(id);
    }

    public Question getQuestionById(Long id) {
        return questionMapper.getQuestionById(id);
    }
}
