package ru.itsyn.cuba.message_editor.web.screens.caption;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.GlobalConfig;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.Route;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.model.DataContext.PreCommitEvent;
import com.haulmont.cuba.gui.screen.*;
import ru.itsyn.cuba.message_editor.entity.MessageEntity;
import ru.itsyn.cuba.message_editor.web.screens.util.MessageEntityHelper;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Route(value = "captions/edit", parentPrefix = "captions")
@UiController("msg_Caption.edit")
@UiDescriptor("caption-editor.xml")
@EditedEntityContainer("editDc")
@LoadDataBeforeShow
public class CaptionEditor extends StandardEditor<MessageEntity> {

    @Inject
    protected GlobalConfig globalConfig;
    @Inject
    protected DataManager dataManager;
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
        var messages = new ArrayList<MessageEntity>();
        messages.addAll(loadDefaultMessages());
        messages.addAll(loadDbMessages());
        var map = new LinkedHashMap<String, MessageEntity>();
        messages.forEach(e -> map.put(e.getLocale(), e));
        return new ArrayList<>(map.values());
    }

    protected List<MessageEntity> loadDefaultMessages() {
        var entity = getEditedEntity();
        var messages = new ArrayList<MessageEntity>();
        for (String locale : locales.keySet()) {
            var me = new MessageEntity();
            me.setPack(entity.getPack());
            me.setKey(entity.getKey());
            me.setLocale(locale);
            messages.add(me);
        }
        return messages;
    }

    protected List<MessageEntity> loadDbMessages() {
        var entity = getEditedEntity();
        var query = "select e from msg_MessageEntity e" +
                " where e.pack = :pack and e.key = :key";
        return dataManager.load(MessageEntity.class)
                .query(query)
                .parameter("pack", entity.getPack())
                .parameter("key", entity.getKey())
                .view("full")
                .list();
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

    @Subscribe(target = Target.DATA_CONTEXT)
    public void onPreCommit(PreCommitEvent event) {
        event.getModifiedInstances().remove(getEditedEntity());
        event.getModifiedInstances().removeIf(e -> {
            var me = (MessageEntity) e;
            return isBlank(me.getText());
        });
    }

}
