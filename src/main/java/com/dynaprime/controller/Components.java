package com.dynaprime.controller;

import com.dynaprime.domain.Component;
import java.util.ArrayList;
import java.util.List;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.behavior.confirm.ConfirmBehavior;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.dnd.Draggable;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.inputtextarea.InputTextarea;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.component.panel.Panel;
import org.primefaces.component.panelgrid.PanelGrid;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.component.selectoneradio.SelectOneRadio;

public abstract class Components {

    //Formon levő PanelGrid, amelybe a komponensek kerülnek populálásra
    private UIComponent uiComponent;
    
    public Components() {
        this.uiComponent = FacesContext.getCurrentInstance().getViewRoot().findComponent(":component-form:component-grid");
    }
    
    /**
     * Input elemeket befoglaló komponens
     * @param component kompones tulajdonsága
     * @param uic komponens, ami a panelon lesz
     * @return panel
     */
    private Panel createPanel(Component component,UIComponent uic) {
        Panel panel = new Panel();
        panel.setId(component.getId()+"Panel");
        panel.setVisible(true);
        
        panel.getChildren().add(createComamndButton(panel,component));
        panel.getChildren().add(createDraggable(panel, component));
        panel.getChildren().add(createGrid(component,uic));
        
        return panel;
    }
    
    /**
     * Mozgatás
     * @param panel mozgatni kívánt panel
     * @param component mozgatás tulajdonsága
     * @return drag
     */
    private Draggable createDraggable(Panel panel, Component component) {
        Draggable draggable = new Draggable();
        draggable.setFor(panel.getId());
        draggable.setId(component.getId()+"Draggable");
        draggable.setAxis("y");
        
        return draggable;
    }
    
    /**
     * Panelon hálós elrendezés
     * @param component grid tulajdonsága
     * @param uic gridre elhelyezni kívánt komponens
     * @return grid
     */
    private PanelGrid createGrid(Component component, UIComponent uic) {
         PanelGrid grid = new PanelGrid();
         grid.setColumns(2);
         grid.setId(component.getId()+"Grid");
         
         grid.getChildren().add(createLabel(component));
         grid.getChildren().add(uic);
         
         return grid;
    }
    
    /**
     * Címke létrehozása
     * @param component címke tulajdonsága
     * @return címke
     */
    private OutputLabel createLabel(Component component) {
        OutputLabel label = new OutputLabel();
        label.setId(component.getId()+"Label");
        label.setValue(component.getLabelText());
        
        return label;
    }
    
    /**
     * Input mező létrehozása
     * @param component mező tulajdonsága 
     * @return input
     */
    private InputText createInput(Component component) {
        InputText input = new InputText();
        input.setId(component.getId());
        input.setValueExpression("value", ExpressionFactory.newInstance().createValueExpression(FacesContext.getCurrentInstance().getELContext(), 
                                 "#{componentController.fields['"+input.getId()+"']}", Object.class));
        input.setRequired(component.isRequired());
        input.setRequiredMessage(component.getRequiredMessage());
        
        return input;
    }
    
    /**
     * Naptár mező létrehozása
     * @param component mező tulajdonsága 
     * @return input
     */
    private Calendar createCalendar(Component component) {
        Calendar calendar = new Calendar();
        calendar.setId(component.getId());
        calendar.setValueExpression("value", ExpressionFactory.newInstance().createValueExpression(FacesContext.getCurrentInstance().getELContext(), 
                                "#{componentController.fields['"+calendar.getId()+"']}", Object.class));
        calendar.setRequired(component.isRequired());
        calendar.setRequiredMessage(component.getRequiredMessage());
        
        return calendar;
    }
    
    /**
     * LEnyíló lista és radio menük értékeinek létrehozása felhasználó által bevitt felsorolásból
     * @param component felsorolt értékekű
     * @param type 0-lenyíló lista 1-rádió gombok
     * @return válaszok
     */
    private List<SelectItem> prepareValues(Component component, int type) {
        List<SelectItem> statusList = new ArrayList<>();
        if(type == 0) {
            //DropDown
            statusList.add(new SelectItem(null,"Select one..."));
        }
        String[] values = component.getValues().split(";");
        for (String value : values) {
             statusList.add(new SelectItem(value, value));
        }
        return statusList;
    }
    
