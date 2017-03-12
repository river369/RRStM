package com.yi.db;

import org.apache.ibatis.session.SqlSession;

/**
 * Created by jianguog on 17/3/12.
 */
public class SelectedStockDao {
    public void insert(SelectedStock selectedStock){
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        session.insert("com.yi.db.SelectedStock.insertSelectedStock", selectedStock);
        session.commit();
        session.close();
    }

    public static void main(String[] args) {
        SelectedStockDao selectedStockDao = new SelectedStockDao();
        SelectedStock selectedStock = new SelectedStock();
        selectedStock.setStock_id("SH001");
        selectedStock.setStock_name("北京首钢");
        selectedStockDao.insert(selectedStock);
    }
}
