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
                <h1 class="page-header">添加官方商品</h1>
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
                                    <!-- 扩展属性 -->
                                    <input class="form-control hide" name="extendAttribute">
                                    <div class="form-group">
                                        <label>商品图片</label>
                                        <input class="form-control" name="imageFile" type="file">
                                    </div>
                                    <div class="form-group">
                                        <label>商品名称</label>
                                        <input class="form-control" name="name">
                                    </div>
                                    <div class="form-group">
                                        <label>商品单价</label>
                                        <input class="form-control" name="price">
                                    </div>
                                    <div class="form-group">
                                        <label>商品类别</label>
                                        <select class="form-control" name="type">
                                            <option value="1">大门</option>
                                            <option value="2">墓碑</option>
                                            <option value="3">祭品（香）</option>
                                            <option value="4">祭品（蜡烛）</option>
                                            <option value="5">祭品（花）</option>
                                            <option value="6">普通祭品</option>
                                            <option value="7">扩展陵园存储容量</option>
                                            <option value="8">增加可建陵园数</option>
                                            <option value="9">动物饲料</option>
                                            <option value="10">植物肥料</option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label>二级分类</label>
                                        <select class="form-control" name="secondClassifyId">
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label>商品描述</label>
                                        <textarea class="form-control" rows="3" name="description"></textarea>
                                    </div>

                                    <div class="form-group hide">
                                        <label>容量（bit）</label>
                                        <input class="form-control" id="extend_storage">
                                    </div>
                                    <div class="form-group hide">
                                        <label>扩建个数</label>
                                        <input class="form-control" id="extend_count">
                                    </div>
                                    <div class="form-group">
                                        <label>生命周期（单位：天）</label>
                                        <input class="form-control" id="lifecycle" name="lifecycle">
                                    </div>
                                    <div class="form-group">
                                        <label>模型编号</label>
                                        <input class="form-control" id="extend_model_id"
                                               onclick="window.open('/system-manage/gotoPage?url=/goods official/model_list','','top=50,left=400,width=500,height=600');"
                                               readonly>
                                    </div>

                                    <input type="button" class="btn btn-outline btn-success submit" value="添加">
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
    //根据一级分类获取二级商品分类集合
    var getSecondClassify = function (firstClassify) {
        $.ajax({
            type: "POST",
            url: "/second_classify/getList",
            data: {'firstClassify': firstClassify},
            success: function (resp) {
                if (resp.code == "200" && resp.data.length > 0) {

                    var secondClassifyOption = '';
                    $(resp.data).each(function () {
                        secondClassifyOption += '<option value="' + this.id + '">' + this.name + '</option>';
                    });

                    $("select[name='secondClassifyId']").html(secondClassifyOption);
                } else {
                    $("#form-tip").removeClass("hidden alert-success").addClass("alert-warning").show().find("strong").text(resp.message);
                    $(".submit").removeAttr("disabled");
                }
            }
        });
    }
    $(function () {

        //获取二级分类数据集合
        getSecondClassify(null);

        $("select[name='type']").change(function () {
            //根据一级分类获取二级分类数据集合
            getSecondClassify($(this).val());

            $("#extend_model_id").parent("div").addClass("hide");
            $("#extend_storage").parent("div").addClass("hide");
            $("#extend_count").parent("div").addClass("hide");
            $("#lifecycle").parent("div").addClass("hide");
            switch (parseInt($(this).val())) {
                case 1:
                    ;
                case 2:
                    ;
                case 3:
                    ;
                case 4:
                    ;
                case 5:
                    ;
                case 6:
                    $("#lifecycle").parent("div").removeClass("hide");
                    $("#extend_model_id").parent("div").removeClass("hide");
                    break;
                case 7:
                    $("#extend_storage").parent("div").removeClass("hide");

                    break;
                case 8:
                    $("#extend_count").parent("div").removeClass("hide");

                    break;
                case 9:;
                case 10:
                    $("#extend_model_id").parent("div").removeClass("hide");
                    break;
            }
        });

        //返回
        $(".back").click(function () {
            window.history.go(-1);
        });

        //提交保存
        $(".submit").click(function () {
            $(this).attr('disabled', "true")

            $("input[name='extendAttribute']").val("");
            switch (parseInt($("select[name='type']").val())) {
                case 1:
                    ;
                case 2:
                    ;
                case 3:
                    ;
                case 4:
                    ;
                case 5:
                    ;
                case 9:
                    ;
                case 10:
                    ;
                case 6:
                    $("input[name='extendAttribute']").val($("#extend_model_id").val());
                    break;
                case 7:
                    $("input[name='extendAttribute']").val($("#extend_storage").val());
                    break;
                case 8:
                    $("input[name='extendAttribute']").val($("#extend_count").val());
                    break;
            }
            //异步提交表单
            $.ajax({
                type: "POST",
                url: "/goodsOfficial/add",
                data: new FormData($("form")[0]),
                cache: false,
                contentType: false, //改变默认文本传输，允许文件传输
                processData: false, //允许data传递对象
                success: function (response) {
                    if (response.code == "200") {
                        $("#form-tip").removeClass("hidden alert-warning").addClass("alert-success").show().find("strong").text(response.message);

                        setTimeout(function () {
                            window.location.href = "/system-manage/gotoPage?url=goods official/list";
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
