package com.hueljk.ibeacon.mode;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zc on 2017/2/9.
 */
public class Goods implements Parcelable{
    //Parcelable接口就是在不同组件之间传递对象的时候进行的序列化

    /*
     "id": "4",
                "name": "草莓",
                "url": "Goods/strawberry.PNG",
                "price": 9.9,
                "desc": "水果草莓",
                "sold": 900
     */
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("url")
    private String mPurl;
    @SerializedName("price")
    private float mPrice;
    @SerializedName("desc")
    private String mPdesc;
    @SerializedName("sold")
    private int mSold;

    public Goods(int id, String name, String purl, float price, String pdesc, int sold) {
        mId = id;
        mName = name;
        mPurl = purl;
        mPrice = price;
        mPdesc = pdesc;
        mSold = sold;
    }

    public Goods() {
    }

    protected Goods(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mPurl = in.readString();
        mPrice = in.readFloat();
        mPdesc = in.readString();
        mSold = in.readInt();
    }

    public static final Creator<Goods> CREATOR = new Creator<Goods>() {
        @Override
        public Goods createFromParcel(Parcel in) {
            return new Goods(in);
        }

        @Override
        public Goods[] newArray(int size) {
            return new Goods[size];
        }
    };

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPurl() {
        return mPurl;
    }

    public void setPurl(String purl) {
        mPurl = purl;
    }

    public float getPrice() {
        return mPrice;
    }

    public void setPrice(float price) {
        mPrice = price;
    }

    public String getPdesc() {
        return mPdesc;
    }

    public void setPdesc(String pdesc) {
        mPdesc = pdesc;
    }

    public int getSold() {
        return mSold;
    }

    public void setSold(int sold) {
        mSold = sold;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeString(mPurl);
        dest.writeFloat(mPrice);
        dest.writeString(mPdesc);
        dest.writeInt(mSold);
    }

    @Override
    public String toString() {
        return "Goods{" +
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mPurl='" + mPurl + '\'' +
                ", mPrice=" + mPrice +
                ", mPdesc='" + mPdesc + '\'' +
                ", mSold=" + mSold +
                '}';
    }
}
