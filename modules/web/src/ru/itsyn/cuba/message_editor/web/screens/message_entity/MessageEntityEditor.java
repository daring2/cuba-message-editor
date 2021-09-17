package ru.itsyn.cuba.message_editor.web.screens.message_entity;

import com.haulmont.cuba.core.global.GlobalConfig;
import com.haulmont.cuba.gui.Route;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.TextArea;
import com.haulmont.cuba.gui.model.InstanceContainer.ItemPropertyChangeEvent;
import com.haulmont.cuba.gui.screen.*;
import ru.itsyn.cuba.message_editor.entity.MessageEntity;
import ru.itsyn.cuba.message_editor.web.screens.util.MessageEntityHelper;

import javax.inject.Inject;
import java.util.LinkedHashMap;

@Route(value = "messages/edit", parentPrefix = "messages")
@UiController("msg_MessageEntity.edit")
@UiDescriptor("message-entity-editor.xml")
@EditedEntityContainer("editDc")
@LoadDataBeforeShow
public class MessageEntityEditor extends StandardEditor<MessageEntity> {

    @Inject
    protected GlobalConfig globalConfig;
    @Inject
    protected MessageEntityHelper messageEntityHelper;
    @Inject
    protected LookupField<String> localeField;
    @Inject
    protected TextArea<String> defaultTextField;

    @Subscribe
    public void onInit(InitEvent event) {
        initLocaleField();
    }

    protected void initLocaleField() {
        var opts = new LinkedHashMap<String, String>();
        globalConfig.getAvailableLocales().forEach((name, locale) ->
                opts.put(name, locale.getLanguage())
        );
        localeField.setOptionsMap(opts);
    }

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
        var text = messageEntityHelper.getDefaultText(entity);
        defaultTextField.setValue(text);
    }

}