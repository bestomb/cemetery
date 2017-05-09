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
                <h1 class="page-header">修改模型</h1>
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
                                <form role="form" enctype="multipart/form-data">
                                    <!-- 数据序列编号 -->
                                    <input class="form-control hidden" name="id" value="${param.id }">
                                    <div class="form-group">
                                        <label>所属模型分类</label>
                                        <input class="form-control" name="classifyName" disabled>
                                    </div>
                                    <div class="form-group">
                                        <label>模型名称</label>
                                        <input class="form-control" name="name">
                                    </div>
                                    <div class="form-group">
                                        <label>PC端模型文件</label>
                                        <input class="form-control" name="file" type="file" value="选择PC端模型">
                                    </div>
                                    <div class="form-group">
                                        <label>Android端模型文件</label>
                                        <input class="form-control" name="file2" type="file" value="选择Android端模型">
                                    </div>
                                    <div class="form-group">
                                        <label>IOS端模型文件</label>
                                        <input class="form-control" name="file3" type="file" value="选择IOS端模型">
                                    </div>

                                    <input type="button" class="btn btn-outline btn-success submit" value="确认">
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
            url: "/model/info",
            data: {id: "${param.id}"},
            dataType: "json",
            success: function (response) {
                if (response.code == "200") {
                    var _this = response.data;
                    $("input[name='name']").val(_this.name);
                    $("input[name='classifyName']").val(_this.classifyName);
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

            //异步提交表单
            $.ajax({
                type: "POST",
                url: "/model/modify",
                data: new FormData($( "form" )[0]),
                cache: false,
                contentType: false, //改变默认文本传输，允许文件传输
                processData: false, //允许data传递对象
                dataType: "json",
                success: function (response) {
                    if (response.code == "200") {
                        $("#form-tip").removeClass("hidden alert-warning").addClass("alert-success").show().find("strong").text(response.message);

                        setTimeout(function () {
                            window.location.href = "/system-manage/gotoPage?url=model/list";
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
