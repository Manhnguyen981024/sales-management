package com.servicemanagement.notificationservice.service;

import com.servicemanagement.notificationservice.dto.OrderResponseDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
@AllArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private TemplateEngine templateEngine;

    public void sendOrderConfirmation(String to, String customerName, String orderId) {
        Context context = new Context();
        context.setVariable("customerName", customerName);
        context.setVariable("orderId", orderId);
        context.setVariable("orderLink", "https://myshop.vn/orders/" + orderId);

        // Render HTML từ template
        String htmlContent = templateEngine.process("order-confirmation", context);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("Xác nhận đơn hàng #" + orderId);
            helper.setText(htmlContent, true); // true = gửi HTML

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Lỗi gửi email", e);
        }
    }

    public void sendOrderConfirmation(Map<String, Object> event) {
        sendOrderConfirmation(
                event.get("email").toString(),
                event.get("customerName").toString(),
                event.get("orderId").toString()
        );

    }
}
