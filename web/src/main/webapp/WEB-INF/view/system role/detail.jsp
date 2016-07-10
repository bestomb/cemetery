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
                <h1 class="page-header">管理员角色详细信息</h1>
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
                                    <div class="form-group">
                                        <label>角色名称</label>
                                        <input class="form-control" name="name" disabled>
                                    </div>
                                    <div class="form-group">
                                        <label>备注</label>
                                        <textarea class="form-control" rows="3" name="remark" disabled></textarea>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label">绑定菜单</label>
                                        <div class="controls">
                                            <ul id="menuTree" class="ztree span6 m-wrap"
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
            url: "/system_role/info",
            data: {id: "${param.id}"},
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
            success: function (response) {
                if (response.code != "200" || response.data.length <= 0) {
                    return false;
                }

                $(response.data).each(function(){
                    var _this = this;
                    $(menuIds).each(function () {
                        if(_this.id == this){
                            _this.checked = true;
                            return true;
                        }
                    });
                    _this.chkDisabled = true;
                });

                menuTree = $.fn.zTree.init($("#menuTree"), setting, response.data);
                menuTree.expandAll(true);
            }
        });
    }
</script>
</body>

</html>
