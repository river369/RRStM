package com.yi.db;

import com.yi.utils.DateUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

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

    public void insertSelectionItemRealTime(SelectionItemRealTime selectionItemRealTime){
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        session.insert("com.yi.db.SelectionItem.insertSelectionItemRealtimeHistory", selectionItemRealTime);
        session.commit();
        session.close();
    }

    public List<SelectionItem> getSelectedStockInXDaysBefore(int days) {
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        //System.out.println(LeadTimeDateUtils.getDaysString(days));
        List<SelectionItem> selectionItemList = session.selectList("com.yi.db.SelectionItem.selectDistinctSelectionItems", DateUtils.getDaysString(days));
        session.close();
        return selectionItemList;
    }

    public static void main(String[] args) {
        SelectionDao selectionDao = new SelectionDao();
        List<SelectionItem> selectionItemList = selectionDao.getSelectedStockInXDaysBefore(-2);
        for (SelectionItem selectionItem : selectionItemList){
            System.out.println(selectionItem.getStock_id());
            System.out.println(selectionItem.getId());
        }
//        Selection selection = new Selection();
//        selection.setSelection_id(3);
//        selectionDao.insertSelection(selection);
//        SelectionItem selectionItem = new SelectionItem();
//        selectionItem.setSelection_id(3);
//        selectionItem.setStock_id("SH001");
//        selectionItem.setStock_name("北京首钢");
//        selectionDao.insertSelectionItem(selectionItem);
    }
}
