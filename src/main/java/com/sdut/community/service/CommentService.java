package com.sdut.community.service;

import com.sdut.community.dto.CommentDTO;
import com.sdut.community.enums.CommentTypeEnum;
import com.sdut.community.exception.CustomizeErrorCode;
import com.sdut.community.exception.CustomizeException;
import com.sdut.community.mapper.CommentMapper;
import com.sdut.community.mapper.QuestionMapper;
import com.sdut.community.mapper.UserMapper;
import com.sdut.community.model.Comment;
import com.sdut.community.model.Question;
import com.sdut.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author 24699
 */
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Transactional(rollbackFor = RuntimeException.class)
    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExit(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getType().equals(CommentTypeEnum.COMMENT.getType())) {
            // 回复评论
            Comment dbComment = commentMapper.selectById(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        } else {
            // 回复问题
            Question question = questionMapper.getQuestionById(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            questionMapper.updateByComment(question);
        }
    }

    public List<CommentDTO> listByTargetId(Long id, Integer type) {
        List<Comment> comments = commentMapper.questionsByTargetId(id, type);
        if (comments.size() == 0) {
            return new ArrayList<>();
        }
        // 找出所有去重的评论者  (java8 语法)
        Set<Long> commentators = comments.stream().map(comment->comment.getCommentator()).collect(Collectors.toSet());
        // 获取User
        List<User> users = new ArrayList<>();
        for (Long commentator : commentators) {
            users.add(userMapper.selectByPrimaryKey(commentator));
        }
        // 将userList转换成Map, 降低时间复杂度
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
        // 转换Comment为CommentDTO(set User)
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            // 通过映射拷贝属性
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }
}
