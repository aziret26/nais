package kg.nais.validators;

import kg.nais.facade.UserFacade;
import kg.nais.models.User;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by timur on 14-Apr-17.
 */
@FacesValidator("emailValidator")
public class EmailValidator implements javax.faces.validator.Validator {

    private final static String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private final static Pattern EMAIL_COMPILED_PATTERN = Pattern.compile(EMAIL_PATTERN);

    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        String email = (String) o;
        if(email==null || email.equals("")){
            FacesMessage msg = new FacesMessage("No email value!", "Email Validation Error");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        Matcher matcher = EMAIL_COMPILED_PATTERN.matcher((String)o);

        if (!matcher.matches()) {
            FacesMessage msg = new FacesMessage("Invalid email value!", "Email Validation Error");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }

        User user = new UserFacade().findByEmail(email);
        if(user != null){
            FacesMessage msg = new FacesMessage("Email exists, please choose another email",
                    "Email Validation Error");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
