package com.aqueel.project.Exc;

/**
 * Created by aqueelmiqdad on 4/10/16.
 */

public class DaoException extends Exception {

    private final Exception original;

    public DaoException(Exception original, String msg) {
        super(msg);
        this.original = original;
    }

}
