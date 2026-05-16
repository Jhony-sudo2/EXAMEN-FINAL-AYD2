package refactored;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.examen.refactored.Notification;
import com.examen.refactored.NotificationChannels;
import com.examen.refactored.NotificationTypes;

public class NotificationBuilderTest {
    private static final String USER_ID ="user123";
    private static final String MESSAGE = "System error";
    @Test
    void build_whenRequiredFieldsProvided_shouldCreateNotificationWithDefaultValues() {
        // Arrange

        // Act
        Notification notification = Notification.builder()
                .userId(USER_ID)
                .message(MESSAGE)
                .build();

        // Assert
        assertEquals(USER_ID, notification.getUserId());
        assertEquals(MESSAGE, notification.getMessage());
        assertEquals(NotificationTypes.PLAIN, notification.getType());
        assertEquals(NotificationChannels.EMAIL, notification.getChannel());
        assertEquals("Notification", notification.getTitle());
        assertEquals(1, notification.getPriority());
    }

    @Test
    void build_whenOptionalFieldsProvided_shouldCreateNotificationWithProvidedValues() {
        // Arrange
        String type = NotificationTypes.ALERT;
        String channel = NotificationChannels.PUSH;
        String title = "Important notification";
        int priority = 2;

        // Act
        Notification notification = Notification.builder()
                .userId(USER_ID)
                .message(MESSAGE)
                .type(type)
                .channel(channel)
                .title(title)
                .priority(priority)
                .build();

        // Assert
        assertEquals(USER_ID, notification.getUserId());
        assertEquals(MESSAGE, notification.getMessage());
        assertEquals(type, notification.getType());
        assertEquals(channel, notification.getChannel());
        assertEquals(title, notification.getTitle());
        assertEquals(priority, notification.getPriority());
    }

    @Test
    void build_whenUserIdIsEmpty_shouldThrowIllegalArgumentException() {
        // Arrange
        String userId = "";

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Notification.builder()
                        .userId(userId)
                        .message(MESSAGE)
                        .build()
        );

        // Assert
        assertEquals("User id cannot be empty", exception.getMessage());
    }

    @Test
    void build_whenMessageIsBlank_shouldThrowIllegalArgumentException() {
        // Arrange
        String message = "   ";

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Notification.builder()
                        .userId(USER_ID)
                        .message(message)
                        .build()
        );

        // Assert
        assertEquals("Message cannot be empty", exception.getMessage());
    }

}
