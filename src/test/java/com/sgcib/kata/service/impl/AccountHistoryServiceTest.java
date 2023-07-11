package com.sgcib.kata.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sgcib.kata.bean.OperationBean;
import com.sgcib.kata.bean.OperationType;
import com.sgcib.kata.service.AccountHistoryService;

/**
 * Classe de test du service {@link AccountHistoryService}.
 * 
 * @author Christophe Moitel
 */
public class AccountHistoryServiceTest {

	private AccountHistoryService accountHistoryService;

	@BeforeEach
	void setUp() {
		accountHistoryService = new AccountHistoryServiceImpl(12345l, 88888l);
	}

	/**
	 * Test du service de récupération des opérations.
	 */
	@Test
	void testGet() throws Exception {
		Long accountNumber = 12345l;

		// peuplement des opérations en "base"
		((AccountHistoryServiceImpl) accountHistoryService).clientAccounts.get(accountNumber)
				.addAll(List.of(
						new OperationBean(OperationType.DEPOSIT, BigDecimal.valueOf(10.), BigDecimal.valueOf(100.),
								LocalDateTime.of(2023, 7, 11, 15, 30)),
						new OperationBean(OperationType.WITHDRAWAL, BigDecimal.valueOf(5.), BigDecimal.valueOf(95.),
								LocalDateTime.of(2023, 7, 11, 15, 31))));

		List<OperationBean> history = accountHistoryService.getHistory(accountNumber);

		assertNotNull(history, "Une opération au-moins devrait exister");
		assertEquals(2, history.size(), "Une opération exactement devrait exister");

		OperationBean operation1 = history.get(0);

		assertEquals(OperationType.DEPOSIT, operation1.getOperationType(),
				"Le type de l'opération n'est pas celui attendu");
		assertNotNull(operation1.getAmount(), "Le montant de l'opération ne devrait pas être nul");
		assertEquals(BigDecimal.valueOf(10.), operation1.getAmount(),
				"Le montant de l'opération n'est pas celui attendu");
		assertNotNull(operation1.getBalance(), "Le montant de l'opération ne devrait pas être nul");
		assertEquals(BigDecimal.valueOf(100.), operation1.getBalance(),
				"Le solde après opération n'est pas celui attendu");
		assertEquals(LocalDateTime.of(2023, 7, 11, 15, 30), operation1.getDate(),
				"La date de l'opération n'est pas celle attendue");

		OperationBean operation2 = history.get(1);

		assertEquals(OperationType.WITHDRAWAL, operation2.getOperationType(),
				"Le type de l'opération n'est pas celui attendu");
		assertNotNull(operation2.getAmount(), "Le montant de l'opération ne devrait pas être nul");
		assertEquals(BigDecimal.valueOf(5.), operation2.getAmount(),
				"Le montant de l'opération n'est pas celui attendu");
		assertNotNull(operation2.getBalance(), "Le montant de l'opération ne devrait pas être nul");
		assertEquals(BigDecimal.valueOf(95.), operation2.getBalance(),
				"Le solde après opération n'est pas celui attendu");
		assertEquals(LocalDateTime.of(2023, 7, 11, 15, 31), operation2.getDate(),
				"La date de l'opération n'est pas celle attendue");
	}
}
