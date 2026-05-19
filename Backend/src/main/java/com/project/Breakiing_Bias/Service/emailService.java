import sibApi.TransactionalEmailsApi;
import sibModel.SendSmtpEmail;
import sibModel.SendSmtpEmailTo;
import ApiClient;
import ApiException;

@Service
public class emailService {

    @Value("${brevo.api.key}")
    private String brevoApiKey;

    public void linkSender(Users user, String token) {
        try {
            ApiClient client = new ApiClient();
            client.setApiKey(brevoApiKey);

            TransactionalEmailsApi api = new TransactionalEmailsApi(client);

            SendSmtpEmailTo to = new SendSmtpEmailTo();
            to.setEmail(user.getEmail());
            to.setName(user.getUsername());

            SendSmtpEmail email = new SendSmtpEmail();
            email.setTo(List.of(to));
            email.setSender(new sibModel.SendSmtpEmailSender()
                    .email("abcae3001@smtp-brevo.com")
                    .name("Breaking Bias"));
            email.setSubject("Email Verification");
            email.setTextContent("Click to verify: " + 
                "https://breaking-bias-production.up.railway.app/api/auth/verify?token=" + token);

            api.sendTransacEmail(email);
        } catch (ApiException e) {
            throw new RuntimeException("Email failed: " + e.getMessage());
        }
    }
}
