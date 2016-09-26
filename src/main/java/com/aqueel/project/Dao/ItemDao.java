package com.aqueel.project.Dao;

import com.aqueel.project.Exc.DaoException;
import com.aqueel.project.Models.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aqueelmiqdad on 9/24/16.
 */
public interface ItemDao {

    List<Item> find(int oid) throws DaoException;
    int add(Item i) throws DaoException;

}
