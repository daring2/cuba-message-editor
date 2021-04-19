package ru.itsyn.cuba.message_editor.web.screens.message_entity;

import com.haulmont.cuba.gui.screen.*;
import ru.itsyn.cuba.message_editor.entity.MessageEntity;

@UiController("msg_MessageEntity.edit")
@UiDescriptor("message-entity-editor.xml")
@EditedEntityContainer("editDc")
@LoadDataBeforeShow
public class MessageEntityEditor extends StandardEditor<MessageEntity> {
}