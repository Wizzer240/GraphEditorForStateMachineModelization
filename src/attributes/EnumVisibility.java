package attributes;

/*
 Copyright 2014 Jean-Baptiste Lespiau jeanbaptiste.lespiau@gmail.com

 This file is part of Fizzim.

 Fizzim is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 3 of the License, or
 (at your option) any later version.

 Fizzim is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
