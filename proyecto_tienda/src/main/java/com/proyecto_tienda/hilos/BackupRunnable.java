package com.proyecto_tienda.hilos;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;




public class BackupRunnable implements Runnable {

	

	private String backupLocation;
	private String binLocation;

	public BackupRunnable(String backupLocation, String binLocation) {
		this.backupLocation = backupLocation;
		this.binLocation = binLocation;
	}

	@Override
	public void run() {

		if (backupLocation != null && binLocation != null) {

			if (new File(backupLocation).exists()) {

				try {

					SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyy_HH_mm_ss");
					String backupFile = String.format("\\%s.sql", sdf.format(new Date()));

					String[] command = { "cmd", "/C", String.format(
							"mysqldump -u root proyecto_web_gestion_tienda> \"%s%s\"", backupLocation, backupFile) };

					ProcessBuilder pb = new ProcessBuilder(command);
					pb.directory(new File(binLocation));

					Process p = pb.start();

					p.waitFor();



				} catch (Exception e) {

				

				}

			} else {

			}

		}

	}

}

