package com.sdut.community.controller;

import com.sdut.community.dto.CommentDTO;
import com.sdut.community.dto.QuestionDTO;
import com.sdut.community.enums.CommentTypeEnum;
import com.sdut.community.model.Question;
import com.sdut.community.model.User;
import com.sdut.community.service.CommentService;
import com.sdut.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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

    @PostMapping("/upload/{id}")
    private String upload(@PathVariable(name = "id") Long id,
                          @RequestParam("files")MultipartFile file) {

        //得到上传时的文件名
        String fileName = file.getOriginalFilename();

        //上传的文件要存储的地址
        String storagePathPrefix = "D:/File/";
        Long time = System.currentTimeMillis();
        //重命名
        String newName = "_" + id + time + fileName;
        String path = storagePathPrefix;
        File dest = new File(path + newName);
        //判断父目录是否存在
        if(!dest.getParentFile().exists()){  //getParentFile() : 获得父目录
            dest.getParentFile().mkdirs();
        }
        try{
            //transferTo(dest)方法将上传文件写到服务器上指定的文件
            file.transferTo(dest);

            //文件的映射地址
            String urlPath = null;

            String urlPrefix = "http://localhost:8081/file/";
            urlPath = urlPrefix + newName;

            //把文件映射地址保存到用户信息表
            Question question = questionService.getQuestionById(id);
            question.setFile(urlPath);
            questionService.createOrUpdate(question);
            return "redirect:/question/"+id.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
    }

    @ResponseBody
    @PostMapping("/download/{id}")
    private String download(@PathVariable(name = "id") Long id,
                            HttpServletResponse response) {
        //待下载文件名
        String fileName = "_" + questionService.getById(id).getFile().split("_")[1];
        //告诉浏览器它发送的数据属于什么文件类型
        response.setHeader("content-type", "image/jpg");
        //response.setContentType(MIME)的作用是使客户端浏览器，区分不同种类的数据，并根据不同的MIME调用浏览器内不同的程序嵌入模块来处理相应的数据。
        response.setContentType("application/octet-stream");
        //当Content-Type 的类型为要下载的类型时 , Content-Disposition这个信息头会告诉浏览器这个文件的名字和类型
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        byte[] buff = new byte[1024];
        //创建缓冲输入流
        /**
         * BufferedInputStream继承于FilterInputStream，提供缓冲输入流功能。
         * 缓冲输入流相对于普通输入流的优势是，它提供了一个缓冲数组，每次调用read方法的时候，它首先尝试从缓冲区里读取数据，
         * 若读取失败（缓冲区无可读数据），则选择从物理数据源（譬如文件）读取新数据（这里会尝试尽可能读取多的字节）放入到缓冲区中，
         * 最后再将缓冲区中的内容部分或全部返回给用户.由于从缓冲区里读取数据远比直接从物理数据源（譬如文件）读取速度快。
         */
        BufferedInputStream bis = null;
        OutputStream outputStream = null;

        try {
            //以字节流输出
            outputStream = response.getOutputStream();

            //这个路径为待下载文件的路径
            bis = new BufferedInputStream(new FileInputStream(new File("D:/File/" + fileName )));
            int read = bis.read(buff);

            //通过while循环写入到指定了的文件夹中
            while (read != -1) {
                outputStream.write(buff, 0, buff.length);
                outputStream.flush();
                read = bis.read(buff);
            }
        } catch ( IOException e ) {
            e.printStackTrace();
            //出现异常返回给页面失败的信息
            return "下载失败";
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //成功后返回成功信息
        return "redirect:/question/"+id.toString();
    }
}
