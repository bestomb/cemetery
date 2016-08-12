
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
  <div id="page-wrapper">
    <div class="row">
      <div class="col-lg-12">
        <h1 class="page-header">商品列表</h1>
      </div>
      <!-- /.col-lg-12 -->
    </div>
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
          <%--<div class="panel-heading">--%>
            <%--<button type="button" class="btn btn-default btn-lg add">1</button>--%>
            <%--<button type="button" class="btn btn-danger btn-lg delete">2</button>--%>
          <%--</div>--%>
          <%--<div class="alert alert-warning alert-dismissable hidden operatorTip">--%>
            <%--<button type="button" class="close" data-dismiss="operatorTip"--%>
                    <%--aria-hidden="true">--%>
              <%--&times;--%>
            <%--</button>--%>
            <%--<span></span>--%>
          <%--</div>--%>
          <%--<div class="panel-body">--%>
            <%--<div class="dataTable_wrapper">--%>
              <%--<table width="100%" class="table table-striped table-bordered table-hover"--%>
                     <%--id="dataTables">--%>
                <%--<thead>--%>
                <%--<tr>--%>
                  <%--<th><input type="checkbox" class="checkAll"/></th>--%>
                  <%--<th>模型名称</th>--%>
                  <%--<th>模型地址</th>--%>
                  <%--<th>操作</th>--%>
                <%--</tr>--%>
                <%--</thead>--%>
                <%--<tbody></tbody>--%>
              <%--</table>--%>
            <%--</div>--%>
          <%--</div>--%>
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

  <!-- 模型分类模态框（Modal） -->
  <div class="modal fade" id="modelClassify" tabindex="-1" role="dialog"
       aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal"
                  aria-hidden="true">×
          </button>
          <h4 class="modal-title">
            模型分类操作确认
          </h4>
        </div>
        <div class="modal-body">
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default"
                  data-dismiss="modal">确认
          </button>
        </div>
      </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
  </div><!-- /.modal -->
</div>

<script>
  var pagination ;
//  $(document).ready(function () {
    //首次根据根目录编号查出一级分类
   getModelClassifyListByParent(0);
