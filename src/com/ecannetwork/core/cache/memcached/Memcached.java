package com.ecannetwork.core.cache.memcached;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

public class Memcached
{
	public MemCachedClient client()
	{
		return this.mcc;
	}

	// create a static client as most installs only need
	// a single instance
	private MemCachedClient mcc = null;

	public void init()
	{

		// server list and weights
		String[] svrs = this.servers.trim().split("\\|");

		String[] servers = new String[svrs.length];
		Integer[] weights = new Integer[svrs.length];

		for (int i = 0; i < svrs.length; i++)
		{
			String[] s = svrs[i].split(":");
			servers[i] = s[0] + ":" + s[1];
			weights[i] = Integer.valueOf(s[2]);
		}

		// grab an instance of our connection pool
		SockIOPool pool = SockIOPool.getInstance();

		// set the servers and the weights
		pool.setServers(servers);
		pool.setWeights(weights);

		// set some basic pool settings
		// 5 initial, 5 min, and 250 max conns
		// and set the max idle time for a conn
		// to 6 hours
		pool.setInitConn(this.initConn > 0 ? this.initConn : 5);
		pool.setMinConn(this.minConn > 0 ? this.minConn : 5);
		pool.setMaxConn(this.maxConn > 0 ? this.maxConn : 250);
		pool.setMaxIdle(this.maxIdle > 0 ? this.maxIdle : 1000 * 60 * 30);

		// set the sleep for the maint thread
		// it will wake up every x seconds and
		// maintain the pool size
		pool.setMaintSleep(this.maxMainSleep/1000 > 0 ? this.maxMainSleep/1000 : 30);

		// set some TCP settings
		// disable nagle
		// set the read timeout to 3 secs
		// and don’t set a connect timeout
		pool.setNagle(false);
		pool.setSocketTO(this.socketTimeout > 0 ? this.socketTimeout : 3000);
		pool
				.setSocketConnectTO(this.connectionTimeout > 0 ? this.connectionTimeout
						: 10000);

		// initialize the connection pool
		pool.initialize();

		mcc = new MemCachedClient();

		// // lets set some compression on for the client
		// // compress anything larger than 64k
		// mcc.setCompressEnable(true);
		// mcc.setCompressThreshold(64 * 1024);
	}

	private String servers;
	private int initConn;
	private int maxConn;
	private int minConn;
	private long maxIdle;
	private int maxMainSleep;
	private int socketTimeout;
	private int connectionTimeout;

	public MemCachedClient getMcc()
	{
		return mcc;
	}

	public void setMcc(MemCachedClient mcc)
	{
		this.mcc = mcc;
	}

	public String getServers()
	{
		return servers;
	}

	public void setServers(String servers)
	{
		this.servers = servers;
	}

	public int getInitConn()
	{
		return initConn;
	}

	public void setInitConn(int initConn)
	{
		this.initConn = initConn;
	}

	public int getMaxConn()
	{
		return maxConn;
	}

	public void setMaxConn(int maxConn)
	{
		this.maxConn = maxConn;
	}

	public int getMinConn()
	{
		return minConn;
	}

	public void setMinConn(int minConn)
	{
		this.minConn = minConn;
	}

	public long getMaxIdle()
	{
		return maxIdle;
	}

	public void setMaxIdle(long maxIdle)
	{
		this.maxIdle = maxIdle;
	}

	public int getMaxMainSleep()
	{
		return maxMainSleep;
	}

	public void setMaxMainSleep(int maxMainSleep)
	{
		this.maxMainSleep = maxMainSleep;
	}

	public int getSocketTimeout()
	{
		return socketTimeout;
	}

	public void setSocketTimeout(int socketTimeout)
	{
		this.socketTimeout = socketTimeout;
	}

	public int getConnectionTimeout()
	{
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout)
	{
		this.connectionTimeout = connectionTimeout;
	}
}
