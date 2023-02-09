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
package org.openurp.edu.clazz.service.internal;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.metadata.Model;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.lang.tuple.Pair;
import org.openurp.edu.clazz.model.*;
import org.openurp.edu.clazz.service.ClazzNameStrategy;
import org.openurp.edu.clazz.service.limit.RestrictionItemContentProvider;
import org.openurp.edu.clazz.service.limit.RestrictionItemContentProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class DefaultTeachClassNameStrategy implements ClazzNameStrategy {

  protected Logger logger = LoggerFactory.getLogger(getClass());

  private static Integer nameMaxSize;

  private String delimeter = " ";

  private RestrictionItemContentProviderFactory courseLimitItemContentProviderFactory;

  public String genName(List<Restriction> groups) {
    return Strings.abbreviate(buildAll(groups).getLeft(), getNameMaxSize());
  }

  public String genName(Clazz clazz) {
    return genName(clazz.getEnrollment().getRestrictions());
  }

  public String genName(String fullname) {
    if (Strings.isBlank(fullname)) {
      return fullname;
    }
    return Strings.abbreviate(fullname, getNameMaxSize());
  }

  public void autoName(Clazz clazz) {
    Enrollment teachclass = clazz.getEnrollment();
    Pair<String, String> names = buildAll(teachclass.getRestrictions());
    clazz.setName(names.getLeft());
    // 如果条件组为空，年级不为空，教学班名称为年级+全校
    if (clazz.getEnrollment().getRestrictions().isEmpty() && null != clazz.getEnrollment().getGrades()) {
      clazz.setName(teachclass.getGrades() + names.getLeft());
    }
    if (null != teachclass.getGenderRatio() && !teachclass.getGenderRatio().isEmpty()) {
      if (teachclass.getGenderRatio().isAllMale()) {
        if (!clazz.getName().contains("男")) {
          clazz.setName(clazz.getName() + "男");
        }
      } else if (teachclass.getGenderRatio().isAllFemale()) {
        if (!clazz.getName().contains("女")) {
          clazz.setName(clazz.getName() + "女");
        }
      }
    }
  }

  private Pair<String, String> buildAll(List<Restriction> groups) {
    Map<RestrictionMeta, RestrictionItemContentProvider<?>> providers = CollectUtils.newHashMap();
    List<Map<RestrictionMeta, Pair<Boolean, Set<String>>>> groupContentTitles = CollectUtils.newArrayList();
    Map<RestrictionMeta, List<Set<String>>> excludeContents = CollectUtils.newHashMap();
    for (Restriction restriction : groups) {
      Map<RestrictionMeta, Pair<Boolean, Set<String>>> metaContentTitles = CollectUtils.newHashMap();
      for (RestrictionItem item : restriction.getItems()) {
        boolean exclude = !item.isIncluded();
        RestrictionMeta meta = item.getMeta();
        RestrictionItemContentProvider<?> provider = providers.get(meta);
        if (null == provider) {
          provider = courseLimitItemContentProviderFactory.getProvider(meta);
          providers.put(meta, provider);
        }
        Map<String, String> contentIdTitles = provider.getContentIdTitleMap(item.getContents());
        Set<String> contentTitles = new LinkedHashSet<String>(contentIdTitles.values());
        if (exclude) {
          List<Set<String>> oneMetaExcludeContents = excludeContents.get(meta);
          if (null == oneMetaExcludeContents) {
            oneMetaExcludeContents = CollectUtils.newArrayList();
            excludeContents.put(meta, oneMetaExcludeContents);
          }
          oneMetaExcludeContents.add(contentTitles);
        }
        metaContentTitles.put(meta, new Pair<>(item.isIncluded(), contentTitles));
      }
      groupContentTitles.add(metaContentTitles);
    }
    for (Map<RestrictionMeta, Pair<Boolean, Set<String>>> oneGroupContentTitles : groupContentTitles) {
      for (Entry<RestrictionMeta, Pair<Boolean, Set<String>>> entry : oneGroupContentTitles.entrySet()) {
        RestrictionMeta meta = entry.getKey();
        Boolean op = entry.getValue().getLeft();
        if (op) {
          Set<String> contents = entry.getValue().getRight();
          List<Set<String>> oneMetaExcludeContents = excludeContents.get(meta);
          if (null != oneMetaExcludeContents) {
            for (Set<String> oneMetaExcludeContentSet : oneMetaExcludeContents) {
              oneMetaExcludeContentSet.removeAll(contents);
            }
          }
        }
      }
    }
    StringBuilder fullNameBuilder = new StringBuilder();
    StringBuilder nameBuilder = new StringBuilder();
    RestrictionMeta[] enums = RestrictionMeta.values();
    Map<RestrictionMeta, RestrictionMeta> metasEnums = CollectUtils.newHashMap();
    for (RestrictionMeta courseLimitMetaEnum : enums) {
      metasEnums.put(courseLimitMetaEnum, courseLimitMetaEnum);
    }
    Map<RestrictionMeta, String> metaTitles = CollectUtils.newHashMap();
    metaTitles.put(RestrictionMeta.Squad, "班级");
    metaTitles.put(RestrictionMeta.Department, "院系");
    metaTitles.put(RestrictionMeta.Direction, "方向");
    metaTitles.put(RestrictionMeta.Level, "培养层次");
    metaTitles.put(RestrictionMeta.Gender, "性别");
    metaTitles.put(RestrictionMeta.Grade, "年级");
    metaTitles.put(RestrictionMeta.Major, "专业");
    metaTitles.put(RestrictionMeta.StdType, "学生类别");

    for (Map<RestrictionMeta, Pair<Boolean, Set<String>>> oneGroupContentTitles : groupContentTitles) {
      boolean isEmptyGroup = true;
      for (Entry<RestrictionMeta, Pair<Boolean, Set<String>>> entry : oneGroupContentTitles.entrySet()) {
        RestrictionMeta meta = entry.getKey();
        RestrictionMeta metaEnum = metasEnums.get(meta);
        int length = fullNameBuilder.length();
        if (RestrictionMeta.Grade.equals(metaEnum)) {
          appendGradeContents(fullNameBuilder, oneGroupContentTitles);
        } else {
          appendEntityContents(fullNameBuilder, metaEnum, oneGroupContentTitles, metaTitles.get(metaEnum));
        }
        isEmptyGroup = length == fullNameBuilder.length();
      }
      if (!isEmptyGroup) {
        fullNameBuilder.append(delimeter);
      }
      StringBuilder sb = new StringBuilder();
      // 单组中 有行政班 仅显示行政班
      appendEntityContents(sb, RestrictionMeta.Squad, oneGroupContentTitles, "班级");
      if (sb.length() == 0) {
        // 添加年级
        appendGradeContents(sb, oneGroupContentTitles);
        boolean containsMajor = containsMeta(RestrictionMeta.Major, oneGroupContentTitles);
        if (containsMajor) {
          // 添加专业
          appendEntityContents(sb, RestrictionMeta.Major, oneGroupContentTitles, "专业");
        } else {
          // 添加院系
          appendEntityContents(sb, RestrictionMeta.Department, oneGroupContentTitles, "院系");
        }
        // 添加方向
        appendEntityContents(sb, RestrictionMeta.StdType, oneGroupContentTitles, "方向");
      }
      if (sb.length() > 0) {
        if (nameBuilder.length() > 0) {
          nameBuilder.append(delimeter);
        }
        nameBuilder.append(sb.toString());
      }
    }
    if (nameBuilder.length() == 0) {
      nameBuilder.append("全校");
    }

    String name = nameBuilder.toString();
    String fullname = "全校";
    if (fullNameBuilder.length() > 0) {
      fullname = fullNameBuilder.substring(0, fullNameBuilder.length() - 1);
    }
    return new Pair<String, String>(name, fullname);
  }

  private boolean containsMeta(RestrictionMeta meta,
                               Map<RestrictionMeta, Pair<Boolean, Set<String>>> groupContents) {
    Pair<Boolean, Set<String>> pair = groupContents.get(meta);
    if (null != pair) {
      return CollectUtils.isNotEmpty(pair.getRight());
    }
    return false;
  }

  private StringBuilder appendEntityContents(StringBuilder sb, RestrictionMeta meta,
                                             Map<RestrictionMeta, Pair<Boolean, Set<String>>> oneGroupContentTitles, String key) {
    Pair<Boolean, Set<String>> directionPair = oneGroupContentTitles.get(meta);
    if (null != directionPair) {
      Set<String> contents = directionPair.getRight();
      if (CollectUtils.isNotEmpty(contents)) {
        if (sb.length() > 0) {
          sb.append(",");
        }
        Boolean directionOp = directionPair.getLeft();
        if (!directionOp) {
          sb.append("非 ");
        }
        sb.append(Strings.join(contents.toArray(new String[contents.size()]), " "));
      }
    }
    return sb;
  }

  private StringBuilder appendGradeContents(StringBuilder sb,
                                            Map<RestrictionMeta, Pair<Boolean, Set<String>>> oneGroupContentTitles) {
    Pair<Boolean, Set<String>> gradePair = oneGroupContentTitles.get(RestrictionMeta.Grade);
    if (null != gradePair) {
      if (CollectUtils.isNotEmpty(gradePair.getRight())) {
        if (sb.length() > 0) {
          sb.append(",");
        }
        Boolean gradeOp = gradePair.getLeft();
        if (!gradeOp) {
          sb.append("非 ");
        }
        for (String grade : gradePair.getRight()) {
          sb.append(grade).append("级 ");
        }
        sb.deleteCharAt(sb.length() - 1);
      }
    }
    return sb;
  }

  private int getNameMaxSize() {
    if (null == nameMaxSize) {
      nameMaxSize = 100;
      Class<?> entityClass = Model.getType(Clazz.class).getEntityClass();
      try {
        nameMaxSize = entityClass.getDeclaredField("name").getAnnotation(Size.class).max();
      } catch (NoSuchFieldException e) {
        logger.info("get " + entityClass.getName() + ".name max size failure", e);
      } catch (SecurityException e) {
        logger.info("get " + entityClass.getName() + ".name max size failure", e);
      }
    }
    return nameMaxSize;
  }

  public void setRestrictionItemContentProviderFactory(
      RestrictionItemContentProviderFactory courseLimitItemContentProviderFactory) {
    this.courseLimitItemContentProviderFactory = courseLimitItemContentProviderFactory;
  }
}