//
////    pagination = {
////      initStatus: false,      //分页插件初始化状态-true:已经构建了分页插件、false:还未构建
////      data: {},
////      page: {
////        pageNo: 1,
////        pageSize: 10
////      },
////      init: function () {
////        //初始化分页
////        $(".pagination").createPage({
////          pageCount: pagination.page.pageCount,
////          current: pagination.page.pageNo,
////          initStatus: pagination.initStatus,
////          backFn: function (pageNo) {
////            pagination.page.pageNo = pageNo;
////            pagination.list();
////          }
////        });
////
////        pagination.initStatus = true;
////      },
////      list: function () {
////        $("#dataTables tbody").html("");
////        $(".checkAll").prop("checked", false);
////
////        $.ajax({
////          type: "POST",
////          url: "/model/paginationList",
////          data: $.extend({}, pagination.data, pagination.page),
////          success: function (response) {
////            //设置分页
////            pagination.page.pageNo = response.data.pageNo;
////            pagination.page.pageCount = response.data.pageCount;
////            pagination.init();
////            if (response.data.totalCount > 0) {
////              var tableBody = "";
////              $(response.data.list).each(function () {
////                var subFileAddress = this.fileAddress;
////                if (this.fileAddress != null && this.fileAddress.length > 30) {
////                  subFileAddress = this.fileAddress.substring(0, 30) + " ... ";
////                }
////                tableBody += '<tr>'
////                        + '<td><input type="checkbox" class="itemCheck" value="' + this.id + '"/></td>'
////                        + '<td>' + this.name + '</td>'
////                        + '<td title="'+this.fileAddress+'">' + subFileAddress + '</td>'
////                        + '<td>'
////                        + '<button type="button" class="btn btn-outline btn-info detail">详情</button>&nbsp;'
////                        + '<button type="button" class="btn btn-outline btn-warning update">修改</button>&nbsp;'
////                        + '<button type="button" class="btn btn-outline btn-danger singleDelete">注销</button></td>'
////                        + '</tr>';
////              });
////
////              $("#dataTables tbody").html(tableBody);
////            } else {
////              $("#dataTables tbody").html('<tr class="text-center"><td colspan="5">查无数据</td></tr>');
////            }
////          }
////        });
////      }
////    };
//
//    //获取列表数据
//    pagination.list();
//
//    //添加模型
//    $(".add").click(function () {
//      //判断模型分类节点有没有被选中
//      var nodes = modelClassifyTree.getSelectedNodes();
//      if (nodes == null
//              || nodes.length <= 0) {
//        $('#modelClassify').modal();
//        $("#modelClassify .modal-body").text("请先选择左侧任一分类");
//        return false;
//      }
//      window.location.href = "/system-manage/gotoPage?url=model/add&classifyId=" + nodes[0].id + "&classifyName=" + nodes[0].name;
//    });
//
//    var ids = new Array();
//    $(".delete").click(function () {
//      //清空ids集合
//      ids.length = 0;
//      hideOperatorTip();
//      //获取当前数据表中，被选中的数据
//      $("#dataTables input[type='checkbox']:checked").each(function () {
//        if (!$(this).hasClass("checkAll")) {
//          ids.push($(this).val());
//        }
//      });
//
//      if (ids.length == 0) {
//        $(".operatorTip").removeClass("hidden").find("span").text('请选择需要注销的数据');
//      } else {
//        $('#myModal').modal();
//        $(".modal-body").text("是否注销所选数据，被注销数据无法还原！");
//      }
//    });
//
//    //单个数据内容删除
//    $("#dataTables tbody").on("click", ".singleDelete", function () {
//      $('#myModal').modal();
//      $(".modal-body").text("是否注销所选数据，被注销数据无法还原！");
//
//      //清空ids集合
//      ids.length = 0;
//      hideOperatorTip();
//      ids.push($(this).parents("tr").find("input[type='checkbox']").val());
//    })
//
//    $(".confirm").click(function () {
//      $.ajax({
//        type: "post",
//        url: "/model/delete",
//        traditional: true,
//        data: {"id": ids},
//        success: function (response) {
//          $("#myModal").modal('hide')
//          $(".operatorTip").removeClass("hidden").find("span").text(response.message);
//          if (response.code != "200") {
//            $("#myModal").modal('hide')
//            $(".operatorTip").removeClass("hidden").find("span").text(response.message);
//          } else {
//            pagination.page.pageNo = 1;
//            pagination.list();
//
//            //3秒后自动关闭警告框
//            setTimeout("hideOperatorTip()", 3000);
//          }
//        }
//      });
//    });
//
//    //数据修改
//    $("#dataTables tbody").on("click", ".update", function () {
//      window.location.href = "/system-manage/gotoPage?url=model/update&id=" + $(this).parents("tr").find("input[type='checkbox']").val();
//    })
//
//    //数据详细信息
//    $("#dataTables tbody").on("click", ".detail", function () {
//      window.location.href = "/system-manage/gotoPage?url=model/detail&id=" + $(this).parents("tr").find("input[type='checkbox']").val();
//    })
//  });

  //模型分类树状图setting
  var setting = {
    data: {
      simpleData: {
        enable: true,
      }
    },
    async: {
      enable: true,
      url: "/sys_goodsclassify/getleveParentid",
      autoParam: ["id=parentId"],
      dataFilter: ajaxDataFilter
    },
    edit: {
      enable: true,
      renameTitle: "修改商品分类",
      removeTitle: "删除商品分类",
      editNameSelectAll: true,
    },
    view: {
      addHoverDom: addHoverDom,
      removeHoverDom: removeHoverDom,
      selectedMulti: false
    },
    callback: {
      beforeDrag: beforeDrag,
      beforeRemove: beforeRemove,
      beforeRename: zTreeBeforeRename,
      onRename: zTreeOnRename,
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
      url: "/sys_goodsclassify/getleveParentid",
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
   * 当鼠标悬停节点，添加节点操作按钮
   * @param treeId
   * @param treeNode
   */
  function addHoverDom(treeId, treeNode) {
    var sObj = $("#" + treeNode.tId + "_span");
    if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;

    var addStr = "";
    //一级菜单才可以有添加功能按钮
    if (treeNode.parentId == "0") {
      addStr = "<span class='button add' id='addBtn_" + treeNode.tId
              + "' title='添加商品分类' onfocus='this.blur();'></span>";
    }

    sObj.after(addStr);

    var btn = $("#addBtn_" + treeNode.tId);
    if (btn) btn.bind("click", function () {
      var nodeInfo = {"name": "新商品分类", "parentId": treeNode.id};
      //异步添加子节点数据
      $.ajax({
        type: "post",
        url: "/sys_goodsclassify/add",
        data: nodeInfo,
        success: function (response) {
          if (response.code == "200") {
            nodeInfo.id = response.data.id;
            modelClassifyTree.addNodes(treeNode, nodeInfo);
          } else {
            $('#modelClassify').modal();
            $("#modelClassify .modal-body").text(response.message);
          }
        }
      });
    });
  }

  function removeHoverDom(treeId, treeNode) {
    $("#addBtn_" + treeNode.tId).unbind().remove();
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
   * 节点删除
   * @param treeId
   * @param treeNodes
   * @returns {boolean}
   */
  function beforeRemove(treeId, treeNode) {
    //如果节点禁止编辑，则不让删除
    if (treeNode.canEdit == 1) {
      return false;
    }

    var returnFlag = true;
    $.ajax({
      type: "post",
      url: "/sys_goodsclassify/delete",
      data: {"id": treeNode.id},
      async: false,
      success: function (response) {
        if (response.code != "200") {
          returnFlag = false;
          $('#modelClassify').modal();
          $("#modelClassify .modal-body").text(response.message);
        }
      }
    });

    return returnFlag;
  }

  //节点重命名预处理，用于记录重命名前的名称title，便于重命名失败后，回滚显示名称
  function zTreeBeforeRename(treeId, treeNode, newName) {
    oldName = treeNode.name;
    return true;
  }
  /**
   * 重命名
   * @param event
   * @param treeId
   * @param treeNode
   */
  function zTreeOnRename(event, treeId, treeNode) {
    $.ajax({
      type: "post",
      url: "/sys_goodsclassify/update",
      data: {"id": treeNode.id, "name": treeNode.name, "parentId": treeNode.parentId},
      success: function (response) {
        if (response.code != "200") {
          treeNode.name = oldName;
          modelClassifyTree.updateNode(treeNode);
          $('#modelClassify').modal();
          $("#modelClassify .modal-body").text(response.message);
        }
      }
    });
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

  /**
   * 隐藏警告框
   */
  function hideOperatorTip() {
    $(".operatorTip").addClass("hidden")
  }
</script>

</body>
</html>
