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
                <h1 class="page-header">陵园管理详细信息</h1>
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
                                        <label>陵园编号</label>
                                        <input class="form-control" name="id" disabled>
                                    </div>
                                    <div class="form-group">
                                        <label>陵园名称</label>
                                        <input class="form-control" name="name" disabled>
                                    </div>
                                    <div class="form-group">
                                        <label>所属会员</label>
                                        <input class="form-control" name="memberName" disabled>
                                    </div>
                                    <div class="form-group">
                                        <label>对外开放状态</label>
                                        <input class="form-control" name="isOpen" disabled>
                                    </div>
                                    <div class="form-group">
                                        <label>总容量(KB)</label>
                                        <input class="form-control" name="remainingStorageSize" disabled>
                                    </div>
                                    <div class="form-group">
                                        <label>剩余容量(KB)</label>
                                        <input class="form-control" name="storageSize" disabled>
                                    </div>
                                    <div class="form-group">
                                        <label>创建时间</label>
                                        <input class="form-control" name="createTimeForStr" disabled>
                                    </div>
                                    <div class="form-group">
                                        <label>省</label>
                                        <input class="form-control" name="provinceName" disabled>
                                    </div>
                                    <div class="form-group">
                                        <label>市</label>
                                        <input class="form-control" name="cityName" disabled>
                                    </div>
                                    <div class="form-group">
                                        <label>区</label>
                                        <input class="form-control" name="countyName" disabled>
                                    </div>
                                    <div class="form-group">
                                        <label>乡</label>
                                        <input class="form-control" name="townName" disabled>
                                    </div>
                                    <div class="form-group">
                                        <label>村</label>
                                        <input class="form-control" name="villageName" disabled>
                                    </div>
                                    <div class="form-group">
                                        <label>社</label>
                                        <input class="form-control" name="communityName" disabled>
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
            url: "/cemetery/getInfoById",
            data: {cemeteryId: "${param.id}"},
            dataType: "json",
            success: function (response) {
                if (response.code == "200") {
                    var _this = response.data;
                    $("input[name='id']").val(_this.id);
                    $("input[name='name']").val(_this.name);
                    $("input[name='memberName']").val(_this.memberName);
                    $("input[name='isOpen']").val(_this.isOpen == 1 ? "公开" : "不公开");
                    $("input[name='remainingStorageSize']").val(_this.remainingStorageSize);
                    $("input[name='storageSize']").val(_this.storageSize);
                    $("input[name='createTimeForStr']").val(_this.createTimeForStr);
                    $("input[name='provinceName']").val(_this.provinceName);
                    $("input[name='cityName']").val(_this.cityName);
                    $("input[name='countyName']").val(_this.countyName);
                    $("input[name='townName']").val(_this.townName);
                    $("input[name='villageName']").val(_this.villageName);
                    $("input[name='communityName']").val(_this.communityName);
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
            url: "/cemetery/list",
            traditional: true,
            data: null,
            dataType: "json",
            success: function (response) {
                if (response.code != "200" || response.data.length <= 0) {
                    return false;
                }

                $(response.data).each(function () {
                    var _this = this;
                    $(roleIds).each(function () {
                        if (_this.id == this) {
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
