package ru.itsyn.cuba.message_editor.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name = "MSG_MESSAGE")
@Entity(name = "msg_MessageEntity")
@NamePattern("%s|key")
public class MessageEntity extends StandardEntity {
    private static final long serialVersionUID = -8384518811402955112L;

    @NotNull
    @Column(name = "PACK", nullable = false, length = 1000)
    private String pack;

    @NotNull
    @Column(name = "KEY_", nullable = false, length = 1000)
    private String key;

    @NotNull
    @Column(name = "LOCALE", nullable = false, length = 64)
    private String locale;

    @Column(name = "ACTIVE")
    private Boolean active;

    @Lob
    @Column(name = "TEXT")
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
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