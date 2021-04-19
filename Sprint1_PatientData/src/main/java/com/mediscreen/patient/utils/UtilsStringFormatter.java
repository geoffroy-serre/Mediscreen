package com.mediscreen.patient.utils;

public class UtilsStringFormatter {

  public static String TrimCapitalizeFirst(String toBeFormatted) {
    String trimmed = toBeFormatted.trim().replaceAll("\\s{02,}", " ");
    return trimmed.substring(0, 1).toUpperCase() + trimmed.substring(1).toLowerCase();
  }

  public static String TrimSpaces(String toBeFormatted) {
    return toBeFormatted.trim().replaceAll("\\s{02,}", " ");
  }

}
