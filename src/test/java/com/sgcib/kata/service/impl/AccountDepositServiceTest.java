package com.sgcib.kata.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sgcib.kata.bean.OperationBean;
import com.sgcib.kata.bean.OperationType;
import com.sgcib.kata.exception.AccountFunctionalException;
import com.sgcib.kata.service.AccountDepositService;
import com.sgcib.kata.service.AccountHistoryService;

/**
 * Classe de test du service {@link AccountDepositService}.
 * 
 * @author Christophe Moitel
 */
public class AccountDepositServiceTest {

	private AccountDepositService accountDepositService;
	private AccountHistoryService accountHistoryService;

	@BeforeEach
	void setUp() {
		accountHistoryService = new AccountHistoryServiceImpl(12345l, 88888l);
		accountDepositService = new AccountDepositServiceImpl(accountHistoryService);
	}

	/**
	 * Test nominal du service (dépôt d'1 montant, sans erreur, avec solde
	 * correspondant à ce montant).
	 */
	@Test
	void testDeposit01() throws Exception {
		Long accountNumber = 12345l;

		assertDoesNotThrow(() -> accountDepositService.deposit(accountNumber, BigDecimal.valueOf(123.45)),
				"Aucune erreur n'est attendue");

		List<OperationBean> history = accountHistoryService.getHistory(accountNumber);

		assertNotNull(history, "Une opération au-moins devrait exister");
		assertEquals(1, history.size(), "Une opération exactement devrait exister");

		OperationBean operation = history.get(0);

		assertEquals(OperationType.DEPOSIT, operation.getOperationType(),
				"Le type de l'opération n'est pas celui attendu");
		assertNotNull(operation.getAmount(), "Le montant de l'opération ne devrait pas être nul");
		assertEquals(BigDecimal.valueOf(123.45), operation.getAmount(),
				"Le montant de l'opération n'est pas celui attendu");
		assertNotNull(operation.getBalance(), "Le montant de l'opération ne devrait pas être nul");
		assertEquals(BigDecimal.valueOf(123.45), operation.getBalance(),
				"Le solde après opération n'est pas celui attendu");
	}

	/**
	 * Test nominal du service (dépôt de 2 montants, sans erreur, avec solde
	 * correspondant à la somme des 2).
	 */
	@Test
	void testDeposit02() throws Exception {
		Long accountNumber = 12345l;

		assertDoesNotThrow(() -> accountDepositService.deposit(accountNumber, BigDecimal.valueOf(10.01)),
				"Aucune erreur n'est attendue");
		assertDoesNotThrow(() -> accountDepositService.deposit(accountNumber, BigDecimal.valueOf(100.1)),
				"Aucune erreur n'est attendue");

		List<OperationBean> history = accountHistoryService.getHistory(accountNumber);

		assertNotNull(history, "Une opération au-moins devrait exister");
		assertEquals(2, history.size(), "Une opération exactement devrait exister");

		OperationBean operation1 = history.get(0);

		assertEquals(OperationType.DEPOSIT, operation1.getOperationType(),
				"Le type de l'opération n'est pas celui attendu");
		assertNotNull(operation1.getAmount(), "Le montant de l'opération ne devrait pas être nul");
		assertEquals(BigDecimal.valueOf(010.01), operation1.getAmount(),
				"Le montant de l'opération n'est pas celui attendu");
		assertNotNull(operation1.getBalance(), "Le montant de l'opération ne devrait pas être nul");
		assertEquals(BigDecimal.valueOf(010.01), operation1.getBalance(),
				"Le solde après opération n'est pas celui attendu");

		OperationBean operation2 = history.get(1);

		assertEquals(OperationType.DEPOSIT, operation2.getOperationType(),
				"Le type de l'opération n'est pas celui attendu");
		assertNotNull(operation2.getAmount(), "Le montant de l'opération ne devrait pas être nul");
		assertEquals(BigDecimal.valueOf(100.1), operation2.getAmount(),
				"Le montant de l'opération n'est pas celui attendu");
		assertNotNull(operation2.getBalance(), "Le montant de l'opération ne devrait pas être nul");
		assertEquals(BigDecimal.valueOf(110.11), operation2.getBalance(),
				"Le solde après opération n'est pas celui attendu");
	}

	/**
	 * Test du service avec erreur (absence du compte client).
	 */
	@Test
	void testDeposit03() throws Exception {
		Long accountNumber = 12345l;

		// on supprime le compte client de la base
		((AccountHistoryServiceImpl) accountHistoryService).clientAccounts.remove(accountNumber);

		assertThrows(AccountFunctionalException.class,
				() -> accountDepositService.deposit(accountNumber, BigDecimal.valueOf(123.45)),
				"Une erreur devrait être remontée indiquant l'absence de compte");
	}
}
