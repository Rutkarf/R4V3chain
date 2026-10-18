package io.r4v3chain.backend.showcase.chat.store;

import io.r4v3chain.backend.showcase.model.ChatMessage;

import java.util.List;

public interface ChatMessageStore {

    List<ChatMessage> findAll();

    void save(ChatMessage message);

    void replaceAll(List<ChatMessage> messages);
}
