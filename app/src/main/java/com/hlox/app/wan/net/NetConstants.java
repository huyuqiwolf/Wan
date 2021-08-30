package com.hlox.app.wan.net;

import java.util.Map;

/**
 * 请求的常量类，主要存储url，以及分页等常量
 * POST 请求接口需要登录的，请携带Cookie，Cookie中携带登录的用户名和密码即可
 */
public interface NetConstants {
    int PAGE_SIZE = 10;
    /**
     * GET 查询公众号列表
     */
    String OFFICIAL_ACCOUNT = "https://wanandroid.com/wxarticle/chapters";
    /**
     * https://wanandroid.com/wxarticle/list/408/1/json
     * 方法：GET
     * 参数：
     * 公众号 ID：拼接在 url 中，eg:405
     * 公众号页码：拼接在url 中，eg:1
     * page_size
     */
    String OFFICIAL_HISTORY = "https://wanandroid.com/wxarticle/list";
    /**
     * https://wanandroid.com/wxarticle/list/405/1/json?k=Java
     * 方法：GET
     * 参数 ：
     * k : 字符串，eg:Java
     * 公众号 ID：拼接在 url 中，eg:405
     * 公众号页码：拼接在url 中，eg:1
     * page_size
     */
    String OFFICIAL_QUERY ="https://wanandroid.com/wxarticle/list";
    // 首页
    /**
     * 首页文章列表，%d表示页码
     * 支持page_size查询，1-40
     */
    String HOME_ARTICAL ="https://www.wanandroid.com/article/list/%d/json";
    /**
     * 首页banner
     */
    String BANNER_URL = "https://www.wanandroid.com/banner/json";
    /**
     * frient site
     */
    String FRIEDN_SITE = "https://www.wanandroid.com/friend/json";
    /**
     * 搜索热词
     */
     String SEARCH_HOT_WORD ="https://www.wanandroid.com//hotkey/json";
    /**
     * 置顶文章
     */
    String TOP_ARTICAL = "https://www.wanandroid.com/article/top/json";
    // 体系
    /**
     * 体系数据
     */
    String TREE ="https://www.wanandroid.com/tree/json";
    /**
     * 体系下的文章
     * cid
     */
    String TREE_ARTICAL ="https://www.wanandroid.com/article/list/0/json";
    /**
     * 按照作者昵称搜索文章
     * author 作者昵称，不支持模糊
     */
    String SEARCH_ARTICAL ="https://wanandroid.com/article/list/0/json";
    // 导航
    /**
     * 导航
     */
    String NAVI="https://www.wanandroid.com/navi/json";
    // 项目
    /**
     * 项目分类
     */
    String PROJECT_SORT ="https://www.wanandroid.com/project/tree/json";

