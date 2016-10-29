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
                <h1 class="page-header">公众人物详细信息</h1>
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
                                        <label>序列编号</label>
                                        <input class="form-control" name="id" disabled>
                                    </div>
                                    <div class="form-group">
                                        <label>会员昵称</label>
                                        <input class="form-control" name="memberId" disabled>
                                    </div>
                                    <%--<div class="form-group">--%>
                                    <%--<label>申请描述</label>--%>
                                    <%--<input class="form-control" name="bewrite" disabled>--%>
                                    <%--</div>--%>
                                    <div class="form-group">
                                        <label>管理员信息</label>
                                        <input class="form-control" name="systemUser" disabled>
                                    </div>
                                    <div class="form-group">
                                        <label>创建时间</label>
                                        <input class="form-control" name="createTime" disabled>
                                    </div>
                                    <div class="form-group">
                                        <label>处理时间</label>
                                        <input class="form-control" name="disposeTime" disabled>
                                    </div>

                                    <div id="div1" class="form-group">
                                        <label>审核结果</label>
                                        <select id="status" class="form-control" name="status">
                                            <option value="1">同意</option>
                                            <option value="2">驳回</option>
                                        </select>
                                    </div>

                                    <div class="form-group">
                                        <label>审核意见</label>
                                        <textarea class="form-control" rows="3" name="auditOpinion"></textarea>
                                    </div>


                                    <input id="button1" type="button" class="btn btn-outline btn-info submit"
                                           value="确定">


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
            url: "/sys_publfigures/info",
            data: {id: "${param.id}"},
            success: function (response) {
                if (response.code == "200") {
                    var _this = response.data;
                    $("input[name='id']").val(_this.id);
                    $("input[name='memberId']").val(_this.memberId);
//                    $("input[name='bewrite']").val(_this.bewrite);
                    $("textarea[name='auditOpinion']").val(_this.auditOpinion);
                    $("select[name='status']").val(_this.status == 3 ? 1 : _this.status);
                    if (_this.status == 1 || _this.status == 2) {
                        $("#button1").hide();
                    }
                    $("input[name='systemUser']").val(_this.systemUser);
                    $("input[name='createTime']").val(_this.createTime);
                    $("input[name='disposeTime']").val(_this.disposeTime);
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

        $(".submit").click(function () {
            $(this).attr('disabled', "true");
            //获取当前数据对象的主键ID
            var id = $("input[name='id']").val();
            //获取到value的值
            var statusvalue = $("#status").val();

            $.ajax({
                type: "post",
                url: "/sys_publfigures/modify",
                traditional: true,
                data: {"id": id, "status": statusvalue, "auditOpinion": $("textarea[name='auditOpinion']").val()},
                success: function (response) {

                    if (response.code == "200") {
                        $("#form-tip").removeClass("hidden alert-warning").addClass("alert-success").show().find("strong").text(response.message);
                        setTimeout(function () {
                            window.location.href = "/system-manage/gotoPage?url=publicFigure/list";
                        }, 500);
                    } else {
                        $("#form-tip").removeClass("hidden alert-success").addClass("alert-warning").show().find("strong").text(response.message);
                        $(".submit").removeAttr("disabled");
                    }
                }
            });
        });
    });

    //不同意 将状态修改为2
    function update1() {
        $.ajax({
            type: "post",
            url: "/sys_publfigures/modify",
            traditional: true,
            data: {"id": id, "status": 2},
            success: function (response) {
                var butongyi = ["驳回"];
                var status;
                this.status == "2"
                status = butongyi;
                setMonthOption(status);
            }
        });
//            setTimeout(function () {
//                window.location.href = "/system-manage/gotoPage?url=publicFigure/list";
//            }, 200);
    }

</script>
</body>
</html>
