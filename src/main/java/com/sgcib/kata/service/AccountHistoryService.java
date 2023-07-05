package com.sgcib.kata.service;

import java.util.Stack;

import com.sgcib.kata.bean.OperationBean;

/**
 * Service de gestion de l'historique des comptes client.
 * 
 * @author Christophe Moitel
 */
public interface AccountHistoryService {

	/**
	 * Retourne l'ensemble des opération réalisées par un client, dans l'ordre
	 * antichronologique.
	 * 
	 * @param accountNumber Numéro de compte client.
	 * @return L'ensemble des opérations, de la plus récente à la plus ancienne.
	 */
	Stack<OperationBean> getHistory(Long accountNumber);
}
