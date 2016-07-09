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
    <script src="/js/jquery-1.10.1.min.js"></script>
    <script src="/js/bootstrap.min.js"></script>
</head>

<body>

<div id="wrapper">
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">上位机信息</h1>
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
                                        <label>设备名称</label>
                                        <input class="form-control" name="name" disabled>
                                    </div>
                                    <div class="form-group">
                                        <label>设备代码</label>
                                        <input class="form-control" name="code" disabled>
                                    </div>
                                    <div class="row">
                                        <div class="form-group col-xs-4">
                                            <label>省</label>
                                            <select class="form-control province" name="province" disabled></select>
                                        </div>
                                        <div class="form-group col-xs-4">
                                            <label>市</label>
                                            <select class="form-control city" name="city" disabled></select>
                                        </div>
                                        <div class="form-group col-xs-4">
                                            <label>区</label>
                                            <select class="form-control county" name="county" disabled></select>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label>设备地址</label>
                                        <input class="form-control" name="address" disabled>
                                    </div>
                                    <div class="form-group">
                                        <label>设备描述</label>
                                        <textarea class="form-control" rows="3" name="description" disabled></textarea>
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
            url: "/system-manage/masterComputer/object",
            data: {id: "${param.id}"},
            success: function (response) {
                if (response.code == "200") {
                    var _this = response.data;
                    $("input[name='name']").val(_this.name);
                    $("input[name='code']").val(_this.code);
                    $("select[name='province']").html('<option>' + _this.province_name + '</option>');
                    $("select[name='city']").html('<option>' + _this.city_name + '</option>');
                    $("select[name='county']").html('<option>' + _this.county_name + '</option>');
                    $("input[name='address']").val(_this.address);
                    $("textarea[name='description']").val(_this.description);
                } else {
                    $("#form-tip").removeClass("hidden alert-warning").addClass("alert-success").show().find("strong").text(response.message);
                    //3秒后自动关闭警告框
                    setTimeout("hideOperatorTip()", 3000);
                }
            }
        });

        $(".back").click(function () {
            window.history.go(-1);
        });
    });

    /**
     * 隐藏警告框
     */
    function hideOperatorTip() {
        $("#form-tip").addClass("hidden")
        window.history.go(-1);
    }

</script>
</body>

</html>
