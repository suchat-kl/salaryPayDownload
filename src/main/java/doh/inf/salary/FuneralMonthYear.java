/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doh.inf.salary;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author suchat
 */
@ManagedBean(name = "funeralMonthYear")
@ViewScoped
public class FuneralMonthYear {

    /**
     * @return the year
     */
//    public String getYear() {
//        return year;
//    }

    /**
     * @param year the year to set
     */
//    public void setYear(String year) {
//        this.year = year;
//    }

    /**
     * @return the month
     */
//    public String getMonth() {
//        return month;
//    }

    /**
     * @param month the month to set
     */
//    public void setMonth(String month) {
//        this.month = month;
//    }

    

    /**
     * @return the yyyyMM
     */
    public String getYyyyMM() {
        return yyyyMM;
    }

    /**
     * @param yyyyMM the yyyyMM to set
     */
    public void setYyyyMM(String yyyyMM) {
        this.yyyyMM = yyyyMM;
    }

    

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
    private String typeFile;
    private String descFile;
    private String fileName;
    private String fileFor = "3";
    private int rowNum;
    private String yyyyMM;
//   private String year;
//   private String month;

    public FuneralMonthYear(int rowNum, int num, String typeFile, String descFile, String fileName, String fileFor, String yyyyMM) {
        this.rowNum = rowNum;
        this.num = num;
        this.typeFile = typeFile;
        this.descFile = descFile;
        this.fileName = fileName;
        this.fileFor = fileFor;
       this.yyyyMM=yyyyMM;
//       String[] s=yyyyMM.split("-");
//       this.setYear(s[0]);
//       this.setMonth(s[1]);
    }

    public String getFileForName() {
        String itemLabel = "";
        if (fileFor.equals("1")) {
            itemLabel = "ข้าราชการ";
        } else if (fileFor.equals("2")) {
            itemLabel = "ลูกจ้างประจำ";
        } else if (fileFor.equals("3")) {
            itemLabel = "ทั้งหมด";
        }
        return itemLabel;
    }

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
    public String getTypeFile() {
        return typeFile;
    }

    /**
     * @param typeFile the typeFile to set
     */
    public void setTypeFile(String typeFile) {
        this.typeFile = typeFile;
    }

    /**
     * @return the descFile
     */
    public String getDescFile() {
        return descFile;
    }

    /**
     * @param descFile the descFile to set
     */
    public void setDescFile(String descFile) {
        this.descFile = descFile;
    }

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
    public String getFileFor() {
        return fileFor;
    }

    /**
     * @param fileFor the fileFor to set
     */
    public void setFileFor(String fileFor) {
        this.fileFor = fileFor;
    }

}
