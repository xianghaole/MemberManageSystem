package cn.lger.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Code that Changed the World
 * Pro said
 * Created by Pro on 2017-12-13.
 */
@Entity
public class Commodity {

    @Id
    @GeneratedValue(generator="id")
    @GenericGenerator(name="id",strategy="uuid")
    private String id;
    private String commodityName;
    private Long commodityIntegral;
    private Integer commodityNumber;
    private Float commodityPrice;
    private String commodityImg;
    private String commodityTitle;
    private String commodityTime;

    public String getCommodityImg() {
        return commodityImg;
    }

    public void setCommodityImg(String commodityImg) {
        this.commodityImg = commodityImg;
    }

    public String getCommodityTitle() {
        return commodityTitle;
    }

    public void setCommodityTitle(String commodityTitle) {
        this.commodityTitle = commodityTitle;
    }

    public String getCommodityTime() {
        return commodityTime;
    }

    public void setCommodityTime(String commodityTime) {
        this.commodityTime = commodityTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public Long getCommodityIntegral() {
        return commodityIntegral;
    }

    public void setCommodityIntegral(Long commodityIntegral) {
        this.commodityIntegral = commodityIntegral;
    }

    public Integer getCommodityNumber() {
        return commodityNumber;
    }

    public void setCommodityNumber(Integer commodityNumber) {
        this.commodityNumber = commodityNumber;
    }

    public Float getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(Float commodityPrice) {
        this.commodityPrice = commodityPrice;
    }

    @Override
    public String toString() {
        return "Commodity{" +
                "id='" + id + '\'' +
                ", commodityName='" + commodityName + '\'' +
                ", commodityIntegral=" + commodityIntegral +
                ", commodityNumber=" + commodityNumber +
                ", commodityPrice=" + commodityPrice +
                ", commodityImg='" + commodityImg + '\'' +
                ", commodityTitle='" + commodityTitle + '\'' +
                ", commodityTime='" + commodityTime + '\'' +
                '}';
    }
}
