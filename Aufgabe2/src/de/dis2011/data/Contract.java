package de.dis2011.data;

import javax.persistence.*;

@Entity
@Table(name="CONTRACT")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Contract {
    @Id
    @GeneratedValue
    @Column(name="CONTRACT_NO")
    private int contract_no;
    @Column(name="DATE")
    private String date;
    @Column(name="PLACE")
    private String place;
    @ManyToOne
    @JoinColumn(name="ESTATE", referencedColumnName = "ID")
    private Estate estate;
    @ManyToOne
    @JoinColumn(name="PERSON", referencedColumnName="ID")
    private Person person;

    public Contract(){}

    public int getContract_no() {
        return contract_no;
    }

    public void setContract_no(int contract_no) {
        this.contract_no = contract_no;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Estate getEstate() {
        return estate;
    }

    public void setEstate(Estate estate) {
        this.estate = estate;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person personID) {
        this.person = person;
    }
}
