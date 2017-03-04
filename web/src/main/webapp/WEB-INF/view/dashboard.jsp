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
    <script language="javascript" type="text/javascript" src="/js/my97/WdatePicker.js"></script>
</head>

<body>

<div id="wrapper">
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">陵园数据统计</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="row">
                            <div class="col-lg-3">
                                <div class="form-group">
                                    <label>开始时间</label>
                                    <input class="form-control" readonly id="begin" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'end\',{d:-3});}'})" value="${beginTime}"/>
                                </div>
                                <div class="form-group">
                                    <label>结束时间</label>
                                    <input class="form-control" readonly id="end" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'begin\',{d:3});}'})" value="${endTime}"/>
                                </div>
                                <div class="form-group">
                                    <button type="button" class="btn btn-primary btn-lg btn-block" id="search">统计查询</button>
                                </div>
                            </div>
                            <div class="col-lg-6">
                                <form role="form">
                                    <div class="form-group">
                                        <label>陵园总数</label>
                                        <input class="form-control" disabled value="${cemetery}">
                                    </div>
                                    <div class="form-group">
                                        <label>墓碑总数</label>
                                        <input class="form-control" disabled value="${tombstone}">
                                    </div>
                                    <div class="form-group">
                                        <label>纪念人总数</label>
                                        <input class="form-control" disabled value="${master}">
                                    </div>
                                    <div class="form-group">
                                        <label>参祭次数</label>
                                        <input class="form-control" disabled value="${action}">
                                    </div>
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
    $(function(){
        $("#search").click(function(){
            document.location.href = "/dashboard/getStatistics?beginTime="+$("#begin").val()+"&endTime="+$("#end").val();
        });
    })

</script>
</body>

</html>
