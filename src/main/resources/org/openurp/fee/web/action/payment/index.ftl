[#ftl]
[@b.head/]
<style type="text/Css">
  .resultTb{
    border-collapse: collapse;
    border: solid;
    border-width: 1px;
    border-color: #e3e8bf;
    background:#feffed;
    width:60%;
        cellpadding:0;
       cellspacing:0;
       margin-top:2%;
     }
  .resultTb a:link {
    color:#afddf2
    text-decoration: none;
  }
  .resultTb a:visited {
    color:#7fbbd5;
    text-decoration: none;
  }
  .resultTb a:hover {
    color: red;
  }
</style>
[@b.toolbar title="订单支付结果"]
  bar.addClose();
[/@]
<center>
  <table class="resultTb" height="100px">
    <tr><td colSpan="2">${result!}</td></tr>
  </table>
</center>
[@b.foot/]
