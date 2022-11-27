package org.openurp.base.std.model;

import org.beangle.commons.entity.pojo.IntegerIdObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.openurp.base.edu.model.Project;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity(name = "org.openurp.base.std.model.GraduateGrade")
@Cacheable
@Cache(region = "openurp.base", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GraduateGrade extends IntegerIdObject {

  private String code;

  private String name;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Project project;

  private int graduateYear;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public int getGraduateYear() {
    return graduateYear;
  }

  public void setGraduateYear(int graduateYear) {
    this.graduateYear = graduateYear;
  }
}
