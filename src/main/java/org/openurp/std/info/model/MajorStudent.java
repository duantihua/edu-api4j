package org.openurp.std.info.model;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.openurp.base.edu.model.Student;
import org.openurp.code.edu.model.DisciplineCategory;
import org.openurp.code.edu.model.Institution;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity(name = "org.openurp.std.info.model.MajorStudent")
public class MajorStudent extends LongIdObject {
  @ManyToOne(fetch = FetchType.LAZY)
  private Student std;

  private String code;

  private String majorName;

  private String enMajorName;

  @ManyToOne(fetch = FetchType.LAZY)
  private Institution school;
  @ManyToOne(fetch = FetchType.LAZY)
  private DisciplineCategory majorCategory;

  public Student getStd() {
    return std;
  }

  public void setStd(Student std) {
    this.std = std;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMajorName() {
    return majorName;
  }

  public void setMajorName(String majorName) {
    this.majorName = majorName;
  }

  public String getEnMajorName() {
    return enMajorName;
  }

  public void setEnMajorName(String enMajorName) {
    this.enMajorName = enMajorName;
  }

  public Institution getSchool() {
    return school;
  }

  public void setSchool(Institution school) {
    this.school = school;
  }

  public DisciplineCategory getMajorCategory() {
    return majorCategory;
  }

  public void setMajorCategory(DisciplineCategory majorCategory) {
    this.majorCategory = majorCategory;
  }
}
