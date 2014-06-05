package entities;

/*
 Copyright 2007 Zimmer Design Services.
 Written by Michael Zimmer - mike@zimmerdesignservices.com
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

import java.awt.Graphics;
import java.awt.Point;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.LinkedList;

import attributes.GlobalAttributes;
import attributes.ObjAttribute;

/**
 * @brief Abstract object for all displayable objects (state, transitions, and
 *        text boxes)
 */
public abstract class GeneralObj implements Cloneable {

  /* List of obj attributes for the object */
  LinkedList<ObjAttribute> attrib = new LinkedList<ObjAttribute>();
  LinkedList<ObjAttribute> global;
  GlobalAttributes allGlobal;

  public GeneralObj(String name, GlobalAttributes globals) {
    global = getAttributes(globals);
    allGlobal = globals;
    if (name != null) {
      setName(name);
    }
  }

  /**
   * Manages the display of the object
   */
  public abstract void paintComponent(Graphics g);

  public abstract boolean setSelectStatus(int x, int y);

  public abstract void setSelectStatus(boolean b);

  /**
   * Unselect the given object.
   */
  public abstract void unselect();

  /**
   * @brief This function is used to allow the selection of multiple items by
   *        the user. The parameters are the coordinates of the rectangle
   *        selected by the user
   * @return True if the object is in the given rectangle
   */
  public abstract boolean setBoxSelectStatus(int x0, int y0, int x1, int y1);

  public boolean setBoxSelectStatus(int x, int y) {
    return false;
  }

  public abstract void adjustShapeOrPosition(int x, int y);

  /**
   * @return The selection status of the object
   */
  public abstract SelectOptions getSelectStatus();

  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  public abstract Point getStart();

  public abstract boolean containsParent(GeneralObj oldObj);

  /**
   * Returns the type of object.
   * 
   * @see GeneralObjType
   */
  public abstract GeneralObjType getType();

  public abstract boolean isModified();

  public boolean modified = false;

  public void setModified(boolean b) {
    modified = b;
  }

  public abstract void updateObj();

  public boolean isParentModified() {
    return modifiedParent;
  }

  public boolean modifiedParent = false;

  public void setParentModified(boolean b) {
    modifiedParent = b;
  }

  /**
   * @param page
   *          ?? 0 for StateObj, 3 for text object
   * @return Returns the center of the item.
   */
  public abstract Point getCenter(int page);

  public abstract void notifyChange(GeneralObj oldObj, GeneralObj clonedObj);

  public LinkedList<ObjAttribute> getAttributeList() {
    return attrib;
  }

  // public int jumpCount = 0; Unused ?

  /**
   * Return the global attributes associated with the current object type.
   * 
   * @param globals
   *          The GlobalAttributes containing all the global attributes.
   * @return The global attributes for the current object.
   */
  public abstract LinkedList<ObjAttribute>
      getAttributes(GlobalAttributes globals);

