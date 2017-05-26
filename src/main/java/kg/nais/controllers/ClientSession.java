package kg.nais.controllers;

import kg.nais.models.Client;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Created by B-207 on 5/2/2017.
 */
@ManagedBean
@SessionScoped
public class ClientSession {
    private Client cleint;

    public Client getCleint() {
        return cleint;
    }

    public void setCleint(Client cleint) {
        this.cleint = cleint;
    }
}
