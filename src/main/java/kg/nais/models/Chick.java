package kg.nais.models;

import kg.nais.controllers.FeedController;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Created by b-207 on 5/1/2017.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Chick.findAll",
                query = "SELECT c FROM Chick c"),
        @NamedQuery(name = "Chick.findByAgeRange",
                query = "SELECT c FROM Chick c WHERE c.age >= :ageFrom AND c.age <= :ageTo"),
        @NamedQuery(name = "Chick.findByFeed",
                query = "SELECT c FROM Chick c WHERE c.modFeed = true AND c.feed = :feed"),
        @NamedQuery(name = "Chick.findByClientAndFeed",
                query = "SELECT c FROM Chick c WHERE c.client = :client AND c.feed = :feed"),
        @NamedQuery(name="Chick.findByClient",
                query = "SELECT c FROM Chick c where c.client = :client"),
        @NamedQuery(name="Chick.findChicksAgeBefore",
                query = "SELECT c FROM Chick c where c.age <= :age"),
        @NamedQuery(name="Chick.findActiveChicksAgeBefore",
                query = "SELECT c FROM Chick c where c.age <= :age AND c.client.clientStatus.id=1"),
        @NamedQuery(name="Chick.findByClientForFeedBelow",
                query = "SELECT c FROM Chick c where c.client = :client AND c.age <= :ageTo"),
        @NamedQuery(name="Chick.findByActiveClientForFeedBelow",
                query = "SELECT c FROM Chick c where c.client = :client AND c.age <= :ageTo AND c.client.clientStatus.id=1")
})
public class Chick implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int chickId;

    @Column
    private int age;

    @Column
    private int amount;

    @Column
    private Calendar dob;

    @ManyToOne
    @JoinColumn(name="clientId")
    private Client client;

    @Column
    private boolean modFeed = false;

    @ManyToOne
    @JoinColumn(name = "feedId")
    private Feed feed;

    @Transient
    private int selectedFeedId = 0;

    @Transient
    private boolean editable = true;

    public int getChickId() {
        return chickId;
    }

    public void setChickId(int chickId) {
        this.chickId = chickId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {

        if(age == this.age)
            return;

        this.age = age;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Calendar getDob() {
        return dob;
    }

    public void setDob(Calendar dob) {
        if(this.dob != null &&
                this.dob.get(Calendar.DAY_OF_YEAR) == dob.get(Calendar.DAY_OF_YEAR)) {
            return;
        }
        this.dob = dob;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isModFeed() {
        return modFeed;
    }

    public void setModFeed(boolean modFeed) {
        this.modFeed = modFeed;
    }

    public int getSelectedFeedId() {
        if(selectedFeedId == 0 && feed != null){
            selectedFeedId = feed.getFeedId();
        }
        return selectedFeedId;
    }

    public void setSelectedFeedId(int selectedFeedId) {
        this.selectedFeedId = selectedFeedId;
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    private Calendar calculateDob(int age){
        Calendar now = Calendar.getInstance();
        now.add(Calendar.WEEK_OF_YEAR,- age );
        return now;
    }

    public int calculateAge(Calendar calendar){
        if(dob.after(calendar)){
            return -1;
        }

        if(dob.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)){
            return calendar.get(Calendar.WEEK_OF_YEAR) - dob.get(Calendar.WEEK_OF_YEAR);
        }

        int numOfWeeks = 0;
        Calendar date = (Calendar) dob.clone();
        for(int y = date.get(Calendar.YEAR); y <= calendar.get(Calendar.YEAR);y++){
            if(date.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)){
                numOfWeeks += calendar.get(Calendar.WEEK_OF_YEAR);
                return numOfWeeks;
            }
            numOfWeeks += date.getMaximum(Calendar.WEEK_OF_YEAR) - date.get(Calendar.WEEK_OF_YEAR);
            date.set(date.get(Calendar.YEAR)+1,Calendar.JANUARY,1);
        }
        return numOfWeeks;
    }

    public void updateAge(){
        setAge(calculateAge(Calendar.getInstance()));
    }

    public void updateDob(){
        setDob(calculateDob(age));
    }

    public void increaseAgeByDay(){
        increaseAgeByDays(1);
    }

    public void increaseAgeByDays(int days){
        Calendar c = (Calendar)dob.clone();
        c.add(Calendar.DAY_OF_YEAR, -days);
        setDob(c);
        updateAge();
    }

    public void decreaseAgeByDay(){
        decreaseAgeByDays(1);
    }

    public void decreaseAgeByDays(int days){
        Calendar c = (Calendar)dob.clone();
        c.add(Calendar.DAY_OF_YEAR, days);
        setDob(c);
        updateAge();
    }

    @Override
    public String toString() {
        return "Chick{" +
                "chickId=" + chickId +
                ", age=" + age +
                ", feed=" + feed +
                '}';
    }
}
