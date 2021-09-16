package ru.itsyn.cuba.message_editor.web.message;

import com.haulmont.cuba.client.sys.MessagesClientImpl;
import org.springframework.stereotype.Component;

@Component(DefaultMessages.NAME)
public class DefaultMessages extends MessagesClientImpl {

    public static final String NAME = "msg_DefaultMessages";

}
