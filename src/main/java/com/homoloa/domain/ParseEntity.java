package com.homoloa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ParseEntity {

    private String activeCode;
    private String activeMixName;
    private String activeName;
    private String activeUnit;
    private String casNumber;
    private String companyCode;
    private String companyName;
    private String concentration;
    private String countryAbbreviation;
    private String countryName;
    private String formulation;
    private String phraseAbbreviation;
    private String phraseCode;
    private String phraseGroupCode;
    private String phraseName;
    private String phraseGroupName;
    private String pictogramCode;
    private String productCode;
    private String productNameEnglish;
    private String registrationNumber;

    public String getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }

    public String getActiveMixName() {
        return activeMixName;
    }

    public void setActiveMixName(String activeMixName) {
        this.activeMixName = activeMixName;
    }

    public String getActiveName() {
        return activeName;
    }

    public void setActiveName(String activeName) {
        this.activeName = activeName;
    }

    public String getActiveUnit() {
        return activeUnit;
    }

    public void setActiveUnit(String activeUnit) {
        this.activeUnit = activeUnit;
    }

    public String getCasNumber() {
        return casNumber;
    }

    public void setCasNumber(String casNumber) {
        this.casNumber = casNumber;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getConcentration() {
        return concentration;
    }

    public void setConcentration(String concentration) {
        this.concentration = concentration;
    }

    public String getCountryAbbreviation() {
        return countryAbbreviation;
    }

    public void setCountryAbbreviation(String countryAbbreviation) {
        this.countryAbbreviation = countryAbbreviation;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getFormulation() {
        return formulation;
    }

    public void setFormulation(String formulation) {
        this.formulation = formulation;
    }

    public String getPhraseAbbreviation() {
        return phraseAbbreviation;
    }

    public void setPhraseAbbreviation(String phraseAbbreviation) {
        this.phraseAbbreviation = phraseAbbreviation;
    }

    public String getPhraseCode() {
        return phraseCode;
    }

    public void setPhraseCode(String phraseCode) {
        this.phraseCode = phraseCode;
    }

    public String getPhraseGroupCode() {
        return phraseGroupCode;
    }

    public void setPhraseGroupCode(String phraseGroupCode) {
        this.phraseGroupCode = phraseGroupCode;
    }

    public String getPhraseName() {
        return phraseName;
    }

    public void setPhraseName(String phraseName) {
        this.phraseName = phraseName;
    }

    public String getPhraseGroupName() {
        return phraseGroupName;
    }

    public void setPhraseGroupName(String phraseGroupName) {
        this.phraseGroupName = phraseGroupName;
    }

    public String getPictogramCode() {
        return pictogramCode;
    }

    public void setPictogramCode(String pictogramCode) {
        this.pictogramCode = pictogramCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductNameEnglish() {
        return productNameEnglish;
    }

    public void setProductNameEnglish(String productNameEnglish) {
        this.productNameEnglish = productNameEnglish;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
}
