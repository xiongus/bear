package com.xiongus.bear.core.exception;

import com.xiongus.bear.common.exception.BearException;
import com.xiongus.bear.common.utils.ExceptionUtils;
import java.io.IOException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler.
 *
 * @author xiongus
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * For IllegalArgumentException, we are returning void with status code as 400, so our error-page
   * will be used in this case.
   *
   * @throws IllegalArgumentException IllegalArgumentException.
   */
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<String> handleIllegalArgumentException(Exception ex) throws IOException {
    return ResponseEntity.status(400).body(ExceptionUtils.getAllExceptionMsg(ex));
  }

  /**
   * For BearException.
   *
   */
  @ExceptionHandler(BearException.class)
  public ResponseEntity<String> handleBearException(BearException ex) throws IOException {
    return ResponseEntity.status(ex.getErrCode()).body(ExceptionUtils.getAllExceptionMsg(ex));
  }

  /**
   * For DataAccessException.
   *
   */
  @ExceptionHandler(DataAccessException.class)
  public ResponseEntity<String> handleDataAccessException(DataAccessException ex)
      throws DataAccessException {
    return ResponseEntity.status(500).body(ExceptionUtils.getAllExceptionMsg(ex));
  }
}
