[#ftl]
[@b.head]
[/@]
<div class="banner">
  <div>
    <div class="banner_area">
      <table cellpadding="0" cellspacing="0"  class="button_box_1">
        <tr>
          <td width="25"></td>
          <td width="90">
            <a href="#" class="a3" onclick="editAccount()">我的账户</a>&nbsp;&nbsp;
          </td>
          <td width="90">
            [@b.a href="!welcome" cssClass="a3" target="main"]返回首页[/@]&nbsp;&nbsp;
          </td>
          <td>
            [@b.a href="logout" cssClass="a3" target="_top"]退出[/@]
          </td>
        </tr>
      </table>
    </div>
    <div class="banner_area">
      [@b.form]
        [@b.a href="/security/my" target="_blank" style="color:#ffffff" title="查看登录记录"]${user.fullname}(${user.username})[/@]&nbsp;&nbsp;

        [#if (menuProfiles?size==1)]
          <span style="padding:0 2px;color:#FFF;">${menuProfiles[0].name}</span>
          <input  type="hidden" name="security.userCategoryId" value="${userCategoryId}" style="width:70px"/>
        [#else]
          [@b.select name="menuProfileId" items=menuProfiles title="entity.userCategory"  style="width:70px" value=menuProfile option="id,name"/]
        [/#if]
        [#if menuProfiles?size > 1]
        <input type="submit" value="切换"/>
        [/#if]
      [/@]
    </div>
  </div>
</div>

<div>
  <h1>缺少教学项目数据级权限，请联系管理员</h1>
</div>
<script type="text/javascript">
  function editAccount(){
        window.open("${b.url('/security/my')}");
  }
</script>

[@b.foot/]
