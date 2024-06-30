package booksville.services.implementation;

import booksville.entities.model.UserEntity;
import booksville.infrastructure.events.event.ForgotPasswordEvent;
import booksville.repositories.UserEntityRepository;
import booksville.services.EmailSenderService;
import booksville.utils.HelperClass;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EmailSenderServiceImpl implements EmailSenderService {
    private final JavaMailSender mailSender;
    private final HelperClass helperClass;


    @Value("${spring.mail.username}")
    private String senderMail;
    private final UserEntityRepository userEntityRepository;

    @Override
    public void sendNotificationEmail(String url,
                                      String email,
                                      String firstName,
                                      String subject,
                                      String description) {

        String action = "Contact Us";
        String serviceProvider = "BooksVille Customer Service";

        helperClass.sendNotificationEmail(
                firstName,
                url,
                mailSender,
                senderMail,
                email,
                action,
                serviceProvider,
                subject,
                description
        );
    }

    @Override
    public void sendRegistrationEmailVerification(String url, String email, String firstName) {
        String action = "Verify Email";
        String serviceProvider = "BooksVille Registration Portal Service";
        String subject = "Email Verification";
        String description = "Please follow the link below to complete your registration.";

        helperClass.sendEmail(
                firstName,
                url,
                mailSender,
                senderMail,
                email,
                action,
                serviceProvider,
                subject,
                description
            );
    }

    @Override
    public void sendForgotPasswordEmailVerification(String url, ForgotPasswordEvent event) {
        Optional<UserEntity> optionalUser = userEntityRepository.findByEmail(event.getEmail());


        String action = "Change Password";
        String serviceProvider = "BooksVille Registration Portal Service";
        String subject = "Email Verification";
        String description = "Please follow the link below to change your password.";

        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();


            helperClass.sendEmail(
                    user.getFirstName(),
                    url,
                    mailSender,
                    senderMail,
                    event.getEmail(),
                    action,
                    serviceProvider,
                    subject,
                    description
            );
        }
    }
}
