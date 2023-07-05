package com.sgcib.kata.exception;

/**
 * Exception indiquant qu'une erreur technique (base indisponible, problème
 * d'accès au filesystem, etc.) s'est produite (de type <em>checked</em>,
 * impliquant une gestion explicite, même si elle enveloppe typiquement une
 * exception de type {@link RuntimeException}).
 * 
 * @author Christophe Moitel
 */
public class AccountTechnicalException extends Exception {

	/**
	 * Identifiant de sérialisation.
	 */
	private static final long serialVersionUID = 910328936300854347L;

	public AccountTechnicalException() {
		super();
	}

	public AccountTechnicalException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AccountTechnicalException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountTechnicalException(String message) {
		super(message);
	}

	public AccountTechnicalException(Throwable cause) {
		super(cause);
	}
}
