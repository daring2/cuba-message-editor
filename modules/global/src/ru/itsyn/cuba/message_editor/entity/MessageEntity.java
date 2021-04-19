package ru.itsyn.cuba.message_editor.entity;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name = "MSG_MESSAGE")
@Entity(name = "msg_MessageEntity")
public class MessageEntity extends StandardEntity {
    private static final long serialVersionUID = -8384518811402955112L;

    @Column(name = "PACK", nullable = false, length = 1000)
    @NotNull
    private String pack;

    @Column(name = "KEY_", nullable = false, length = 1000)
    @NotNull
    private String key;

    @Column(name = "LANGUAGE_", length = 64)
    private String language;

    @Column(name = "ACTIVE")
    private Boolean active;

    @Column(name = "MESSAGE")
    private String message;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

}