package aubay.sibs.service;

import aubay.sibs.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final Logger logger = LogManager.getLogger(EmailService.class);
    public void sendEmail(String email, User user){
        logger.info("email sent to email:" + user.getEmail());
    }
}
