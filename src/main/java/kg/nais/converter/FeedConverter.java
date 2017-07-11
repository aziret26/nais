package kg.nais.converter;

import kg.nais.facade.FeedFacade;
import kg.nais.models.Feed;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Created by aziret on 7/8/17.
 */
@FacesConverter(value = "kg.nais.converter.FeedConverter")
public class FeedConverter implements Converter{

    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if(s==null) {
            return null;
        }
        Feed  feed = new FeedFacade().findById(Integer.parseInt(s));
        return feed.getFeedId();
    }

    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if(o == null){
            return null;
        }

        return ((Feed)o).getFeedId()+"";

    }
}
