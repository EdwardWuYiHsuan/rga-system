package dao;

import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.ClassUtils;

import play.Logger;
import play.Play;

/**
 * Set all of available Dao in application 
 */
public class DaoManager {
	
	public static final Class<CustomerDao> CUSTOMER = CustomerDao.class;
	public static final Class<TokenDao> TOKEN = TokenDao.class;
	
	private static final ConcurrentHashMap<Class<? extends Dao>, Dao> managers = new ConcurrentHashMap<>(); 
	
	/**
	 * Get Dao Instance
	 * @param clazz	The clazz is extended by {@link Dao}
	 * @return instance
	 * @throws NullPointerException If not find in DaoManagers
	 */
	public static <T> T getDao(Class<T> clazz) 
	{
		Dao dao = managers.get(clazz);
		
		if (null == dao) {
			for (Map.Entry<Class<? extends Dao>, Dao> entry : managers.entrySet()) {
				if (ClassUtils.isAssignable(entry.getKey(), clazz)) {
					dao = entry.getValue();
					break;
				}
			}
			
			if (null == dao)
				throw new NullPointerException(String.format("Cannot find the %s's dao", clazz.getSimpleName()));
		}
		
		return (T) dao;
	}
	
	/**
	 * Class-CustomerDaoImpl / instance obj
	 */
	public synchronized static void init() 
	{
		Logger.info("DaoManager init");
		
		if (managers.size() == 0) {
			for (Class clazz : Play.classloader.getAllClasses()) {
				if (!clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers()) && ClassUtils.isAssignable(clazz, Dao.class)) 
				{
					Dao dao;
					try {
						dao = (Dao) clazz.newInstance();
						Logger.info("Dao '%s' loaded", clazz.getSimpleName());
					} catch (Exception e) {
						throw new RuntimeException(String.format("Dao '%s' load failed!", clazz.getSimpleName()), e);
					}
					managers.put(clazz, dao);
				}
			}
		} else {
			Logger.warn("DaoManager has been initiaited!!");
		}
	}
	
	
}
