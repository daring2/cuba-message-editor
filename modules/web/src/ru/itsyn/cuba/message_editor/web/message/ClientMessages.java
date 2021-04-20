package ru.itsyn.cuba.message_editor.web.message;

import com.haulmont.cuba.client.sys.MessagesClientImpl;
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
        messageCache.update(messageService.getActiveMessages());
        super.clearCache();
    }

}
