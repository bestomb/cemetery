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
                <h1 class="page-header">公众人物管理列表</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">

                    <div class="panel-heading">
                        <div class="panel-body panel-search">
                            <form class="form-inline">
                                <div class="form-group">
                                    <label>审核结果</label>
                                    <select class="form-control" id="search_by_role">
                                        <option value="">---请选择---</option>
                                        <option value="1">同意</option>
                                        <option value="2">驳回</option>
                                        <option value="3">待审核</option>
                                    </select>
                                </div>
                                <button type="button" class="btn btn-default btn-sm" id="search">筛选查找</button>
                            </form>
                        </div>
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
                            <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables">
                                <thead>
                                <tr>
                                    <th>序列编号</th>
                                    <th>会员昵称</th>
                                    <%--<th>申请描述</th>--%>
                                    <th>审核意见</th>
                                    <th>审核结果</th>
                                    <th>管理员信息</th>
                                    <th>创建时间</th>
                                    <th>处理时间</th>
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
                    url: "/sys_publfigures/publifigureslist",
                    data: $.extend({}, pagination.data, pagination.page),
                    success: function (response) {
                        //设置分页
                        pagination.page.pageNo = response.data.pageNo;
                        pagination.page.pageCount = response.data.pageCount;
                        pagination.init();
                        if (response.data.totalCount > 0) {
                            var tableBody = "";
                            $(response.data.list).each(function () {

                                var subAuditOpinion = this.auditOpinion;
                                if (this.auditOpinion != null && this.auditOpinion.length > 30) {
                                    subAuditOpinion = this.auditOpinion.substring(0, 30) + " ... ";
                                }

                                tableBody += '<tr id="' + this.id + '">'
                                        + '<td>' + this.id + '</td>'
                                        + '<td>' + this.memberId + '</td>'
//                                        + '<td title="' + this.bewrite + '">' + subBewrite + '</td>'
                                        + '<td title="' + this.auditOpinion + '">' + subAuditOpinion + '</td>'
                                        + '<td>' + (this.status == 3 ? "待审核" : this.status == 1 ? "同意" : "驳回") + '</td>'
                                        + '<td>' + this.systemUser + '</td>'
                                        + '<td>' + this.createTime + '</td>'
                                        + '<td>' + this.disposeTime + '</td>'
                                        + '<td>'
                                        + '<button type="button" class="btn btn-outline btn-info detail">详情</button>&nbsp;'
//                                        + (this.status == 3 ? '<button type="button" class="btn btn-outline btn-danger" onclick="javascript:update(' + this.id + ');">同意</button>&nbsp;' : '')
//                                        + (this.status == 3 ? '<button type="button" class="btn btn-outline btn-danger" onclick="javascript:update1(' + this.id + ');">驳回</button></td>' : '')
                                        + '</tr>';
                            });
                            $("#dataTables tbody").html(tableBody);
                        } else {
                            $("#dataTables tbody").html('<tr class="text-center"><td colspan="8">查无数据</td></tr>');
                        }
                    }
                });
            }
        };

        //获取列表数据
        pagination.list();

        $("#search").click(function(){
            pagination.data.status = $("#search_by_role").val();
            pagination.page.pageNo = 1;
            pagination.list();
        });

        //数据详细信息
        $("#dataTables tbody").on("click", ".detail", function () {
            window.location.href = "/system-manage/gotoPage?url=publicFigure/detail&id=" + $(this).parents("tr").attr("id");
        })
    })


//        //同意 将状态修改为1
//        function update(id) {
//            $.ajax({
//                type: "post",
//                url: "/sys_publfigures/modify",
//                traditional: true,
//                data: {"id": id, "status": 1},
//                success: function (response) {
//                    var tongyi = ["同意"];
//                    var status;
//                    this.status == "1"
//                    status = tongyi;
//                    setMonthOption(status);
//                }
//            });
//            setTimeout(function () {
//                window.location.href = "/system-manage/gotoPage?url=publicFigure/list";
//            }, 200);
//        }
    //
    //    //不同意 将状态修改为2
    //    function update1(id) {
    //        $.ajax({
    //            type: "post",
    //            url: "/sys_publfigures/modify",
    //            traditional: true,
    //            data: {"id": id, "status": 2},
    //            success: function (response) {
    //                var butongyi = ["驳回"];
    //                var status;
    //                this.status == "2"
    //                status = butongyi;
    //                setMonthOption(status);
    //            }
    //        });
    //        setTimeout(function () {
    //            window.location.href = "/system-manage/gotoPage?url=publicFigure/list";
    //        }, 200);
    //    }

    /**
     * 隐藏警告框
     */
    function hideOperatorTip() {
        $(".operatorTip").addClass("hidden")
    }
</script>

</body>
</html>
