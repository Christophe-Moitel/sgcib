package com.sgcib.kata.service.impl;

import java.math.BigDecimal;
import java.util.Stack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sgcib.kata.bean.OperationBean;
import com.sgcib.kata.bean.OperationType;
import com.sgcib.kata.exception.AccountFunctionalException;
import com.sgcib.kata.exception.AccountTechnicalException;
import com.sgcib.kata.service.AccountHistoryService;
import com.sgcib.kata.service.AccountWithdrawalService;

/**
 * Implémentation de {@link AccountWithdrawalService}, reposant essentiellement
 * sur le service de gestion de l'historique.
 * 
 * @author Christophe Moitel
 */
public class AccountWithdrawalServiceImpl implements AccountWithdrawalService {

	/**
	 * Objet de journalisation.
	 */
	private static final Logger logger = LogManager.getLogger();

	/**
	 * Service de gestion de l'historique.
	 */
	private AccountHistoryService accountHistoryService;

	/**
	 * Méthode d'injection du service de gestion de l'historique.
	 * 
	 * @param accountHistoryService Service de gestion de l'historique à injecter.
	 */
	public AccountWithdrawalServiceImpl(AccountHistoryService accountHistoryService) {
		this.accountHistoryService = accountHistoryService;
	}

	@Override
	public void withdraw(Long accountNumber, BigDecimal amount)
			throws AccountFunctionalException, AccountTechnicalException {
		logger.info("Client number {} is making a withdrawal of {}", accountNumber, amount);

		Stack<OperationBean> history = accountHistoryService.getHistory(accountNumber);

		if (history == null) {
			throw new AccountFunctionalException(String.format("Account %d does not exist", accountNumber));
		}

		BigDecimal lastBalance = BigDecimal.ZERO;

		if (!history.isEmpty()) {
			logger.debug("{} operation(s) already registered for client {}", history.size(), accountNumber);

			OperationBean lastOperation = history.peek();
			lastBalance = lastOperation.getBalance();
		}

		logger.debug("Withdrawing the amount {} from balance {}", amount, lastBalance);

		history.push(new OperationBean(OperationType.WITHDRAWAL, amount, lastBalance.subtract(amount)));

		logger.info("New balance for client number {} : {}", accountNumber, history.peek().getBalance());
	}
}
