package com.sdut.community.advice;

import com.sdut.community.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 24699
 */
@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable exception, Model model) {
        if (exception instanceof CustomizeException) {
            model.addAttribute("message",exception.getMessage());
        } else {
            model.addAttribute("message","你好像找到bug了。。。");
        }
        return new ModelAndView("error");
    }

//    private HttpStatus getStatus(HttpServletRequest request) {
//        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
//        if (statusCode == null) {
//            return HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//        return HttpStatus.valueOf(statusCode);
//    }
}
