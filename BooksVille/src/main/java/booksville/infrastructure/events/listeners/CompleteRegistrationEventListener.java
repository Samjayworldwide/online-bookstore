package booksville.infrastructure.events.listeners;

import booksville.infrastructure.events.event.CompleteRegistrationEvent;
import booksville.infrastructure.security.JWTGenerator;
import booksville.services.EmailSenderService;
import booksville.utils.SecurityConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Async
@Component
@RequiredArgsConstructor
public class CompleteRegistrationEventListener implements ApplicationListener<CompleteRegistrationEvent> {
    private final EmailSenderService emailSenderService;
    private final JWTGenerator tokenGenerator;

    @Override
    public void onApplicationEvent(CompleteRegistrationEvent event) {
        // Create a verification token for the user
        String verificationToken = tokenGenerator.generateSignUpVerificationToken(event.getEmail(), SecurityConstants.JWT_SIGNUP_EXPIRATION);

        // Build the verification url to be sent to the user
        String url = event.getApplicationUrl() + "/auth/verify-email?email=" + event.getEmail() + "&token=" + verificationToken;

        // Send the email to the user
        emailSenderService.sendRegistrationEmailVerification(url, event.getEmail(), event.getFirstName());

        log.info("Click the link to verify your email and change ur password : {}", url);
    }
}
