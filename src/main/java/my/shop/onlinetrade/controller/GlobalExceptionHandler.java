package my.shop.onlinetrade.controller;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNoHandlerFoundException(
            NoHandlerFoundException ex,
            HttpServletResponse response,
            Model model) {
        int statusCode = HttpStatus.NOT_FOUND.value();
        String errorMessage = "Page " + ex.getRequestURL() + " not found.";
        setErrorResponse(statusCode, errorMessage, response, model);
        return "error-page";
    }

    private void setErrorResponse(int statusCode, String errorMessage, HttpServletResponse response, Model model) {
        response.setStatus(statusCode);
        model.addAttribute("statusCode", statusCode);
        model.addAttribute("errorMessage", errorMessage);
    }

}
