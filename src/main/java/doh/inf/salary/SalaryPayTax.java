/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doh.inf.salary;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;

import javax.faces.bean.ManagedBean;
//import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
//import javax.faces.bean.RequestScoped;
//import javax.faces.bean.ViewScoped;

import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.sql.DataSource;

@ManagedBean(name = "sal")
@ViewScoped
//@SessionScoped
/**
 *
 * @author suchat
 */
public class SalaryPayTax implements Serializable {

    public String dwFilePay(String s) throws Exception {

        SAVE_DIR = s;
        u.setSave_dir(SAVE_DIR);
        return "salaryPayDetail.xhtml";
    }
    private String total = "0";

    public String dwFileTax(String total) {
//        SAVE_DIR=s;
//        u.setSave_dir(SAVE_DIR);
        this.setTotal(total);
//  FacesContext context = FacesContext.getCurrentInstance();
//        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
//        session.setAttribute("total", total);
        return "salaryPayDetail_Tax.xhtml";
    }

    /**
     * @return the SAVE_DIR
     */
    public String getSAVE_DIR() {
        return u.getSave_dir();
    }

    /**
     * @return the file_dw
     */
    public String getFile_dw() {
        return file_dw;
    }

    private Utils u ;//= Utils.getInstance();
    private String p; // = u.getPermission();
/*
    public String getP() {
        return p;
    }
     */
    public String getPermissionForUpload() {
        //    u = Utils.getInstance();
        p = u.getPermission();
        this.setPermission_upload(true);
        if (p != null && !p.isEmpty()) {
            if (p.equals("2")) //has  upload file
            {
                this.setPermission_upload(false);
            }
        }

        return "gen_UploadForm.xhtml";
    }

    /**
     * @return the permission_upload
     */
    public boolean isPermission_upload() {
        return permission_upload;

    }

    /**
     * @param permission_upload the permission_upload to set
     */
    public void setPermission_upload(boolean permission_upload
    ) {
        this.permission_upload
                = permission_upload;

    }

    /**
     * @return the add_render
     */
    public boolean isAdd_render() {
        return add_render;

    }

    /**
     * @param add_render the add_render to set
     */
    public void setAdd_render(boolean add_render
    ) {
        this.add_render
                = add_render;

    }

    /**
     * @return the lock_number
     */
    public boolean isLock_number() {
        return lock_number;

    }

    /**
     * @param lock_number the lock_number to set
     */
    public void setLock_number(boolean lock_number
    ) {
        this.lock_number
                = lock_number;

    }

    /**
     * @return the statusSaveDB_File
     */
    public boolean isStatusSaveDB_File() {
        return statusSaveDB_File;

    }

    /**
     * @param statusSaveDB_File the statusSaveDB_File to set
     */
    public void setStatusSaveDB_File(boolean statusSaveDB_File
    ) {
        this.statusSaveDB_File
                = statusSaveDB_File;

    }

