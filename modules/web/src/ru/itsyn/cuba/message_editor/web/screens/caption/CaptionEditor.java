package ru.itsyn.cuba.message_editor.web.screens.caption;

import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.Route;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.screen.*;
import ru.itsyn.cuba.message_editor.entity.MessageEntity;
import ru.itsyn.cuba.message_editor.web.screens.util.MessageEntityHelper;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Route(value = "captions/edit", parentPrefix = "captions")
@UiController("msg_Caption.edit")
@UiDescriptor("caption-editor.xml")
@EditedEntityContainer("editDc")
@LoadDataBeforeShow
public class CaptionEditor extends StandardEditor<MessageEntity> {

    @Inject
    protected MessageEntityHelper messageEntityHelper;

    @Install(to = "messagesDl", target = Target.DATA_LOADER)
    private List<MessageEntity> loadMessages(LoadContext<MessageEntity> loadContext) {
        return new ArrayList<>();
    }

    @Install(to = "messagesTable.defaultText", subject = "columnGenerator")
    private Component newDefaultTextCell(MessageEntity entity) {
        var text = messageEntityHelper.getDefaultText(entity);
        return new Table.PlainTextCell(text);
    }

}
