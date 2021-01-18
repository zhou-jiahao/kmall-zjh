package com.kgc.kmall.payment.config;

public class AlipayConfig {
    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号，此处是沙箱环境的appid
    public static String app_id = "2016102200738541";

    // 商户私钥，您的PKCS8格式RSA2私钥，需要使用支付宝平台开发助手生成私钥和公钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCS2gz767VN86JQg+wEwp7Cnav6HAcEy5Ju1eYYqrSmjX70OthIpKv3SAasA7tHbmDFEgCGdGfvH8aufhaOSqRSbwXUomB/qriRf/68eueaiGRI4FY1jMSFC6psvUChxXe5VOCi3tf4ollEBAnhhnCnHv0RbIAZBGJKT/NlFTviMTts8CL2jfQOUfB3hc+R/dD3/IMQR9utqavPxp7IYXVsmcw8uPZoOIx7/DX6hv/tT2aqWCqxFKr7eEGjy27m0EKLL2bNPS8yi+bDR0olipCIWgo3BxUQuWK9q5ZiSfXsComk3rSDyVnLyJW3btg2uny4Um+IhfKTbEEU6RWuEJX1AgMBAAECggEABGl6tGLripEEUmj0QVxbirtT/Gq1J0a6eowAosMWNF7R7qKJ9vzDkeVfFfHywX2meKVG0MNUkBGjVYDCSrohX7yoLzBFt/A4JV70F1OV+8WXRb0/udIl4V1tXGD/PR0CsNXE/f+1+2ldRzbb2SPB5LOApPJekuHX1iW6yVGu/+ew1IybUuyxcjKhbGM2G6nxdUu7gXqEijFz5+FF5Kvs+D8VzBkkcguOl90iDKpcZeAwwv/58gyew/iCej41apu3Cz3XzDLhS1cyRiwUkAtBpKnjCvJMfz0QtS7Y0t81TrdyDtuQT+l8cCkAjBh9prFeHvEwD9+faeBugOTHNnAwLQKBgQDxJbFpnJF+4vCGnuKgRtuZW2OrI3xUX8NDGoft8LJi1AqgZjUJBrAUeHk09o0spB87ycmP0uCmPMpV+cpkm7T94RZR1k78saBufOB+uKZ0nASLVNDZJdXJ61q8E+Pn6q2FWsRUI3Ijjr1SrA8/PYT86dV4VdFbWgBGoQCeEDM1fwKBgQCb5Yv4NbWuY+1b7qhSJ19wRGwDlSllqv3tzOJo0j8tGL9FbVdC6Bk5CBkNhfAQ4eEqVRWyqE2n1k5uXI5H8cTacpifKlQQq8LTPN1F4jA1aqPNkISTWRGhHDjt2Fc1q3SULUnwoaJx9iDh6Qthbt7NpTuyisRJBS0AZCO9iWZ2iwKBgEPTRzgeOmoG6IHktHdCFsfmhX0AKRsOKDx6fNr2xSW44BhHRwl34RSQ4f58ji6NJt/JHLEgLeFz3bEa5cswZFK3+XAhJ+w5BZet9fKOevr3WuvZejhpRN1bHhUiPwre3dlRM0E7CwJnRma3UD6IThxYpJsJAC6CfOtgo2zRv6V/AoGAL7VX2GXXa7747KFksX+LHXhS9eW3/X0OqK+CukFdRBfGsSB9C+Srk2B/Cjxq8z+tAJ95PGt1GF/lMLDyeeHrNE7NligGAfQOc0a1EKXfyvkPl+EaMV2Yn41bfq4uQiZuTsptTk6ov+dquLfw26hqVtSyKDl/SDllRDFhHqfly3ECgYEA7taqhmVCLu9sVeYB9sCN61A00edN5OAEHE0D+eDm/fJCKpfunPpQccCg2sHxlyaEifkerCOrnE/GmCrpnh4Yq/U/dh2SDIbDgIOgMGPVFH0RMGDi9jzqX/Q6YEnFzetZowDSNzOOdgqk9ghhg/eh0T1QityBcY59rTuPLYQg5i8=";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzs+Pvt3QQls5sJSAdt9ME2Zj1MqTtqfOg7TrGT1Sqtha3+wJxEEuou9gkGtfdPtLfGbLGwsGlsKjIbaWjCz0r1iT5BUxSSGK904YdEduZR6trIDXo2ALacLlzlNd8dZos5lZV20TtsJLQ1LHxnbCwCM8mBtvd4kgnG7y1SHhvl1yikSZ0oCMZbQQOhFJroeYn4TuprkxxXxftqd/L7egDozRsNSzIL5BFujw3ATWLovraFh63sASaOek71aSm4MHIyQgPQWQTWpfnegpNNboLXa5oLARfXd2lGayPldMVuYrz/hQSEhrOjRce8neczgzfd0g0UM94HbKnLi11CkMJQIDAQAB";
    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url ="http://60.205.215.91/alipay/callback/notify";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://payment.kmall.com:8088/alipay/callback/return";


    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝沙箱网关
    public static String gatewayUrl ="https://openapi.alipaydev.com/gateway.do";
}
