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
    <script type="text/javascript" src="/js/common/eqianyuan.page.js"></script>
    <script type="text/javascript" src="/js/common/common_utils.js"></script>
</head>

<body>
<div id="wrapper">
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">陵园列表</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="alert alert-warning alert-dismissable hidden operatorTip">
                        <button type="button" class="close" data-dismiss="operatorTip"
                                aria-hidden="true">
                            &times;
                        </button>
                        <span></span>
                    </div>
                    <div class="panel-body panel-search">
                        <form class="form-inline">
                            <div class="form-group">
                                <label>省</label>
                                <select class="form-control province" name="province"><option value="">-- 请选择省 --</option></select>
                            </div>
                            <div class="form-group">
                                <label>市</label>
                                <select class="form-control city" name="city"><option value="">-- 请选择市 --</option></select>
                            </div>
                            <div class="form-group">
                                <label>区</label>
                                <select class="form-control county" name="county"><option value="">-- 请选择区 --</option></select>
                            </div>
                            <button type="button" class="btn btn-default btn-sm" id="search">筛选查找</button>
                        </form>
                    </div>
                    <div class="panel-body">
                        <div class="dataTable_wrapper">
                            <table width="100%" class="table table-striped table-bordered table-hover"
                                   id="dataTables">
                                <thead>
                                <tr>
                                    <th>陵园编号</th>
                                    <th>陵园名称</th>
                                    <th>所属会员</th>
                                    <th>对外开放状态</th>
                                    <th>剩余容量/总容量(KB)</th>
                                    <th>创建时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody></tbody>
                            </table>
                        </div>
                    </div>
                    <ul class="pagination pagination-right"></ul>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {
        var pagination = {
            initStatus: false,      //分页插件初始化状态-true:已经构建了分页插件、false:还未构建
            data: {},
            page: {
                pageNo: 1,
                pageSize: 10
            },
            init: function () {
                //初始化分页
                $(".pagination").createPage({
                    pageCount: pagination.page.pageCount,
                    current: pagination.page.pageNo,
                    initStatus: pagination.initStatus,
                    backFn: function (pageNo) {
                        pagination.page.pageNo = pageNo;
                        pagination.list();
                    }
                });

                pagination.initStatus = true;
            },
            list: function () {
                $("#dataTables tbody").html("");
                $(".checkAll").prop("checked", false);

                $.ajax({
                    type: "POST",
                    url: "/cemetery/getListByArea",
                    data: $.extend({}, pagination.data, pagination.page),
                    success: function (response) {
                        //设置分页
                        pagination.page.pageNo = response.data.pageNo;
                        pagination.page.pageCount = response.data.pageCount;
                        pagination.init();
                        if (response.data.totalCount > 0) {
                            var tableBody = "";
                            $(response.data.list).each(function () {
                                tableBody += '<tr>'
                                        + '<td>' + this.id + '</td>'
                                        + '<td><a href="/system-manage/gotoPage?url=cemetery/memorial&cemeteryId=' + this.id + '">'+ this.name + '</a></td>'
                                        + '<td>' + this.memberName + '</td>'
                                        + '<td>' + (this.isOpen == 1 ? "公开" : "不公开") + '</td>'
                                        + '<td>' + this.remainingStorageSize + "/" + this.storageSize + '</td>'
                                        + '<td>' + this.createTimeForStr + '</td>'
                                        + '<td>'
                                        + '<button type="button" class="btn btn-outline btn-info detail" id= "'+this.id+'">详情</button>&nbsp;'
                                        + '</tr>';
                            });

                            $("#dataTables tbody").html(tableBody);
                        } else {
                            $("#dataTables tbody").html('<tr class="text-center"><td colspan="7">查无数据</td></tr>');
                        }
                    }
                });
            }
        };

        //获取列表数据
        pagination.list();

        $("#search").click(function () {
            pagination.data.provinceId = $("select[name='province']").val();
            pagination.data.cityId = $("select[name='city']").val();
            pagination.data.countyId = $("select[name='county']").val();
            pagination.page.pageNo = 1;
            pagination.list();
        });

        //数据详细信息
        $("#dataTables tbody").on("click", ".detail", function () {
            window.location.href = "/system-manage/gotoPage?url=cemetery/detail&id=" + $(this).attr("id");
        })

        /**
         * 获取地区集合
         * params postUrl  请求地址
         * params postData  请求参数
         * params callBack  回调函数
         */
        var getArea = function (postUrl, postData, callBack) {
            $.ajax({
                type: "GET",
                url: postUrl,
                data: postData,
                success: function (resp) {
                    if (resp.code == "200") {
                        eval(callBack + "(resp.data)");
                    } else {
                        $("#form-tip").removeClass("hidden alert-success").addClass("alert-warning").show().find("strong").text(resp.message);
                        $(".submit").removeAttr("disabled");
                    }
                }
            });
        }

        //获取省级数据地址
        var getProvinceUrl = "/getProvince";
        //获取市级数据地址
        var getCityUrl = "/getCity";
        //获取区级数据地址
        var getCountyUrl = "/getCounty";

        /**
         * 获取省级数据
         */
        getArea(getProvinceUrl, null, "setProvince");

        //省点击事件
        $(".province").change(function () {
            getArea(getCityUrl, {provinceId: $(this).val()}, "setCity");
        });

        //市点击事件
        $(".city").change(function () {
            getArea(getCountyUrl, {cityId: $(this).val()}, "setCounty");
        });

        /**
         * 填充省级数据
         */
        var setProvince = function (data) {
            //还原市和区数据
            $(".city").html("<option value=''>-- 请选择市 --</option>");
            $(".county").html("<option value=''>-- 请选择区 --</option>");

            if (data != null && data.length > 0) {
                var option = "<option value=''>-- 请选择省 --</option>";
                $(data).each(function () {
                    option += '<option value="' + this.provinceId + '">' + this.provinceName + '</option>';
                });

                $(".province").html(option);
            }
        }

        /**
         * 填充市级数据
         */
        var setCity = function (data) {
            //还原区数据
            $(".county").html("<option value=''>-- 请选择区 --</option>");

            if (data != null && data.length > 0) {
                var option = "<option value=''>-- 请选择市 --</option>";
                $(data).each(function () {
                    option += '<option value="' + this.cityId + '">' + this.cityName + '</option>';
                });

                $(".city").html(option);
            }
        }

        /**
         * 填充区级数据
         */
        var setCounty = function (data) {
            if (data != null && data.length > 0) {
                var option = "<option value=''>-- 请选择区 --</option>";
                $(data).each(function () {
                    option += '<option value="' + this.countyId + '">' + this.countyName + '</option>';
                });

                $(".county").html(option);
            }
        }
    });

</script>

</body>

</html>
