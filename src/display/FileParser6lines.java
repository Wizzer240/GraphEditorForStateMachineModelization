package display;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Vector;

import attributes.GlobalAttributes;
import attributes.ObjAttribute;
import entities.GeneralObj;

public class FileParser6lines {

  private File6lines parser;
  // private HashMap<String, String> states;
  private Vector<String> graphs;
  private String file;
  private FizzimGui fizzim;
  private DrawArea drawArea;
  private String graph_name;
  private String source_state;
  private String end_state;
  private String event;
  private String condition;
  private String action;
  int x0 = 366;
  int x1 = 496;
  int y0 = 216;
  int y1 = 346;

  public FileParser6lines(File file, FizzimGui fizzim, DrawArea drawArea)
      throws IOException {
    this.file = file.getName();
    this.fizzim = fizzim;
    this.drawArea = drawArea;
    drawArea.setCurrPage(1);
    // states = new HashMap<String, String>();
    graphs = new Vector<String>();
    parser = new File6lines(this.file);
    fizzim.resetTabs();
    resetGlobalList();
    parse();
    // drawArea.open(objList);
  }

  public void parse() throws IOException {
    int page;
    GeneralObj obj;

    while (parser.get6Lines()) {
      graph_name = parser.getGraphName();
      source_state = parser.getSourceState();
      end_state = parser.getDestinationState();
      event = parser.getEvent();
      condition = parser.getCondition();
      action = parser.getAction();

      if (!graphs.contains(graph_name)) {
        // add the graph_name in the vector graphs and create a new page with
        // that name. Set the current page of drawArea to the new page.
        graphs.add(graph_name);
        fizzim.addNewPage(graph_name);
      } else {
        // Set the current page of drawArea to the page of the graph.
        page = drawArea.getPageNumb(graph_name);
        drawArea.setCurrPage(page);
      }

      // Create the new state if it doesn't already exists.
      if (!drawArea.checkStateNameCurrPage(source_state)) {
        obj = drawArea.addNewState(source_state, x0, y0, x1, y1);
      }

      if (!drawArea.checkStateNameCurrPage(end_state)) {
        obj = drawArea.addNewState(end_state, x0, y0, x1, y1);
      }

      if (source_state == end_state) {
        obj = drawArea.addNewLoopbackTransition(source_state, x0, y0, x1, y1);
      } else {
        obj = drawArea.addNewTransition(source_state, end_state);
      }

      LinkedList<ObjAttribute> attributes = obj.getAttributeList();
      for (ObjAttribute attribute : attributes) {
        String name = (String) attribute.get(0);
        if (name.equals(FizzimGui.event_field)) {
          attribute.set(1, event);
        } else if (name.equals(FizzimGui.condition_field)) {
          attribute.set(1, condition);
        } else if (name.equals(FizzimGui.action_field)) {
          attribute.set(1, action);
        }
        attribute.setEditable(1, ObjAttribute.LOCAL);

        // TODO create a transition and fill the event condition action field

      }
    }
  }

  private void resetGlobalList() {

    GlobalAttributes global_attributes = new GlobalAttributes();

    drawArea.updateGlobal(global_attributes);
    fizzim.updateGlobal(global_attributes);
    fizzim.initGlobal();
    drawArea.open(global_attributes);
  }
}
