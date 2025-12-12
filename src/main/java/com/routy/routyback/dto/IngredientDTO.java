package com.routy.routyback.dto;

import java.util.Date;

public class IngredientDTO {

    private int ingNo;
    private String ingName;
    private String ingEnName;
    private String ingDesc;
    private int ingAllergen;
    private int ingDanger;
    private String ingFunctional;
    private int ingGrpNo;
    private Date ingRegDate;

    public int getIngNo() {
        return ingNo;
    }

    public void setIngNo(int ingNo) {
        this.ingNo = ingNo;
    }

    public String getIngName() {
        return ingName;
    }

    public void setIngName(String ingName) {
        this.ingName = ingName;
    }

    public String getIngEnName() {
        return ingEnName;
    }

    public void setIngEnName(String ingEnName) {
        this.ingEnName = ingEnName;
    }

    public String getIngDesc() {
        return ingDesc;
    }

    public void setIngDesc(String ingDesc) {
        this.ingDesc = ingDesc;
    }

    public int getIngAllergen() {
        return ingAllergen;
    }

    public void setIngAllergen(int ingAllergen) {
        this.ingAllergen = ingAllergen;
    }

    public int getIngDanger() {
        return ingDanger;
    }

    public void setIngDanger(int ingDanger) {
        this.ingDanger = ingDanger;
    }

    public String getIngFunctional() {
        return ingFunctional;
    }

    public void setIngFunctional(String ingFunctional) {
        this.ingFunctional = ingFunctional;
    }

    public int getIngGrpNo() {
        return ingGrpNo;
    }

    public void setIngGrpNo(int ingGrpNo) {
        this.ingGrpNo = ingGrpNo;
    }

    public Date getIngRegDate() {
        return ingRegDate;
    }

    public void setIngRegDate(Date ingRegDate) {
        this.ingRegDate = ingRegDate;
    }
}