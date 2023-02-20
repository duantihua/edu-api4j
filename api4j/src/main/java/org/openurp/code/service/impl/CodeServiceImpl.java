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
package org.openurp.code.service.impl;

import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.entity.pojo.Code;
import org.beangle.ems.dictionary.model.CodeMeta;
import org.openurp.code.service.CodeService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * @version $Id: CodeServiceImpl.java May 5, 2011 3:33:07 PM chaostone $
 */
public class CodeServiceImpl extends BaseServiceImpl implements CodeService {

  public <T extends Code<Integer>> T getCode(Class<T> codeClass, String code) {
    OqlBuilder<T> builder = OqlBuilder.from(codeClass, "basecode").where("basecode.code=:code", code);
    List<T> rs = entityDao.search(builder);
    if (!rs.isEmpty()) return rs.get(0);
    else return null;
  }

  public <T extends Code<Integer>> List<T> getCodes(Class<T> codeClass) {
    // dont use new java.sql.Date(System.currentTimeMillis) 这样同一天的hashCode不固定
    Date now = Date.valueOf(LocalDate.now());
    OqlBuilder<T> builder = OqlBuilder.from(codeClass, "basecode")
        .where("basecode.beginOn <= :now and (basecode.endOn is null or basecode.endOn >= :now)", now);
    builder.orderBy("basecode.code").cacheable();
    return entityDao.search(builder);
  }

  public <T extends Code<Integer>> T getCode(Class<T> codeClass, Integer codeId) {
    return entityDao.get(codeClass, codeId);
  }

  public <T extends Code<Integer>> List<T> getCodes(Class<T> type, Integer... ids) {
    OqlBuilder<T> builder = OqlBuilder.from(type, "basecode").where("basecode.id in(:ids)", ids);
    return entityDao.search(builder);
  }

  @SuppressWarnings("unchecked")
  public Class<? extends Code<Integer>> getCodeType(String name) {
    OqlBuilder<CodeMeta> builder = OqlBuilder.from(CodeMeta.class, "coder");
    builder.where("coder.name=:name or coder.enName=:name", name);
    List<CodeMeta> coders = entityDao.search(builder);
    try {
      if (1 != coders.size()) return null;
      else return (Class<? extends Code<Integer>>) Class.forName(coders.get(0).getClassName());
    } catch (ClassNotFoundException e) {
      logger.error("Basecode " + name + "stdType not found", e);
      throw new RuntimeException(e);
    }
  }

  public void removeCodes(Class<? extends Code<Integer>> codeClass, Integer... codeIds) {
    entityDao.remove(entityDao.get(codeClass, codeIds));
  }

  public void saveOrUpdate(Code<Integer> code) {
    code.setUpdatedAt(new Date(System.currentTimeMillis()));
    entityDao.saveOrUpdate(code);
  }
}
