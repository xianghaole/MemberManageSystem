package cn.lger.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Code that Changed the World
 * Pro said
 * Created by Pro on 2017-12-13.
 */
@Entity
public class Gift {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String giftName;
    private Integer giftIntegral;
    private Integer giftNumber;
    private Float giftPrice;
    private String giftImg;
    private String giftTime;
    private String giftDesc;

    public String getGiftImg() {
        return giftImg;
    }

    public void setGiftImg(String giftImg) {
        this.giftImg = giftImg;
    }

    public String getGiftTime() {
        return giftTime;
    }

    public void setGiftTime(String giftTime) {
        this.giftTime = giftTime;
    }

    public String getGiftDesc() {
        return giftDesc;
    }

    public void setGiftDesc(String giftDesc) {
        this.giftDesc = giftDesc;
    }

    public Float getGiftPrice() {
        return giftPrice;
    }

    public void setGiftPrice(Float giftPrice) {
        this.giftPrice = giftPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public Integer getGiftIntegral() {
        return giftIntegral;
    }

    public void setGiftIntegral(Integer giftIntegral) {
        this.giftIntegral = giftIntegral;
    }

    public Integer getGiftNumber() {
        return giftNumber;
    }

    public void setGiftNumber(Integer giftNumber) {
        this.giftNumber = giftNumber;
    }

    @Override
    public String toString() {
        return "Gift{" +
                "id=" + id +
                ", giftName='" + giftName + '\'' +
                ", giftIntegral=" + giftIntegral +
                ", giftNumber=" + giftNumber +
                ", giftPrice=" + giftPrice +
                ", giftImg='" + giftImg + '\'' +
                ", giftTime='" + giftTime + '\'' +
                ", giftDesc='" + giftDesc + '\'' +
                '}';
    }
}
