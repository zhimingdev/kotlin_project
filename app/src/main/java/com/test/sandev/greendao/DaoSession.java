package com.test.sandev.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.test.sandev.entity.BookEntity;
import com.test.sandev.entity.CollectEntity;

import com.test.sandev.greendao.BookEntityDao;
import com.test.sandev.greendao.CollectEntityDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig bookEntityDaoConfig;
    private final DaoConfig collectEntityDaoConfig;

    private final BookEntityDao bookEntityDao;
    private final CollectEntityDao collectEntityDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        bookEntityDaoConfig = daoConfigMap.get(BookEntityDao.class).clone();
        bookEntityDaoConfig.initIdentityScope(type);

        collectEntityDaoConfig = daoConfigMap.get(CollectEntityDao.class).clone();
        collectEntityDaoConfig.initIdentityScope(type);

        bookEntityDao = new BookEntityDao(bookEntityDaoConfig, this);
        collectEntityDao = new CollectEntityDao(collectEntityDaoConfig, this);

        registerDao(BookEntity.class, bookEntityDao);
        registerDao(CollectEntity.class, collectEntityDao);
    }
    
    public void clear() {
        bookEntityDaoConfig.clearIdentityScope();
        collectEntityDaoConfig.clearIdentityScope();
    }

    public BookEntityDao getBookEntityDao() {
        return bookEntityDao;
    }

    public CollectEntityDao getCollectEntityDao() {
        return collectEntityDao;
    }

}
