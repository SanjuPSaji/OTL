package com.onthelookout.otl.ui.dashboard;

public class model_dashboard {
    String type,information,url;

    model_dashboard (){

    }
    public model_dashboard(String type, String information, String url) {
        this.type = type;
        this.information = information;
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
