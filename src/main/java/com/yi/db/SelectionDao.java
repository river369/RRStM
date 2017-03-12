package com.yi.db;

import org.apache.ibatis.session.SqlSession;

/**
 * Created by jianguog on 17/3/12.
 */
public class SelectionDao {
    public void insertSelection(Selection selection){
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        session.insert("com.yi.db.SelectionItem.insertSelection", selection);
        session.commit();
        session.close();
    }

    public void insertSelectionItem(SelectionItem selectionItem){
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        session.insert("com.yi.db.SelectionItem.insertSelectionItems", selectionItem);
        session.commit();
        session.close();
    }

    public static void main(String[] args) {
        SelectionDao selectionDao = new SelectionDao();
        Selection selection = new Selection();
        selection.setSelection_id(3);
        selectionDao.insertSelection(selection);
        SelectionItem selectionItem = new SelectionItem();
        selectionItem.setSelection_id(3);
        selectionItem.setStock_id("SH001");
        selectionItem.setStock_name("北京首钢");
        selectionDao.insertSelectionItem(selectionItem);
    }
}
