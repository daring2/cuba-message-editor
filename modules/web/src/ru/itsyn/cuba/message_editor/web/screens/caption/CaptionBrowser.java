package ru.itsyn.cuba.message_editor.web.screens.caption;

import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.MessageTools;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.components.Action.ActionPerformedEvent;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.screen.*;
import ru.itsyn.cuba.message_editor.entity.MessageEntity;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@UiController("msg_Caption.browse")
@UiDescriptor("caption-browser.xml")
@LookupComponent("table")
@LoadDataBeforeShow
public class CaptionBrowser extends StandardLookup<MessageEntity> {

    @Inject
    protected Metadata metadata;
    @Inject
    protected Messages messages;
    @Inject
    protected MessageTools messageTools;
    @Inject
    protected LookupField<MetaClass> entityLookup;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        initEntityLookup();
    }

    protected void initEntityLookup() {
        var mcs = metadata.getModels().stream()
                .flatMap(m -> m.getClasses().stream())
                .collect(Collectors.toList());
        entityLookup.setOptionsList(mcs);
        entityLookup.setOptionCaptionProvider(messageTools::getEntityCaption);
    }

    @Install(to = "tableDl", target = Target.DATA_LOADER)
    protected List<MessageEntity> loadDelegate(LoadContext<MessageEntity> loadContext) {
        return new ArrayList<>();
    }

    @Subscribe("table.applyChanges")
    public void onApplyChanges(ActionPerformedEvent event) {
        messages.clearCache();
    }

}