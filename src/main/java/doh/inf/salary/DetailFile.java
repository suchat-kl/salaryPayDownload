/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doh.inf.salary;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author suchat
 */
@ManagedBean(name = "funeralGeneral")
@RequestScoped
public class DetailFile {

    /**
     * @return the rowNum
     */
    public int getRowNum() {
        return rowNum;
    }

    /**
     * @param rowNum the rowNum to set
     */
    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    private int num;
//    private String typeFile;
//    private String descFile;
    private String fileName;
//    private String fileFor = "3";
    private int rowNum;
    private String name; private String userName; private String email; 

    public DetailFile(int rowNum, String name, String userName, String email, String fileName) {
        this.rowNum = rowNum;
       
        this.name =name;
        this.userName = userName;
        this.fileName = fileName;
        this.email = email;

    }
/*
    public String getFileForName() {
        String itemLabel="";
        if (fileFor.equals("1")) {
            itemLabel = "ข้าราชการ";
        } else if (fileFor.equals("2")) {
            itemLabel = "ลูกจ้างประจำ";
        } else if (fileFor.equals("3")) {
            itemLabel = "ทั้งหมด";
        }
return itemLabel;
    }
*/
    /**
     * @return the num
     */
    public int getNum() {
        return num;
    }

    /**
     * @param num the num to set
     */
    public void setNum(int num) {
        this.num = num;
    }

    /**
     * @return the typeFile
     */
//    public String getTypeFile() {
//        return typeFile;
//    }

    /**
     * @param typeFile the typeFile to set
     */
//    public void setTypeFile(String typeFile) {
//        this.typeFile = typeFile;
//    }

    /**
     * @return the descFile
     */
//    public String getDescFile() {
//        return descFile;
//    }

    /**
     * @param descFile the descFile to set
     */
//    public void setDescFile(String descFile) {
//        this.descFile = descFile;
//    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the fileFor
     */
//    public String getFileFor() {
//        return fileFor;
//    }

    /**
     * @param fileFor the fileFor to set
     */
//    public void setFileFor(String fileFor) {
//        this.fileFor = fileFor;
//    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

}
