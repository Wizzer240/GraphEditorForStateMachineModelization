package entities;

/* To replace the previously hard-coded enumeration :
 * The type of object :
 * 0 for StateObj
 * 1 for StateTransitionObj
 * 2 for LoopbackTransitionObj
 * 3 for TextObj
 */
public enum GeneralObjType {
  STATE,
  TRANSITION,
  LOOPBACK_TRANSITION,
  TEXT;
}
