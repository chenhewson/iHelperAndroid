package hewson.logindemo2.common;

public class Const {
    //本地ip
    public static final String IpAddress="http://192.168.0.5:8080/";

    //阿里云
//    public static final String IpAddress="http://101.132.143.174:8080/";

    public static double LONGITUDE = 118.085730;//经度,写死，厦门理工，如果定位功能没问题，会自动修改这个值。
    public static double LATITUDE = 24.623940;//纬度

    public static int SDKAPPID=1400353314;

    public static String SECRETKEY="850dddbc143d91e4c085a990a9e19465f803029dc3e3745b25336637dceb1638";


    /**
     * 用于支付宝支付业务的入参 app_id。
     */
    public static final String APPID = "2016092300575026";

    /**
     * 用于支付宝账户登录授权业务的入参 pid。商户的UID
     */
    public static final String PID = "2088102176859041";

    /**
     * 用于支付宝账户登录授权业务的入参 target_id。先不传
     */
    public static final String TARGET_ID = "";

    /**
     *  pkcs8 格式的商户私钥。
     *
     * 	如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个，如果两个都设置了，本 Demo 将优先
     * 	使用 RSA2_PRIVATE。RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议商户使用
     * 	RSA2_PRIVATE。
     *
     * 	建议使用支付宝提供的公私钥生成工具生成和获取 RSA2_PRIVATE。
     * 	工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC9FlD/v/chyv1U+dH6u3YKQQXCd1rhuqfpcBfz1RlMZ5OW1SeIkRYuMF2RP9c3VzN+/PyykooD4tU3FHi7TWLEPXaGDTzTbfRIn/f/VsBWXVIanvWeb9Mbbyy+/3UCWa71AWv2Tq7vpwaEC8Cdhd6xfq3DpQg4OV/K/4aClz8FTXpbnRIxPTwmL2HAhreA1HsNKKtO4wLityE7U6rOBwGc+pqjmWukzFHRSbAZ0W2nD2gv12VuYHSGl/9ooNTtKq1zeNI2IG/HhnAr9ZadiAEimoQ+KEybd81v9PNNhshjXTMBSfwXQQIGRbfROPhitaxlimVq1cdMyxcakezOLzEpAgMBAAECggEAWptS/PpTxdGrSwja4bbqfUdjJyhiWEBFLcsU7zZyBPas9l31r682XbO3Yl6hFlemyXcY78jgcbRETzx0DeyrIzYAkYPOXdIWeKaPA7/iMoBNU4xQTjdvhXnkizGGNDYdbDHeNgzGLvhuWt/20ITRjWQSh+mQ4sA7ct626eTnwfXmME1CVRgEHoRscid1ER2uJl95GHWkKVREKFocna18tjsQFkvVPsdyq8P8iI+J8r3Eq06LICVaUXjgN+VOBQyJ6PXd23QQVvnq8vklMon8QrfG3SoIqDReTad219p+XRilb1hTiiDm9PLwAir37VtyrwAIT/zi4t4EoEiziZFswQKBgQD2eg9t+3AvMNo5vNOB5EBxV1CAM88UTPMW1hseYl8lHFDDQGFCMLU1c+t5yrGLlWFZrC18SQHzKlcqOQ0Fp+WUbVeqU+SHYlwOyjuGBNDbzpRI8SyDs2qYIaMYifFR/WSdBBgFz65sRHnyDk8ArsM2gl6737bbPXI0dRDUrnW4fQKBgQDEZJtITbcxnvOSMgkrLDDq7bhZFSdUCOYSDZYn/eHiA/mUd18Q8bAgBe0YTk8Ln/Kq0zMJiSTFMXJIxcEV6VQ8bFryEiaun3Ly1fBBvQOWTovHPl4+eFz6Vd7Hf6bjqzRVn03qY/hPmoV2nUsey+LPBAyX1ma7itLfmGKj9UtnHQKBgHaEQNEYu1vluLlUo7tsntQhMRNEVkmjqqwLbeTRVoDIucj5NwsV0ryOLedVYMQGo1X0JbcCgA6GCH3ECw/yKMGTRjkft96lnDB8a9zIigl3rvPYDBiT+CiI3Bzbj49mMbASnN6Ge4+R6bVLzg88+Z9HVvgt3YZUaIry05IBBZgdAoGAGeKgzPDhWnr246gQqU4TzIM0rxkoq9zYZjzAM9k0spOPi9AICbWxXycozvFTXdbhJz7bFUCsRgl0ZL8b6gVtAuHNQua9jnqZrgQgvKlrFZ6YsdQ4m8dFigFTcbwThSKFrVtn66LtkaBuQuoMgqyg4mRIPKMXu1Ra4vm01qN4IskCgYEAlG5apxJ0xr1tvwIkLE6UctC4zsha/JoR2j0/ov6+WbB8ikn6KWYO0d8gQ0H+ZmBeXezlh8QZy4/x28ulHkeC9RiY5vv2lfSbn7lHwtxX2N1WfSuulDEee/YmJdMNjMyrc5CdPIfMFJHrM4MZv1DYVDZzr65LHfNUCm6MFWLJIRA=";
    public static final String RSA_PRIVATE = "";



}