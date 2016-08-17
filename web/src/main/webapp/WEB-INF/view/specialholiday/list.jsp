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
                <h1 class="page-header">特殊节日列表</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <button type="button" class="btn btn-default btn-lg add">添加特殊节日</button>
                        <button type="button" class="btn btn-danger btn-lg delete">删除特殊节日</button>
                    </div>
                    <div class="alert alert-warning alert-dismissable hidden operatorTip">
                        <button type="button" class="close" data-dismiss="operatorTip"
                                aria-hidden="true">
                            &times;
                        </button>
                        <span></span>
                    </div>
                    <div class="panel-body">
                        <div class="dataTable_wrapper">
                            <table width="100%" class="table table-striped table-bordered table-hover"
                                   id="dataTables">
                                <thead>
                                <tr>
                                    <th><input type="checkbox" class="checkAll"/></th>
                                    <th>序列编号</th>
                                    <th>日期-月</th>
                                    <th>日期-日</th>
                                    <th>节日类型</th>
                                    <th>节日推送语</th>
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

    <!-- 模态框（Modal） -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-hidden="true">×
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
                        管理操作确认
                    </h4>
                </div>
                <div class="modal-body">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default"
                            data-dismiss="modal">取消
                    </button>
                    <button type="button" class="btn btn-primary confirm">
                        确定
                    </button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
    <!-- /.modal -->
</div>

<script>
    $(document).ready(function () {
        //定义农历月份名称
        var monthByNL = ["正月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "冬月", "腊月"];
        //定义农历天名称
        var dayByNL = ['初一', '初二', '初三', '初四', '初五', '初六', '初七', '初八', '初九', '初十', '十一', '十二', '十三', '十四', '十五', '十六', '十七', '十八', '十九', '二十', '廿一', '廿二', '廿三', '廿四', '廿五', '廿六', '廿七', '廿八', '廿九', '三十'];

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
                    url: "/sys_specialholiday/getlistpagespecial",
                    data: $.extend({}, pagination.data, pagination.page),
                    success: function (response) {
                        //设置分页
                        pagination.page.pageNo = response.data.pageNo;
                        pagination.page.pageCount = response.data.pageCount;
                        pagination.init();
                        if (response.data.totalCount > 0) {
                            var tableBody = "";
                            $(response.data.list).each(function () {

                                var subMessage = this.message;
                                if (subMessage != null && subMessage.length > 20) {
                                    subMessage = subMessage.substring(0, 20) + " ... ";
                                }

                                var dataMonthNL = this.dateMonth;
                                var dataDay = this.dateDay;
                                //如果当前类型是农历类型，则将月和日转换为对应文字
                                if (this.type == "2") {
                                    dataMonthNL = monthByNL[dataMonthNL - 1];
                                }
                                //如果当前类型是农历类型，则将月和日转换为对应文字
                                if (this.type == "2") {
                                    dataDay = dayByNL[dataDay - 1];
                                }

                                tableBody += '<tr>'
                                        + '<td><input type="checkbox" class="itemCheck" value="' + this.id + '"/></td>'
                                        + '<td>' + this.id + '</td>'
                                        + '<td>' + dataMonthNL + '</td>'
                                        + '<td>' + dataDay + '</td>'
                                        + '<td>' + (this.type == "1" ? "公历" : "农历") + '</td>'
                                        + '<td title="' + this.message + '">' + subMessage + '</td>'
                                        + '<td>'
                                        + '<button type="button" class="btn btn-outline btn-danger singleDelete">注销</button></td>'
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

        $(".delete").click(function () {
            //清空ids集合
            ids.length = 0;
            hideOperatorTip();
            //获取当前数据表中，被选中的数据
            $("input[type='checkbox']:checked").each(function () {
                if (!$(this).hasClass("checkAll")) {
                    ids.push($(this).val());
                }
            });

            if (ids.length == 0) {
                $(".operatorTip").removeClass("hidden").find("span").text('请选择需要注销的数据');
            } else {
                $('#myModal').modal();
                $(".modal-body").text("是否注销所选数据，被注销数据无法还原！");
            }
        });

        $(".operatorTip .close").click(function () {
            hideOperatorTip();
            return false;
        });

        var ids = new Array();
        $(".confirm").click(function () {
            $.ajax({
                type: "post",
                url: "/sys_specialholiday/specialdelete",
                traditional: true,
                data: {"id": ids},
                success: function (response) {
                    $("#myModal").modal('hide')
                    $(".operatorTip").removeClass("hidden").find("span").text(response.message);
                    if (response.code != "200") {
                        $("#myModal").modal('hide')
                        $(".operatorTip").removeClass("hidden").find("span").text(response.message);
                    } else {
                        pagination.page.pageNo = 1;
                        pagination.list();

                        //3秒后自动关闭警告框
                        setTimeout("hideOperatorTip()", 3000);
                    }
                }
            });
        });

        //单个数据内容删除
        $("#dataTables tbody").on("click", ".singleDelete", function () {
            $('#myModal').modal();
            $(".modal-body").text("是否注销所选数据，被注销数据无法还原！");

            //清空ids集合
            ids.length = 0;
            hideOperatorTip();
            ids.push($(this).parents("tr").find("input[type='checkbox']").val());
        })

        //添加角色
        $(".add").click(function () {
            window.location.href = "/system-manage/gotoPage?url=specialholiday/add";
        });
    });

    /**
     * 隐藏警告框
     */
    function hideOperatorTip() {
        $(".operatorTip").addClass("hidden")
    }
</script>

</body>
</html>
