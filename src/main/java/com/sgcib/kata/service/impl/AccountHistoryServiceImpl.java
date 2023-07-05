package com.sgcib.kata.service.impl;

import java.util.Hashtable;
import java.util.Map;
import java.util.Stack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sgcib.kata.bean.OperationBean;
import com.sgcib.kata.service.AccountHistoryService;

/**
 * Implémentation de {@link AccountHistoryService}, avec persistance en mémoire
 * afin de rendre le cas plus intéressant. La base de données fictive est rendue
 * accessible pour un contrôle dans les TU, qui normalement devraient y avoir
 * accès par un autre média.
 * 
 * @author Christophe Moitel
 */
public class AccountHistoryServiceImpl implements AccountHistoryService {

	/**
	 * Objet de journalisation.
	 */
	private static final Logger logger = LogManager.getLogger();

	/**
	 * Ensemble des comptes clients organisés par numéro de compte auxquels sont
	 * associées les opérations. De type {@link Hashtable} pour la gestion des accès
	 * concurrents.
	 */
	protected Map<Long, Stack<OperationBean>> clientAccounts = new Hashtable<>();

	/**
	 * Initialise la base de données mémoire avec des comptes clients.
	 * 
	 * @param accounts Ensemble des numéros de comptes clients à créer.
	 */
	public AccountHistoryServiceImpl(Long... accountNumbers) {
		for (var accountNumber : accountNumbers) {
			logger.trace("Registering client account number {}", accountNumber);

			clientAccounts.put(accountNumber, new Stack<>());
		}
	}

	@Override
	public Stack<OperationBean> getHistory(Long accountNumber) {
		return clientAccounts.get(accountNumber);
	}
}
