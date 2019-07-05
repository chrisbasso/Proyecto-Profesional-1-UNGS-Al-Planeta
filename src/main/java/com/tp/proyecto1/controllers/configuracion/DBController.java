package com.tp.proyecto1.controllers.configuracion;


import java.io.File;
import java.io.FileFilter;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tp.proyecto1.services.ConfiguracionService;
import com.tp.proyecto1.utils.Inject;
import com.tp.proyecto1.views.configuracion.BackUpCrearForm;
import com.tp.proyecto1.views.configuracion.BackUpTomarForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.UIScope;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileFilter;

@Controller
@UIScope
public class DBController {
	
	private static String fullPathExample = "/home/ricardo/eclipse-workspace/db/alplaneta_grupo4.sql";
	@Autowired
	private ConfiguracionService configService;
	private BackUpCrearForm bckUpCrearView;
	private BackUpTomarForm bckUpTomarView;

	@Autowired
	private Environment env;
	
	public DBController() {
		Inject.Inject(this);
	}

	public void getCrearBackUpView() {
		bckUpCrearView = new BackUpCrearForm();
		agregarListenersCrear();
		bckUpCrearView.open();
	}
	
	private void agregarListenersCrear() {
		bckUpCrearView.nombreArchivoListener(e->nombreArchivo());
		bckUpCrearView.btnAceptarListener(e->validarGuardadoBackup());
	}
	
	private void nombreArchivo() {
		if(bckUpCrearView.getNombreArchivo() != null) {
			bckUpCrearView.habilitarGuardado(true);
		}else {
			bckUpCrearView.habilitarGuardado(false);
		}		
	}
	
	private void validarGuardadoBackup() {
		String path = configService.findValueByKey("backup_path");
		String nombreArchivo = bckUpCrearView.getNombreArchivo();		
		if(nombreArchivo != null) {
			boolean confirmacion = false;
			bckUpCrearView.cargarProgressBar();
			confirmacion = backup(path + nombreArchivo + ".sql");
			if(confirmacion) {
				Notification.show("Se ha guardado con éxito");
			}else {
				Notification.show("No se ha podido guardar");
			}			
			bckUpCrearView.close();
		}
	}

	 /**
     * Creates a new backup executing the mysqldump command.     *
     * @param fullPathAndName
     *            Full path and name are required to save the backup.            
     *            Example = "/home/ricardo/eclipse-workspace/db/alplaneta_grupo4.sql"
     */

	private boolean backup(String fullPathAndName) {

		String username = env.getProperty("spring.datasource.username");
		String pass = env.getProperty("spring.datasource.password");

		String sqlCmd = "mysqldump -u"+username + " -p" + pass + " --add-drop-database -B alplaneta_grupo4 -r " + fullPathAndName;

		System.out.println("Running backup");
		boolean result = execute(sqlCmd);
		if(!result) {
			System.out.println("Finalizado con error");			
		}else {
			System.out.println("Finalizado con éxito");
		}
		return result;
	}
	
	public void getTomarBackUpView() {
		bckUpTomarView = new BackUpTomarForm();
		agregarListenersTomar();
		cargarArchivos();
		bckUpTomarView.open();
	}
	
	private void agregarListenersTomar() {
		bckUpTomarView.seleccionListener(e->validarHabilitarBtn());
		bckUpTomarView.btnAceptarListener(e->validarTomarBck());		
	}
	
	private void validarHabilitarBtn() {
		String nombre = bckUpTomarView.getNombreArchivo();
		if(nombre != null) {
			bckUpTomarView.habilitarTomar(true);
		}else {
			bckUpTomarView.habilitarTomar(false);
		}
	}
	
	private void validarTomarBck() {
		String nombre = bckUpTomarView.getNombreArchivo();
		if(nombre != null) {
			bckUpTomarView.cargarProgressBar();
			boolean confirmacion = restore(nombre);			
			if(confirmacion) {
				Notification.show("Se ha cargado el back up con éxito");
			}else {
				Notification.show("No se ha podido cargar el back up");
			}	
			bckUpTomarView.close();
		}		
	}
	
	private void cargarArchivos() {

		File folder = new File("./");

	    FileFilter ff = new FileFilter() {			
			@Override
			public boolean accept(File pathname) {
				boolean esSql = FilenameUtils.getExtension(pathname.getAbsolutePath()).equals("sql");
				return esSql;
			}
		}; 
		
		File [] listOfFiles = folder.listFiles(ff);
		String [] listOfFiles2 = new String [listOfFiles.length];
		int i = 0;
		for(File file : listOfFiles) {
			listOfFiles2 [i] = file.getName();
			i++;
		}
		bckUpTomarView.cargarArchivos(listOfFiles2);
	}
	/**
     * Restores an existing backup executing a mysql command.     
     * @param fullPathAndName
     *            Full path and name are required to load the backup.            
     *            Example = "/home/ricardo/eclipse-workspace/db/alplaneta_grupo4.sql"
     */	
	private boolean restore(String fullPathAndName) {

		String username = env.getProperty("spring.datasource.username");
		String pass = env.getProperty("spring.datasource.password");

		String[] sqlCmd = new String[]{"mysql", "--user=" + username, "--password=" + pass, "-e", "source " + fullPathAndName};

		System.out.println("Running restore");
		boolean result = execute(sqlCmd);
		if(!result) {
			System.out.println("Finalizado con error");			
		}else {
			System.out.println("Finalizado con éxito");
		}
		return result;
	}

	private boolean execute(String[] sqlCmd) {
		boolean ret = false;
		try {
			Process process = Runtime.getRuntime().exec(sqlCmd);
			int result = process.waitFor();
			if (result == 0) {
				ret = true;
			} 
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ret;
	}
	
	private boolean execute(String sqlCmd) {
		boolean ret = false;
		try {
			Process process = Runtime.getRuntime().exec(sqlCmd);
			int result = process.waitFor();
			if (result == 0) {
				ret = true;
			} 
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ret;	}

	
	 /**
     * Swing example to select a folder..
     */
	public static String selectFolder() {
		JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("SQL & DB DataBaseFiles", "sql", "db");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getAbsolutePath();
            return path;
        }
        return null;
	}	
}