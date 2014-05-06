package attributes;

public enum EnumVisibility {
  NO, /* Not visible in the display */
  YES, /* Always visible in the display */
  NONDEFAULT; /* Visible only when the value is not the default value */

  @Override
  public String toString() {
    switch (this) {
    case NO:
      return "No";
    case YES:
      return "Yes";
    case NONDEFAULT:
      return "Only non-default";
    default:
      throw new IllegalArgumentException();
    }
  }

  /**
   * @deprecated Use the EnumVisibility directly instead of integer equivalents
   */
  @Deprecated
  static public String fromInt(int i) {
    if (i == 0) {
      return "NO";
    } else if (i == 1) {
      return "YES";
    } else if (i == 2) {
      return "NONDEFAULT";
    } else {
      System.err.println(i
          + " is not recognized as a visibility. NO taken as default");
      return "NO";
    }
  }
}
