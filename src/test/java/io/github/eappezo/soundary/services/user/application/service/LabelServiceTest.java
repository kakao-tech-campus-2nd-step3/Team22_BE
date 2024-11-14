package io.github.eappezo.soundary.services.user.application.service;

import static org.mockito.Mockito.*;

import io.github.eappezo.soundary.core.identification.Identifier;
import io.github.eappezo.soundary.core.user.Label;
import io.github.eappezo.soundary.services.user.application.LabelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class LabelServiceTest {

    @InjectMocks
    private LabelService labelService;

    @Mock
    private LabelRepository labelRepository;

    private Identifier userId;
    private List<Label> labels;

    @BeforeEach
    void setUp() {
        userId = Identifier.fromString("user123");
        labels = List.of(Label.CLASSIC, Label.JAZZ, Label.ROCK);
    }

    @Test
    @DisplayName("유저의 기존 라벨 삭제 후 새로운 라벨 추가")
    void addLabel_Success() {
        labelService.addLabel(userId, labels);

        verify(labelRepository).deleteAllByUserId(userId);
        verify(labelRepository).saveAll(userId, labels);
    }

    @Test
    @DisplayName("유저의 라벨 삭제")
    void deleteLabel_Success() {
        Label label = Label.CLASSIC;

        labelService.deleteLabel(userId, label);

        verify(labelRepository).deleteLabel(userId, label);
    }

    @Test
    @DisplayName("유저의 모든 라벨 조회")
    void getUserLabelList_Success() {
        when(labelRepository.findAllByUserId(userId)).thenReturn(labels);

        List<Label> result = labelService.getUserLabelList(userId);

        verify(labelRepository).findAllByUserId(userId);
        assert(result.equals(labels));
    }
}
