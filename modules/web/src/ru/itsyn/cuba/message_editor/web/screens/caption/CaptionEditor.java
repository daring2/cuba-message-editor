package ru.itsyn.cuba.message_editor.web.screens.caption;

import com.haulmont.cuba.gui.screen.*;
import ru.itsyn.cuba.message_editor.entity.MessageEntity;

@UiController("msg_Caption.edit")
@UiDescriptor("caption-editor.xml")
@EditedEntityContainer("editDc")
@LoadDataBeforeShow
public class CaptionEditor extends StandardEditor<MessageEntity> {

}
