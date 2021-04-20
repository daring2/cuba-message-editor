package ru.itsyn.cuba.message_editor.message;

import org.springframework.stereotype.Component;
import ru.itsyn.cuba.message_editor.entity.MessageEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.haulmont.cuba.core.global.LocaleResolver.localeToString;

@Component(MessageEntityCache.NAME)
public class MessageEntityCache {

    public static final String NAME = "msg_MessageEntityCache";

    protected volatile Map<String, MessageEntity> data = new HashMap<>();

    public MessageEntity getMessage(String pack, String key, Locale locale) {
        var cacheKey = buildCacheKey(pack, key, localeToString(locale));
        return data.get(cacheKey);
    }

    protected String buildCacheKey(String pack, String key, String locale) {
        return pack + "/" + locale + "/" + key;
    }

    public void update(List<MessageEntity> entities) {
        var newData = new HashMap<String, MessageEntity>();
        for (MessageEntity me : entities) {
            var key = buildCacheKey(me.getPack(), me.getKey(), me.getLocale());
            newData.put(key, me);
        }
        this.data = newData;
    }

}
