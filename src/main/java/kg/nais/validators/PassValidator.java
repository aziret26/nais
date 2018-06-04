package kg.nais.validators;

import kg.nais.tools.Tools;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Created by timur on 14-Apr-17.
 */
@FacesValidator("validatePassword")
public class PassValidator implements Validator{

    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        String password = (String) o;
        String confirm = (String) uiComponent.getAttributes().get("confirmPassword");
        System.out.printf("new pswd: %s\nconf: %s\n\n" ,password,confirm);
        if (password == null || confirm == null) {
            return;
        }
        if (!password.equals(confirm)) {
            throw new ValidatorException(
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Passwords do not mtach",""));

        }


    }
}