    /**
     * DropDown mező létrehozása
     * @param component mező tulajdonsága 
     * @return dropdown
     */
    private SelectOneMenu createDropDown(Component component) {
        SelectOneMenu selectOne = new SelectOneMenu();
        selectOne.setId(component.getId());
        selectOne.setRequired(component.isRequired());
        selectOne.setRequiredMessage(component.getRequiredMessage());

        UISelectItems menuOptions = new UISelectItems();  
        menuOptions.setValue(prepareValues(component, 0));

        selectOne.setValueExpression("value", ExpressionFactory.newInstance().createValueExpression(FacesContext.getCurrentInstance().getELContext(), 
                                     "#{componentController.fields['"+selectOne.getId()+"']}", Object.class));
        selectOne.getChildren().add(menuOptions);
        selectOne.setValue(new Integer(1));
        
        return selectOne;
    }
    
    /**
     * Rádió gomb mező létrehozása
     * @param component mező tulajdonsága 
     * @return rádió gomb
     */
    private SelectOneRadio createRadioButtons(Component component) {
        SelectOneRadio menu = new SelectOneRadio();
        menu.setId(component.getId());
        menu.setRequired(component.isRequired());
        menu.setRequiredMessage(component.getRequiredMessage());

        UISelectItems radioBtnOptions = new UISelectItems();  
        radioBtnOptions.setValue(prepareValues(component, 1));
        menu.setValueExpression("value", ExpressionFactory.newInstance().createValueExpression(FacesContext.getCurrentInstance().getELContext(), 
                                     "#{componentController.fields['"+menu.getId()+"']}", Object.class));
        menu.getChildren().add(radioBtnOptions);
        menu.setValue(new Integer(1));
        menu.setLayout("pageDirection"); 
        
        return menu;
    }
    
    /**
     * TextArea mező létrehozása
     * @param component mező tulajdonsága 
     * @return text area
     */
    private InputTextarea createTextArea(Component component) {
        InputTextarea area = new InputTextarea();
        area.setId(component.getId());
        area.setValueExpression("value", ExpressionFactory.newInstance().createValueExpression(FacesContext.getCurrentInstance().getELContext(), 
                                 "#{componentController.fields['"+area.getId()+"']}", Object.class));
        area.setRequired(component.isRequired());
        area.setRequiredMessage(component.getRequiredMessage());
        
        return area;
    }
    
    /**
     * Művelet megerősítést kérő felugró ablak
     * @param header ablak címsora
     * @param message ablak üzenetet
     * @param icon üzenet előtti ikon
     * @param componentId a komponens id-je, ami az üzenetet küldi
     * @return 
     */
    private String confirmScript(String header, String message, String icon, String componentId) {
        return "PrimeFaces.confirm({source:'" + componentId + "',header:'" + header + "',message:'" + message + "',icon:'" + icon  + "'});return false;";
    }
    
    /**
     * Komponens törlését lehetővé tevő gomb létrehozása
     * @param panel a gomb melyik panelen legyen
     * @return törlés gomb
     */
    private CommandButton createComamndButton(Panel panel,Component component) {
        CommandButton button = new CommandButton();
        button.setId(component.getId()+"Delete");
        MethodExpression me = ExpressionFactory.newInstance().createMethodExpression(
                              FacesContext.getCurrentInstance().getELContext(),"#{componentController.deleteComponent('"+panel.getId()+"')}", 
                              Void.TYPE, new Class<?>[0]);
        button.setActionExpression(me);
        button.setUpdate(getUiComponent().getId());
        button.setProcess(getUiComponent().getId());
        button.setValue("Delete");
        button.setConfirmationScript(confirmScript("Confirmation", "Are you sure?", "ui-icon-alert", button.getId()));
        
        return button;
    }
    
    protected void addInputText(Component component) {
        getUiComponent().getChildren().add(createPanel(component,createInput(component)));
    }
    
    protected void addCalendar(Component component) {
         getUiComponent().getChildren().add(createPanel(component,createCalendar(component)));
    }
    
    protected void addSelectOneMenu(Component component) {
        getUiComponent().getChildren().add(createPanel(component,createDropDown(component)));
    }
    
    protected void addRadioGroup(Component component) {
        getUiComponent().getChildren().add(createPanel(component,createRadioButtons(component)));
    }
    
    protected void addTextArea(Component component) {
        getUiComponent().getChildren().add(createPanel(component,createTextArea(component)));
    }

    protected UIComponent getUiComponent() {
        return uiComponent;
    }
}
