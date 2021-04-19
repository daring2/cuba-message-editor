package ru.itsyn.cuba.message_editor.web.screens.message_entity;

import com.haulmont.cuba.gui.screen.*;
import ru.itsyn.cuba.message_editor.entity.MessageEntity;

@UiController("msg_MessageEntity.browse")
@UiDescriptor("message-entity-browser.xml")
@LookupComponent("table")
@LoadDataBeforeShow
public class MessageEntityBrowser extends StandardLookup<MessageEntity> {
}