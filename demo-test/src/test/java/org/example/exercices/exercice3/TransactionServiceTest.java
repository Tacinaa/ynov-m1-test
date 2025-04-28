package org.example.exercices.exercice3;

import org.example.exception.NotEnoughMoneyException;
import org.example.model.Account;
import org.example.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionServiceTest {
    private TransactionService transactionService;
    private Account from;
    private Account to;

    @BeforeEach
    void init() {
        transactionService = new TransactionService();
        from = new Account("1", 500);
        to = new Account("2", 300);
    }

    @Test
    void transfert_reussi_quand_toutes_les_conditions_sont_remplies() {
        boolean result = transactionService.transfer(from, to, 100);

        assertAll(
                () -> assertTrue(result),
                () -> assertEquals(400, from.getBalance()),
                () -> assertEquals(400, to.getBalance())
        );
    }

    @Test
    void transfert_echoue_si_compte_emetteur_est_null() {
        assertThrows(IllegalArgumentException.class, () ->
                transactionService.transfer(null, to, 100)
        );
    }

    @Test
    void transfert_echoue_si_compte_destinataire_est_null() {
        assertThrows(IllegalArgumentException.class, () ->
                transactionService.transfer(from, null, 100)
        );
    }

    @Test
    void transfert_echoue_si_montant_est_null() {
        assertThrows(IllegalArgumentException.class, () ->
                transactionService.transfer(from, to, 0)
        );
    }

    @Test
    void transfert_echoue_si_montant_est_negatif() {
        assertThrows(IllegalArgumentException.class, () ->
                transactionService.transfer(from, to, -50)
        );
    }

    @Test
    void transfert_echoue_si_solde_insuffisant() {
        assertThrows(NotEnoughMoneyException.class, () ->
                transactionService.transfer(from, to, 600)
        );
    }

    @Test
    @Timeout(1)
    void transfert_doit_se_terminer_en_moins_dune_seconde() {
        boolean result = transactionService.transfer(from, to, 100);
        assertTrue(result);
    }

    @ParameterizedTest
    @ValueSource(doubles = {100, 200, 50, -10})
    void testTransfertMixte(double montant) {
        if (montant > 0) {
            boolean result = transactionService.transfer(from, to, montant);
            assertTrue(result);
        } else {
            assertThrows(IllegalArgumentException.class, () ->
                    transactionService.transfer(from, to, montant)
            );
        }
    }
}
