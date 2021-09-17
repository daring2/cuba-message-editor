package ru.itsyn.cuba.message_editor.web.screens.caption;

import com.haulmont.cuba.core.global.GlobalConfig;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.Route;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.screen.*;
import ru.itsyn.cuba.message_editor.entity.MessageEntity;
import ru.itsyn.cuba.message_editor.web.screens.util.MessageEntityHelper;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Route(value = "captions/edit", parentPrefix = "captions")
@UiController("msg_Caption.edit")
@UiDescriptor("caption-editor.xml")
@EditedEntityContainer("editDc")
@LoadDataBeforeShow
public class CaptionEditor extends StandardEditor<MessageEntity> {

    @Inject
    protected GlobalConfig globalConfig;
    @Inject
    protected MessageEntityHelper messageEntityHelper;

    protected Map<String, String> locales = new LinkedHashMap<>();

    @Subscribe
    public void onInit(InitEvent event) {
        initLocales();
    }

    protected void initLocales() {
        globalConfig.getAvailableLocales().forEach((name, locale) -> {
            locales.put(locale.getLanguage(), name);
        });
    }

    @Install(to = "messagesDl", target = Target.DATA_LOADER)
    protected List<MessageEntity> loadMessages(LoadContext<MessageEntity> loadContext) {
        var entity = getEditedEntity();
        var rs = new ArrayList<MessageEntity>();
        for (String locale : locales.keySet()) {
            var me = new MessageEntity();
            me.setPack(entity.getPack());
            me.setKey(entity.getKey());
            me.setLocale(locale);
            me.setActive(false);
            rs.add(me);
        }
        return rs;
    }

    @Install(to = "messagesTable.locale", subject = "columnGenerator")
    protected Component newLocaleCell(MessageEntity entity) {
        var text = locales.get(entity.getLocale());
        return new Table.PlainTextCell(text);
    }

    @Install(to = "messagesTable.defaultText", subject = "columnGenerator")
    protected Component newDefaultTextCell(MessageEntity entity) {
        var text = messageEntityHelper.getDefaultText(entity);
        return new Table.PlainTextCell(text);
    }

}
