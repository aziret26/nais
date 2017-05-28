package kg.nais.controllers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;

/**
 * Created by B-207 on 5/28/2017.
 */

@ManagedBean(name = "item")
@RequestScoped
public class ItemController implements Serializable {

    private String itemSelected;

    public String getItemSelected() {
        return itemSelected;
    }

    public String changeItem(String item) {
        itemSelected = item;
        return itemSelected;
    }

}
