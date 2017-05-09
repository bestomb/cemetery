<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://apps.bdimg.com/libs/bootstrap/3.3.0/css/bootstrap.min.css">
  <link href="/css/style.css" rel="stylesheet">
  <link href="/css/view/common.css" rel="stylesheet">
  <link href="/css/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
  <link rel="stylesheet" href="/css/zTreeStyle/zTreeStyle.css" type="text/css">
  <script src="/js/jquery-1.10.1.min.js"></script>
  <script src="/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="/js/jquery.ztree.core-3.5.js"></script>
  <script type="text/javascript" src="/js/jquery.ztree.excheck-3.5.js"></script>
</head>

<body>

<div id="wrapper">
  <div id="page-wrapper">
    <div class="row">
      <div class="col-lg-12">
        <h1 class="page-header">会员管理详细信息</h1>
      </div>
      <!-- /.col-lg-12 -->
    </div>
    <div class="row">
      <div class="col-lg-12">
        <div class="panel panel-default">
          <div class="panel-body">
            <div class="row">
              <div class="col-lg-6">
                <div id="form-tip" class="alert hidden">
                  <strong></strong>
                </div>
                <form role="form">
                  <!-- 会员编号 -->
                  <div class="form-group">
                    <label>会员编号</label>
                    <input class="form-control" name="memberId" disabled>
                  </div>
                  <div class="form-group">
                    <label>会员手机号码</label>
                    <input class="form-control" name="mobileNumber" disabled>
                  </div>
                  <div class="form-group">
                    <label>昵称</label>
                    <input class="form-control" name="nickName" disabled>
                  </div>
                  <div class="form-group">
                    <label>邀请者编号</label>
                    <input class="form-control" name="inviterId" disabled>
                  </div>
                  <div class="form-group">
                    <label>会员积分</label>
                    <input class="form-control" name="integral" disabled>
                  </div>
                  <div class="form-group">
                    <label>交易币</label>
                    <input class="form-control" name="tradingAmount" disabled>
                  </div>
                  <div class="form-group">
                    <label>可建设陵园总数</label>
                    <input class="form-control" name="constructionCount" disabled>
                  </div>
                  <div class="form-group">
                    <label>注册时间</label>
                    <input class="form-control" name="createTime" disabled>
                  </div>

                  <div class="form-group" id="kind_aa">
                    <input type="checkbox" id="ch1" value="1">
                    <label>内置管理员</label>
                    <input type="checkbox" id="ch2" value="2" disabled>
                    <label>公众事件申请临时管理</label>
                  </div>

                  <%--<div class="form-group">--%>
                  <%--<label class="control-label">绑定菜单</label>--%>

                  <%--<div class="controls">--%>
                  <%--<ul id="menuTree" class="ztree span6 m-wrap"--%>
                  <%--style="border: 1px solid;overflow-y: scroll;overflow-x: auto;"></ul>--%>
                  <%--</div>--%>
                  <%--</div>--%>

                  <input type="button" class="btn btn-outline btn-info back" value="返回">
                  <input type="button" class="btn btn-outline btn-info submit" value="确定">
                </form>
              </div>
            </div>
            <!-- /.row (nested) -->
          </div>
          <!-- /.panel-body -->
        </div>
        <!-- /.panel -->
      </div>
      <!-- /.col-lg-12 -->
    </div>
  </div>
</div>

<script>
  $(function () {
    $.ajax({
      type: "POST",
      url: "/system_MemberAccount/info",
      data: {memberId: ${param.id}},
      dataType: "json",
      success: function (response) {
        if (response.code == "200") {
          var _this = response.data;
          $("input[name='memberId']").val(_this.memberId);
          $("input[name='mobileNumber']").val(_this.mobileNumber);
          $("input[name='nickName']").val(_this.nickName);
          $("input[name='inviterId']").val(_this.inviterId);
          $("input[name='integral']").val(_this.integral);
          $("input[name='tradingAmount']").val(_this.tradingAmount);
          $("input[name='constructionCount']").val(_this.constructionCount);
          $("input[name='createTime']").val(_this.createTime);

          getSystemMenuList(_this.memberId);

          //console.log(getSystemMenuList);

        } else {
          $("#form-tip").removeClass("hidden alert-warning").addClass("alert-success").show().find("strong").text(response.message);
          //3秒后自动关闭警告框
          setTimeout("hideOperatorTip()", 3000);
        }
      }
    });

    //返回
    $(".back").click(function () {
      window.history.go(-1);
    });
  });

  /**
   * 获取会员角色数据,如果type存在，则将节点选中
   *
   */
  function getSystemMenuList(memberIds) {
    //获取菜单数据集合
    $.ajax({
      type: "post",
      url: "/sys_memberrole/list",
      traditional: true,
      data: {memberId: "${param.id}"},
      dataType: "json",
      success: function (response) {
        if (response.code != "200" || response.data.length <= 0) {
          return false;
        }
        console.log(response);
        for(var i= 0;i<response.data.length;i++){
          var id=response.data[i].type;
          $("#ch"+id).attr("checked",true);
          //$("#ch"+id).attr('disabled', 'disabled');

        }
      }
    });
  }


  $(".submit").click(function () {
    $.ajax({
      type: "post",
      url: "/sys_memberrole/memberadd",
      traditional: true,
      data: {memberId: "${param.id}", type:$("#ch1:checked").val()},
      dataType: "json",
      success: function (response) {

        if (response.code == "200") {
          $("#form-tip").removeClass("hidden alert-warning").addClass("alert-success").show().find("strong").text(response.message);
          setTimeout(function () {
            window.location.href = "/system-manage/gotoPage?url=memberaccount/memberlist";
          }, 500);
        } else {
          $("#form-tip").removeClass("hidden alert-success").addClass("alert-warning").show().find("strong").text(response.message);
          $(".submit").removeAttr("disabled");
        }
      }
    });
  });

</script>

</body>
</html>
