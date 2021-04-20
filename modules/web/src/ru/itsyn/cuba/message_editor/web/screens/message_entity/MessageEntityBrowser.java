package ru.itsyn.cuba.message_editor.web.screens.message_entity;

import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.components.Action.ActionPerformedEvent;
import com.haulmont.cuba.gui.screen.*;
import ru.itsyn.cuba.message_editor.entity.MessageEntity;

import javax.inject.Inject;

@UiController("msg_MessageEntity.browse")
@UiDescriptor("message-entity-browser.xml")
@LookupComponent("table")
@LoadDataBeforeShow
public class MessageEntityBrowser extends StandardLookup<MessageEntity> {

    @Inject
    protected Messages messages;

    @Subscribe("table.applyChanges")
    public void onApplyChanges(ActionPerformedEvent event) {
        messages.clearCache();
    }

}