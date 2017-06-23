package com.botpy.loanbanker.api.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by liuxuhui on 2017/5/10 18:35
 */
public class UserInfo implements Parcelable {

    private String uid;

    @SerializedName("company")
    private String companyName;

    private String phone;

    /** token，用于计算签名秘钥和加密秘钥 */
    private String token;

    @SerializedName("nickname")
    private String nickName;

    @SerializedName("avatar_url")
    private String avatarUrl;

    public static UserInfo getUser() {
        return user;
    }

    private static UserInfo user;

    private UserInfo() { }

    public static UserInfo getInstance(){
        if(user == null){
            synchronized (UserInfo.class){
                if(user == null){
                    user = new UserInfo();
                }
            }
        }
        return user;
    }

    /**
     * Gson 反射之后的用户进行设置，保证单利
     *
     * @param uif 反序列化之后的对象
     */
    public static void setUser(UserInfo uif) {
        user = uif;
    }

    /**
     * 当获取用户信息之后更新下单利对象
     *
     * @param uif 用户信息反序列的对象
     */
    public static void updateUser(UserInfo uif) {
        if (user != null) {
            String token = user.getToken();
            user = uif;
            user.setToken(token);
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public static Creator<UserInfo> getCREATOR() {
        return CREATOR;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "uid='" + uid + '\'' +
                ", companyName='" + companyName + '\'' +
                ", phone='" + phone + '\'' +
                ", token='" + token + '\'' +
                ", nickName='" + nickName + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.phone);
        dest.writeString(this.nickName);
        dest.writeString(this.companyName);
        dest.writeString(this.avatarUrl);
        dest.writeString(this.uid);
        dest.writeString(this.token);
    }

    protected UserInfo(Parcel in) {
        this.phone = in.readString();
        this.nickName = in.readString();
        this.companyName = in.readString();
        this.avatarUrl = in.readString();
        this.uid = in.readString();
        this.token = in.readString();
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel source) {
            return new UserInfo(source);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
}
