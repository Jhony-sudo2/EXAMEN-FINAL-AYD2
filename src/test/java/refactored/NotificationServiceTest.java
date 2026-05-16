package refactored;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.examen.refactored.DeliveryResult;
import com.examen.refactored.Notification;
import com.examen.refactored.NotificationChannels;
import com.examen.refactored.NotificationService;
import com.examen.refactored.NotificationTypes;
import com.examen.refactored.adapter.NotificationChanel;
import com.examen.refactored.format.NotificationFormatterRegister;
import com.examen.refactored.login.AudithService;

public class NotificationServiceTest {
    private NotificationService service;
    private NotificationFormatterRegister formatterRegistry;
    private Map<String, NotificationChanel> channels;
    private NotificationChanel emailChannel;
    private AudithService auditService;
    private static final String USER_ID = "juan";
    private static final String MESSAGE = "System error";
    private static final String EXPECTED_ALERT_MESSAGE = "[ALERT] System error - Action required!";

    @BeforeEach
    void setUp() {
        formatterRegistry = new NotificationFormatterRegister();
        channels = new HashMap<>();
        emailChannel = mock(NotificationChanel.class);
        auditService = mock(AudithService.class);

        channels.put(NotificationChannels.EMAIL, emailChannel);

        service = new NotificationService(
                formatterRegistry,
                channels,
                auditService
        );
    }

    @Test
    void send_whenNotificationIsValid_shouldFormatSendAndAudit() {
        // Arrange
        Notification notification = Notification.builder()
                .userId(USER_ID)
                .message(MESSAGE)
                .type(NotificationTypes.ALERT)
                .channel(NotificationChannels.EMAIL)
                .build();

        DeliveryResult expectedResult = DeliveryResult.success("Email sent");

        when(emailChannel.send(notification, EXPECTED_ALERT_MESSAGE)).thenReturn(expectedResult);

        // Act
        DeliveryResult result = service.send(notification);

        // Assert
        assertTrue(result.isSuccessful());
        assertEquals("Email sent", result.getDetail());
        verify(emailChannel).send(notification, EXPECTED_ALERT_MESSAGE);
        verify(auditService).log(notification, expectedResult);
    }

    @Test
    void send_whenCustomFormatterIsRegistered_shouldUseNewFormatWithoutChangingService() {
        // Arrange
        String customType = "custom";
        String expectedMessage = "[CUSTOM] System error";

        formatterRegistry.register(customType, notification -> "[CUSTOM] " + notification.getMessage());

        Notification notification = Notification.builder()
                .userId(USER_ID)
                .message(MESSAGE)
                .type(customType)
                .channel(NotificationChannels.EMAIL)
                .build();

        DeliveryResult expectedResult = DeliveryResult.success("Email sent");

        when(emailChannel.send(notification, expectedMessage)).thenReturn(expectedResult);

        // Act
        DeliveryResult result = service.send(notification);

        // Assert
        assertTrue(result.isSuccessful());
        assertEquals("Email sent", result.getDetail());
        verify(emailChannel).send(notification, expectedMessage);
        verify(auditService).log(notification, expectedResult);
    }

    @Test
    void send_whenChannelIsUnsupported_shouldThrowIllegalArgumentException() {
        // Arrange
        Notification notification = Notification.builder()
                .userId(USER_ID)
                .message(MESSAGE)
                .channel("whatsapp")
                .build();

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.send(notification)
        );

        // Assert
        assertEquals("Unsupported notification channel: whatsapp", exception.getMessage());
    }

    @Test
    void send_whenNotificationIsNull_shouldThrowIllegalArgumentException() {
        // Arrange
        Notification notification = null;

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.send(notification)
        );

        // Assert
        assertEquals("Notification cannot be null", exception.getMessage());
    }
}
