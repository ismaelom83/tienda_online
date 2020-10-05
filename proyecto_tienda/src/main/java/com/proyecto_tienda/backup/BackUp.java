package com.proyecto_tienda.backup;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.proyecto_tienda.hilos.BackupRunnable;



@Service
public class BackUp {



	// backup location (directory)
	private String backupLocation;

	// MYSQL bin location (directory)
	private String binLocation;

	public BackUp(@Value("${backup.location}") String backupLocation,
			@Value("${mysql.bin.location}") String binLocation) {

		this.backupLocation = backupLocation;
		this.binLocation = binLocation;

	}

	/**
	 * Exports the database
	 */
	public void exportDB() {

		Thread backupThread = new Thread(new BackupRunnable(backupLocation, binLocation));
		backupThread.setName("backupThread");
		backupThread.start();

	}

	public String getBackupLocation() {
		return backupLocation;
	}

}
