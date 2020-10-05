package com.proyecto_tienda.controller;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.proyecto_tienda.backup.BackUp;

@Controller
public class BackUpController {

	static Logger logger = Logger.getLogger(BackUpController.class);
	
	@Autowired
	BackUp backUp;
	
	@GetMapping("/create")
	public String createBackup(Model model, HttpSession session) {

		logger.info("Se ha solicitado un GET a /e/backup/create");

		System.out.println("estoy en el controlador");
		
		if (session.getAttribute("nombre") != null) {

			backUp.exportDB();

			return "redirect:/backUpDB";

		}

		logger.warn("Un usuario no registrado ha tratado de hacer una copia de seguridad");
		return "redirect:/login";

	}
	
	@GetMapping("/backUpDB")
	public String showBackup(Model model, HttpSession session) {

		logger.info("Se solicitó un GET a /e/backup");
		
		

		if (session.getAttribute("nombre") != null) {

			File backupLocation = new File(backUp.getBackupLocation());

			List<File> backups = Arrays.asList(backupLocation.listFiles());

			if (backupLocation.isDirectory() && !backups.isEmpty()) {

				// removes directorys
				for (File backup : backups) {

					if (backup.isDirectory()) {

						backups.remove(backup);

					}

				}

				model.addAttribute("backups", backups);

			} else {

				model.addAttribute("backups", null);

			}

			return "app/backUpDB";

		}

		return "redirect:/login";

	}
	
	@GetMapping("/backup/remove/{index}")
	public String removeBackup(@PathVariable int index, Model model, HttpSession session) {

		logger.info(String.format("Se ha solicitado un GET a /backup/remove/%d", index));

		if (session.getAttribute("nombre") != null) {

			File backupLocation = new File(backUp.getBackupLocation());

			List<File> backups = Arrays.asList(backupLocation.listFiles());

			if (backupLocation.isDirectory() && !backups.isEmpty()) {

				// removes directorys
				for (File backup : backups) {

					if (backup.isDirectory()) {

						backups.remove(backup);

					}

				}

				if (index >= 0 && index <= backups.size()) {

					String backupName = backups.get(index).getName();
					backups.get(index).delete();

					logger.info(String.format("eliminado backup %s", backupName));

					return "redirect:/backUpDB";

				}

			}

		}

		logger.warn("Un usuario no registrado ha tratado de eliminar una copia de seguridad");
		return "redirect:/backUpDB";

	}


	
	
}
