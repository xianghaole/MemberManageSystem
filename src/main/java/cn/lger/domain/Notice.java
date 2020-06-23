package cn.lger.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    公告id
    private Integer nid;
//    公告标题
    private String nTitle;
//    公告内容
    private String nContent;
//    公告发布时间
    private String nTime;
    @ManyToOne
    private Admin admin;

}
