package com.sgcib.kata.service;

import java.math.BigDecimal;

import com.sgcib.kata.exception.AccountFunctionalException;
import com.sgcib.kata.exception.AccountTechnicalException;

/**
 * Service de gestion des dépôts sur compte client.
 * 
 * @author Christophe Moitel
 */
public interface AccountDepositService {

	/**
	 * Méthode utilisée pour effectuer un dépôt sur un compte client.
	 * 
	 * @param accountNumber Numéro de compte client.
	 * @param amount        Montant à déposer.
	 * @throws AccountFunctionalException En cas d'erreur fonctionnelle, par
	 *                                    exemple, si le compte n'existe pas.
	 * @throws AccountTechnicalException  En cas d'erreur technique, par exemple si
	 *                                    la base est indisponible.
	 */
	void deposit(Long accountNumber, BigDecimal amount) throws AccountFunctionalException, AccountTechnicalException;
}
