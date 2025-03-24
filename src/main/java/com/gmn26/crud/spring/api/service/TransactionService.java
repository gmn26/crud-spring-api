package com.gmn26.crud.spring.api.service;

import com.gmn26.crud.spring.api.bean.transaction.CreateTransactionDTO;
import com.gmn26.crud.spring.api.bean.transaction.TransactionResponse;
import com.gmn26.crud.spring.api.entity.QTransactionEntity;
import com.gmn26.crud.spring.api.entity.TransactionEntity;
import com.gmn26.crud.spring.api.entity.UserEntity;
import com.gmn26.crud.spring.api.repository.TransactionRepository;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final JPAQueryFactory queryFactory;
    private final TransactionRepository transactionRepository;

    public TransactionService(JPAQueryFactory queryFactory, TransactionRepository transactionRepository) {
        this.queryFactory = queryFactory;
        this.transactionRepository = transactionRepository;
    }

    private TransactionResponse toTransactionResponse(TransactionEntity transactionEntity) {
        return TransactionResponse.builder()
                .id(transactionEntity.getId())
                .userId(transactionEntity.getUser().getId())
                .userName(transactionEntity.getUser().getName())
                .amount(transactionEntity.getAmount())
                .description(transactionEntity.getDescription())
                .status(transactionEntity.getStatus())
                .approvedBy(transactionEntity.getApprovedBy() != null ? transactionEntity.getApprovedBy().getName() : null)
                .createdAt(transactionEntity.getCreatedAt())
                .approvedAt(transactionEntity.getApprovedAt())
                .build();
    }

    public List<TransactionResponse> findAll(UserEntity user) {
        String role = user.getRoles();

        QTransactionEntity qTransaction = QTransactionEntity.transactionEntity;

        JPAQuery<TransactionEntity> query = queryFactory.select(qTransaction);

        query.from(qTransaction);

        if(!role.equals("Admin")) {
            query.where(qTransaction.user.id.eq(user.getId()));
        }

        List<TransactionEntity> transactions = query.fetch();

        return transactions.stream().map(this::toTransactionResponse).collect(Collectors.toList());
    }

    public String create(UserEntity user, CreateTransactionDTO request) {
        TransactionEntity transactionEntity = new TransactionEntity();

        transactionEntity.setUser(user);
        transactionEntity.setAmount(request.getAmount());
        transactionEntity.setDescription(request.getDescription());

        transactionRepository.save(transactionEntity);

        return "Transaction Created";
    }

    @Transactional
    public String approve(UserEntity user, UUID transactionId) {
        String role = user.getRoles();

        if(!role.equals("Admin")) {
            return "You do not have permission to approve this transaction";
        }

        QTransactionEntity qTransaction = QTransactionEntity.transactionEntity;

        TransactionEntity transaction = queryFactory
                .selectFrom(qTransaction)
                .where(qTransaction.id.eq(transactionId))
                .fetchOne();

        if(transaction == null) {
            return "Transaction Not Found";
        }

        if(transaction.getApprovedAt() != null) {
            return "Transaction Already Approved";
        }

        queryFactory.update(qTransaction).where(qTransaction.id.eq(transactionId))
                .set(qTransaction.status, "approved")
                .set(qTransaction.approvedAt, new Date())
                .set(qTransaction.approvedBy, user)
                .execute();

        return "Transaction Approved";
    }

}
