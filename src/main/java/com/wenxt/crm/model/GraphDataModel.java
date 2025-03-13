package com.wenxt.crm.model;

public class GraphDataModel {
    private String month;
    private int leads;
    
    public GraphDataModel() {
    	
    }

    public GraphDataModel(String month, int leads) {
        this.month = month;
        this.leads = leads;
    }

    // Getters and Setters
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getLeads() {
        return leads;
    }

    public void setLeads(int leads) {
        this.leads = leads;
    }
}