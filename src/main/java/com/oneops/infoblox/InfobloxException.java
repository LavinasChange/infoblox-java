package com.oneops.infoblox;

import com.oneops.infoblox.model.Error;
import java.io.IOException;

/**
 * A custom exception for Infoblox (IBA) response and i/o error messages.
 *
 * @author Suresh G
 */
public class InfobloxException extends IOException {

  private String code;
  private String error;
  private String text;

  /**
   * Creates new Infoblox exception for the given error response.
   *
   * @param ibaError {@link Error}
   */
  public InfobloxException(Error ibaError) {
    super(ibaError.code() + " - " + ibaError.error());
    code = ibaError.code();
    error = ibaError.error();
    text = ibaError.text();
  }

  /**
   * Symbolic error code.
   *
   * @return error code.
   */
  public String getCode() {
    return code;
  }

  /**
   * Error type (followed by an explanation after :)
   *
   * @return error type
   */
  public String getError() {
    return error;
  }

  /**
   * Explanation of the error.
   *
   * @return error description
   */
  public String getText() {
    return text;
  }
}
