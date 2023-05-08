package space.cuongnh2k.core.utils;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class SendEmailUtil {
    private final JavaMailSender javaMailSender;

    @Async
    public void activateAccount(String to, String cid, String code) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            message.setContent("<!DOCTYPE html>\n" +
                    "<html lang=\"vi-VN\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\"\n" +
                    "          content=\"width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0\">\n" +
                    "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                    "    <title>Kích hoạt tài khoản</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<div style=\"margin: 100px auto; width: 300px;\">\n" +
                    "    <a href=\"https://cuongnh2k.space/activate?cid=" + cid + "&code=" + code + "\" target=\"_blank\">\n" +
                    "        <button style=\" height: 50px;\n" +
                    "                        width: 100%;\n" +
                    "                        color: white;\n" +
                    "                        background-color: forestgreen;\n" +
                    "                        border-radius: 5px;\n" +
                    "                        font-size: 30px;\n" +
                    "                        cursor: pointer;\n" +
                    "                        border: none;\">\n" +
                    "            Kích hoạt tài khoản\n" +
                    "        </button>\n" +
                    "    </a>\n" +
                    "    <p style=\"margin-top: 50px;\">Mã kích hoạt:</p>\n" +
                    "    <p style=\"margin-top: 20px;\">" + cid + "/" + code + "</p>\n" +
                    "</div>\n" +
                    "</body>\n" +
                    "</html>", "text/html; charset=UTF-8");
            helper.setTo(to);
            helper.setSubject("Kích hoạt tài khoản");
            javaMailSender.send(message);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Async
    public void activateDevice(String to, String deviceId, String code) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            message.setContent("<!DOCTYPE html>\n" +
                    "<html lang=\"vi-VN\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\"\n" +
                    "          content=\"width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0\">\n" +
                    "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                    "    <title>Kích hoạt thiết bị</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<div style=\"margin: 100px auto; width: 300px;\">\n" +
                    "    <a href=\"https://cuongnh2k.space/activate?deviceId=" + deviceId + "&code=" + code + "\" target=\"_blank\">\n" +
                    "        <button style=\" height: 50px;\n" +
                    "                        width: 100%;\n" +
                    "                        color: white;\n" +
                    "                        background-color: forestgreen;\n" +
                    "                        border-radius: 5px;\n" +
                    "                        font-size: 30px;\n" +
                    "                        cursor: pointer;\n" +
                    "                        border: none;\">\n" +
                    "            Kích hoạt thiết bị\n" +
                    "        </button>\n" +
                    "    </a>\n" +
                    "    <p style=\"margin-top: 50px;\">Mã kích hoạt:</p>\n" +
                    "    <p style=\"margin-top: 20px;\">" + deviceId + "/" + code + "</p>\n" +
                    "</div>\n" +
                    "</body>\n" +
                    "</html>", "text/html; charset=UTF-8");
            helper.setTo(to);
            helper.setSubject("Kích hoạt thiết bị");
            javaMailSender.send(message);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Async
    public void confirmResetPassword(String to, String cid, String code) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            message.setContent("<!DOCTYPE html>\n" +
                    "<html lang=\"vi-VN\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\"\n" +
                    "          content=\"width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0\">\n" +
                    "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                    "    <title>Xác nhận thiết lập lại mật khẩu</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<div style=\"margin: 100px auto; width: 300px;\">\n" +
                    "    <a href=\"https://cuongnh2k.space/confirm?cid=" + cid + "&code=" + code + "\" target=\"_blank\">\n" +
                    "        <button style=\" height: 50px;\n" +
                    "                        width: 100%;\n" +
                    "                        color: white;\n" +
                    "                        background-color: forestgreen;\n" +
                    "                        border-radius: 5px;\n" +
                    "                        font-size: 20px;\n" +
                    "                        cursor: pointer;\n" +
                    "                        border: none;\">\n" +
                    "            Xác nhận thiết lập lại mật khẩu\n" +
                    "        </button>\n" +
                    "    </a>\n" +
                    "    <p style=\"margin-top: 50px;\">Mã xác nhận:</p>\n" +
                    "    <p style=\"margin-top: 20px;\">" + cid + "/" + code + "</p>\n" +
                    "</div>\n" +
                    "</body>\n" +
                    "</html>", "text/html; charset=UTF-8");
            helper.setTo(to);
            helper.setSubject("Xác nhận thiết lập lại mật khẩu");
            javaMailSender.send(message);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
