package ru.itsyn.cuba.message_editor.web.screens.caption;

import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.MessageTools;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Action.ActionPerformedEvent;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import ru.itsyn.cuba.message_editor.entity.MessageEntity;
import ru.itsyn.cuba.message_editor.web.screens.util.MessageEntityHelper;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

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
    protected ScreenBuilders screenBuilders;
    @Inject
    protected MessageEntityHelper messageEntityHelper;
    @Inject
    protected LookupField<MetaClass> entityLookup;
    @Inject
    protected CollectionLoader<MessageEntity> tableDl;
    @Inject
    protected Table<MessageEntity> table;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        initEntityLookup();
    }

    protected void initEntityLookup() {
        var mcs = metadata.getModels().stream()
                .flatMap(m -> m.getClasses().stream())
                .sorted(comparing(MetaClass::getName))
                .collect(Collectors.toList());
        entityLookup.setOptionsList(mcs);
        entityLookup.setOptionCaptionProvider(messageTools::getDetailedEntityCaption);
        entityLookup.addValueChangeListener(e -> tableDl.load());
    }

    @Install(to = "tableDl", target = Target.DATA_LOADER)
    protected List<MessageEntity> loadDelegate(LoadContext<MessageEntity> loadContext) {
        var mc = entityLookup.getValue();
        if (mc == null)
            return List.of();
        var rs = new ArrayList<MessageEntity>();
        rs.add(messageEntityHelper.createMessageEntity(mc));
        for (var mp : mc.getProperties()) {
            rs.add(messageEntityHelper.createMessageEntity(mp));
        }
        rs.sort(comparing(MessageEntity::getText));
        return rs;
    }

    @Subscribe("table.edit")
    public void onEdit(ActionPerformedEvent event) {
        screenBuilders.editor(table)
                .withScreenClass(CaptionEditor.class)
                .show();
    }

    @Subscribe("table.applyChanges")
    public void onApplyChanges(ActionPerformedEvent event) {
        messages.clearCache();
    }

}