package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * .
 * User: Ostap
 * Date: 5/25/14
 * Time: 8:14 PM
 */
@Entity
@Table
public class Session implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "computer")
    private Computer computer;

    @Column(insertable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date begin;

    @Column
    private String status;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Column
    private String uid;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Computer getComputer() {
        return computer;
    }

    public void setComputer(Computer computer) {
        this.computer = computer;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date date) {
        this.begin = begin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
