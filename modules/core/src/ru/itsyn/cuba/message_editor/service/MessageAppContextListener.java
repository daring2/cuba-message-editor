package ru.itsyn.cuba.message_editor.service;

import com.haulmont.cuba.core.sys.events.AppContextStartedEvent;
import com.haulmont.cuba.security.app.Authentication;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component(MessageAppContextListener.NAME)
public class MessageAppContextListener {

    public static final String NAME = "msg_MessageAppContextListener";

    @Inject
    protected Authentication authentication;
    @Inject
    protected MessageEntityService messageEntityService;

    @EventListener(AppContextStartedEvent.class)
    public void onAppContextStarted() {
        authentication.withSystemUser(() -> {
            messageEntityService.refreshCache();
            return null;
        });
    }

}
