package com.kahan.david.weatherapp.app;
/**
 * Created by david on 28/07/2017.
 */
public class StringUtils {

  private StringUtils() {
  }
  public static String stripPrefix(String inString) {
    if (inString.startsWith(", ")) {
      return inString.replaceFirst(", ", "");
    }
    return inString;
  }
}