  // updates the attribute list for a particular object by reading in the global
  // list
  public void updateAttrib(GlobalAttributes globals) {
    global = getAttributes(globals);
    allGlobal = globals;

    for (int i = 0; i < global.size(); i++) {
      ObjAttribute g = global.get(i);
      ObjAttribute l = null;
      if (attrib.size() > i) {
        l = attrib.get(i);
      }

      // if names match for current index
      if (l != null && g.getName().equals(l.getName())) {
        // global attribute name can't be local
        if (l.getEditable(0) == ObjAttribute.LOCAL)
          l.setEditable(0, ObjAttribute.GLOBAL_FIXED);
        // if blank string, make global variable
        if (l.get(1).equals(""))
          l.setEditable(1, ObjAttribute.GLOBAL_VAR);
        if (l.get(3).equals(""))
          l.setEditable(3, ObjAttribute.GLOBAL_VAR);

        for (int j = 0; j < 7; j++) {
          // if value in field isn't locally set variable, replace with global
          if (l.getEditable(j) != ObjAttribute.LOCAL)
            l.set(j, g.get(j));
        }

        // checking for changed non-default value
        if (l.getEditable(1) == ObjAttribute.LOCAL
            && g.getValue().equals(l.getValue()))
          l.setEditable(1, ObjAttribute.GLOBAL_VAR);
      }
      // TODO rerun check on swapped lists
      else {
        boolean breaker = false;
        // look for attribute in wrong place
        for (int k = 0; k < attrib.size(); k++) {
          ObjAttribute l2 = attrib.get(k);
          // if one is found, fix it
          if (g.getName().equals(l2.getName()))
          {
            attrib.addLast((ObjAttribute) attrib.get(i));
            attrib.set(i, l2);
            attrib.remove(k);
            breaker = true;
            break;
          }
        }
        // if wasnt fixed, then it doesnt exist and needs to be created
        if (!breaker) {
          ObjAttribute cloned = null;
          cloned = (ObjAttribute) g.clone();

          if (cloned.getName().equals("name")) {
            cloned.setValue(getName());
            cloned.setEditable(1, ObjAttribute.LOCAL);
          }
          attrib.add(i, cloned);
        }
      }
    }

    /* Delete attributes that were removed in global */
    if (attrib.size() > global.size()) {
      for (int i = attrib.size() - 1; i > global.size() - 1; i--) {
        ObjAttribute obj = attrib.get(i);
        if (obj.getEditable(0) != ObjAttribute.LOCAL)
          attrib.remove(i);
      }
    }

    // set up correct pages
    for (int i = 0; i < attrib.size(); i++) {
      ObjAttribute l = attrib.get(i);
      l.setPage(myPage, "update");
      // l.setOutputTypeFlag(true);
      if (l.getType().equals("output")) {
        boolean b = false;
        boolean f = false;
        for (int j = 0; j < allGlobal.getOutputsAttributes().size(); j++) {
          ObjAttribute obj = allGlobal.getOutputsAttributes().get(j);
          if (obj.getName().equals(l.getName())) {
            if (obj.getType().equals("reg") || obj.getType().equals("regdp"))
              b = true;
            if (obj.getType().equals("flag"))
              f = true;
            break;
          }
        }
        l.setOutputTypeReg(b);
        l.setOutputTypeFlag(f);
      } else {
        l.setOutputTypeReg(false);
        l.setOutputTypeFlag(false);
      }
    }

  }

  public String getOutputType(String name) {
    LinkedList<ObjAttribute> list = allGlobal.getOutputsAttributes();
    // / find output with matching name, and return its type
    for (int i = 0; i < list.size(); i++) {
      ObjAttribute obj = list.get(i);
      if (obj.getName().equals(name)) {
        return obj.getType();
      }
    }
    return "";
  }

  public String toString() {
    return getName();
  }

  public String getName() {
    for (ObjAttribute attribute : attrib) {
      if (attribute.getName().equals("name")) {
        return attribute.getValue();
      }
    }
    throw new Error("Name not found in the attributes " + attrib);
  }

  public void setName(String str) {
    for (ObjAttribute attribute : attrib) {
      if (attribute.getName().equals("name")) {
        attribute.setValue(str);
        return;
      }
    }
    /* The name attribute does not exist, it is be created. */
    for (ObjAttribute global_attr : getAttributes(allGlobal)) {
      if (global_attr.getName().equals("name")) {
        ObjAttribute name_attribute;
        name_attribute = (ObjAttribute) global_attr.clone();

        name_attribute.setValue(str);
        name_attribute.setEditable(1, ObjAttribute.LOCAL);
        attrib.add(0, name_attribute);
        return;
      }
    }
    throw new Error("Global name attribute not found in "
        + getAttributes(allGlobal));
  }

  public abstract void save(BufferedWriter writer) throws IOException;

  // pages
  int currPage = 1;
  int myPage = 1;

  public void paintComponent(Graphics g, int i) {
    currPage = i;
    paintComponent(g);
    if (attrib != null) {
      int step = -1;
      for (int j = 0; j < attrib.size(); j++) {
        ObjAttribute obj = attrib.get(j);
        if (obj.getVisible()) {
          step++;
        }
        obj.paintComponent(g, currPage, getCenter(currPage), getSelectStatus(),
            step);
      }
    }
  }

  public int getPage() {
    return myPage;
  }

  public void setPage(int i) {
    myPage = i;
    if (attrib != null) {
      for (int j = 0; j < attrib.size(); j++) {
        ObjAttribute obj = attrib.get(j);
        if (getType() != GeneralObjType.TRANSITION) {
          obj.setPage(i);
        }
      }
    }
  }

  public void decrementPage() {
    myPage = myPage - 1;
    if (attrib != null) {
      for (int i = 0; i < attrib.size(); i++) {
        ObjAttribute obj = attrib.get(i);
        obj.setPage(myPage);
      }
    }

  }

  // indentation (also exits in FizzimGui.java and ObjAttribute.java
  public String i(int indent) {
    String ind = "";
    for (int i = 0; i < indent; i++) {
      ind = ind + "   ";
    }
    return ind;
  }
}
