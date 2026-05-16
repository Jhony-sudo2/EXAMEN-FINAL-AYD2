package refactored.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import com.examen.refactored.DeliveryResult;
import com.examen.refactored.Notification;
import com.examen.refactored.NotificationChannels;
import com.examen.refactored.adapter.EmailNotificationAdapter;
import com.jgranados.examenfinal.ayd2.legacy.EmailSender;

public class EmailNotificationAdapterTest {

    private EmailNotificationAdapter adapter;

    private static final String USER_ID = "juan";
    private static final String EXPECTED_EMAIL = "juan@company.com";
    private static final String MESSAGE = "System error";
    private static final String FORMATTED_MESSAGE = "[ALERT] System error - Action required!";

    @BeforeEach
    void setUp() {
        adapter = new EmailNotificationAdapter();
    }

    @Test
    void send_whenNotificationIsValid_shouldSendEmailAndReturnSuccess() {
        // Arrange
        Notification notification = Notification.builder()
                .userId(USER_ID)
                .message(MESSAGE)
                .channel(NotificationChannels.EMAIL)
                .build();

        try (
                MockedStatic<EmailSender> emailSenderMock = mockStatic(EmailSender.class)) {
            // Act
            DeliveryResult result = adapter.send(notification, FORMATTED_MESSAGE);

            // Assert
            assertTrue(result.isSuccessful());
            assertEquals("Email sent to " + EXPECTED_EMAIL, result.getDetail());

            emailSenderMock.verify(() -> EmailSender.send(EXPECTED_EMAIL, FORMATTED_MESSAGE));
        }
    }
}