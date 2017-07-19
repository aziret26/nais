package kg.nais.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by aziret on 7/12/17.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "ServiceUpdate.findAll",
            query = "SELECT su FROM ServiceUpdate su")
})
public class ServiceUpdate implements Serializable{
    @Id
    @GeneratedValue
    private int serviceUpdateId;

    @Column
    private Calendar ordersLastUpd;

    @Column
    private Calendar chicksLastUpd;

    @Column
    private Calendar notificationsLastUpd;

    /**
     * set time for update in hh:mm format
     * system will be updating everyday selected time
     */
    @Column
    private String updateTime;

    public int getServiceUpdateId() {
        return serviceUpdateId;
    }

    public void setServiceUpdateId(int serviceUpdateId) {
        this.serviceUpdateId = serviceUpdateId;
    }

    public Calendar getOrdersLastUpd() {
        return ordersLastUpd;
    }

    public void setOrdersLastUpd(Calendar ordersLastUpd) {
        this.ordersLastUpd = ordersLastUpd;
    }

    public Calendar getChicksLastUpd() {
        return chicksLastUpd;
    }

    public void setChicksLastUpd(Calendar chicksLastUpd) {
        this.chicksLastUpd = chicksLastUpd;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Calendar getNotificationsLastUpd() {
        return notificationsLastUpd;
    }

    public void setNotificationsLastUpd(Calendar notificationsLasUpd) {
        this.notificationsLastUpd = notificationsLasUpd;
    }
}
