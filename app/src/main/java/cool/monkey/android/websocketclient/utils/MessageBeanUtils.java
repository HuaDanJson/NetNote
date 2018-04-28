package cool.monkey.android.websocketclient.utils;

import android.content.Context;

import com.aidebar.greendaotest.gen.DaoManager;
import com.aidebar.greendaotest.gen.MessageBeanDao;

import java.util.List;

import cool.monkey.android.websocketclient.bean.MessageBean;

public class MessageBeanUtils {

    private MessageBeanDao dbUserInfoBeanDao;

    private static MessageBeanUtils dbUserInvestmentUtils = null;

    public MessageBeanUtils(Context context) {
        dbUserInfoBeanDao = DaoManager.getInstance(context).getNewSession().getMessageBeanDao();
    }

    public static MessageBeanUtils getInstance() {
        return dbUserInvestmentUtils;
    }

    public static void Init(Context context) {
        if (dbUserInvestmentUtils == null) {
            dbUserInvestmentUtils = new MessageBeanUtils(context);
        }
    }

    /**
     * 完成对数据库中插入一条数据操作
     *
     * @param
     * @return
     */
    public void insertOneData(MessageBean dbUserInvestment) {
        dbUserInfoBeanDao.insertOrReplace(dbUserInvestment);
    }

    /**
     * 完成对数据库中插入多条数据操作
     *
     * @param dbUserInvestmentList
     * @return
     */
    public boolean insertManyData(List<MessageBean> dbUserInvestmentList) {
        boolean flag = false;
        try {
            dbUserInfoBeanDao.insertOrReplaceInTx(dbUserInvestmentList);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对数据库中删除一条数据操作
     *
     * @param dbUserInvestment
     * @return
     */
    public boolean deleteOneData(MessageBean dbUserInvestment) {
        boolean flag = false;
        try {
            dbUserInfoBeanDao.delete(dbUserInvestment);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对数据库中删除一条数据 ByKey操作
     *
     * @return
     */
    public boolean deleteOneDataByKey(long id) {
        boolean flag = false;
        try {
            dbUserInfoBeanDao.deleteByKey(id);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对数据库中批量删除数据操作
     *
     * @return
     */
    public boolean deleteManData(List<MessageBean> dbUserInvestmentList) {
        boolean flag = false;
        try {
            dbUserInfoBeanDao.deleteInTx(dbUserInvestmentList);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对数据库中数据全部删除
     *
     * @return
     */
    public boolean deleteAll() {
        boolean flag = false;
        try {
            dbUserInfoBeanDao.deleteAll();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    /**
     * 完成对数据库更新数据操作
     *
     * @return
     */
    public boolean updateData(MessageBean dbUserInvestment) {
        boolean flag = false;
        try {
            dbUserInfoBeanDao.update(dbUserInvestment);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对数据库批量更新数据操作
     *
     * @return
     */
    public boolean updateManData(List<MessageBean> dbUserInvestmentList) {
        boolean flag = false;
        try {
            dbUserInfoBeanDao.updateInTx(dbUserInvestmentList);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对数据库查询数据操作
     *
     * @return
     */
    public MessageBean queryOneData(long id) {
        return dbUserInfoBeanDao.load(id);
    }

    /**
     * 完成对数据库查询所有数据操作
     *
     * @return
     */
    public List<MessageBean> queryAllData() {
        return dbUserInfoBeanDao.loadAll();
    }

    /**
     * 完成对数据库条件查询数据操作 senderId
     *
     * @return
     */
    public List<MessageBean> queryDataDependSendId(int senderId) {
        return dbUserInfoBeanDao.queryBuilder().where(MessageBeanDao.Properties.SendUserId.eq(senderId)).build().list();
    }

    /**
     * 完成对数据库条件查询数据操作 senderId
     *
     * @return
     */
    public List<MessageBean> queryDataDependCurrentUserId(int currentUserId) {
        return dbUserInfoBeanDao.queryBuilder().where(MessageBeanDao.Properties.UserId.eq(currentUserId)).build().list();
    }

}
