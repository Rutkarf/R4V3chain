package io.r4v3chain.backend.showcase.faq.store;

import io.r4v3chain.backend.showcase.model.FaqQuestion;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface FaqQuestionStore {

    List<FaqQuestion> findAll();

    Optional<FaqQuestion> findById(String id);

    void save(FaqQuestion question);

    void saveAll(List<FaqQuestion> questions);

    Map<String, Map<String, String>> getVotes();

    void replaceVotes(Map<String, Map<String, String>> votes);

    void setVote(String questionId, String userId, String direction);

    void removeVote(String questionId, String userId);
}