    /**
     * 项目列表数据
     * cid=294
     */
    String PROJECT_LIST = "https://www.wanandroid.com/project/list/1/json";
    //登陆与注册
    /**
     * 注册 POST
     * username
     * password
     * repassword
     */
    String REGISTER ="https://www.wanandroid.com/user/register";
    /**
     * 登录 POST
     * username
     * password
     */
    String LOGIN ="https://www.wanandroid.com/user/login";
    /**
     * 登出
     */
    String LOGOUT ="https://www.wanandroid.com/user/logout/json";
    // 收藏文章立标
    /**
     * 收藏文章
     * 支持page_size
     * %d 页码
     */
    String FAVORITE_ARTICAL_LIST = "https://www.wanandroid.com/lg/collect/list/%d/json";
    /**
     * 添加收藏 POST
     * %d 文章id，站外文章
     */
    String FAVORITE_ARTICAL_ADD_IN ="https://www.wanandroid.com/lg/collect/%d/json";
    /**
     * 添加收藏 POST
     * title
     * author
     * link
     */
    String FAVORITE_ARTICAL_ADD_OUT ="https://www.wanandroid.com/lg/collect/add/json";
    /**
     * 编辑收藏的文章，支持站内站外，POST
     * id %d
     * title 不为null
     * link 不为null
     * authot 不为null
     */
    String FAVORITE_ARTICAL_EDIT="https://wanandroid.com/lg/collect/user_article/update/%d/json";
    /**
     * 取消收藏 01 文章列表页触发
     * %d 文章id
     */
    String FAVORITE_ARTICAL_REMOVE ="https://www.wanandroid.com/lg/uncollect_originId/%d/json";
    /**
     * 我的收藏页面 02 POST
     * %d id:拼接在链接上
     * 	originId:列表页下发，无则为-1
     */
    String FAVORITE_ARTICAL_REMOVE_MY = "https://www.wanandroid.com/lg/uncollect/%d/json";
    /**
     * 收藏网站列表,需要登录
     */
    String FAVORITE_SITE_LIST ="https://www.wanandroid.com/lg/collect/usertools/json";
    /**
     * 收藏网站 POST
     * id
     * name
     * link
     */
    String FAVORITE_SITE_ADD ="https://www.wanandroid.com/lg/collect/addtool/json";
    /**
     * 编辑收藏网站 POST
     * id
     * name
     * link
     */
    String FAVORITE_SITE_EDIT ="https://www.wanandroid.com/lg/collect/updatetool/json";
    /**
     * 删除收藏网站
     */
    String FAVORITE_SITE_REMOVE = "https://www.wanandroid.com/lg/collect/deletetool/json";
    //搜索
    /**
     * 搜索 POST
     * %d 页码
     * k：关键词
     */
    String SEARCH = "https://www.wanandroid.com/article/query/%d/json";
    //TODO工具
    /**
     * todo 工具，需要登录，登录后的结果存储在cookie中 POST
     * title
     * content
     * data 2020-09-02 默认当天
     * type > 0 可选
     * priority >0 可选
     */
    String TODO_ADD ="https://www.wanandroid.com/lg/todo/add/json";
    /**
     * 更新TODO POST
     * %d id
     * title
     * content
     * data
     * status 0 未完成 1 已完成
     * type
     * priority
     */
    String TODO_UPDATE = "https://www.wanandroid.com/lg/todo/update/%d/json";
    /**
     * 删除TODO POST
     * %d id
     */
    String TODO_REMOVE = "https://www.wanandroid.com/lg/todo/delete/%d/json";
    /**
     * 更新todo状态 POST
     * %d
     * status
     */
    String TODO_UPDATE_STATUS = "https://www.wanandroid.com/lg/todo/done/%d/json";
    /**
     * TODO 列表
     * %d 页码
     * status
     * type
     * priority
     * orderby 1 完成日期顺序，2 完成日期逆序 3 创建日期顺序 4 创建日期逆序 默认
     */
    String TODO_LIST ="https://www.wanandroid.com/lg/todo/v2/list/%d/json";
    // 积分
    /**
     * 查询积分排行榜
     */
    String RANK ="https://www.wanandroid.com/coin/rank/1/json";
    /**
     * 个人积分
     */
    String RANK_MY ="https://www.wanandroid.com/lg/coin/userinfo/json";
    // 广场
    /**
     * 广场列表数据呀
     * %d 页码
     */
    String SQUARE = "https://wanandroid.com/user_article/list/%d/json";
    /**
     * 分享人对应的列表数据
     * %d 用户id
     * %d 页码
     */
    String SQUARE_LIST_BY_SHARE ="https://www.wanandroid.com/user/%d/share_articles/%d/json";
    /**
     * 自己分享的文章列表
     * %d 页码
     * page_size
     */
    String SQUARE_LIST_BY_MY ="https://wanandroid.com/user/lg/private_articles/%d/json";
    /**
     * 删除自己的分享 POST
     * %d 文章id
     */
    String SQUARE_LIST_DELETE = "https://wanandroid.com/lg/user_article/delete/%d/json";
    /**
     * 分享文章 POST
     * title
     * link
     */
    String SQUARE_SHARE = "https://www.wanandroid.com/lg/user_article/add/json";
    //问答
    /**
     * 问答
     * %d pageid
     */
    String QA ="https://wanandroid.com/wenda/list/%d/json ";
}
