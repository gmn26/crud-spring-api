package com.gmn26.crud.spring.api.bean.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private UUID id;
    private UUID userId;
    private String userName;
    private Double amount;
    private String description;
    private String status;
    private String approvedBy;
    private Date createdAt;
    private Date approvedAt;
}
