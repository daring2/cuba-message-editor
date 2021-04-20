package ru.itsyn.cuba.message_editor.service;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Messages;
import org.springframework.stereotype.Service;
import ru.itsyn.cuba.message_editor.entity.MessageEntity;
import ru.itsyn.cuba.message_editor.message.MessageEntityCache;

import javax.inject.Inject;
import java.util.List;

@Service(MessageEntityService.NAME)
public class MessageEntityServiceBean implements MessageEntityService {

    @Inject
    protected Messages messages;
    @Inject
    protected DataManager dataManager;
    @Inject
    protected MessageEntityCache messageCache;

    protected volatile List<MessageEntity> activeMessages;

    @Override
    public List<MessageEntity> getActiveMessages() {
        return activeMessages;
    }

    @Override
    public void refreshCache() {
        loadActiveMessages();
        messageCache.update(activeMessages);
        messages.clearCache();
    }

    protected void loadActiveMessages() {
        var query = "select e from msg_MessageEntity e where e.active = true";
        activeMessages = dataManager.load(MessageEntity.class)
                .query(query)
                .view("full")
                .list();
    }

}