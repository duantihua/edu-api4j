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
package org.openurp.std.info.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.openurp.code.geo.model.RailwayStation;

/**
 * 学生家庭信息
 */
@Entity(name = "org.openurp.std.info.model.Home")
public class Home extends StudentInfoBean {

  private static final long serialVersionUID = 6479456696916354885L;

  /** 家庭电话 */
  private String phone;

  /** 家庭地址 */
  private String address;

  /** 家庭地址邮编 */
  private String postcode;

  /** 户籍 */
  private String formerAddr;

  /** 派出所 */
  private String police;

  /** 派出所电话 */
  private String policePhone;

  /** 火车站 用于打印学生证 */
  @ManyToOne(fetch = FetchType.LAZY)
  private RailwayStation railwayStation;

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getPostcode() {
    return postcode;
  }

  public void setPostcode(String postcode) {
    this.postcode = postcode;
  }

  public String getFormerAddr() {
    return formerAddr;
  }

  public void setFormerAddr(String formerAddr) {
    this.formerAddr = formerAddr;
  }

  public String getPolice() {
    return police;
  }

  public void setPolice(String police) {
    this.police = police;
  }

  public String getPolicePhone() {
    return policePhone;
  }

  public void setPolicePhone(String policePhone) {
    this.policePhone = policePhone;
  }

  public RailwayStation getRailwayStation() {
    return railwayStation;
  }

  public void setRailwayStation(RailwayStation railwayStation) {
    this.railwayStation = railwayStation;
  }
}
