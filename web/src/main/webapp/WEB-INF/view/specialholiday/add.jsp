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
                <h1 class="page-header">添加特殊节日</h1>
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
                                        <label>节日类型</label>

                                        <div class="row">
                                            <div class="col-xs-2">
                                                <input type="radio" name="type" value="1" checked> 公历
                                            </div>
                                            <div class="col-xs-2">
                                                <input type="radio" name="type" value="2"> 农历
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label>月</label>
                                        <select class="form-control" name="dateMonth">
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label>日</label>
                                        <select class="form-control" name="dateDay">
                                        </select>
                                    </div>

                                    <div class="form-group">
                                        <label>节日推送语</label>
                                        <textarea class="form-control" rows="3" name="message"></textarea>
                                    </div>

                                    <input type="button" class="btn btn-outline btn-success submit" value="保存">
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
        var setting = {
            check: {
                enable: true
            },
            data: {
                simpleData: {
                    enable: true,
                    pIdKey: "parentId",
                    rootPid: 0,
                }
            }
        };

        //获取菜单数据集合
        $.ajax({
            type: "post",
            url: "/system_menu/list",
            traditional: true,
            data: null,
            dataType: "json",
            success: function (response) {
                if (response.code == "200" && response.data.length > 0) {
                    menuTree = $.fn.zTree.init($("#menuTree"), setting, response.data);
                    menuTree.expandAll(true);
                }
            }
        });

        //返回
        $(".back").click(function () {
            window.history.go(-1);
        });

        //提交保存
        $(".submit").click(function () {
            $(this).attr('disabled', "true")

            //获取所有被选中的菜单
            var checkedNodes = menuTree.getCheckedNodes(true);
            var menuIds = new Array();
            if (checkedNodes.length > 0) {
                $(checkedNodes).each(function () {
                    menuIds.push(this.id);
                });
            }

            //异步提交表单
            $.ajax({
                type: "POST",
                url: "/sys_specialholiday/specialadd",
                data: $('form').serialize() + "&menuId=" + menuIds.join(","),
                dataType: "json",
                success: function (response) {
                    if (response.code == "200") {
                        $("#form-tip").removeClass("hidden alert-warning").addClass("alert-success").show().find("strong").text(response.message);

                        setTimeout(function () {
                            window.location.href = "/system-manage/gotoPage?url=specialholiday/list";
                        }, 500);
                    } else {
                        $("#form-tip").removeClass("hidden alert-success").addClass("alert-warning").show().find("strong").text(response.message);
                        $(".submit").removeAttr("disabled");
                    }
                }
            });
        });

        //定义公历月：分别为31天的月/30天的月/28,29的月
        var tThirtyOne = [1, 3, 5, 7, 8, 10, 12];
        var thirty = [4, 6, 9, 11];

        //定义公历月份名称
        var monthByGL = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"];
        //定义农历月份名称
        var monthByNL = ["正月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "冬月", "腊月"];
        //定义农历天名称
        var dayByNL = ['初一', '初二', '初三', '初四', '初五', '初六', '初七', '初八', '初九', '初十', '十一', '十二', '十三', '十四', '十五', '十六', '十七', '十八', '十九', '二十', '廿一', '廿二', '廿三', '廿四', '廿五', '廿六', '廿七', '廿八', '廿九', '三十'];

        $("input[name='type']").click(function () {
            var month;
            if ($(this).val() == "1") {
                month = monthByGL;
                //初始加载天数下拉选项
                setDayOptionByGL(31);
            } else {
                month = monthByNL;
                setDayOptionByNL(dayByNL);
            }
            setMonthOption(month);
        });

        //初始加载月份下拉选项
        setMonthOption(monthByGL);
        //初始加载天数下拉选项
        setDayOptionByGL(31);

        //月份选项值切换事件
        $("select[name='dateMonth']").change(function () {
            if ($("input[name='type']:checked").val() == "1") {
                var day;
                if (tThirtyOne.indexOf(parseInt($(this).val())) != -1) {
                    day = 31;
                } else if (thirty.indexOf(parseInt($(this).val())) != -1) {
                    day = 30;
                } else {
                    day = 29;
                }
                setDayOptionByGL(day);
            } else {
                setDayOptionByNL(dayByNL);
            }
        });
    });

    /**
     *   设置日下拉框选择项--公历
     * @param day
     */
    function setDayOptionByGL(day) {
        var option;
        for (var i = 1; i <= day; i++) {
            option += '<option value="' + i + '">' + i + '</option>';
        }
        $("select[name='dateDay']").html(option);
    }

    /**
     *   设置日下拉框选择项 -- 农历
     * @param day
     */
    function setDayOptionByNL(day) {
        var option;
        $(day).each(function (i) {
            option += '<option value="' + (i + 1) + '">' + this + '</option>';
        });
        $("select[name='dateDay']").html(option);
    }

    /**
     *   设置月份下拉框选择项
     * @param monthArray
     */
    function setMonthOption(monthArray) {
        var option;
        $(monthArray).each(function (i) {
            option += '<option value="' + (i + 1) + '">' + this + '</option>';
        });
        $("select[name='dateMonth']").html(option);
    }

</script>

</body>
</html>
