package com.ququ.imserver.api.neteaseim.apiclient;

public class NeteaseConfig {
    //网易云信配置
    public static final String APPKEY = "";
    public static final String APPSECRET = "";

    //接口地址
    public static final String API_URL_BASE = "https://api.netease.im/nimserver";
    public static final String API_URL_REGISTER = "/user/create.action";    //注册云信ID
    public static final String API_URL_UPDATE_USERID = "/user/update.action";    //更新云信ID
    public static final String API_URL_REFRESH_TOKEN = "/user/refreshToken.action";    //刷新云信token
    public static final String API_URL_BLOCK = "/user/block.action";    //封禁云信ID
    public static final String API_URL_UNBLOCK = "/user/unblock.action";    //解禁云信ID
    public static final String API_URL_UPDATE_USERINFO = "/user/updateUinfo.action";    //更新用户资料
    public static final String API_URL_GET_USERINFO = "/user/getUinfos.action";    //获取用户资料,批量
    public static final String API_URL_FRIEND_ADD = "/friend/add.action";    //添加好友
    public static final String API_URL_FRIEND_ALIAS = "/friend/update.action";    //修改好友别名
    public static final String API_URL_FRIEND_DEL = "/friend/delete.action";    //删除好友
    public static final String API_URL_FRIEND_GET = "/friend/get.action";    //查询某时间点起到现在有更新的双向好友
    public static final String API_URL_FRIEND_BLACK = "/user/setSpecialRelation.action";    //拉黑/取消拉黑；设置静音/取消静音
    public static final String API_URL_FRIEND_BLACKLIST = "/user/listBlackAndMuteList.action";    //查看用户的黑名单和静音列表
    public static final String API_URL_MESSAGE_SEND = "/msg/sendMsg.action";    //给用户或者高级群发送普通消息
    public static final String API_URL_MESSAGE_BATCH_SEND = "/msg/sendBatchMsg.action";    //群发消息，最多500人
    public static final String API_URL_NOTIFY_SEND = "/msg/sendAttachMsg.action";    //发送自定义系统通知
    public static final String API_URL_NOTIFY_BATCH_SEND = "/msg/sendBatchAttachMsg.action";    //群发自定义系统通知，最多500人
    public static final String API_URL_FILE_UPLOAD = "/msg/upload.action";    //文件上传，最大15M，字符流
    public static final String API_URL_FILE_MULTIPART = "/msg/fileUpload.action";    //MultiPart方式文件上传
    public static final String API_URL_MESSAGE_RECALL = "/msg/recall.action";    //消息撤回
    public static final String API_URL_BROADCAST_SEND = "/msg/broadcastMsg.action";    //发送广播通知

    public static final String API_URL_GROUP_CREATE = "/team/create.action";    //创建群
    public static final String API_URL_GROUP_ADD = "/team/add.action";    //拉人入群
    public static final String API_URL_GROUP_KICK = "/team/kick.action";    //踢人出群
    public static final String API_URL_GROUP_REMOVE = "/team/remove.action";    //解散群
    public static final String API_URL_GROUP_UPDATE = "/team/update.action";    //编辑群资料
    public static final String API_URL_GROUP_QUERY = "/team/query.action";    //高级群信息与成员列表查询
    public static final String API_URL_GROUP_QUERY_DETAIL = "/team/queryDetail.action";    //查询指定群的详细信息（群信息+成员详细信息）
    public static final String API_URL_GROUP_MSG_READED_INFO = "/team/getMarkReadInfo.action";    //获取群组已读消息的已读详情信息
    public static final String API_URL_GROUP_CHANGE_OWNER = "/team/changeOwner.action";    //移交群主,群主可以选择离开此群，还是留下来成为普通成员
    public static final String API_URL_GROUP_ADMIN_ADD = "/team/addManager.action";    //提升普通成员为群管理员，可以批量，但是一次添加最多不超过10个人
    public static final String API_URL_GROUP_ADMIN_REMOVE = "/team/removeManager.action";    //解除管理员身份，可以批量，但是一次解除最多不超过10个人
    public static final String API_URL_GROUP_LIST = "/team/joinTeams.action";    //获取某个用户所加入高级群的群信息
    public static final String API_URL_GROUP_NICK = "/team/updateTeamNick.action";    //修改指定账号在群内的昵称
    public static final String API_URL_GROUP_MUTE = "/team/muteTeam.action";    //修改消息提醒开关
    public static final String API_URL_GROUP_MUTE_MEMBER = "/team/muteTlist.action";    //高级群禁言群成员
    public static final String API_URL_GROUP_LEAVE = "/team/leave.action";    //主动退群
    public static final String API_URL_GROUP_MUTE_ALL = "/team/muteTlistAll.action";    //将群组整体禁言
    public static final String API_URL_GROUP_MUTE_LIST = "/team/listTeamMute.action";    //获取群组禁言的成员列表

    public static final String API_URL_HISTORY_PEER = "/history/querySessionMsg.action";    //单聊云端历史消息查询
    public static final String API_URL_HISTORY_GROUP = "/history/queryTeamMsg.action";    //群聊云端历史消息查询


    public static final int MESSAGE_TEXT = 0;   //文本消息
    public static final int MESSAGE_PICTURE = 1;    //图片消息
    public static final int MESSAGE_VOICE = 2;      //语音消息
    public static final int MESSAGE_VIDEO = 3;      //视频消息
    public static final int MESSAGE_LOCATE = 4;     //地理位置消息
    public static final int MESSAGE_FILE = 6;       //文件消息
    public static final int MESSAGE_CUSTOM = 100;   //自定义消息类型

}
