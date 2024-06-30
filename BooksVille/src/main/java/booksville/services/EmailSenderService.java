package booksville.services;

import booksville.infrastructure.events.event.ForgotPasswordEvent;

public interface EmailSenderService {
    void sendNotificationEmail(String url, String email, String firstName, String subject, String description);
    void sendRegistrationEmailVerification(String url, String email, String firstName);

    void sendForgotPasswordEmailVerification(String url, ForgotPasswordEvent event);
}
