package jobs;

import dao.DaoManager;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

@OnApplicationStart
public class BootStrap extends Job<Void> {
	
	@Override
	public void doJob() throws Exception {
		DaoManager.init();
	}

}
