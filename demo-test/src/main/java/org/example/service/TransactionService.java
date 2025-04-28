package org.example.service;

import org.example.model.Account;
import org.example.exception.NotEnoughMoneyException;

public class TransactionService {

    public boolean transfer(Account from, Account to, double amount) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Les comptes ne doivent pas être nuls.");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Le montant doit être positif.");
        }

        if (from.getBalance() < amount) {
            throw new NotEnoughMoneyException("Pas assez d'argent sur le compte.");
        }

        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);

        return true;
    }
}