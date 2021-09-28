package com.ququ.imserver.constant;

public enum NeaseImErrorCode {
        SUCCESS(200,"操作成功"),
        CLIENT_VERSION_UNMATCH(201,"客户端版本不对，需升级sdk"),
        USER_IN_BLACKLIST(301,"被封禁"),
        LOGIN_FAIL(302,"用户名或密码错误"),
        IP_BLOCKED(315,"IP限制"),
        AUTH_FAIL(403,"非法操作或没有权限"),
        NOT_FOUND(404,"对象不存在"),
        PARAMETER_TOO_LONG(405,"参数长度过长"),
        OBJECT_READONLY(406,"对象只读"),
        REQUEST_TIMEOUT(408,"客户端请求超时"),
        SMSCODE_VERIFY_FAIL(413,"验证失败(短信服务)"),
        PARAMETER_FAIL(414,"参数错误"),
        NETWORK_FAIL(415,"客户端网络问题"),
        FREQUENCY_CONTROLL(416,"频率控制"),
        DUPLICATE_ERROR(417,"重复操作"),
        SMS_CHANNEL_UNUSEABLE(418,"通道不可用(短信服务)"),
        OUT_OF_BOUND(419,"数量超过上限"),
        ACCOUNT_DISBALED(422,"账号被禁用"),
        HTTP_DUPLICATE(431,"HTTP重复请求"),
        HTTP_ERROR(500,"服务器内部错误"),
        SERVER_BUSY(503,"服务器繁忙"),
        RECALL_TIMEOUT(508,"消息撤回时间超限"),
        UNAVAILABLE_PROTOCOL(509,"无效协议"),
        SERVICE_UNAVAILABLE(514,"服务不可用"),
        UNPACKET_ERROR(998,"解包错误"),
        PACKET_ERROR(999,"打包错误"),
        GROUP_MEMBER_REACHED_LIMIT(801,"群人数达到上限"),
        USER_NO_PERIMISSION(802,"没有权限"),
        GROUP_UNAVAILABLE(803,"群不存在"),
        GROUP_HASNOT_THIS_MEMBER(804,"用户不在群"),
        GROUP_TYPE_UNMATCH(805,"群类型不匹配"),
        GROUP_CREATE_REACHED_LIMIT(806,"创建群数量达到限制"),
        GROUP_MEMBER_STATUS_ERROR(807,"群成员状态错误"),
        APPLY_SUCCESS(808,"申请成功"),
        USER_IN_GROUP(809,"已经在群内"),
        INVITE_SUCCESS(810,"邀请成功"),
        CHANNEL_UNAVAILABLE(9102,"通道失效"),
        CALL_RESPONDED(9103,"已经在他端对这个呼叫响应过了"),
        CALL_FAIL_OFFLINE(11001,"通话不可达，对方离线状态"),
        CONNECT_ERROR(13001,"IM主连接状态异常"),
        CHATROOM_STATUS_ERROR(13002,"聊天室状态异常"),
        CHATROOM_USER_IN_BLACKLIST(13003,"账号在黑名单中,不允许进入聊天室"),
        USER_IN_MUTELIST(13004,"在禁言列表中,不允许发言"),
        USERINFO_IN_SPAM(13005,"用户的聊天室昵称、头像或成员扩展字段被反垃圾"),
        EMAIL_ERROR(10431,"输入email不是邮箱"),
        MOBILE_ERROR(10432,"输入mobile不是手机号码"),
        PASSWORD_UNMATCH(10433,"注册输入的两次密码不相同"),
        COMPANY_UNAVAILABLE(10434,"企业不存在"),
        USER_PASSWORD_ERROR(10435,"登陆密码或帐号不对"),
        APP_UNAVAILABLE(10436,"app不存在"),
        EMAIL_REGISTERED(10437,"email已注册"),
        MOBILE_REGISTERED(10438,"手机号已注册"),
        APP_NAME_USED(10441,"app名字已经存在");

        private int code;
        private String msg;

        private NeaseImErrorCode(int code, String msg){
            this.code=code;
            this.msg=msg;
        }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "NeaseImErrorCode{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }

    public static String getErrorMsg(int code){
            for (NeaseImErrorCode neaseImErrorCode : NeaseImErrorCode.values()){
                if (neaseImErrorCode.getCode() == code)
                    return neaseImErrorCode.getMsg();
            }
            return null;
    }
}
