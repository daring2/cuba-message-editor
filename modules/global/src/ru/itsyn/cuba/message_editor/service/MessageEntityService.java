package ru.itsyn.cuba.message_editor.service;

import ru.itsyn.cuba.message_editor.entity.MessageEntity;

import java.util.List;

public interface MessageEntityService {

    String NAME = "msg_MessageEntityService";

    List<MessageEntity> getActiveMessages();

    void refreshCache();

}