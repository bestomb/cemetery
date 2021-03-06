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
                <h1 class="page-header">修改管理员角色</h1>
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
                                    <!-- 数据序列编号 -->
                                    <input class="form-control hidden" name="id" value="${param.id }">
                                    <div class="form-group">
                                        <label>角色名称</label>
                                        <input class="form-control" name="name">
                                    </div>
                                    <div class="form-group">
                                        <label>备注</label>
                                        <textarea class="form-control" rows="3" name="remark"></textarea>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label">绑定菜单</label>
                                        <div class="controls">
                                            <ul id="menuTree" class="ztree span6 m-wrap"
                                                style="border: 1px solid;overflow-y: scroll;overflow-x: auto;"></ul>
                                        </div>
                                    </div>
                                    <input type="button" class="btn btn-outline btn-success submit" value="保存">
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
            url: "/system_role/info",
            data: {id: "${param.id}"},
            dataType: "json",
            success: function (response) {
                if (response.code == "200") {
                    var _this = response.data;
                    $("input[name='name']").val(_this.name);
                    $("textarea[name='remark']").val(_this.remark);
                    getSystemMenuList(_this.menuId);
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

        //提交保存
        $(".submit").click(function () {
            $(this).attr('disabled', "true")

            //获取所有被选中的菜单
            var checkedNodes = menuTree.getCheckedNodes(true);
            var menuIds = new Array();
            if (checkedNodes.length > 0) {
                $(checkedNodes).each(function () {
                    menuIds.push(this.id);
                });
            }

            //异步提交表单
            $.ajax({
                type: "POST",
                url: "/system_role/modify",
                data: $('form').serialize() + "&menuId=" + menuIds.join(","),
                dataType: "json",
                success: function (response) {
                    if (response.code == "200") {
                        $("#form-tip").removeClass("hidden alert-warning").addClass("alert-success").show().find("strong").text(response.message);

                        setTimeout(function () {
                            window.location.href = "/system-manage/gotoPage?url=system role/list";
                        }, 500);
                    } else {
                        $("#form-tip").removeClass("hidden alert-success").addClass("alert-warning").show().find("strong").text(response.message);
                        $(".submit").removeAttr("disabled");
                    }
                }
            });
        });
    });

    /**
     * 获取系统菜单数据并渲染成树状结构，渲染的同时，如果menuIds存在，则将节点选中
     *
     */
    function getSystemMenuList(menuIds) {
        var setting = {
            check: {
                enable: true
            },
            data: {
                simpleData: {
                    enable: true,
                    pIdKey: "parentId",
                    rootPid: 0,
                }
            }
        };

        //获取菜单数据集合
        $.ajax({
            type: "post",
            url: "/system_menu/list",
            traditional: true,
            data: null,
            dataType: "json",
            success: function (response) {
                if (response.code != "200" || response.data.length <= 0) {
                    return false;
                }

                if (menuIds != null && menuIds.length > 0) {
                    $(response.data).each(function(){
                        var _this = this;
                        $(menuIds).each(function () {
                            if(_this.id == this){
                                _this.checked = true;
                                return true;
                            }
                        });
                    });
                }

                menuTree = $.fn.zTree.init($("#menuTree"), setting, response.data);
                menuTree.expandAll(true);
            }
        });
    }
</script>
</body>

</html>
