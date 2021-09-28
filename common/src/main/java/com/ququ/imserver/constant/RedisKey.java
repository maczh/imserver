package com.ququ.imserver.constant;

public class RedisKey {
    public static final String TOKEN = "im:user:token:";  //token保存
    public static final String IM_API = "im:api";       //IM通道,String，网易云信通道设置为填"neteaseim"
    public static final String IM_UID_LAST = "im:uid:seq";  //IMUID序号
    public static final String SHOP_CONTACT_STAFFS = "im:shop:contact:staffs:";     //商户客服成员
    public static final String USER_SHOPID = "im:shopid:";      //员工归属商户号
    public static final String GROUP_CUSTOMERSERVICE = "im:group:customerservice:";    //客服群信息
    public static final String SHOP_CUSTOMER_GROUPID = "im:group:shopid:userid:";       //根据shopid与userid对应的客服群ID
    public static final String SHOP_INFO = "im:shop:info:";     //商户信息缓存
    public static final String TAGS_PUBLIC = "im:tags:public:";     //公共标签
    public static final String TAGS_PUBLIC_MY = "im:tags:my:public:";        //我的公共标签
    public static final String TAGS_FRIEND = "im:tags:friend:";     //好友标签 + <userid>:<friendid>,集合
    public static final String TAGS_MY_FRIENDS = "im:tags:my:friend:";    //好友标签的用户清单 +<userid>:<tag>，集合
    public static final String TAGS_BY_FRIEND = "im:tags:by:friend:";   //好友给我打的标签，集合
}
