<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">

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
                <h1 class="page-header">系统管理用户详细信息</h1>
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
                                    <div class="form-group">
                                        <label>用户名</label>
                                        <input class="form-control" name="loginName" disabled>
                                    </div>
                                    <div class="form-group">
                                        <label>真实姓名</label>
                                        <input class="form-control" name="realName" disabled>
                                    </div>
                                    <div class="form-group">
                                        <label>手机号码</label>
                                        <input class="form-control" name="mobile" disabled>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label">绑定管理员角色</label>
                                        <div class="controls">
                                            <ul id="roleTree" class="ztree span6 m-wrap"
                                                style="border: 1px solid;overflow-y: scroll;overflow-x: auto;"></ul>
                                        </div>
                                    </div>
                                    <input type="button" class="btn btn-outline btn-info back" value="返回">
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
            url: "/system_user/info",
            data: {id: "${param.id}"},
            dataType: "json",
            success: function (response) {
                if (response.code == "200") {
                    var _this = response.data;
                    $("input[name='loginName']").val(_this.loginName);
                    $("input[name='realName']").val(_this.loginName);
                    $("input[name='mobile']").val(_this.mobile);
                    getSystemRoleList(_this.roleId);
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
     * 获取系统角色数据并渲染成树状结构，渲染的同时，如果roleIds存在，则将节点选中
     *
     */
    function getSystemRoleList(roleIds) {
        var setting = {
            check: {
                enable: true
            },
            data: {
                simpleData: {
                    enable: true,
                    rootPid: 0,
                }
            }
        };

        //获取菜单数据集合
        $.ajax({
            type: "post",
            url: "/system_role/list",
            traditional: true,
            data: null,
            dataType: "json",
            success: function (response) {
                if (response.code != "200" || response.data.length <= 0) {
                    return false;
                }

                $(response.data).each(function(){
                    var _this = this;
                    $(roleIds).each(function () {
                        if(_this.id == this){
                            _this.checked = true;
                            return true;
                        }
                    });

                    _this.chkDisabled = true;
                });

                roleTree = $.fn.zTree.init($("#roleTree"), setting, response.data);
                roleTree.expandAll(true);
            }
        });
    }
</script>
</body>

</html>
