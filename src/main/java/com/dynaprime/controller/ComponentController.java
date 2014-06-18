package com.dynaprime.controller;

import com.dynaprime.domain.Component;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named("componentController")
@ViewScoped
public class ComponentController extends Components implements Serializable {

    private Map<String, Object> fields = new HashMap<>();
    private Component component;
    private int type;
    
    public ComponentController() {
        this.component = new Component();
    }
    
    public void addInput() {
            switch(type) {
                case 0: addInputText(component);
                    break;
                case 1: addTextArea(component);
                    break;
                case 3: addSelectOneMenu(component);
                 break;                 
                case 4: addRadioGroup(component);
                    break;
                case 5: addCalendar(component);                   
                    break;              
                default: break;
          }
          setComponent(new Component());
    }
    
    public void save() {
        for(String key : fields.keySet()) {
            System.out.println(key + ": " + fields.get(key));
        }
    }
    
    public void deleteComponent(String id) {
        UIComponent root = FacesContext.getCurrentInstance().getViewRoot().findComponent(":component-form:component-grid");
        for(UIComponent comp: root.getChildren()) {
            if(comp.getId().equals(id)){
                root.getChildren().remove(comp);
                break;
            }
        }
    }
    
    public void add(String key, Object value) {
        fields.put(key, value);
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }
}
