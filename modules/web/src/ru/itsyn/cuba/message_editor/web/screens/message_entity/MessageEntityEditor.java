package ru.itsyn.cuba.message_editor.web.screens.message_entity;

import com.haulmont.cuba.core.global.LocaleResolver;
import com.haulmont.cuba.gui.components.TextArea;
import com.haulmont.cuba.gui.model.InstanceContainer.ItemPropertyChangeEvent;
import com.haulmont.cuba.gui.screen.*;
import ru.itsyn.cuba.message_editor.entity.MessageEntity;
import ru.itsyn.cuba.message_editor.web.message.DefaultMessages;

import javax.inject.Inject;

@UiController("msg_MessageEntity.edit")
@UiDescriptor("message-entity-editor.xml")
@EditedEntityContainer("editDc")
@LoadDataBeforeShow
public class MessageEntityEditor extends StandardEditor<MessageEntity> {

    @Inject
    protected DefaultMessages defaultMessages;
    @Inject
    protected TextArea<String> defaultTextField;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        updateDefaultText();
    }

    @Subscribe(id = "editDc", target = Target.DATA_CONTAINER)
    public void onItemPropertyChange(ItemPropertyChangeEvent<MessageEntity> event) {
        updateDefaultText();
    }

    protected void updateDefaultText() {
        var entity = getEditedEntity();
        var locale = LocaleResolver.resolve(entity.getLocale());
        var defaultText = defaultMessages.getMessage(entity.getPack(), entity.getKey(), locale);
        defaultTextField.setValue(defaultText);
    }

}