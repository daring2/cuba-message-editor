package ru.itsyn.cuba.message_editor.web.screens.caption;

import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.MessageTools;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Route;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Action.ActionPerformedEvent;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.config.WindowConfig;
import com.haulmont.cuba.gui.config.WindowInfo;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.sys.ScreensHelper;
import ru.itsyn.cuba.message_editor.entity.MessageEntity;
import ru.itsyn.cuba.message_editor.web.message.ClientMessages;
import ru.itsyn.cuba.message_editor.web.screens.util.MessageEntityHelper;

import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static com.haulmont.cuba.gui.sys.ScreenUtils.getScreenMessagePack;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toMap;

@Route("captions")
@UiController("msg_Caption.browse")
@UiDescriptor("caption-browser.xml")
@LookupComponent("table")
@LoadDataBeforeShow
public class CaptionBrowser extends StandardLookup<MessageEntity> {

    @Inject
    protected Metadata metadata;
    @Inject
    protected WindowConfig windowConfig;
    @Inject
    protected ClientMessages messages;
    @Inject
    protected MessageTools messageTools;
    @Inject
    protected ScreenBuilders screenBuilders;
    @Inject
    protected ScreensHelper screensHelper;
    @Inject
    protected MessageEntityHelper messageEntityHelper;
    @Inject
    protected LookupField<Object> entityLookup;
    @Inject
    protected CollectionLoader<MessageEntity> tableDl;
    @Inject
    protected Table<MessageEntity> table;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        initEntityLookup();
    }

    protected void initEntityLookup() {
        var opts = new TreeMap<String, Object>();
        opts.putAll(metadata.getModels().stream()
                .flatMap(m -> m.getClasses().stream())
                .collect(toMap(messageTools::getDetailedEntityCaption, mc -> mc))
        );
        opts.putAll(windowConfig.getWindows().stream()
                .collect(toMap(this::getScreenCaption, w -> w, (w1, w2) -> w2))
        );
        entityLookup.setOptionsMap(opts);
        entityLookup.addValueChangeListener(e -> tableDl.load());
    }

    @Install(to = "tableDl", target = Target.DATA_LOADER)
    protected List<MessageEntity> loadDelegate(LoadContext<MessageEntity> loadContext) {
        var rs = new ArrayList<MessageEntity>();
        var ev = entityLookup.getValue();
        if (ev instanceof MetaClass) {
            var mc = (MetaClass) ev;
            rs.add(messageEntityHelper.createMessageEntity(mc));
            for (var mp : mc.getProperties()) {
                rs.add(messageEntityHelper.createMessageEntity(mp));
            }
        } else if (ev instanceof WindowInfo) {
            var wi = (WindowInfo) ev;
            var pack = getScreenMessagePack(screensHelper, wi);
            var keys = messages.loadMessageKeys(pack);
            for (var key : keys) {
                rs.add(messageEntityHelper.createMessageEntity(pack, key));
            }
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

    protected String getScreenCaption(WindowInfo wi) {
        try {
            return screensHelper.getDetailedScreenCaption(wi);
        } catch (FileNotFoundException e) {
            return wi.getId();
        }
    }

}