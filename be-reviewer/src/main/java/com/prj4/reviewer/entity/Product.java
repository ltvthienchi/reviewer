package com.prj4.reviewer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "PRODUCT")
public class Product {
    @Id
    @Column(name = "ID_PRODUCT")
    private String idProduct;

    @Column(name = "NAME_PRODUCT")
    private String nameProduct;

    @Column(name = "TAG_PRODUCT")
    private String tagProduct;

    @Column(name = "DT_CREATE")
    private Date dtCreated;

    @Column(name = "AVG_BATTERY")
    private float avgBattery;

    @Column(name = "AVG_DISPLAY")
    private float avgDisplay;

    @Column(name = "AVG_PERFORMANCE")
    private float avgPerformance;

    @Column(name = "AVG_DESIGN")
    private float avgDesign;

    @Column(name = "AVG_CAMERA")
    private float avgCamera;

    @Column(name = "NUMB_RATING")
    private int numRating;

    @Column(name = "INFO_BATTERY")
    private String infoBattery;

    @Column(name = "INFO_DISPLAY")
    private String infoDisplay;

    @Column(name = "INFO_PERFORMANCE")
    private String infoPerformance;

    @Column(name = "INFO_DESIGN")
    private String infoDesign;

    @Column(name = "INFO_CAMERA")
    private String infoCamera;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive;

    public Product() {}

    public Product(String idProduct, String nameProduct, String tagProduct, Date dtCreated, float avgBattery, float avgDisplay, float avgPerformance, float avgDesign, float avgCamera, int numRating, String infoBattery, String infoDisplay, String infoPerformance, String infoDesign, String infoCamera, Boolean isActive) {
        this.idProduct = idProduct;
        this.nameProduct = nameProduct;
        this.tagProduct = tagProduct;
        this.dtCreated = dtCreated;
        this.avgBattery = avgBattery;
        this.avgDisplay = avgDisplay;
        this.avgPerformance = avgPerformance;
        this.avgDesign = avgDesign;
        this.avgCamera = avgCamera;
        this.numRating = numRating;
        this.infoBattery = infoBattery;
        this.infoDisplay = infoDisplay;
        this.infoPerformance = infoPerformance;
        this.infoDesign = infoDesign;
        this.infoCamera = infoCamera;
        this.isActive = isActive;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getTagProduct() {
        return tagProduct;
    }

    public void setTagProduct(String tagProduct) {
        this.tagProduct = tagProduct;
    }

    public Date getDtCreated() {
        return dtCreated;
    }

    public void setDtCreated(Date dtCreated) {
        this.dtCreated = dtCreated;
    }

    public float getAvgBattery() {
        return avgBattery;
    }

    public void setAvgBattery(float avgBattery) {
        this.avgBattery = avgBattery;
    }

    public float getAvgDisplay() {
        return avgDisplay;
    }

    public void setAvgDisplay(float avgDisplay) {
        this.avgDisplay = avgDisplay;
    }

    public float getAvgPerformance() {
        return avgPerformance;
    }

    public void setAvgPerformance(float avgPerformance) {
        this.avgPerformance = avgPerformance;
    }

    public float getAvgDesign() {
        return avgDesign;
    }

    public void setAvgDesign(float avgDesign) {
        this.avgDesign = avgDesign;
    }

    public float getAvgCamera() {
        return avgCamera;
    }

    public void setAvgCamera(float avgCamera) {
        this.avgCamera = avgCamera;
    }

    public int getNumRating() {
        return numRating;
    }

    public void setNumRating(int numRating) {
        this.numRating = numRating;
    }

    public String getInfoBattery() {
        return infoBattery;
    }

    public void setInfoBattery(String infoBattery) {
        this.infoBattery = infoBattery;
    }

    public String getInfoDisplay() {
        return infoDisplay;
    }

    public void setInfoDisplay(String infoDisplay) {
        this.infoDisplay = infoDisplay;
    }

    public String getInfoPerformance() {
        return infoPerformance;
    }

    public void setInfoPerformance(String infoPerformance) {
        this.infoPerformance = infoPerformance;
    }

    public String getInfoDesign() {
        return infoDesign;
    }

    public void setInfoDesign(String infoDesign) {
        this.infoDesign = infoDesign;
    }

    public String getInfoCamera() {
        return infoCamera;
    }

    public void setInfoCamera(String infoCamera) {
        this.infoCamera = infoCamera;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
