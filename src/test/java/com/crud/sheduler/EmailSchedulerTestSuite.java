package com.crud.sheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.schelduler.EmailScheduler;
import com.crud.tasks.service.SimpleEmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailSchedulerTestSuite {

    @InjectMocks
    private EmailScheduler emailScheduler;

    @Mock
    private SimpleEmailService emailService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private AdminConfig adminConfig;

    @Mock
    private Mail mail;

    @Captor
    ArgumentCaptor<Mail> mailArgumentCaptor;

    @Test
    public void shouldEmailServiceSendCall() {
        //Given
        //When
        doNothing().when(emailService).send(isA(Mail.class));
        emailService.send(mail);
        //Then
        verify(emailService).send(mail);

    }

    @Test
    public void shouldTaskRepositoryCountTasks() {
        //Given
        long quantityTasks = 0;
        //When
        when(taskRepository.count()).thenReturn(quantityTasks + 1);
        quantityTasks = taskRepository.count();
        //Then
        assertEquals(1, quantityTasks);
    }

    @Test
    public void shouldEmailSendToAdmin() {
        //Given
        String adminMail = "admin@post.com";
        //When
        when(adminConfig.getAdminMail()).thenReturn(adminMail);
        adminConfig.getAdminMail();
        //Then
        verify(adminConfig).getAdminMail();
    }

    @Test
    public void shouldSentInformationEmailBeSend() {
        //Given
        Mail expectMail = new Mail("address", "Tasks: Once a day email", "Currently in database you got: 2 tasks");
        when(taskRepository.count()).thenReturn(2L);
        when(adminConfig.getAdminMail()).thenReturn("address");
        doNothing().when(emailService).send(any());
        //When
        emailScheduler.sendInformationEmail();
        //Then
        verify(emailService).send(mailArgumentCaptor.capture());
        Mail actualMail = mailArgumentCaptor.getValue();
        assertEquals(expectMail, actualMail);

    }
}
