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
                <h1 class="page-header">系统用户列表</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <button type="button" class="btn btn-default btn-lg add">添加管理用户</button>
                        <button type="button" class="btn btn-danger btn-lg delete">删除管理用户</button>
                    </div>
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
                                <label>角色</label>
                                <select class="form-control" id="search_by_role"></select>
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
                                    <th><input type="checkbox" class="checkAll"/></th>
                                    <th>序列编号</th>
                                    <th>用户名</th>
                                    <th>真实姓名</th>
                                    <th>手机号码</th>
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
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
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
                    url: "/system_user/paginationList",
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
                                        + '<td><input type="checkbox" class="itemCheck" value="' + this.id + '"/></td>'
                                        + '<td>' + this.id + '</td>'
                                        + '<td>' + this.loginName + '</td>'
                                        + '<td>' + this.realName + '</td>'
                                        + '<td>' + this.mobile + '</td>'
                                        + '<td>' + this.createTimeForStr + '</td>'
                                        + '<td>'
                                        + '<button type="button" class="btn btn-outline btn-info detail">详情</button>&nbsp;'
                                        + '<button type="button" class="btn btn-outline btn-warning update">修改</button>&nbsp;'
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

        //获取系统角色数据
        getSystemRoleList();

        //获取列表数据
        pagination.list();

        $("#search").click(function(){
            pagination.data.roleId = $("#search_by_role").val();
            pagination.page.pageNo = 1;
            pagination.list();
        });

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
                url: "/system_user/delete",
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

        //数据修改
        $("#dataTables tbody").on("click", ".update", function () {
            window.location.href = "/system-manage/gotoPage?url=system user/update&id=" + $(this).parents("tr").find("input[type='checkbox']").val();
        })

        //数据详细信息
        $("#dataTables tbody").on("click", ".detail", function () {
            window.location.href = "/system-manage/gotoPage?url=system user/detail&id=" + $(this).parents("tr").find("input[type='checkbox']").val();
        })

        //注册上位机
        $(".add").click(function () {
            window.location.href = "/system-manage/gotoPage?url=system user/add";
        });
    });

    /**
     * 隐藏警告框
     */
    function hideOperatorTip() {
        $(".operatorTip").addClass("hidden")
    }

    /**
     * 获取系统角色集合
     */
    function getSystemRoleList() {
        $.ajax({
            type: "POST",
            url: "/system_role/list",
            data: null,
            success: function (response) {
                var search_by_role = "<option value=''>-- 请选择角色 --</option>";
                if (response.code == "200"
                        && response.data.length > 0) {
                    $(response.data).each(function () {
                        search_by_role += '<option value="' + this.id + '">' + this.name + '</option>';
                    });
                }

                $("#search_by_role").html(search_by_role);
            }
        });
    }
</script>

</body>

</html>
