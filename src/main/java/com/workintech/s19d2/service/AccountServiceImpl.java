package com.workintech.s19d2.service;

import com.workintech.s19d2.repository.AccountRepository;
import com.workintech.s19d2.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService{
    private AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> {
                    System.out.println("Bulunamadı");
                    throw new UsernameNotFoundException("Bulunamadı");
                });
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account update(Long id, Account account) {
        Account account1 = accountRepository.findById(id)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("Kullanıcı bulunamadı");
                });
        return accountRepository.save(account1);
    }

    @Override
    public Account delete(Long id) {
        Account account1 = accountRepository.findById(id)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("Kullanıcı bulunamadı");
                });
        accountRepository.delete(account1);
        return account1;
    }
}
