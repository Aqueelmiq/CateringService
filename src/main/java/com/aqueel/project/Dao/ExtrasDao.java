package com.aqueel.project.Dao;

import com.aqueel.project.Exc.DaoException;

/**
 * Created by aqueelmiqdad on 9/25/16.
 */
public interface ExtrasDao {

    int update(String name, double amt) throws DaoException;
    double get(String name) throws DaoException;

}
