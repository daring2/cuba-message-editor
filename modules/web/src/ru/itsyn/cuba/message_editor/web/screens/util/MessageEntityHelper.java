package ru.itsyn.cuba.message_editor.web.screens.util;

import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.chile.core.model.MetaProperty;
import com.haulmont.cuba.core.global.LocaleResolver;
import com.haulmont.cuba.core.global.MessageTools;
import org.springframework.stereotype.Component;
import ru.itsyn.cuba.message_editor.entity.MessageEntity;
import ru.itsyn.cuba.message_editor.web.message.DefaultMessages;

import javax.inject.Inject;

@Component("msg_MessageEntityHelper")
public class MessageEntityHelper {

    @Inject
    protected DefaultMessages defaultMessages;
    @Inject
    protected MessageTools messageTools;

    public String getDefaultText( MessageEntity me) {
        var validKey = me.getPack() != null
                && me.getKey() != null
                && me.getLocale() != null;
        if (!validKey)
            return null;
        var locale = LocaleResolver.resolve(me.getLocale());
        return defaultMessages.getMessage(me.getPack(), me.getKey(), locale);
    }

    public MessageEntity createMessageEntity(MetaClass mc) {
        var e = new MessageEntity();
        e.setPack(mc.getJavaClass().getPackageName());
        e.setKey(mc.getJavaClass().getSimpleName());
        e.setText(messageTools.getEntityCaption(mc));
        return e;
    }

    public MessageEntity createMessageEntity(MetaProperty mp) {
        var mc = mp.getDomain();
        var e = new MessageEntity();
        e.setPack(mc.getJavaClass().getPackageName());
        e.setKey(mc.getJavaClass().getSimpleName() + "." + mp.getName());
        e.setText(messageTools.getPropertyCaption(mp));
        return e;
    }

}
