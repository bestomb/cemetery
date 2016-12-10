<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>在线充值</title>
    <link rel="stylesheet" href="http://apps.bdimg.com/libs/bootstrap/3.3.0/css/bootstrap.min.css">
    <script src="js/pingpp.js"></script>
    <script src="js/client-pingpp.js"></script>
    <script src="/js/jquery-1.10.1.min.js"></script>

    <script>
        $(function () {
            var codeImgUrl = getCharge("${param.amount}", "${param.channel}", "${param.jsessionId}");
            $("div img").attr("src", codeImgUrl);
        });
    </script>
</head>

<body>
<div style="width: 400px;height: 400px;margin: auto;">
    <img width="100%" height="100%"/>
    <span style="padding: 34%;font-size: 20px;font-weight: bold;">手机扫码支付</span>
</div>
</body>

</html>
