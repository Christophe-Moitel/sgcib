package com.sgcib.kata.service;

import java.math.BigDecimal;

import com.sgcib.kata.exception.AccountFunctionalException;
import com.sgcib.kata.exception.AccountTechnicalException;

/**
 * Service de gestion des retraits sur compte client.
 * 
 * @author Christophe Moitel
 */
public interface AccountWithdrawalService {

	/**
	 * Méthode utilisée pour effectuer un retrait sur un compte client.
	 * 
	 * @param accountNumber Numéro de compte client.
	 * @param amount        Montant à retirer.
	 * @throws AccountFunctionalException En cas d'erreur fonctionnelle, par
	 *                                    exemple, si le compte n'existe pas.
	 * @throws AccountTechnicalException  En cas d'erreur technique, par exemple si
	 *                                    la base est indisponible.
	 */
	void withdraw(Long accountNumber, BigDecimal amount) throws AccountFunctionalException, AccountTechnicalException;
}
