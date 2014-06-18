package com.dynaprime.domain;

import java.io.Serializable;

public class Component implements Serializable {
    private String id;
    private String labelText;
    private String values;
    private boolean required;
    private String requiredMessage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabelText() {
        return labelText;
    }

    public void setLabelText(String labelText) {
        this.labelText = labelText;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getRequiredMessage() {
        return requiredMessage;
    }

    public void setRequiredMessage(String requiredMessage) {
        this.requiredMessage = requiredMessage;
    }
    
}
