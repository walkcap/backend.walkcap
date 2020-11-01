package com.walkcap.common;

public enum ErrorMessage {

    NEED_LOGIN (Integer.valueOf(-100), "需要先登录"),
    NO_AUTH(-101,"无相关权限"),
    LOGIN_ERROR(-102, "错误的用户名或密码"),
    SERVER_ERROE(-103,"服务器错误"),
    DEPT_DELETE_ERROER(-104,"该部门还有子部门信息，不能删除"),
    DEPT_HAS_STAFF_ERROER(-105,"该部门还有员工信息，不能删除"),
    DEPT_UPDATE_ERROER(-106,"该部门还有子部门信息，不能禁用"),
    NO_SUCH_USER(-107,"无用户信息"), 
    USER_UNREGISTERED (Integer.valueOf(-108), "用户不存在"),
    ERROR_PASSWORD (Integer.valueOf(-109), "密码不正确"),
    USELESS_VALIDATE_CODE (Integer.valueOf(-110), "无效的验证码"),
    ILLEGAL_PARAMETER (Integer.valueOf(-111), "无效的参数"),
    DUPLICATE_INVOKE (Integer.valueOf(-112), "重复调用"),
    PARAMETER_LACK (Integer.valueOf(-113), "请求参数缺失"),

    MUST_UPDATE (Integer.valueOf(-114), "您的版本过旧或者存在严重隐患，请务必更新"),
    COULD_UPDATE (Integer.valueOf(-115), "云协服务推出新版本啦~"),
    ILLEGAL_TOKEN (Integer.valueOf(-116), "非法的token"),
    NO_RELATED_DATA (Integer.valueOf(-117), "无相关数据"),
    OPERATE_UP_LIMIT (Integer.valueOf(-118), "操作上限"),
    BODY_LACK (Integer.valueOf(-119), "post请求消息体缺失"),
    FAILED (Integer.valueOf(-120), "操作失败"),

    EMPTY_FILE(-200,"上传文件为空"),
    UPLOAD_ERROE(-201,"上传文件出错"),
    NO_FILE(-202,"服务器无相关文件"),
    DOWN_ERROR(-203,"下载文件出错"),
    USER_EXISTS (Integer.valueOf(-204), "用户已存在"),
    ERROR_YPASSWORD (Integer.valueOf(-205), "原密码不正确"),

    NO_DATA(-300,"无数据，无法导出"),
    CONFIG_PATH_NOT_ERROE(-301,"当前设备类型的方案未配置"),
    DATA_EMPTY (Integer.valueOf(-302), "返回数据为空"),
    PARSE_ERROR (Integer.valueOf(-303), "参数解析失败"),
    DATA_ERROR (Integer.valueOf(-304), "返回数据格式错误"),

    NO_COEXIST (Integer.valueOf(-305), "type和item这两个参数不能同时存在"),
    VALUE1_ERROR (Integer.valueOf(-306), "否则返回参数数值错误。只能选0或1"),
    VALUE2_ERROR (Integer.valueOf(-307), "否则返回参数数值错误。只能选2或1"),
    DATA_EXIT (Integer.valueOf(-308), "商品服务已经存在"),
    PLEASE_OPEN_LBS (Integer.valueOf(-309), "亲，请启动定位服务"),

    ORDER_ILLEGAL_REQUEST (Integer.valueOf(-501), "非法获取订单信息"),
    COUPON_USELESS_UNUSED (Integer.valueOf(502), "优惠券失效或已用，未使用优惠券"),
    COUPON_USELESS (Integer.valueOf(-503), "优惠券过期或已使用"),
    COUPON_NOT_SUIT_AREA (Integer.valueOf(-504), "亲，活动不支持当前城市，详见优惠券规则"),
    ALREADY_VERIFY (Integer.valueOf(-508), "该验证码已使用"),
    COUPON_BIGGER_THAN_ORDER (Integer.valueOf(-509), "优惠券金额大于订单金额"),
    ORDER_DUPLI_SUBMIT (Integer.valueOf(-510), "订单重复提交"),
    ORDER_EXISTS_COMMENT (Integer.valueOf(-511), "重复的评价提交"),
    COUPON_NO_RIGHT (Integer.valueOf(-512), "不能领取优惠券"),
    COUPON_NOT_SUIT (Integer.valueOf(-514), "优惠券不适用"),
    COUPON_UP_LIMIT (Integer.valueOf(-532), "优惠券领取上限"),

    ILLEGAL_SIGN (Integer.valueOf(-701), "无效的签名"),
    OVERDUE_TIME (Integer.valueOf(-702), "超时的时间戳"),
    ERROR_REQUEST (Integer.valueOf(-995), "错误的请求"),
    DB_ERROR (Integer.valueOf(-996), "数据库异常"),
    INNER_SERVER_ERROR (Integer.valueOf(-997), "非正常的内部服务"),
    SERVERS_LINK_ERROR (Integer.valueOf(-998), "服务器间通信异常"),
    OTHER_SERVER_ERROR (Integer.valueOf(-999), "亲，服务器君又开小差了.您放心，我们的程序猿正批评教育之");
    
    private Integer code;

    private String message;

    private ErrorMessage(){}

    private ErrorMessage(Integer code,String message){
        this.code = code;
        this.message = message;
    }

    public Integer getCode(){
        return this.code;
    }

    public String message(){
        return this.message;
    }
}

