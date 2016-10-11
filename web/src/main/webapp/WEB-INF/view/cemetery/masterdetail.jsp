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
                <h1 class="page-header">陵园墓碑主人详细信息</h1>
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
                                        <label>墓碑信息编号</label>
                                        <input class="form-control" name="id" disabled>
                                    </div>
                                    <div class="form-group">
                                        <label>姓名</label>
                                        <input class="form-control" name="name" disabled>
                                    </div>
                                    <div class="form-group">
                                        <label>遗像</label>
                                        <input class="form-control" name="portrait" disabled>
                                    </div>
                                    <div class="form-group">
                                        <label>墓碑编号</label>
                                        <input class="form-control" name="tombstone_id" disabled>
                                    </div>
                                    <div class="form-group">
                                        <label>生辰</label>
                                        <input class="form-control" name="birthday" disabled>
                                    </div>
                                    <div class="form-group">
                                        <label>辞世时间</label>
                                        <input class="form-control" name="death_time" disabled>
                                    </div>
                                    <div class="form-group">
                                        <label>同墓碑排列位置</label>
                                        <input class="form-control" name="sort" disabled>
                                    </div>
                                    <div class="form-group">
                                        <label>生平介绍</label>
                                        <input class="form-control" name="life_introduce" disabled>
                                    </div>
                                    <div class="form-group">
                                        <label>遗言遗愿</label>
                                        <input class="form-control" name="last_wish" disabled>
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
            url: "/sys_master/queryid",
            data: {id: "${param.id}"},
            success: function (response) {

                if (response.code == "200") {
                    var _this = response.data;
                    $("input[name='id']").val(_this.id);
                    $("input[name='name']").val(_this.name);
                    $("input[name='portrait']").val(_this.portrait);
                    $("input[name='tombstone_id']").val(_this.tombstoneId);
                    $("input[name='death_time']").val(_this.deathTime);
                    $("input[name='birthday']").val(_this.birthday);
                    $("input[name='sort']").val(_this.sort);
                    $("input[name='life_introduce']").val(_this.lifeIntroduce);
                    $("input[name='last_wish']").val(_this.lastWish);
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

</script>

</body>
</html>
