package ru.itsyn.cuba.message_editor.web.screens.util;

import com.haulmont.chile.core.datatypes.Enumeration;
import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.chile.core.model.MetaProperty;
import com.haulmont.cuba.core.global.LocaleResolver;
import com.haulmont.cuba.core.global.MessageTools;
import com.haulmont.cuba.gui.config.WindowInfo;
import com.haulmont.cuba.gui.sys.ScreensHelper;
import org.springframework.stereotype.Component;
import ru.itsyn.cuba.message_editor.entity.MessageEntity;
import ru.itsyn.cuba.message_editor.web.message.ClientMessages;
import ru.itsyn.cuba.message_editor.web.message.DefaultMessages;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static com.haulmont.cuba.gui.sys.ScreenUtils.getScreenMessagePack;
import static java.util.Comparator.comparing;

@Component("msg_MessageEntityHelper")
public class MessageEntityHelper {

    @Inject
    protected ClientMessages messages;
    @Inject
    protected MessageTools messageTools;
    @Inject
    protected DefaultMessages defaultMessages;
    @Inject
    protected ScreensHelper screensHelper;

    public String getDefaultText(MessageEntity me) {
        var validKey = me.getPack() != null
                && me.getKey() != null
                && me.getLocale() != null;
        if (!validKey)
            return null;
        var locale = LocaleResolver.resolve(me.getLocale());
        return defaultMessages.getMessage(me.getPack(), me.getKey(), locale);
    }

    public MessageEntity createMessageEntity(String pack, String key) {
        var e = new MessageEntity();
        e.setPack(pack);
        e.setKey(key);
        e.setText(messages.getMessage(pack, key));
        return e;
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

    public MessageEntity createMessageEntity(Enum<?> ev) {
        var cl = ev.getDeclaringClass();
        var e = new MessageEntity();
        e.setPack(cl.getPackageName());
        e.setKey(cl.getSimpleName() + "." + ev.name());
        e.setText(messages.getMessage(ev));
        return e;
    }

    public List<MessageEntity> createMessageEntities(Object ev) {
        var rs = new ArrayList<MessageEntity>();
        if (ev instanceof MetaClass) {
            var mc = (MetaClass) ev;
            rs.add(createMessageEntity(mc));
            for (var mp : mc.getProperties())
                rs.add(createMessageEntity(mp));
        } else if (ev instanceof Enumeration<?>) {
            var e = (Enumeration<?>) ev;
            for (var v : e.getValues())
                rs.add(createMessageEntity(v));
        } else if (ev instanceof WindowInfo) {
            var wi = (WindowInfo) ev;
            var pack = getScreenMessagePack(screensHelper, wi);
            var keys = messages.loadMessageKeys(pack);
            for (var key : keys)
                rs.add(createMessageEntity(pack, key));
        }
        rs.sort(comparing(MessageEntity::getText));
        return rs;
    }

}
