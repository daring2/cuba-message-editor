package ru.itsyn.cuba.message_editor.web.message;

import com.haulmont.cuba.client.sys.MessagesClientImpl;
import com.haulmont.cuba.web.security.events.AppLoggedInEvent;
import org.springframework.context.event.EventListener;
import ru.itsyn.cuba.message_editor.message.MessageEntityCache;
import ru.itsyn.cuba.message_editor.service.MessageEntityService;

import javax.inject.Inject;
import java.util.Locale;
import java.util.Set;

public class ClientMessages extends MessagesClientImpl {

    @Inject
    protected MessageEntityService messageService;
    @Inject
    protected MessageEntityCache messageCache;

    protected volatile boolean initCache = true;

    @EventListener(AppLoggedInEvent.class)
    public void onAppLoggedIn() {
        if (initCache) {
            updateCache();
            initCache = false;
        }
    }

    @Override
    protected String searchMessage(String packs, String key, Locale locale, Locale truncatedLocale, Set<String> passedPacks) {
        var message = messageCache.getMessage(packs, key, locale);
        if (message != null)
            return message.getText();
        return super.searchMessage(packs, key, locale, truncatedLocale, passedPacks);
    }

    @Override
    public void clearCache() {
        messageService.refreshCache();
        updateCache();
        super.clearCache();
    }

    protected void updateCache() {
        messageCache.update(messageService.getActiveMessages());
    }

    public Set<String> loadMessageKeys(String pack) {
        var path = "/" + pack.replaceAll("\\.", "/");
        var props = loadPropertiesFromResource(path, null, null);
        return props.stringPropertyNames();
    }

}
