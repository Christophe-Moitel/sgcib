package com.sgcib.kata.exception;

/**
 * Exception indiquant qu'une erreur fonctionnelle s'est produite (de type
 * <em>checked</em>, impliquant une gestion explicite).
 * 
 * @author Christophe Moitel
 */
public class AccountFunctionalException extends Exception {

	/**
	 * Identifiant de sérialisation.
	 */
	private static final long serialVersionUID = 3169486260021414571L;

	public AccountFunctionalException() {
		super();
	}

	public AccountFunctionalException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AccountFunctionalException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountFunctionalException(String message) {
		super(message);
	}

	public AccountFunctionalException(Throwable cause) {
		super(cause);
	}
}
