package com.aqueel.project.Dao;

import com.aqueel.project.Exc.DaoException;
import com.aqueel.project.Models.Category;

import java.util.List;

/**
 * Created by aqueelmiqdad on 9/24/16.
 */
public interface CategoryDao {

    List<Category> find(int food_id) throws DaoException;
    int add(Category c) throws DaoException;

}
