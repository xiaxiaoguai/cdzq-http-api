package com.cdzq.api.entity;

import javax.persistence.*;

@Table(name = "whiteip")
public class Whiteip {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "ip")
    private String ip;

    @Column(name = "ywlx")
    private String ywlx;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return ywlx
     */
    public String getYwlx() {
        return ywlx;
    }

    /**
     * @param ywlx
     */
    public void setYwlx(String ywlx) {
        this.ywlx = ywlx;
    }
}