/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doh.inf.salary;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.Part;
@ManagedBean(name = "utils")
@SessionScoped
/**
 *
 * @author suchat
 */
public class Utils {

   
    
    
    /**
     * @return the member_no
     */
    public int getMember_no() {
        return member_no;
    }

    public static String getFileNameFromPart(Part part) {
         for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }

    /**
     * @param member_no the member_no to set
     */
    public void setMember_no(int member_no) {
        this.member_no = member_no;
    }

    /**
     * @return the member_type
     */
    public int getMember_type() {
        return member_type;
    }

    /**
     * @param member_type the member_type to set
     */
    public void setMember_type(int member_type) {
        this.member_type = member_type;
    }

    /**
     * @return the permission
     */
    public String getPermission() {
        return permission;
    }

    /**
     * @param permission the permission to set
     */
    public void setPermission(String permission) {
        this.permission = permission;
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
//    private static final Utils u = new Utils();
    
    private String permission;
    private String userName;
    private int member_no;
    private int member_type;
    private String pl_name;
    private String fileName;
    private String save_dir;
    private boolean upload;

    public static Map<String, String> permission_list() {
        Map<String, String> permission;

        permission = new HashMap<String, String>();

        permission.put("1", "สิทธิ์ระดับที่ 1 การดาวน์โหลดเอกสารในแต่ละเดือน");
        permission.put("2", "สิทธิ์ระดับที่ 2 การนำเอกสารขึ้นเว็บ");

        return permission;
    }
/*
    public static Utils getInstance() {

        return new Utils();
    }
*/
    public  Utils() {
    
    }

    /**
     * @return the pl_name
     */
    public String getPl_name() {
        return pl_name;
    }

    /**
     * @param pl_name the pl_name to set
     */
    public void setPl_name(String pl_name) {
        this.pl_name = pl_name;
    }

    public static String getEncryptPassword(String idcard) throws NoSuchAlgorithmException, NoSuchProviderException {

        String securePassword = null;
         String prefix="2fc2f175e13a998acb3d58e5f761e08d";
        // String idcard=u.getUserName();
         
       idcard=prefix+idcard;

        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
           
            //md.update(u.getUserName().getBytes());
            md.update(idcard.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            securePassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
        }

        return securePassword;
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
     * @return the save_dir
     */
    public String getSave_dir() {
        return save_dir;
    }

    /**
     * @param save_dir the save_dir to set
     */
    public void setSave_dir(String save_dir) {
        this.save_dir = save_dir;
    }

    /**
     * @return the upload
     */
    public boolean isUpload() {
        return upload;
    }

    /**
     * @param upload the upload to set
     */
    public void setUpload(boolean upload) {
        this.upload = upload;
    }

   

}
