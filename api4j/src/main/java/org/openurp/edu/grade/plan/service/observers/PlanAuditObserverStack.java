/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright © 2014, The OpenURP Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.openurp.edu.grade.plan.service.observers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import org.openurp.edu.grade.plan.service.AuditPlanContext;

public class PlanAuditObserverStack extends Observable implements PlanAuditObserver {

  public PlanAuditObserverStack(PlanAuditObserver... initObersers) {
    super();
    for (PlanAuditObserver ob : initObersers) {
      addObserver(ob);
    }
  }

  private List<PlanAuditObserver> observers = new ArrayList<PlanAuditObserver>();

  public void addObserver(PlanAuditObserver observer) {
    observers.add(observer);
  }

  public void notifyStart() {
    for (Iterator<PlanAuditObserver> iterator = observers.iterator(); iterator.hasNext();) {
      PlanAuditObserver observer = iterator.next();
      observer.notifyStart();
    }
  }

  public boolean notifyBegin(AuditPlanContext context, int index) {
    boolean canAudit = true;
    for (Iterator<PlanAuditObserver> iterator = observers.iterator(); iterator.hasNext();) {
      PlanAuditObserver observer = iterator.next();
      canAudit &= observer.notifyBegin(context, index);
    }
    return canAudit;
  }

  public void notifyEnd(AuditPlanContext context, int index) {
    for (Iterator<PlanAuditObserver> iterator = observers.iterator(); iterator.hasNext();) {
      PlanAuditObserver observer = iterator.next();
      observer.notifyEnd(context, index);
    }
  }

  public void finish() {
    for (Iterator<PlanAuditObserver> iterator = observers.iterator(); iterator.hasNext();) {
      PlanAuditObserver observer = iterator.next();
      observer.finish();
    }
  }

  public List<PlanAuditObserver> getObservers() {
    return observers;
  }

  public void setObservers(List<PlanAuditObserver> observers) {
    this.observers = observers;
  }

}
