package legacy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import com.jgranados.examenfinal.ayd2.legacy.AuditLogger;
import com.jgranados.examenfinal.ayd2.legacy.EmailSender;
import com.jgranados.examenfinal.ayd2.legacy.NotificationService;

public class NotificationServiceTest {

    private NotificationService service;

    private static final String USER_ID = "juan";
    private static final String EXPECTED_EMAIL = "juan@company.com";

    @BeforeEach
    void setUp() {
        service = new NotificationService();
    }

    @Test
    void sendNotification_whenTypeAlert_shouldSendFormattedAlertEmailAndLog() {
        // Arrange
        String message = "System error";
        String type = "alert";
        String expectedMessage = "[ALERT] System error - Action required!";

        try (
                MockedStatic<EmailSender> emailSenderMock = mockStatic(EmailSender.class);
                MockedStatic<AuditLogger> auditLoggerMock = mockStatic(AuditLogger.class)) {
            // Act
            service.sendNotification(USER_ID, message, type);

            // Assert
            emailSenderMock.verify(() -> EmailSender.send(EXPECTED_EMAIL, expectedMessage));

            auditLoggerMock.verify(() -> AuditLogger.log("Email sent to " + EXPECTED_EMAIL + " with type: " + type));
        }
    }

    @Test
    void sendNotification_whenTypeReminder_shouldSendFormattedReminderEmailAndLog() {
        // Arrange
        String message = "Meeting at 10";
        String type = "reminder";
        String expectedMessage = "[REMINDER] Meeting at 10 - Don't forget!";

        try (
                MockedStatic<EmailSender> emailSenderMock = mockStatic(EmailSender.class);
                MockedStatic<AuditLogger> auditLoggerMock = mockStatic(AuditLogger.class)) {
            // Act
            service.sendNotification(USER_ID, message, type);

            // Assert
            emailSenderMock.verify(() -> EmailSender.send(EXPECTED_EMAIL, expectedMessage));

            auditLoggerMock.verify(() -> AuditLogger.log("Email sent to " + EXPECTED_EMAIL + " with type: " + type));
        }
    }

    @Test
    void sendNotification_whenTypePromotion_shouldSendFormattedPromotionEmailAndLog() {
        // Arrange
        String message = "Discount available";
        String type = "promotion";
        String expectedMessage = "[PROMO] Discount available - Limited offer!";

        try (
                MockedStatic<EmailSender> emailSenderMock = mockStatic(EmailSender.class);
                MockedStatic<AuditLogger> auditLoggerMock = mockStatic(AuditLogger.class)) {
            // Act
            service.sendNotification(USER_ID, message, type);

            // Assert
            emailSenderMock.verify(() -> EmailSender.send(EXPECTED_EMAIL, expectedMessage));

            auditLoggerMock.verify(() -> AuditLogger.log("Email sent to " + EXPECTED_EMAIL + " with type: " + type));
        }
    }

    @Test
    void sendNotification_whenTypeUnknown_shouldSendOriginalMessageAndLog() {
        // Arrange
        String message = "Normal notification";
        String type = "info";

        try (
                MockedStatic<EmailSender> emailSenderMock = mockStatic(EmailSender.class);
                MockedStatic<AuditLogger> auditLoggerMock = mockStatic(AuditLogger.class)) {
            // Act
            service.sendNotification(USER_ID, message, type);

            // Assert
            emailSenderMock.verify(() -> EmailSender.send(EXPECTED_EMAIL, message));

            auditLoggerMock.verify(() -> AuditLogger.log("Email sent to " + EXPECTED_EMAIL + " with type: " + type));
        }
    }

    @Test
    void sendNotification_whenMessageIsNull_shouldThrowIllegalArgumentException() {
        // Arrange
        String message = null;
        String type = "alert";

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.sendNotification(USER_ID, message, type));

        // Assert
        assertEquals("Message cannot be empty", exception.getMessage());
    }

    @Test
    void sendNotification_whenMessageIsEmpty_shouldThrowIllegalArgumentException() {
        // Arrange
        String message = "";
        String type = "alert";

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.sendNotification(USER_ID, message, type));

        // Assert
        assertEquals("Message cannot be empty", exception.getMessage());
    }

    @Test
    void sendNotification_whenMessageIsBlank_shouldThrowIllegalArgumentException() {
        // Arrange
        String message = "   ";
        String type = "alert";

        // Act
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.sendNotification(USER_ID, message, type));

        // Assert
        assertEquals("Message cannot be empty", exception.getMessage());
    }

}
