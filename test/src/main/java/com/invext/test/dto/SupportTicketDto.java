package com.invext.test.dto;

import com.invext.test.enums.TicketStatus;
import com.invext.test.enums.TicketType;
import com.invext.test.model.User;

import java.time.LocalDateTime;

public record SupportTicketDto(long id, User owner, TicketType ticketType,
                               TicketStatus ticketStatus, LocalDateTime createdAt,
                               LocalDateTime updatedAt) {

}
