package kg.nais.tools;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Map;
import java.util.Random;

/**
 * Created by Aziret on 16.01.2017.
 */
@ManagedBean
@ViewScoped
public class Tools implements Serializable {
    /*
    public static void killInfoSession(){
        SessionTools.setSession("infoMessageSession", null);
    }
    public static final String encode(String login){
        try{
            return Base64.getUrlEncoder().encodeToString(login.getBytes("utf-8"));
        }catch (UnsupportedEncodingException ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public static final String decode(String code){
        return new String(Base64.getUrlDecoder().decode(code));

    }
*/
    public static final void faceMessageWarn(String summary,String detail){
        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_WARN,
                        summary, detail));
    }

    public static final Map<String,String> getRequestParameterMap(){
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
    }
    public static String generateRandomKey(){
        int size = 6;
        String key="";
        String symbols="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random rand = new Random();
        for(int i = 0;i < size;i++){
            key += symbols.charAt(rand.nextInt(36));
        }
        return key;
    }
}