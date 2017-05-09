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
                <h1 class="page-header">添加系统管理用户</h1>
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
                                        <input class="form-control" name="loginName">
                                    </div>
                                    <div class="form-group">
                                        <label>密码</label>
                                        <input class="form-control" name="loginPassword" type="password">
                                    </div>
                                    <div class="form-group">
                                        <label>真实姓名</label>
                                        <input class="form-control" name="realName">
                                    </div>
                                    <div class="form-group">
                                        <label>手机号码</label>
                                        <input class="form-control" name="mobile">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label">绑定管理员角色</label>
                                        <div class="controls">
                                            <ul id="roleTree" class="ztree span6 m-wrap"
                                                style="border: 1px solid;overflow-y: scroll;overflow-x: auto;"></ul>
                                        </div>
                                    </div>
                                    <input type="button" class="btn btn-outline btn-success submit" value="注册">
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
                if (response.code == "200" && response.data.length > 0) {
                    roleTree = $.fn.zTree.init($("#roleTree"), setting, response.data);
                    roleTree.expandAll(true);
                }
            }
        });

        //返回
        $(".back").click(function () {
            window.history.go(-1);
        });

        //提交保存
        $(".submit").click(function () {
            $(this).attr('disabled', "true")

            //获取所有被选中的角色
            var checkedNodes = roleTree.getCheckedNodes(true);
            var roleIds = new Array();
            if (checkedNodes.length > 0) {
                $(checkedNodes).each(function () {
                    roleIds.push(this.id);
                });
            }

            //异步提交表单
            $.ajax({
                type: "POST",
                url: "/system_user/add",
                data: $('form').serialize() + "&roleId=" + roleIds.join(","),
                dataType: "json",
                success: function (response) {
                    if (response.code == "200") {
                        $("#form-tip").removeClass("hidden alert-warning").addClass("alert-success").show().find("strong").text(response.message);

                        setTimeout(function () {
                            window.location.href = "/system-manage/gotoPage?url=system user/list";
                        }, 500);
                    } else {
                        $("#form-tip").removeClass("hidden alert-success").addClass("alert-warning").show().find("strong").text(response.message);
                        $(".submit").removeAttr("disabled");
                    }
                }
            });
        });
    });
</script>
</body>

</html>
