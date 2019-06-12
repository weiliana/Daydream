package com.timemory.daydream.bean.entity;


/**
 * Description：妹子有关的业务bean
 *
 * Step 1：通过HttpUrlConnection发起Get请求，然后获得后台返回的数据，此时是流形式的
 * Step 2：我们需要写一个流转成字节数组的方法
 * Step 3：将字节数组转成字符串后，得到的就是后台的给我们返回的数据了，接着要做的就
 *  是写一个解析这一大串Json的方法了，我们需要获取Json里我们需要的数据，丢到Bean里
 * Step 4：返回处理后的集合数据
 *
 * @author AUSTER on 19.6.12.
 */
public class Sister {
    /**API接口数据*/
    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private Boolean used;
    private String who;


    /**一些get和set方法*/

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }
}