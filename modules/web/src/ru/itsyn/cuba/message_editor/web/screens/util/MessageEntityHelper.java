package ru.itsyn.cuba.message_editor.web.screens.util;

import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.chile.core.model.MetaProperty;
import com.haulmont.cuba.core.global.MessageTools;
import org.springframework.stereotype.Component;
import ru.itsyn.cuba.message_editor.entity.MessageEntity;

import javax.inject.Inject;

@Component
public class MessageEntityHelper {

    public static final String NAME = "msg_MessageEntityHelper";

    @Inject
    protected MessageTools messageTools;

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
