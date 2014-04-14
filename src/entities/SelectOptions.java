package entities;

/*
 Copyright 2007-2011 Zimmer Design Services
 Copyright 2014 Jean-Baptiste Lespiau

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

/**
 * List of possible types for the selection status of displayable objects
 */
public enum SelectOptions {
  // For states
  /* Not selected. All other options are considered as selected for a state */
  NONE,
  CENTER,
  TL, // Top-Left
  TR, // Top-Right
  BL, // Bottom-Left
  BR, // Bottom-Right
  TXT,

  // Only for StateTransition
  START, /* Selection of the starting point of a transition */
  STARTCTRL, /* Selection of the control point of the starting point */
  ENDCTRL, /* Selection of the control point of the ending point */
  END, /* Selection of the ending point of a transition */
  ALL,
  PAGES,
  PAGESC,
  PAGEEC,
  PAGEE
}
