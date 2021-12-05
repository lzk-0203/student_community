//package com.sdut.community.advice;
//
//import com.sdut.community.dto.ResultDTO;
//import com.sdut.community.exception.CustomizeErrorCode;
//import com.sdut.community.exception.CustomizeException;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * @author 24699
// */
//@ControllerAdvice
//@ResponseBody
//public class CustomizeExceptionHandler {
//
//    @ExceptionHandler(Exception.class)
//    Object handle(Throwable exception, Model model, HttpServletRequest request) {
//
//        String contentType = request.getContentType();
//        if ("application/json".equals(contentType)) {
//            // 返回json
//            if (exception instanceof CustomizeException) {
//                return ResultDTO.errorOf((CustomizeException) exception);
//            } else {
//                return ResultDTO.errorOf(CustomizeErrorCode.SYSTEM_ERROR);
//            }
//        } else {
//            // 错误页面跳转
//            if (exception instanceof CustomizeException) {
//                model.addAttribute("message", exception.getMessage());
//            } else {
//                model.addAttribute("message", CustomizeErrorCode.SYSTEM_ERROR.getMessage());
//            }
//            return new ModelAndView("error");
//        }
//    }
//}
