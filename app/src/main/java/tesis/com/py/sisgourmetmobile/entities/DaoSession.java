package tesis.com.py.sisgourmetmobile.entities;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import tesis.com.py.sisgourmetmobile.entities.Person;
import tesis.com.py.sisgourmetmobile.entities.Qualification;
import tesis.com.py.sisgourmetmobile.entities.Users;
import tesis.com.py.sisgourmetmobile.entities.Drinks;
import tesis.com.py.sisgourmetmobile.entities.Lunch;
import tesis.com.py.sisgourmetmobile.entities.Order;
import tesis.com.py.sisgourmetmobile.entities.Provider;
import tesis.com.py.sisgourmetmobile.entities.Garnish;

import tesis.com.py.sisgourmetmobile.entities.PersonDao;
import tesis.com.py.sisgourmetmobile.entities.QualificationDao;
import tesis.com.py.sisgourmetmobile.entities.UsersDao;
import tesis.com.py.sisgourmetmobile.entities.DrinksDao;
import tesis.com.py.sisgourmetmobile.entities.LunchDao;
import tesis.com.py.sisgourmetmobile.entities.OrderDao;
import tesis.com.py.sisgourmetmobile.entities.ProviderDao;
import tesis.com.py.sisgourmetmobile.entities.GarnishDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig personDaoConfig;
    private final DaoConfig qualificationDaoConfig;
    private final DaoConfig usersDaoConfig;
    private final DaoConfig drinksDaoConfig;
    private final DaoConfig lunchDaoConfig;
    private final DaoConfig orderDaoConfig;
    private final DaoConfig providerDaoConfig;
    private final DaoConfig garnishDaoConfig;

    private final PersonDao personDao;
    private final QualificationDao qualificationDao;
    private final UsersDao usersDao;
    private final DrinksDao drinksDao;
    private final LunchDao lunchDao;
    private final OrderDao orderDao;
    private final ProviderDao providerDao;
    private final GarnishDao garnishDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        personDaoConfig = daoConfigMap.get(PersonDao.class).clone();
        personDaoConfig.initIdentityScope(type);

        qualificationDaoConfig = daoConfigMap.get(QualificationDao.class).clone();
        qualificationDaoConfig.initIdentityScope(type);

        usersDaoConfig = daoConfigMap.get(UsersDao.class).clone();
        usersDaoConfig.initIdentityScope(type);

        drinksDaoConfig = daoConfigMap.get(DrinksDao.class).clone();
        drinksDaoConfig.initIdentityScope(type);

        lunchDaoConfig = daoConfigMap.get(LunchDao.class).clone();
        lunchDaoConfig.initIdentityScope(type);

        orderDaoConfig = daoConfigMap.get(OrderDao.class).clone();
        orderDaoConfig.initIdentityScope(type);

        providerDaoConfig = daoConfigMap.get(ProviderDao.class).clone();
        providerDaoConfig.initIdentityScope(type);

        garnishDaoConfig = daoConfigMap.get(GarnishDao.class).clone();
        garnishDaoConfig.initIdentityScope(type);

        personDao = new PersonDao(personDaoConfig, this);
        qualificationDao = new QualificationDao(qualificationDaoConfig, this);
        usersDao = new UsersDao(usersDaoConfig, this);
        drinksDao = new DrinksDao(drinksDaoConfig, this);
        lunchDao = new LunchDao(lunchDaoConfig, this);
        orderDao = new OrderDao(orderDaoConfig, this);
        providerDao = new ProviderDao(providerDaoConfig, this);
        garnishDao = new GarnishDao(garnishDaoConfig, this);

        registerDao(Person.class, personDao);
        registerDao(Qualification.class, qualificationDao);
        registerDao(Users.class, usersDao);
        registerDao(Drinks.class, drinksDao);
        registerDao(Lunch.class, lunchDao);
        registerDao(Order.class, orderDao);
        registerDao(Provider.class, providerDao);
        registerDao(Garnish.class, garnishDao);
    }
    
    public void clear() {
        personDaoConfig.getIdentityScope().clear();
        qualificationDaoConfig.getIdentityScope().clear();
        usersDaoConfig.getIdentityScope().clear();
        drinksDaoConfig.getIdentityScope().clear();
        lunchDaoConfig.getIdentityScope().clear();
        orderDaoConfig.getIdentityScope().clear();
        providerDaoConfig.getIdentityScope().clear();
        garnishDaoConfig.getIdentityScope().clear();
    }

    public PersonDao getPersonDao() {
        return personDao;
    }

    public QualificationDao getQualificationDao() {
        return qualificationDao;
    }

    public UsersDao getUsersDao() {
        return usersDao;
    }

    public DrinksDao getDrinksDao() {
        return drinksDao;
    }

    public LunchDao getLunchDao() {
        return lunchDao;
    }

    public OrderDao getOrderDao() {
        return orderDao;
    }

    public ProviderDao getProviderDao() {
        return providerDao;
    }

    public GarnishDao getGarnishDao() {
        return garnishDao;
    }

}
