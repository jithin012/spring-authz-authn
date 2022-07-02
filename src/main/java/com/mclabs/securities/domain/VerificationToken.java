package com.mclabs.securities.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "verification_token")
public class VerificationToken {
    public static final int EXPIRATION = 60 * 24;

    private String token;
    @Id
    private String username;
    private Date expiryDate;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Date calculateExpiryDate(int expiryInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, expiryInMinutes);
        return cal.getTime();
    }
}
