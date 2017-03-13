package com.yi.db;

import org.apache.ibatis.session.SqlSession;

/**
 * Created by jianguog on 17/3/13.
 */
public class TemperatureDao {
    public void insertTemperature(Temperature temperature){
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();
        session.insert("com.yi.db.Temperature.insertTemperature", temperature);
        session.commit();
        session.close();
    }
}
