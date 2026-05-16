package refactored.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.examen.refactored.DeliveryResult;
import com.examen.refactored.Notification;
import com.examen.refactored.NotificationChannels;
import com.examen.refactored.adapter.SmsNotificationAdapter;
import com.jgranados.examenfinal.ayd2.legacy.external.ThirdPartySMSProvider;

public class SmsNotificationAdapterTest {

    private SmsNotificationAdapter adapter;
    private ThirdPartySMSProvider smsProvider;

    private static final String USER_WITH_PHONE = "user123";
    private static final String USER_WITHOUT_PHONE = "user789";
    private static final String PHONE_NUMBER = "5551234567";
    private static final String FORMATTED_MESSAGE = "[REMINDER] Meeting at 10 - Don't forget!";

    @BeforeEach
    void setUp() {
        smsProvider = mock(ThirdPartySMSProvider.class);
        adapter = new SmsNotificationAdapter(smsProvider);
    }

    @Test
    void send_whenUserHasPhone_shouldSendSmsAndReturnSuccess() {
        // Arrange
        Notification notification = Notification.builder()
                .userId(USER_WITH_PHONE)
                .message("Meeting at 10")
                .channel(NotificationChannels.SMS)
                .build();

        when(smsProvider.dispatchSMS(PHONE_NUMBER, FORMATTED_MESSAGE)).thenReturn(true);

        // Act
        DeliveryResult result = adapter.send(notification, FORMATTED_MESSAGE);

        // Assert
        assertTrue(result.isSuccessful());
        assertEquals("SMS sent to " + PHONE_NUMBER, result.getDetail());
        verify(smsProvider).dispatchSMS(PHONE_NUMBER, FORMATTED_MESSAGE);
    }

    @Test
    void send_whenUserDoesNotHavePhone_shouldReturnFailureAndNotCallProvider() {
        // Arrange
        Notification notification = Notification.builder()
                .userId(USER_WITHOUT_PHONE)
                .message("Meeting at 10")
                .channel(NotificationChannels.SMS)
                .build();

        // Act
        DeliveryResult result = adapter.send(notification, FORMATTED_MESSAGE);

        // Assert
        assertFalse(result.isSuccessful());
        assertEquals("User user789 does not have a phone number registered", result.getDetail());
        verifyNoInteractions(smsProvider);
    }
}