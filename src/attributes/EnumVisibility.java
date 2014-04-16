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
}
