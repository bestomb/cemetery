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
    <link rel="stylesheet" href="/css/metroStyle/metroStyle.css" type="text/css">
    <script src="/js/jquery-1.10.1.min.js"></script>
    <script src="/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/common/eqianyuan.page.js"></script>
    <script type="text/javascript" src="/js/common/common_utils.js"></script>
    <script type="text/javascript" src="/js/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript" src="/js/jquery.ztree.exedit.js"></script>
</head>

<body>
<div id="wrapper">
    <!-- /.row -->
    <div class="row">
            <div class="col-lg-4">
                <div class="controls">
                    <ul id="modelClassifyTree" class="ztree span6 m-wrap"
                        style="border: 1px solid;overflow-y: scroll;overflow-x: auto;"></ul>
                </div>
            </div>
            <div class="col-lg-8">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="dataTable_wrapper">
                            <table width="100%" class="table table-striped table-bordered table-hover"
                                   id="dataTables">
                                <thead>
                                <tr>
                                    <th></th>
                                    <th>模型名称</th>
                                    <th>模型地址</th>
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
<script>
    var pagination ;
    $(document).ready(function () {
        //首次根据根目录编号查出一级分类
        getModelClassifyListByParent(0);

        pagination = {
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
                    url: "/model/paginationList",
                    data: $.extend({}, pagination.data, pagination.page),
                    success: function (response) {
                        //设置分页
                        pagination.page.pageNo = response.data.pageNo;
                        pagination.page.pageCount = response.data.pageCount;
                        pagination.init();
                        if (response.data.totalCount > 0) {
                            var tableBody = "";
                            $(response.data.list).each(function () {
                                var subFileAddress = this.fileAddress;
                                if (this.fileAddress != null && this.fileAddress.length > 30) {
                                    subFileAddress = this.fileAddress.substring(0, 30) + " ... ";
                                }
                                tableBody += '<tr>'
                                        + '<td><input type="radio" class="itemCheck" value="' + this.id + '" name="modelId"/></td>'
                                        + '<td>' + this.name + '</td>'
                                        + '<td title="'+this.fileAddress+'">' + subFileAddress + '</td>'
                                        + '</tr>';
                            });

                            $("#dataTables tbody").html(tableBody);
                        } else {
                            $("#dataTables tbody").html('<tr class="text-center"><td colspan="4">查无数据</td></tr>');
                        }
                    }
                });
            }
        };

        //获取列表数据
        pagination.list();

        //模型数据单选点击事件
        $(document).on("click", "input[name='modelId']", function () {
            window.opener.document.getElementById("extend_model_id").value = $(this).val();
            window.close();
        });
    });

    //模型分类树状图setting
    var setting = {
        data: {
            simpleData: {
                enable: true,
            }
        },
        async: {
            enable: true,
            url: "/model_classify/getLevelOneListByParentId",
            autoParam: ["id=parentId"],
            dataFilter: ajaxDataFilter
        },
        view: {
            selectedMulti: false
        },
        callback: {
            beforeDrag: beforeDrag,
            onClick: zTreeOnClick
        }
    };

    /**
     * 根据父分类编号获取模型分类集合
     */
    function getModelClassifyListByParent(parentId) {
        //获取菜单数据集合
        $.ajax({
            type: "post",
            url: "/model_classify/getLevelOneListByParentId",
            data: {"parentId": parentId},
            success: function (response) {
                if (response.code == "200" && response.data.length > 0) {
                    $(response.data).each(function () {
                        this.isParent = true;
                    });

                    modelClassifyTree = $.fn.zTree.init($("#modelClassifyTree"), setting, response.data);
                } else {
                    $("#modelClassifyTree").text(response.message);
                }
            }
        });
    }

    /**
     * 异步加载时数据过滤
     * @param treeId
     * @param parentNode
     * @param responseData
     * @returns {*}
     */
    function ajaxDataFilter(treeId, parentNode, responseData) {
        return responseData.data;
    }

    /**
     * 禁止拖动
     * @param treeId
     * @param treeNodes
     * @returns {boolean}
     */
    function beforeDrag(treeId, treeNodes) {
        return false;
    }

    /**
     * 节点点击事件，用于获取节点信息，查询模型数据
     * @param event
     * @param treeId
     * @param treeNode
     */
    function zTreeOnClick(event, treeId, treeNode) {
        pagination.page.pageNo = 1;
        pagination.data.classifyId = treeNode.id;
        pagination.list();
    }
</script>
</body>

</html>
