package refactored.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.examen.refactored.DeliveryResult;
import com.examen.refactored.Notification;
import com.examen.refactored.NotificationChannels;
import com.examen.refactored.adapter.PushNotificationAdapter;
import com.jgranados.examenfinal.ayd2.legacy.external.PushNotificationService;

public class PushNotificationAdapterTest {

    private PushNotificationAdapter adapter;
    private PushNotificationService pushService;

    private static final String USER_WITH_DEVICE = "user123";
    private static final String USER_WITHOUT_DEVICE = "user456";
    private static final String DEVICE_TOKEN = "device_abc123";
    private static final String TITLE = "Important notification";
    private static final String FORMATTED_MESSAGE = "[PROMO] Discount available - Limited offer!";
    private static final int PRIORITY = 2;

    @BeforeEach
    void setUp() {
        pushService = mock(PushNotificationService.class);
        adapter = new PushNotificationAdapter(pushService);
    }

    @Test
    void send_whenUserHasDevice_shouldSendPushAndReturnSuccess() {
        // Arrange
        Notification notification = Notification.builder()
                .userId(USER_WITH_DEVICE)
                .message("Discount available")
                .title(TITLE)
                .channel(NotificationChannels.PUSH)
                .priority(PRIORITY)
                .build();

        // Act
        DeliveryResult result = adapter.send(notification, FORMATTED_MESSAGE);

        // Assert
        assertTrue(result.isSuccessful());
        assertEquals("Push notification sent to " + DEVICE_TOKEN, result.getDetail());
        verify(pushService).sendPush(DEVICE_TOKEN, TITLE, FORMATTED_MESSAGE, PRIORITY);
    }

    @Test
    void send_whenUserDoesNotHaveDevice_shouldReturnFailureAndNotCallPushService() {
        // Arrange
        Notification notification = Notification.builder()
                .userId(USER_WITHOUT_DEVICE)
                .message("Discount available")
                .channel(NotificationChannels.PUSH)
                .build();

        // Act
        DeliveryResult result = adapter.send(notification, FORMATTED_MESSAGE);

        // Assert
        assertFalse(result.isSuccessful());
        assertEquals("User user456 does not have a device registered", result.getDetail());
        verifyNoInteractions(pushService);
    }
}