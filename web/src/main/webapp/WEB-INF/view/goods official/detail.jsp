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
                <h1 class="page-header">官网商品详细信息</h1>
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
                                    <div class="form-group">
                                        <label>商品图片</label>
                                        <img id="goodsImg" width="300px" height="300px"/>
                                    </div>
                                    <div class="form-group">
                                        <label>商品名称</label>
                                        <input class="form-control" name="name" readonly>
                                    </div>
                                    <div class="form-group">
                                        <label>商品单价</label>
                                        <input class="form-control" name="price" readonly>
                                    </div>
                                    <div class="form-group">
                                        <label>商品类别</label>
                                        <select class="form-control" name="type" readonly>
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

                                            <option value="11">祭品（广场）</option>
                                            <option value="12">祭品（湖泊）</option>
                                            <option value="13">祭品（盆栽植物）</option>
                                            <option value="14">祭品（食品）</option>
                                            <option value="15">祭品（用品）</option>
                                            <option value="16">祭品（金钱）</option>
                                            <option value="17">祭品（特色）</option>
                                            <option value="18">祭品（守护）</option>
                                            <option value="19">祭品（休闲娱乐）</option>
                                            <option value="20">祭品（儿童用品）</option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label>二级分类</label>
                                        <select class="form-control" name="secondClassify" readonly>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label>商品描述</label>
                                        <textarea class="form-control" rows="3" name="description" readonly></textarea>
                                    </div>

                                    <div class="form-group hide">
                                        <label>容量（bit）</label>
                                        <input class="form-control" id="extend_storage" readonly>
                                    </div>
                                    <div class="form-group hide">
                                        <label>扩建个数</label>
                                        <input class="form-control" id="extend_count" readonly>
                                    </div>
                                    <div class="form-group hide">
                                        <label>生命周期（单位：天）</label>
                                        <input class="form-control" id="lifecycle" readonly>
                                    </div>
                                    <div class="form-group hide">
                                        <label>模型编号</label>
                                        <input class="form-control" id="extend_model_id" readonly>
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
            url: "/goodsOfficial/getInfo",
            data: {goodsId: "${param.goodsId}"},
            success: function (response) {
                if (response.code == "200") {
                    var _this = response.data;

                    $("input[name='name']").val(_this.name);
                    $("#goodsImg").attr("src", _this.images);
                    $("input[name='price']").val(_this.price);
                    $("select[name='type']").val(_this.type);
                    $("textarea[name='description']").val(_this.description);
                    $("select[name='secondClassify']").html('<option>' + _this.secondClassifyName + '</option>');

                    switch (parseInt(_this.type)) {
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
                        case 11:
                            ;
                        case 12:
                            ;
                        case 13:
                            ;
                        case 14:
                            ;
                        case 15:
                            ;
                        case 16:
                            ;
                        case 17:
                            ;
                        case 18:
                            ;
                        case 19:
                            ;
                        case 20:
                            ;
                        case 6:
                            $("#lifecycle").parent("div").removeClass("hide");
                            $("#extend_model_id").parent("div").removeClass("hide");

                            $("#lifecycle").val(_this.lifecycle);
                            $("#extend_model_id").val(_this.extendAttribute);
                            break;
                        case 7:
                            $("#extend_storage").parent("div").removeClass("hide");
                            $("#extend_storage").val(_this.extendAttribute)
                            break;
                        case 8:
                            $("#extend_count").parent("div").removeClass("hide");
                            $("#extend_count").val(_this.extendAttribute)
                            break;
                        case 9:
                            ;
                        case 10:
                            $("#extend_model_id").parent("div").removeClass("hide");

                            $("#extend_model_id").val(_this.extendAttribute);
                            break;
                    }
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
