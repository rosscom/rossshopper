package se.rosscom.shopper.business.authentication.entity;

import se.rosscom.shopper.business.account.entity.Account;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class Token implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String token;
    private LocalDateTime expirationDate;

    @ManyToOne
    @JoinColumn(name = "account")
    private Account account;

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account userName) {
        this.account = userName;
    }
}
