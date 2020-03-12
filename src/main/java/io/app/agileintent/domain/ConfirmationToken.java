package io.app.agileintent.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(updatable = false, nullable = false)
    private String confirmationToken;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "en_NZ", timezone = "Pacific/Auckland")
    @Column(updatable = false)
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "en_NZ", timezone = "Pacific/Auckland")
    @Column(updatable = false)
    private Date expiresAt;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, updatable = false, name = "user_id")
    private User user;

    public ConfirmationToken(User user) {
        this.confirmationToken = UUID.randomUUID().toString();
        this.createdAt = new Date();
        this.expiresAt = new Date(System.currentTimeMillis() + 3600 * 1000);
        this.user = user;
    }

    public ConfirmationToken() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
