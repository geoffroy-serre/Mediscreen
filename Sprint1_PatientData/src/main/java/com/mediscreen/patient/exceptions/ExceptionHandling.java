package com.mediscreen.patient.exceptions;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ExceptionHandling {
  private static final Logger logger = LoggerFactory.getLogger(ExceptionHandling.class);

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseBody
  public ExceptionResponse handleConstraintViolationException(ConstraintViolationException constraintViolationException,
                                                       HttpServletRequest request,
                                                       HttpServletResponse responseCode) {
    responseCode.setStatus(400);
    ExceptionResponse response = new ExceptionResponse(new Date(), 400,
            constraintViolationException.getLocalizedMessage(),
            request.getRequestURI()
    );
    logger.error("ERROR: "+constraintViolationException+" " + response);
    return response;
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseBody
  public ExceptionResponse handleIllegalArgumentException(IllegalArgumentException illegalArgumentException,
                                                              HttpServletRequest request,
                                                              HttpServletResponse responseCode) {
    responseCode.setStatus(400);
    ExceptionResponse response = new ExceptionResponse(new Date(), 400,
            illegalArgumentException.getMessage(),
            request.getRequestURI()
    );
    logger.error("ERROR: "+illegalArgumentException+" " + response);
    return response;
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  @ResponseBody
  public ExceptionResponse handleMissingServletRequestParameterException(MissingServletRequestParameterException missingServletRequestParameterException,
                                                          HttpServletRequest request,
                                                          HttpServletResponse responseCode) {
    responseCode.setStatus(400);
    ExceptionResponse response = new ExceptionResponse(new Date(), 400,
            missingServletRequestParameterException.getMessage(),
            request.getRequestURI()
    );
    logger.error("ERROR: "+missingServletRequestParameterException+" " + response);
    return response;
  }

}