    public SalaryPayTax() throws Exception {
        try {
            Context ctx
                    = new InitialContext();
            sal
                    = (DataSource) ctx
                            .lookup("java:comp/env/jdbc/salary");
            // this.readData();

            this.setStatusSaveDB_File(true);

            this.setLock_number(false);

            this.setAdd_render(true);
            this.total = "0";
//            u=new Utils();
                FacesContext context = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) context.getExternalContext().getSession(false);

             u=  (Utils) session.getAttribute("u");
            
//              FacesContext context = FacesContext.getCurrentInstance();
//        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
//     session.setAttribute("total", total);

            /*
            Utils u = Utils.getInstance();
            String p=u.getPermission();
            if (p.equals("1")) //only download file
            this.setPermission_upload(true);
            else if (p.equals("2"))  //has  upload file
                this.setPermission_upload(false);
             */
        } catch (NamingException e) {
        }
    }

    /**
     * @return the recordPerPage
     */
    public int getRecordPerPage() {
        return recordPerPage;

    }

    /**
     * @param recordPerPage the recordPerPage to set
     */
    public void setRecordPerPage(int recordPerPage
    ) {
        if (recordPerPage
                > 10) {
            return;

        }
        if (recordPerPage
                < 1) {
            return;

        }
        this.recordPerPage
                = recordPerPage;

    }

    /**
     * @return the Tnum
     */
    public int getTnum() {
        return Tnum;

    }

    /**
     * @param Tnum the Tnum to set
     */
    public void setTnum(int Tnum
    ) {
        this.Tnum
                = Tnum;

    }

    /**
     * @return the TtypeFile
     */
    public String
            getTtypeFile() {
        return TtypeFile;

    }

    /**
     * @param TtypeFile the TtypeFile to set
     */
    public void setTtypeFile(String TtypeFile
    ) {
        this.TtypeFile
                = TtypeFile;

    }

    /**
     * @return the TdescFile
     */
    public String
            getTdescFile() {
        return TdescFile;

    }

    /**
     * @param TdescFile the TdescFile to set
     */
    public void setTdescFile(String TdescFile
    ) {
        this.TdescFile
                = TdescFile;

    }

    /**
     * @return the TfileName
     */
    public String
            getTfileName() {
        return TfileName;

    }

    /**
     * @param TfileName the TfileName to set
     */
    public void setTfileName(String TfileName
    ) {
        this.TfileName
                = TfileName;

    }

    /**
     * @return the TfileFor
     */
    public String
            getTfileFor() {
        return TfileFor;

    }

    /**
     * @param TfileFor the TfileFor to set
     */
    public void setTfileFor(String TfileFor
    ) {
        this.TfileFor
                = TfileFor;

    }

    private static final long serialVersionUID
            = 1L;
    private static final int BUFFER_SIZE
            = 1024;
    private Part uploadedFile;

    private String SAVE_DIR = "pay";
    private String message;

    private int pageNum
            = 1, startRowNum, endRowNum;

    private int recordPerPage
            = 6;
    @Resource(name = "jdbc/salary")
    private DataSource sal;
