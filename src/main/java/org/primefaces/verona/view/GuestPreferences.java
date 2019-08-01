package org.primefaces.verona.view;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class GuestPreferences implements Serializable {

    private String layout = "green";

    private String theme = "teal";

    private String menuMode = "horizontal";

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getMenuMode() {
        return this.menuMode;
    }

    public void setMenuMode(String value) {
        this.menuMode = value;
    }
}
