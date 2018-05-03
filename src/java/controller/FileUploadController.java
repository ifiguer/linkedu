/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import model.LoginBean;
import model.LoginBeanDA;

import org.primefaces.event.FileUploadEvent;

@ManagedBean(name = "fileUploadController")
public class FileUploadController implements Serializable {

    @ManagedProperty(value="#{LoginBean}")
    private LoginBean temp;

    public void setTemp(LoginBean temp) {
        this.temp = temp;
    }
    
    private static final String UPLOAD_DIR = "resources\\uploads";

    public void upload(FileUploadEvent event) {
        //FacesMessage msg = new FacesMessage("Success! ", event.getFile().getFileName() + " is uploaded.");
        //FacesContext.getCurrentInstance().addMessage(null, msg);
        // Do what you want with the file        
        try {
            copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void copyFile(String fileName, InputStream in) {
        try {

            String relativeWebPath = "";
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String absoluteDiskPath = servletContext.getRealPath(relativeWebPath);

            // constructs path of the directory to save uploaded file
            String uploadFilePath = absoluteDiskPath + File.separator + UPLOAD_DIR;
           
            // creates upload folder if it does not exists
            File uploadFolder = new File(uploadFilePath);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }

            String absoluteFileName = absoluteDiskPath + File.separator + UPLOAD_DIR + File.separator + fileName;
            
            File file = new File(absoluteFileName);
            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream(file);

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            in.close();
            out.flush();
            out.close();
            LoginBeanDA.storeProfileImgUrlToDB(fileName, temp.getUsername());
            temp.setProfileURL("uploads\\"+fileName);
            System.out.println("New file created at:" + file.getAbsolutePath());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Successful!", "File Uploaded to: " + file.getAbsolutePath()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
