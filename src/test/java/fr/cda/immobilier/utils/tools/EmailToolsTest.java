package fr.cda.immobilier.utils.tools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Captor;
import sibApi.TransactionalEmailsApi;
import sibModel.CreateSmtpEmail;
import sibModel.SendSmtpEmail;
import sibModel.SendSmtpEmailAttachment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EmailToolsTest {

    @Mock
    private TransactionalEmailsApi transactionalEmailsApi;
    @Captor
    private ArgumentCaptor<SendSmtpEmail> captor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        EmailTools.getInstance().setKey("your-api-key");
    }

    @Test
    void testSendEmail() {
        Map<String, String> sendTo = new HashMap<>();
        sendTo.put("email", "test@example.com");
        sendTo.put("name", "Test User");
        Boolean isSeloger = true;
        Boolean isOuestFrance = true;

        try {
            doReturn(new CreateSmtpEmail()).when(transactionalEmailsApi).sendTransacEmail(any(SendSmtpEmail.class));
            EmailTools.getInstance().sendEmail(sendTo, "src/test/resources/annonces.txt", isSeloger, isOuestFrance);

            // Verify that sendTransacEmail was called with any SendSmtpEmail object
            verify(transactionalEmailsApi).sendTransacEmail(any(SendSmtpEmail.class));

            // Capture the argument passed to sendTransacEmail
            verify(transactionalEmailsApi).sendTransacEmail(captor.capture());
            SendSmtpEmail capturedEmail = captor.getValue();

            // Retrieve the attachment from the captured email
            List<SendSmtpEmailAttachment> attachments = capturedEmail.getAttachment();

            // Assert the properties of the attachment
            assertEquals(1, attachments.size());
            SendSmtpEmailAttachment attachment = attachments.get(0);
            assertEquals("annonce.txt", attachment.getName());
        } catch (Exception e) {
            LoggerTools.logInfo("Class EmailsToolsTest : erreur dans le test de la méthode sendEmail()", e.getCause());
        }
    }
}
