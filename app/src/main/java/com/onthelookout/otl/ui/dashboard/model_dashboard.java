package com.onthelookout.otl.ui.dashboard;

public class model_dashboard {
    String Type,Information,url;

    model_dashboard (){

    }
    public model_dashboard(String type, String Information, String url) {
        this.Type = Type;
        this.Information = Information;
        this.url = url;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getInformation() {
        return Information;
    }

    public void setInformation(String Information) {
        this.Information = Information;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
