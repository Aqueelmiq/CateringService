package com.aqueel.project.Dao;


import com.aqueel.project.Exc.DaoException;
import com.aqueel.project.Models.Food;

import java.util.List;

/**
 * Created by aqueelmiqdad on 9/23/16.
 */
public interface FoodDao {

    List<Food> findAll() throws DaoException;

    Food findById(int id) throws DaoException;

    int add(Food f) throws DaoException;

    int update(int fid, double price) throws DaoException;


}
