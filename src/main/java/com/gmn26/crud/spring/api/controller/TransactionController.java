package com.gmn26.crud.spring.api.controller;

import com.gmn26.crud.spring.api.bean.WebResponse;
import com.gmn26.crud.spring.api.bean.transaction.CreateTransactionDTO;
import com.gmn26.crud.spring.api.bean.transaction.TransactionResponse;
import com.gmn26.crud.spring.api.entity.UserEntity;
import com.gmn26.crud.spring.api.service.TransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transaction")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<List<TransactionResponse>>> fetchAllTransactions() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) auth.getPrincipal();
        List<TransactionResponse> response = transactionService.findAll(user);

        WebResponse<List<TransactionResponse>> webResponse = WebResponse.<List<TransactionResponse>>builder()
                .success(true)
                .message("Data fetched successfully")
                .data(response)
                .build();

        return new ResponseEntity<>(webResponse, HttpStatus.OK);
    }

    @PostMapping(path="/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> createTransaction(@RequestBody CreateTransactionDTO transactionDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) auth.getPrincipal();
        String response = transactionService.create(user, transactionDTO);

        WebResponse<String> webResponse = WebResponse.<String>builder()
                .success(true)
                .message(response)
                .data(null)
                .build();

        return ResponseEntity.ok(webResponse);
    }

    @PutMapping(path="/approve/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> approveTransaction(@PathVariable UUID id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) auth.getPrincipal();

        System.out.println(user.getRoles());

        String response = transactionService.approve(user, id);

        if(response.equals("You do not have permission to approve this transaction")){
            WebResponse<String> webResponse = WebResponse.<String>builder()
                    .success(true)
                    .message(response)
                    .data(null)
                    .build();

            return new ResponseEntity<>(webResponse, HttpStatus.FORBIDDEN);
        } else if (response.equals("Transaction Not Found")) {
            WebResponse<String> webResponse = WebResponse.<String>builder()
                    .success(false)
                    .message(response)
                    .data(null)
                    .build();

            return new ResponseEntity<>(webResponse, HttpStatus.NOT_FOUND);
        } else{
            WebResponse<String> webResponse = WebResponse.<String>builder()
                    .success(true)
                    .message(response)
                    .data(null)
                    .build();

            return ResponseEntity.ok(webResponse);
        }
    }

}