//  private String yymm, emp_type, dept;

    private DetailFile[] detailFile;

    private static final int PAGE_MIN
            = 1;
    private int PAGE_MAX;

    private int rnMax;

    private String pageStr
            = "";
    private String edit
            = "false";
    // private String editID;
    private int Tnum;

    private String TtypeFile;

    private String TdescFile;

    private String TfileName;

    private String TfileFor
            = "1";
    private boolean statusSaveDB_File;

    private boolean lock_number;

    private boolean add_render;

    private boolean permission_upload;

    public void editFuneralGen() {
        // code
        //settPid((java.lang.String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pid"));
        //settPname((java.lang.String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("rownum") + ":" + (java.lang.String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pname"));
        this.setLock_number(true);

        this.setAdd_render(false);
        Tnum
                = Integer
                        .parseInt(FacesContext
                                .getCurrentInstance().getExternalContext().getRequestParameterMap().get("Tnum"));
        TtypeFile
                = (java.lang.String) FacesContext
                        .getCurrentInstance().getExternalContext().getRequestParameterMap().get("TtypeFile");
        TdescFile
                = (java.lang.String) FacesContext
                        .getCurrentInstance().getExternalContext().getRequestParameterMap().get("TdescFile");
        TfileName
                = (java.lang.String) FacesContext
                        .getCurrentInstance().getExternalContext().getRequestParameterMap().get("TfileName");
        TfileFor
                = (java.lang.String) FacesContext
                        .getCurrentInstance().getExternalContext().getRequestParameterMap().get("TfileFor");

    }

    public void cancelData() {
        this.setLock_number(false);

        this.setAdd_render(true);

        this.setTnum(0);

        this.setTtypeFile("");;

        this.setTdescFile("");

        this.setTfileName("");

        this.setTfileFor("");
        this.setTfileFor("3");

    }

    private int getRowCount(ResultSet set
    ) throws SQLException {
        int rowCount;

        int currentRow
                = set
                        .getRow();            // Get current row
        rowCount
                = set
                        .last() ? set
                                .getRow() : 0; // Determine number of rows

        if (currentRow
                == 0) // If there was no current row
        {
            set
                    .beforeFirst();                     // We want next() to go to first row

        } else // If there WAS a current row
        {
            set
                    .absolute(currentRow
                    );              // Restore it

        }
        return rowCount;

    }

    private void readData() throws NamingException,
            SQLException,
            Exception {
        FacesContext context
                = FacesContext
                        .getCurrentInstance();
        HttpSession session
                = (HttpSession) context
                        .getExternalContext().getSession(false);
//        total=(String)session.getAttribute("total");
        /*
        this.yymm = (String) session.getAttribute("selYYMM");
        this.emp_type = (String) session.getAttribute("selEMP_TYPE");
        this.dept = (String) session.getAttribute("selDiv");
         */

        int ind
                = 0;
        StringBuilder str
                = new StringBuilder();

        this.startRowNum
                = ((this.pageNum
                - 1) * (getRecordPerPage())) + 1;

        this.endRowNum
                = (this.pageNum) * (getRecordPerPage());

        String sqlCmd, sqlPageMax;

        str
                .append("  select  * from ( ");
        str
                .append("  select  dx.*  from (  ");
        str
                .append("  select   name,username,email,(@rownum := @rownum + 1) AS rn,    filename ");
        str
                .append("  from user_salary  , (SELECT @rownum := 0) r    ");
        if (getTotal().equals("0")) {
            str.append(" where  username=?  ");
        } else {
            str.append(" where  filename in ('emp.zip','gov.zip')  ");
        }
        str
                .append("  )  dx ");
        str
                .append("  ) all_data  ");

        sqlPageMax
                = str
                        .toString();
        str
                .append("  where all_data.rn between ? and ? ");
        str
                .append("  order by rn ");
        sqlCmd
                = str
                        .toString();

        PreparedStatement ps
                = null;
        Connection con
                = null;
        con
                = sal
                        .getConnection();
        ps
                = con
                        .prepareStatement(sqlCmd,
                                ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_READ_ONLY
                        );

//         Utils u = Utils.getInstance();
        //this.setPermission_title( u.getPermission());
        if (getTotal().equals("0")) {

            ps.setString(1, u.getUserName());
            ps
                    .setInt(2, startRowNum
                    );
            ps
                    .setInt(3, endRowNum
                    );
        } else {
            ps
                    .setInt(1, startRowNum
                    );
            ps
                    .setInt(2, endRowNum
                    );
        }

        ResultSet rs
                = ps
                        .executeQuery();

        ResultSet Pmax;

        ps
                = con
                        .prepareStatement(sqlPageMax,
                                ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_READ_ONLY
                        );
        if (getTotal().equals("0")) {
            ps.setString(1, u.getUserName());
        }
        Pmax
                = ps
                        .executeQuery();

        rnMax
                = this.getRowCount(Pmax
                );

        if (rnMax
                == 0) {
            rs
                    .close();
            Pmax
                    .close();
            ps
                    .close();
            con
                    .close();
            pageStr
                    = "หน้าที่ ";

            return;

        }

        this.PAGE_MAX
                = rnMax
                / getRecordPerPage();

        if (rnMax
                % getRecordPerPage() > 0) {
            this.PAGE_MAX++;

        }
        Pmax
                .close();

        pageStr
                = "หน้าที่ " + this.pageNum
                + "/" + this.PAGE_MAX;

        detailFile
                = new DetailFile[this.getRowCount(rs
                )];

        while (rs
                .next()) {

            detailFile[ind++] = new DetailFile(rs
                    .getInt("rn"), rs
                    .getString("name"),
                    rs
                            .getString("userName"),
                    rs
                            .getString("email"), rs
                    .getString("fileName")
            );

        }
        rs
                .close();

        ps
                .close();
        con
                .close();
        str
                = null;
//        System.out.println("max=" + this.PAGE_MAX);
//        System.out.println("min=" + EditData.PAGE_MIN);

    }

    /**
     * @return the emp
     */
    public DetailFile[] getDetailFile() {
        try {
            this.readData();

        } catch (Exception ex) {
            Logger
                    .getLogger(SalaryPayTax.class
                            .getName()).log(Level.SEVERE, null, ex);
        }
        return detailFile;

    }

    public DetailFile[] getDetailFile2() {
        try {
            this.setTotal("1");
            this.readData();

        } catch (Exception ex) {
            Logger
                    .getLogger(SalaryPayTax.class
                            .getName()).log(Level.SEVERE, null, ex);
        }
        return detailFile;

    }

    private void recPosStatus() {
        java.lang.String recpos
                = (java.lang.String) FacesContext
                        .getCurrentInstance().getExternalContext().getRequestParameterMap().get("rpos");

        if (recpos
                == null) {
            return;

        }
        int tmp
                = pageNum;

        if (recpos
                .equals("F")) {
            pageNum
                    = SalaryPayTax.PAGE_MIN;

        } else if (recpos
                .equals("P")) {
            pageNum--;

        } else if (recpos
                .equals("N")) {
            pageNum++;

        } else if (recpos
                .equals("L")) {
            pageNum
                    = this.PAGE_MAX;

        }
        if (pageNum
                < SalaryPayTax.PAGE_MIN) {
            pageNum
                    = SalaryPayTax.PAGE_MIN;

        } else if (pageNum
                > this.PAGE_MAX) {
            pageNum
                    = this.PAGE_MAX;

        }
        try {
            if (pageNum
                    != tmp) {
                this.readData();

            }
        } catch (NamingException ex) {
            Logger
                    .getLogger(SalaryPayTax.class
                            .getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger
                    .getLogger(SalaryPayTax.class
                            .getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger
                    .getLogger(SalaryPayTax.class
                            .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the rnMax
     */
    public String
            getRnMax() {
        return "ทั้งหมด " + rnMax
                + " รายการ";

    }

    /**
     * @return the pageStr
     */
    public String
            getPageStr() {
        return pageStr;

    }

    /**
     * @return the recPos
     */
    public void recpos() {

        recPosStatus();

        //return "gen_UploadForm";
        // return recPos;
    }

    public void updateData() throws NamingException,
            SQLException,
            IOException {
        this.setStatusSaveDB_File(false);
        String tmp
                = saveFile();
        StringBuilder str;

        str
                = new StringBuilder();

        String sqlCmd;

        str
                .append("  update  funeral_general ");
        str
                .append(" set typefile=? ,descfile=?,filename=?,fileFor=?  ");
        str
                .append("  where num=?");

        sqlCmd
                = str
                        .toString();

        PreparedStatement ps
                = null;
        Connection con
                = null;
        con
                = sal
                        .getConnection();
        ps
                = con
                        .prepareStatement(sqlCmd
                        );

        ps
                .setString(1, this.getTtypeFile());
        ps
                .setString(2, this.getTdescFile());
        ps
                .setString(3, this.TfileName
                );
        ps
                .setString(4, this.TfileFor
                );
        ps
                .setInt(5, this.getTnum());

        int i
                = ps
                        .executeUpdate();
//        System.out.println(tPname);
        //    this.setNotDigit("false");
        //   return "edit";

        this.setStatusSaveDB_File(true);

        this.setLock_number(false);

        this.setAdd_render(true);

        this.cancelData();  //clear text box  and show add button 

    }

    public Part
            getUploadedFile() {
        return uploadedFile;

    }

    public void setUploadedFile(Part uploadedFile
    ) {
        this.uploadedFile
                = uploadedFile;

    }

    private String getFileNameFromPart(Part part) {
        /*   final String partHeader = part.getHeader("content-disposition");
        for (String content : partHeader.split(";")) {
            if (content.trim().startsWith("filename")) {
                String fileName = content.substring(content.indexOf('=') + 1)
                        .trim().replace("\"", "");
                return fileName;
            }
        }
        return null;
         */

        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
       

    }

    public String
            saveFile() throws IOException,
            SQLException {
                 String fileName="";    String dir_upload = "";
        String appPath
                = "";
        FacesMessage message
                = null;
        InputStream inputStream
                = null;
        OutputStream outputStream
                = null;

        if (uploadedFile
                == null) {
            //  message = new FacesMessage("ยังไม่เลือกไฟล์ต้องเลือกประเภทไฟล์เอกสาร");
            //message.setSeverity(FacesMessage.SEVERITY_ERROR);
            //throw new ValidatorException(message);
            return null;

        }
        FacesContext context
                = FacesContext
                        .getCurrentInstance();
        ServletContext servletContext
                = (ServletContext) context
                        .getExternalContext().getContext();
        //   String path = servletContext.getRealPath("");

        boolean uploadedFileSuccess
                = false;
/////////
if (!u.isUpload()) {
    setMessage(" เฉพาะฝ่ายบัญชีเท่านั้นที่จะนำเข้าไฟล์ข้อมูล... ");
    return null;
}
        if (uploadedFile
                .getSize() > 0) {
            // gets absolute path of the web application
            appPath
                    = servletContext
                            .getRealPath("");
            // constructs path of the directory to save uploaded file
        
            switch (this.getTfileFor()) {
                case "1":
                    dir_upload = "pay";
                    break;
                case "2":
                    dir_upload = "tax";
                    break;
            }

//            this.SAVE_DIR="pay";
               String savePath
                    = appPath
                    + File.separator
                    + dir_upload;
// creates the save directory if it does not exists


            File fileSaveDir
                    = new File(savePath
                    );

            if (!fileSaveDir
                    .exists()) {
                fileSaveDir
                        .mkdir();

            }
            
          
 fileName
                    =   getFileNameFromPart(uploadedFile);
            //this.TfileName=fileName;

            this.setTfileName(fileName
            );
//         fileName="tax.zip"   ;
            if (1 > 0) {
//  this.SAVE_DIR= 
//                            fileName;
//    this.SAVE_DIR=fileName;
//return  "" ;
            }

            /**
             * destination where the file will be uploaded
             */
            File outputFile
                    = new File(appPath
                            + File.separator
                            + dir_upload
                            + File.separator
                            + fileName
                    );

            inputStream
                    = uploadedFile
                            .getInputStream();
            outputStream
                    = new FileOutputStream(outputFile
                    );

            byte[] buffer
                    = new byte[BUFFER_SIZE];

            int bytesRead
                    = 0;

            while ((bytesRead
                    = inputStream
                            .read(buffer
                            )) != -1) {
                outputStream
                        .write(buffer,
                                0, bytesRead
                        );

            }
            if (outputStream
                    != null) {
                outputStream
                        .close();

            }
            if (inputStream
                    != null) {
                inputStream
                        .close();

            }
            uploadedFileSuccess
                    = true;

        }

        if (uploadedFileSuccess
                && isStatusSaveDB_File()) {
//            saveDBFile();
            System.out
                    .println("File uploaded to : " + appPath
                    );
            /**
             * set the success message when the file upload is successful
             */
            setMessage("นำไฟล์ " + dir_upload + "/"+ fileName   +"  จัดเก็บเรียบร้อย..  "
            );

        } else {
            /**
             * set the error message when error occurs during the file upload
             */
            setMessage("Error, select atleast one file!");

        }
        /**
         * return to the same view
         */
        return null;

    }

    /**
     * @return the message
     */
    public String
            getMessage() {
        return message;

    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message
    ) {
        this.message
                = message;

    }

    public void deleteFuneralGen() throws SQLException {
        String tNum, tFileName;

        tNum
                = (java.lang.String) FacesContext
                        .getCurrentInstance().getExternalContext().getRequestParameterMap().get("Tnum");
        tFileName
                = (java.lang.String) FacesContext
                        .getCurrentInstance().getExternalContext().getRequestParameterMap().get("TfileName");
        StringBuilder str;

        str
                = new StringBuilder();

        String sqlCmd;

        str
                .append("  delete from  funeral_general  where   num =?   ");
        sqlCmd
                = str
                        .toString();

        PreparedStatement ps
                = null;
        Connection con
                = null;
        con
                = sal
                        .getConnection();
        ps
                = con
                        .prepareStatement(sqlCmd
                        );

        ps
                .setInt(1, Integer
                        .parseInt(tNum
                        ));

        int i
                = ps
                        .executeUpdate();
        FacesContext context
                = FacesContext
                        .getCurrentInstance();
        ServletContext servletContext
                = (ServletContext) context
                        .getExternalContext().getContext();
        String appPath
                = servletContext
                        .getRealPath("");
        // constructs path of the directory to save uploaded file
        //         String savePath = appPath + File.separator + SAVE_DIR;

        File outputFile
                = new File(appPath
                        + File.separator
                        + getSAVE_DIR()
                        + File.separator
                        + tFileName
                );
        if (outputFile.exists()) {
            outputFile
                    .delete();
        }
        // return("gen_UploadForm");

    }

    private void saveDBFile() throws SQLException {
        PreparedStatement ps
                = null;
        Connection con
                = null;
        con
                = sal
                        .getConnection();
        StringBuilder str;

        str = new StringBuilder();

        String sqlCmd;
        sqlCmd = "select  num from funeral_general where num= ?  ";

        ps
                = con
                        .prepareStatement(sqlCmd,
                                ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_READ_ONLY
                        );
        ps.setInt(1, this.getTnum());
        ResultSet Pmax;
        Pmax
                = ps
                        .executeQuery();
        int cnt
                = this.getRowCount(Pmax
                );

        if (cnt > 0) { //duplicat primary key num
            return;
        }

        str.append("  insert into funeral_general( ");
        str
                .append("  num,typefile,descfile,filename,fileFor)  ");
        str
                .append("  values ( ?,?,?,?,? )");

        sqlCmd
                = str
                        .toString();

        ps
                = con
                        .prepareStatement(sqlCmd
                        );

        ps
                .setInt(1, this.getTnum());
        ps
                .setString(2, this.getTtypeFile());
        ps
                .setString(3, this.getTdescFile());
        ps
                .setString(4, this.TfileName
                );
        ps
                .setString(5, this.TfileFor
                );

        int i
                = ps
                        .executeUpdate();
    }

    // A PDF to download
//    private static final String PDF_URL = "http://localhost:8080/Funeral/UploadGenForm/abc.pdf";
    /**
     * This method reads PDF from the URL and writes it back as a response.
     *
     * @throws IOException
     */
    private String file_dw;

    public String makeDWfile() {
        TfileName
                = (java.lang.String) FacesContext
                        .getCurrentInstance().getExternalContext().getRequestParameterMap().get("TfileName");
        file_dw = "/" + getSAVE_DIR() + "/" + TfileName;
        return file_dw;
    }

    public void downloadFile() throws IOException {
        // Get the FacesContext
        FacesContext facesContext = FacesContext.getCurrentInstance();

        // Get HTTP response
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
String tfile="",dir_path="";
        // Read PDF contents
        /*    URL url = new URL(PDF_URL);
        InputStream pdfInputStream = url.openStream();
         */
        //TfileName
          tfile      = (java.lang.String) FacesContext
                        .getCurrentInstance().getExternalContext().getRequestParameterMap().get("TfileName");
        //this.SAVE_DIR 
          dir_path      = (java.lang.String) FacesContext
                .getCurrentInstance().getExternalContext().getRequestParameterMap().get("path");
        String file_dw1 =            "/" + dir_path + "/" + tfile;//"/pay/060.zip";//  
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        OutputStream responseOutputStream;
        try (InputStream fileInputStream = externalContext.getResourceAsStream( file_dw1) //"/UploadGenForm/abc.pdf");
        ) {
            String mimeType =     "application/x-zip-compressed";//  URLConnection.guessContentTypeFromStream(fileInputStream); // "application/zip";//  
            //URLConnection.guessContentTypeFromName(TfileName);//
            //"application/msword";//
            // Set response headers
            response.reset();   // Reset the response in the first place
            response.setHeader("Content-Type", mimeType);//"application/pdf");  // Set only the content type
            //     String fileName = URLEncoder.encode(tchCeResource.getRname(), "UTF-8");
//fileName = URLDecoder.decode(fileName, "ISO8859_1");
tfile = URLEncoder.encode(tfile, "UTF-8");
//response.setHeader("Content-disposition", "attachment; filename=" + TfileName);
response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + tfile);
response.setContentType(mimeType + "; charset=UTF-8");
response.setCharacterEncoding("UTF-8");
            // Open response output stream
            responseOutputStream = response.getOutputStream();
            // Read PDF contents and write them to the output
            byte[] bytesBuffer = new byte[2048];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(bytesBuffer)) > 0) {
                responseOutputStream.write(bytesBuffer, 0, bytesRead);
            }   // Make sure that everything is out
            responseOutputStream.flush();
            // Close both streams
        } //  URLConnection.guessContentTypeFromStream(fileInputStream); // "application/zip";//
        responseOutputStream.close();

        // JSF doc: 
        // Signal the JavaServer Faces implementation that the HTTP response for this request has already been generated 
        // (such as an HTTP redirect), and that the request processing lifecycle should be terminated
        // as soon as the current phase is completed.
        facesContext.responseComplete();
        /*
       if(browserType.equals("IE")||browserType.equals("Chrome"))
            response.setHeader("Content-Disposition","attachment; filename="+fileName);
        if(browserType.endsWith("Firefox"))
            response.setHeader("Content-Disposition","attachment; filename*=UTF-8''"+fileName);  
        
         */
    }

    /**
     * @return the total
     */
    public String getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(String total) {
        this.total = total;
    }
}
