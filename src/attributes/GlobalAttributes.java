package attributes;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Define the default attributes for all objects.
 * In particular, it stores the default attributes for :
 * the state machine, the inputs, the outputs, the states and the transitions.
 */
public class GlobalAttributes {

  public static final int MACHINE = 0;
  public static final int INPUTS = 1;
  public static final int OUTPUTS = 2;
  public static final int STATES = 3;
  public static final int TRANSITIONS = 4;

  private LinkedList<ObjAttribute> machineAttributes;
  private LinkedList<ObjAttribute> inputsAttributes;
  private LinkedList<ObjAttribute> outputsAttributes;
  private LinkedList<ObjAttribute> stateAttributes;
  private LinkedList<ObjAttribute> transAttributes;

  /**
   * Create a new empty set of global attributes.
   */
  public GlobalAttributes() {
    machineAttributes = new LinkedList<ObjAttribute>();
    inputsAttributes = new LinkedList<ObjAttribute>();
    outputsAttributes = new LinkedList<ObjAttribute>();
    stateAttributes = new LinkedList<ObjAttribute>();
    transAttributes = new LinkedList<ObjAttribute>();
  }

  /**
   * ONLY used temporarily for backward compatibility.
   * 
   * @return An iterator to iterate over global attributes lists.
   */
  @Deprecated
  public Iterator<LinkedList<ObjAttribute>> iterator() {
    class GAIterator implements Iterator<LinkedList<ObjAttribute>> {
      private int i;

      public GAIterator() {
        /* Set to -1 to be able to increment i at the beginning of next() */
        i = -1;
      }

      @Override
      public boolean hasNext() {
        return i < 4;
      }

      @Override
      public LinkedList<ObjAttribute> next() {
        i++;
        if (i == 0) {
          return getMachineAttributes();
        } else if (i == 1) {
          return getInputsAttributes();
        } else if (i == 2) {
          return getOutputsAttributes();
        } else if (i == 3) {
          return getStateAttributes();
        } else if (i == 4) {
          return getTransAttributes();
        } else {
          throw new NoSuchElementException();
        }
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    }

    return new GAIterator();
  }

  /** Used ONLY for compatibility with a previous version */
  @Deprecated
  public LinkedList<ObjAttribute> getSpecificGlobalAttributes(int i) {
    if (i == 0) {
      return getMachineAttributes();
    } else if (i == 1) {
      return getInputsAttributes();
    } else if (i == 2) {
      return getOutputsAttributes();
    } else if (i == 3) {
      return getStateAttributes();
    } else if (i == 4) {
      return getTransAttributes();
    } else {
      throw new NoSuchElementException();
    }
  }

  public void addMachineAttribute(ObjAttribute attribute) {
    machineAttributes.add(attribute);
  }

  public void addInputAttribute(ObjAttribute attribute) {
    inputsAttributes.add(attribute);
  }

  public void addOutputAttribute(ObjAttribute attribute) {
    outputsAttributes.add(attribute);
  }

  public void addStateAttribute(ObjAttribute attribute) {
    stateAttributes.add(attribute);
  }

  public void addTransitionAttribute(ObjAttribute attribute) {
    transAttributes.add(attribute);
  }

  public LinkedList<ObjAttribute> getMachineAttributes() {
    return machineAttributes;
  }

  public LinkedList<ObjAttribute> getInputsAttributes() {
    return inputsAttributes;
  }

  public LinkedList<ObjAttribute> getOutputsAttributes() {
    return outputsAttributes;
  }

  public LinkedList<ObjAttribute> getStateAttributes() {
    return stateAttributes;
  }

  public LinkedList<ObjAttribute> getTransAttributes() {
    return transAttributes;
  }

}
