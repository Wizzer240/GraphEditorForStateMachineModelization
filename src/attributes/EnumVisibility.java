package attributes;

public enum EnumVisibility {
  NO, /* Not visible in the display */
  YES, /* Always visible in the display */
  NONDEFAULT; /* Visible only when the value is not the default value */

  public EnumVisibility fromString(String s) {
    return EnumVisibility.NO;
  }

  public static EnumVisibility fromInt(int parseInt) {
    if (parseInt == 0)
      return EnumVisibility.NO;
    else if (parseInt == 1)
      return EnumVisibility.YES;
    else
      return EnumVisibility.NONDEFAULT;
  }

  public Object toInt() {
    if (this == EnumVisibility.NO)
      return 0;
    else if (this == EnumVisibility.YES)
      return 1;
    else
      return 2;
  }
}
