package ru.itsyn.cuba.message_editor.message;

import com.haulmont.cuba.core.sys.MessagesImpl;

import javax.inject.Inject;
import java.util.Locale;
import java.util.Set;

public class CoreMessages extends MessagesImpl {

    @Inject
    protected MessageEntityCache messageCache;

    @Override
    protected String searchMessage(String packs, String key, Locale locale, Locale truncatedLocale, Set<String> passedPacks) {
        var message = messageCache.getMessage(packs, key, locale);
        if (message != null)
            return message.getText();
        return super.searchMessage(packs, key, locale, truncatedLocale, passedPacks);
    }

}
