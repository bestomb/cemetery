package com.bestomb.common.util;

import com.bestomb.common.constant.ExceptionMsgConstant;
import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.util.yamlMapper.ClientConf;
import com.pingplusplus.Pingpp;
import com.pingplusplus.model.Charge;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jason on 2016-10-28.
 */
public class PingppUtil {

    private static Logger logger = Logger.getLogger(PingppUtil.class);

    private static String rsaPrivateKey = "pingpp-rsa-private.pem";
    private static String publicKey = "pingpp-pubKey.pem";
    private static Map<String, Map<String, String>> payChannel = new HashMap<String, Map<String, String>>() {{
        //支付宝PC页面支付
        put("alipay_pc_direct", new HashMap<String, String>() {{
            put("success_url", "http://bestomb.eqianyuan.cn/pingpp/pay_success.jsp");
        }});
        //银联PC页面支付
        put("upacp_pc", new HashMap<String, String>() {{
            put("result_url", "http://bestomb.eqianyuan.cn/pingpp/pay_success.jsp");
        }});
        //银联全渠道手机网页支付
        put("upacp_wap", new HashMap<String, String>() {{
            put("result_url", "http://bestomb.eqianyuan.cn/pingpp/pay_success.jsp");
        }});
        //微信扫码支付
        put("wx_pub_qr", new HashMap<String, String>(){{
            put("product_id", "123456789");
        }});
    }};

    /**
     * 获取支付凭证
     *
     * @param amount   支付金额，单位（元）
     * @param channel  支付渠道
     * @param clientIp 客户端IP
     * @param orderNo  商户订单编号
     * @param subject  支付凭证主题
     * @param body     支付凭证内容
     * @return
     */
    public static String getCharge(int amount, String channel, String clientIp, String orderNo, String subject, String body) throws Exception {
        String apiKey = YamlForMapHandleUtil.getValueBykey(ClientConf.getMap(), ClientConf.Pingpp.pingpp.toString(), ClientConf.Pingpp.api_key.toString());
        String appId = YamlForMapHandleUtil.getValueBykey(ClientConf.getMap(), ClientConf.Pingpp.pingpp.toString(), ClientConf.Pingpp.app_id.toString());

        if (ObjectUtils.isEmpty(apiKey)) {
            logger.warn("没有在client-conf.yaml中找到ping++支付配置【api_key】");
            throw new EqianyuanException(ExceptionMsgConstant.GET_CONFIGURATION_ERROR);
        }

        if (ObjectUtils.isEmpty(appId)) {
            logger.warn("没有在client-conf.yaml中找到ping++支付配置【app_id】");
            throw new EqianyuanException(ExceptionMsgConstant.GET_CONFIGURATION_ERROR);
        }

        Pingpp.apiKey = apiKey;
        Pingpp.privateKeyPath = URLDecoder.decode(PingppUtil.class.getResource("/" + rsaPrivateKey).getPath(), "utf-8");

        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("order_no", orderNo);// 推荐使用 8-20 位，要求数字或字母，不允许其他字符
        chargeParams.put("amount", amount);
        Map<String, String> app = new HashMap<String, String>();
        app.put("id", appId);
        chargeParams.put("app", app);
        chargeParams.put("channel", channel);
        if (payChannel.get(channel) != null) {
            chargeParams.put("extra", payChannel.get(channel));
        }
        chargeParams.put("currency", "cny");
        chargeParams.put("client_ip", clientIp);
        chargeParams.put("subject", subject);
        chargeParams.put("body", body);
        return Charge.create(chargeParams).toString();
    }

    /**
     * 在线支付回调事件
     *
     * @param request
     * @throws Exception
     */
    public static String webHooksByChargeSucceeded(HttpServletRequest request) throws Exception {
        logger.debug("支付成功回调...");

        //获取ping++签名内容
        String signatureFormHeader = getSigntureForHeader(request);
        if (StringUtils.isEmpty(signatureFormHeader)) {
            logger.debug("验签结果：失败，因为request-header中不存在签名内容");
            return StringUtils.EMPTY;
        }

        //对签名进行Base64解密
        byte[] signatureBytes = Base64.decodeBase64(signatureFormHeader);

        //获取 Webhooks 请求的原始数据
        String webhooksRawPostData = getWebhooksRawData(request);
        if (StringUtils.isEmpty(webhooksRawPostData)) {
            logger.debug("验签结果：失败，因为request中不存在webhooks数据");
            return StringUtils.EMPTY;
        }

        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(getPubKey());
        signature.update(webhooksRawPostData.getBytes("UTF-8"));
        boolean result = signature.verify(signatureBytes);
        logger.debug("验签结果：" + (result ? "通过" : "失败"));

        if (result) {
            return webhooksRawPostData;
        }

        return StringUtils.EMPTY;
    }

    /**
     * 获取 Webhooks 请求的原始数据
     *
     * @param request
     * @return
     * @throws IOException
     */
    private static String getWebhooksRawData(HttpServletRequest request) throws IOException {
        request.setCharacterEncoding("UTF8");

        //获取请求原数据
        BufferedReader reader = request.getReader();
        StringBuilder webhooksRawPostData = new StringBuilder();
        String string;
        while ((string = reader.readLine()) != null) {
            webhooksRawPostData.append(string);
        }
        reader.close();
        return webhooksRawPostData.toString();
    }

    /**
     * 从request的头信息中获取签名字段内容
     *
     * @param request
     * @return
     */
    private static String getSigntureForHeader(HttpServletRequest request) {
        //获取头部所有信息
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            if (StringUtils.equals(key, "x-pingplusplus-signature")) {
                return request.getHeader(key);
            }
        }
        return StringUtils.EMPTY;
    }

    /**
     * 从文件中获取内容
     *
     * @param filePath
     * @return
     * @throws Exception
     */
    public static String getStringFromFile(String filePath) throws Exception {
        FileInputStream in = new FileInputStream(filePath);
        InputStreamReader inReader = new InputStreamReader(in, "UTF-8");
        BufferedReader bf = new BufferedReader(inReader);
        StringBuilder sb = new StringBuilder();
        String line;
        do {
            line = bf.readLine();
            if (line != null) {
                if (sb.length() != 0) {
                    sb.append("\n");
                }
                sb.append(line);
            }
        } while (line != null);

        return sb.toString();
    }

    /**
     * 获得公钥
     *
     * @return
     * @throws Exception
     */
    public static PublicKey getPubKey() throws Exception {
        String pubKeyString = getStringFromFile(URLDecoder.decode(PingppUtil.class.getResource("/" + publicKey).getPath(), "utf-8"));
        pubKeyString = pubKeyString.replaceAll("(-+BEGIN PUBLIC KEY-+\\r?\\n|-+END PUBLIC KEY-+\\r?\\n?)", "");
        byte[] keyBytes = Base64.decodeBase64(pubKeyString);

        // generate public key
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(spec);
        return publicKey;
    }

}
