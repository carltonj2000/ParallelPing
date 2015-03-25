package com.carltonjoseph;

import java.util.ArrayList;
import java.util.List;

public class ParallelPing {
	static final int MAX_PING = 256;
	static final String IP_BASE = "192.168.1.";
	
	static class ProcIp {
		public Process proc; public String ip;
		ProcIp (Process proc, String ip) { this.proc = proc; this.ip = ip; }
	}

	public static void main(String[] args) {
		final List<ProcIp> procsIps = new ArrayList<>();
		
		System.out.println("Pings About To Started");
		for (int ip_sub=0; ip_sub < MAX_PING; ip_sub++) {
			try {
				String ip = IP_BASE + Integer.toString(ip_sub);
				procsIps.add(new ProcIp(Runtime.getRuntime().exec("ping -c 1 -w 3 " + ip), ip));
			} catch (Throwable t) { t.printStackTrace(); }
		}

		System.out.println("Pings Started");

		for (ProcIp procIp: procsIps) {
			try {
				procIp.proc.waitFor();
				if (procIp.proc.exitValue() == 0) System.out.println("Active IP = " + procIp.ip);
			} catch (Throwable t) { t.printStackTrace(); }
		}
		System.out.println("Pings Ended");
	}
}
