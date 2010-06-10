/*
 * Copyright 2006 Pentaho Corporation.  All rights reserved. 
 * This software was developed by Pentaho Corporation and is provided under the terms 
 * of the Mozilla Public License, Version 1.1, or any later version. You may not use 
 * this file except in compliance with the license. If you need a copy of the license, 
 * please go to http://www.mozilla.org/MPL/MPL-1.1.txt. The Original Code is the Pentaho 
 * BI Platform.  The Initial Developer is Pentaho Corporation.
 *
 * Software distributed under the Mozilla Public License is distributed on an "AS IS" 
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to 
 * the license for the specific language governing your rights and limitations.
*/
package org.pentaho.actionsequence.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public abstract class ActionControlStatement implements IActionControlStatement, Serializable {

  IActionControlStatement parent;
  IActionSequenceExecutableStatement[] children = new IActionSequenceExecutableStatement[0];
  
  
  public ActionControlStatement() {
  }
  
  public ActionControlStatement(IActionControlStatement parent) {
    this.parent = parent;
  }
    
  
  /**
   * @return the child actions and control statements
   */
  public List<IActionSequenceExecutableStatement> getChildren() {
    return Arrays.asList(children);
  }
  
  /**
   * @return the control statement that contains this action definition or null if there is no parent control statement.
   */
  public IActionControlStatement getParent() {
    return parent;
  }
  
  public void setParent(IActionControlStatement parent) {
    this.parent = parent;
  }
  
  /**
   * Adds an action definition to the end of this control statements list of 
   * children. This control statement becomes the new parent of the action
   * definition.
   * @param actionDef the action definition to be added.
   */
  public void add(IActionDefinition actionDef) {
    actionDef.setParent(this);
    List<IActionSequenceExecutableStatement> tmpList = new ArrayList<IActionSequenceExecutableStatement>();
    tmpList.addAll(Arrays.asList(children));
    tmpList.add(actionDef);
    children = tmpList.toArray(new IActionSequenceExecutableStatement[0]);
  }
  
  /**
   * Adds a new child action definition to this control statement. 
   * @param actionDef the action definition to be added.
   * @param index the index of where to add the new action. If index
   * is greater than the number of children then the new action is added
   * at the end of the list of children.
   */
  public void add(IActionDefinition actionDef, int index) {
    if (index >= children.length) {
      add(actionDef);
    } else {
      actionDef.setParent(this.parent);
      List<IActionSequenceExecutableStatement> tmpList = new ArrayList<IActionSequenceExecutableStatement>();
      tmpList.addAll(Arrays.asList(children));
      tmpList.add(index, actionDef);
      children = tmpList.toArray(new IActionSequenceExecutableStatement[0]);
    }
  }
  
  /**
   * Adds a control statement to the end of this control statments list of 
   * children. This control statement becomes the new parent of the specified control statement.
   * @param controlStatement the control statment to be added.
   */
  public void add(IActionControlStatement controlStatement) {
    controlStatement.setParent(this.parent);
    List<IActionSequenceExecutableStatement> tmpList = new ArrayList<IActionSequenceExecutableStatement>();
    tmpList.addAll(Arrays.asList(children));
    tmpList.add(controlStatement);
    children = tmpList.toArray(new IActionSequenceExecutableStatement[0]);
  }
  
  /**
   * Adds a control statement to this conrtol statement's list of 
   * children. 
   * @param controlStatement the control statement to be added.
   * @param index the index of where to add the new control statement. If index
   * is greater than the number of children then the new control statement is added
   * at the end of the list of children.
   */
  public void add(IActionControlStatement controlStatement, int index) {
    if (index >= children.length) {
      add(controlStatement);
    } else {
      controlStatement.setParent(this.parent);
      List<IActionSequenceExecutableStatement> tmpList = new ArrayList<IActionSequenceExecutableStatement>();
      tmpList.addAll(Arrays.asList(children));
      tmpList.add(index, controlStatement);
      children = tmpList.toArray(new IActionSequenceExecutableStatement[0]);
    }
  }
  
  /** 
   * Creates a new child action loop to the end of this control statement's children.
   * @param loopOn the loop on variable name
   */
  public IActionLoop addLoop(String loopOn) {
    IActionLoop loop = new ActionLoop(this.parent);
    loop.setLoopOn(loopOn);
    List<IActionSequenceExecutableStatement> tmpList = new ArrayList<IActionSequenceExecutableStatement>();
    tmpList.addAll(Arrays.asList(children));
    tmpList.add(loop);
    children = tmpList.toArray(new IActionSequenceExecutableStatement[0]);
    return loop;
  }
  
  /** 
   * Creates a new child action loop.
   * @param loopOn the loop on variable name
   * @param index the index where the new loop should be created. If index
   * is greater than the number of children then the new loop is added
   * at the end of the list of children.
   */
  public IActionLoop addLoop(String loopOn, int index) {
    IActionLoop actionLoop = null;
    if (index >= children.length) {
      actionLoop = addLoop(loopOn);
    } else {
      actionLoop = new ActionLoop(this.parent);
      actionLoop.setLoopOn(loopOn);
      List<IActionSequenceExecutableStatement> tmpList = new ArrayList<IActionSequenceExecutableStatement>();
      tmpList.addAll(Arrays.asList(children));
      tmpList.add(index, actionLoop);
      children = tmpList.toArray(new IActionSequenceExecutableStatement[0]);
    }
    return actionLoop;
  }
  
  /** 
   * Creates a new child if statement to the end of this control statement's children.
   * @param condition the if condition
   */
  public IActionIfStatement addIf(String condition) {
    IActionIfStatement ifStatement = new ActionIfStatement(this.parent);
    ifStatement.setCondition(condition);
    List<IActionSequenceExecutableStatement> tmpList = new ArrayList<IActionSequenceExecutableStatement>();
    tmpList.addAll(Arrays.asList(children));
    tmpList.add(ifStatement);
    children = tmpList.toArray(new IActionSequenceExecutableStatement[0]);
    return ifStatement;
  }
  
  /** 
   * Creates a new child if statement.
   * @param condition the if condition
   * @param index the index where the new if statement should be created. If index
   * is greater than the number of children then the new if statement is added
   * at the end of the list of children.
   */
  public IActionIfStatement addIf(String condition, int index) {
    IActionIfStatement actionIf = null;
    if (index >= children.length) {
      actionIf = addIf(condition);
    } else {
      actionIf = new ActionIfStatement(this.parent);
      actionIf.setCondition(condition);
      List<IActionSequenceExecutableStatement> tmpList = new ArrayList<IActionSequenceExecutableStatement>();
      tmpList.addAll(Arrays.asList(children));
      tmpList.add(index, actionIf);
      children = tmpList.toArray(new IActionSequenceExecutableStatement[0]);
    }
    return actionIf;
  }
  
  public IActionSequenceDocument getActionSequence() {
    return getParent().getActionSequence();
  }
}
