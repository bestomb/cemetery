/**
 * 获取支付渠道支付
 * @param amount    支付金额（单位：元）
 * @param channel   支付渠道
 * @param jsessionId  登录session
 */
function getCharge(amount, channel, jsessionId) {
    var data = {"amount": amount, "channel": channel, "jsessionId": jsessionId};

    var payUrl ;
    $.ajax({
        type: "POST",
        url: "/pingpp_api/getCharge",
        async: false,
        data: data,
        success: function (resp) {
            var respJSON = $.parseJSON(resp);
            //如果是扫码支付，则提取支付URL并生成二维码返回
            if (respJSON.channel == "wx_pub_qr") {
                //提取支付URL
                var wx_pub_qr_payUrl = respJSON.credential.wx_pub_qr;
                payUrl = "http://pan.baidu.com/share/qrcode?w=300&h=300&url=" + wx_pub_qr_payUrl;
            } else {
                pingpp.createPayment(resp, function (result, err) {
                });
            }
        }
    });

    return payUrl;
}