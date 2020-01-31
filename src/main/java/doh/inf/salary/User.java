/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doh.inf.salary;

import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.Connection;
//import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author suchat
 */
@ManagedBean(name = "user")
@SessionScoped

public class User implements Serializable {

    private int time_not_login = 0;
    private String msg_login;
    private boolean login_pass = false;
    private String oldPassword = "", newPassword1 = "", newPassword2;

    public void open_page() throws IOException {
        String page;
//        boolean pass;
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        if (session.getAttribute("pass_login") == null) {
            return;
        }
        page = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("page");
        context.getExternalContext().redirect(page);

    }

    public void open_page(String page) throws IOException {
        //  String page;
//        boolean pass;
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        if (session.getAttribute("pass_login") == null) {
            return;
        }
        //page = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("page");
        context.getExternalContext().redirect(page);

    }

    private void updatePwd() throws NoSuchAlgorithmException, NoSuchProviderException, SQLException {
        String sql;
        PreparedStatement ps = null;
        Connection con = null;
        try {
            con = sal.getConnection();

            sql = "update user_salary  set   password=?  ,updated_at=now()  where  username=?   ";

            ps = con.prepareStatement(sql);
            ps.setString(2, this.getUserName());

            ps.setString(1, Utils.getEncryptPassword(this.getNewPassword1()));

            int result = ps.executeUpdate();
        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {

            if (ps != null) {
                ps.close();
            }

            if (con != null) {
                con.close();
            }

        }

    }

    public void showMessage(String msg, boolean er) {
        if (er) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));
        }
    }

    public void changePassword() throws NoSuchAlgorithmException, NoSuchProviderException, SQLException {
        String msg;
        if (!this.getPassword().equals(this.getOldPassword())) {
            msg = "บันทึกรหัสผ่านเดิมไม่ถูกต้อง";
            showMessage(msg, true);
            return;
        }
        if (!this.getNewPassword1().equals(this.getNewPassword2())) {
            msg = "บันทึกรหัสผ่านใหม่ทั้งสองครั้งไม่เหมือนกัน";
            showMessage(msg, true);
            return;
        }

        updatePwd();
        logout();
    }

    /* private static final User u = new User();

    public static User getInstance() {

        return u;
    }
     */
    /**
     * @return the er1
     */
    public String getEr1() {
        return er1;
    }

    /**
     * @param er1 the er1 to set
     */
    public void setEr1(String er1) {
        this.er1 = er1;
    }

    /**
     * @return the er2
     */
    public String getEr2() {
        return er2;
    }

    /**
     * @param er2 the er2 to set
     */
    public void setEr2(String er2) {
        this.er2 = er2;
    }

    /**
     * @return the showLogin
     */
    public boolean isShowLogin() {
        return showLogin;
    }

    /**
     * @param showLogin the showLogin to set
     */
    public void setShowLogin(boolean showLogin) {
        this.showLogin = showLogin;
    }

    @Resource(name = "jdbc/salary")
    private DataSource sal;
    //@Resource(name = "jdbc/dpis")      private DataSource dpis;

    private String userName = "";
    private String password = "";
    //  private Date registerDate;
    private boolean upload;
    private boolean showLogin;
    // private boolean passLoging;
    private String username_all;
    private String permission_title;
    int mem_no, mem_type;

    private String er1, er2;
    private String pl_name;
private  Utils u=null;
    public User() {
        try {
//            this.logout();
            Context ctx = new InitialContext();
            sal = (DataSource) ctx.lookup("java:comp/env/jdbc/salary");
            //   dpis = (DataSource) ctx.lookup("java:comp/env/jdbc/dpis");
            showLogin = true;
//          this.login=true;
            er1 = "";
            er2 = "";
            msg_login = "";
            if (u == null){
           u=new  Utils();
             FacesContext context = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) context.getExternalContext().getSession(false);

                session.setAttribute("u", u);
            }
                    // this.passLoging=false;
        } catch (NamingException e) {
        }
    }

    private boolean validate_hr_usr_member() throws SQLException {
        boolean pass = false;
        String msg = "";
        String sql, d, m, y;
        int y_int;
        PreparedStatement ps = null;
        Connection con = null;
        try {
            con = sal.getConnection();
            d = this.getPassword().substring(0, 2);
            m = this.getPassword().substring(2, 4);
            y = this.getPassword().substring(4);
            y_int = Integer.parseInt(y) - 543;
            y = Integer.toString(y_int);
            String bd = y.concat("-").concat(m).concat("-").concat(d);

            sql = "select firstname   ,lastname,  TITLE_NAME_TH,    member_no,member_type from ffd_member  ,hr_title  where hr_title.title_code=ffd_member.title_code and membership='N' and id_card= '" + this.getUserName() + "' and  to_char( birth_date,'yyyy-mm-dd')= '" + bd + "'     ";
            //     sql = "select  pn_shortname, per_name,per_surname  from per_personal@dpis  p ,per_prename@dpis  n  where   p.pn_code= n.pn_code   and  per_cardno = ? and     to_char(per_birthdate,'yyyy-mm-dd')= ?    ";
            ps = con.prepareStatement(sql);
            //  ps.setString(1, Utils.getInstance().getUserName());
            //    this.msg_login=sql;
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                mem_no = result.getInt("member_no");
                mem_type = result.getInt("member_type");
                pass = true;
                username_all = result.getString("TITLE_NAME_TH").concat(result.getString("firstname")).concat("  ").concat(result.getString("lastname"));
                if (mem_type == 1) {
                    msg = " สมาชิกสามัญ";
                } else if (mem_type == 2) {
                    msg = " สมาชิกสมทบ";
                }
                msg = "เลขสมาชิก " + mem_no + msg;
            }

            if (pass) {
                this.create_permission("1");
//                Utils u = Utils.getInstance();
                //this.setPermission_title( u.getPermission());
                this.setPermission_title(Utils.permission_list().get(u.getPermission()) + msg);
                //    this.setPermission_title(msg);
                this.setShowLogin(false);
            } else {
                this.time_not_login++;
            }

        } //try //try
        catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {

            if (ps != null) {
                ps.close();
            }

            if (con != null) {
                con.close();
            }

        }
        return pass;
    }

    private void validate_salary_usr() throws SQLException, NoSuchAlgorithmException, NoSuchProviderException {
        boolean pass = false;
        String sql, d, m, y;
        int y_int;
        PreparedStatement ps = null;
        Connection con = null;
        try {
            con = sal.getConnection();

            //   sql = "select count(*) as cnt  from per_personal@dpis  where per_cardno = ? and per_birthdate= ?    ";
            //select  per_name,per_surname,per_cardno from per_personal where per_cardno ='3301500165001'  and per_birthdate='1971-02-18' 
            /*      d = this.getPassword().substring(0, 2);
            m = this.getPassword().substring(2, 4);
            y = this.getPassword().substring(4);
            y_int = Integer.parseInt(y) - 543;
            y = Integer.toString(y_int);
            String bd = y.concat("-").concat(m).concat("-").concat(d);*/
//            sql = "select  pn_shortname, per_name,per_surname  from per_personal@dpis  p ,per_prename@dpis  n  where   p.pn_code= n.pn_code   and  p.per_cardno = '" + this.getUserName() + "' and  p.per_birthdate= '" + bd + "' ";
//            sql = "select  pn_shortname, per_name,per_surname  from per_personal@dpis p,  per_prename@dpis  n  where   p.pn_code= n.pn_code   and  p.per_cardno = '" + this.getUserName() + "' and p.per_birthdate =  '" + bd + "'  ";
            //     sql = "select  pn_shortname, per_name,per_surname  from per_personal@dpis  p ,per_prename@dpis  n  where   p.pn_code= n.pn_code   and  per_cardno = ? and     to_char(per_birthdate,'yyyy-mm-dd')= ?    ";
            sql = "SELECT NAME,filename,upload  FROM user_salary WHERE username= ? AND PASSWORD=? and login='1'  ";
            ps = con.prepareStatement(sql);
            ps.setString(1, this.getUserName());
            ps.setString(2, Utils.getEncryptPassword(this.getPassword())); //"879349e99a2cb625971d386bc348aa15"
            // ps.setDate(2, java.sql.Date.valueOf(bd));
            //String s=d.concat("/").concat(m).concat("/").concat(y);
            // Date bd = (Date) new SimpleDateFormat("yyyy-mm-dd").parse(s);
            //   bd = "1971-02-18";
            // System.out.println(bd);
            //ps.setString(2, bd);
            //username_all = bd;
            // System.out.print(bd);
            //   this.msg_login="before loop";
            ResultSet result = ps.executeQuery();
            String fileName = "";boolean upload=false;
            while (result.next()) {
                //     if (result.getInt("cnt") == 1) {
                username_all = "สวัสดี ".concat(result.getString("name"));//    result.getString("pn_shortname").concat(" ").concat(result.getString("per_name")).concat("  ").concat(result.getString("per_surname"));
                //   card_id = result.getString("per_cardno");
                pass = true;
                fileName = result.getString("fileName");
                upload=result.getBoolean("upload");
                FacesContext context = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) context.getExternalContext().getSession(false);

                session.setAttribute("pass_login", pass);
                /*     FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);

        this.yymm = (String) session.getAttribute("selYYMM");
                 */
            }
            if (pass) {
                if (upload){
                    this.create_permission("2");
                }
                else {
                this.create_permission("1");
                }
//                Utils u = Utils.getInstance();
                //this.setPermission_title( u.getPermission());
                this.setPermission_title(Utils.permission_list().get(u.getPermission()));
                u.setUserName(this.getUserName());
                u.setFileName(fileName);
                u.setUpload(upload);
                this.setShowLogin(false);
                //this.msg_login=this.getUsername_all() +"  " +this.showLogin;
            } else {
                this.time_not_login++;
//                this.setEr1("ข้อผิดพลาด");
//                this.setEr2(" ต้องบันทึกชื่อและรหัสผ่านตามรูปแบบที่กำหนด");
            }

        } //try //try
        catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {

            if (ps != null) {
                ps.close();
            }

            if (con != null) {
                con.close();
            }

        }
        //    return pass;
    }

    private void create_permission(String level) {
        /*FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        session.setAttribute("permission", "1");
        session.setAttribute("user", this.getUserName());*/
//        Utils u = Utils.getInstance();

        u.setPermission(level);
        if (level.equals("1")) {
            u.setUserName(this.getUserName());
            if (pl_name != null) {
                u.setMember_no(this.mem_no);
                u.setMember_type(mem_type);
                u.setPl_name(pl_name);
            }
        } else if (level.equals("2")) {

            //     username_all=  (mem_no +","  +mem_type+","+pl_name   );
        }
    }

    public String validate() throws SQLException, ParseException, NoSuchAlgorithmException, NoSuchProviderException {
        //  boolean pass;
        this.validate_salary_usr();
//        this.validate_hr_usr_member();
//        this.validate_upload_files();
        if (this.time_not_login == 1) //3
        {
            this.setEr1("ข้อผิดพลาด");
            this.setEr2(" ต้องบันทึกชื่อและรหัสผ่านตามรูปแบบที่กำหนด");

            msg_login = "ชื่อหรือรหัสผ่านไม่ถูกต้อง";
        } else {
            login_pass = true;

        }
        //  
        //
//         if (!pass)
//        pass = this.validate_dpis_usr();

        //  this.setEr1("ข้อผิดพลาด");
        //      this.setEr2(" ต้องบันทึกชื่อและรหัสผ่านตามรูปแบบที่กำหนด");
//        if (!pass) {
        //    msg_login = "ชื่อหรือรหัสผ่านไม่ถูกต้อง";
        //    return "index";
//        }
        //done=!done;
        /*    if (pass.equals("true")) {
            pass = "false";
        } else {
            pass = "true";
        }*/
        //pass=!pass;
        // this.setPassLoging(!pass);
        //       this.setShowLogin(!pass);   ----->display
        //this.setShowLogin(!showLogin);
        //   this.create_session();
        //not check position      
//        pass = this.validate_dpis_pos();
        //     pass=this.validate_hr_usr_member(); *************
//        if (!pass) {
        // other position dpis =>continue
        //   pass=validate_dpis_position();
        //      pass = this.validate_hr_usr_member();
//        }
        //       pass=this.validate_upload_files();
        //    pass = this.validate_upload_files();
        return "index";
    }

    public boolean passLogin() {
//    boolean done =false;
//    String u=this.getUserName();

        return !this.showLogin;
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
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
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

    /**
     * @return the username_all
     */
    public String getUsername_all() {
        return username_all;
    }

    /**
     * @return the permission_title
     */
    public String getPermission_title() {
        return permission_title;
    }

    /**
     * @param permission_title the permission_title to set
     */
    public void setPermission_title(String permission_title) {
        this.permission_title = permission_title;
    }

    public void logout() {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            context.getExternalContext().redirect("index.xhtml");
//            Utils u = Utils.getInstance();
            u.setPermission("");
            u.setUserName("");
            context.getExternalContext().invalidateSession();
        } catch (IOException e) {
        }
    }

    /*
    private boolean validate_dpis_pos() throws SQLException {
        boolean pass = false;
        String sql;
        PreparedStatement ps = null;
        Connection con = null;
        try {
            con = hr.getConnection();
            //sql = "select member_no,member_type from ffd_member where membership='N' and id_card= ?    ";
            sql = "  select  pl_name,per_cardno   from per_personal@dpis p1 ,per_position@dpis  p2 ,per_line@dpis p3 where p1.pos_id= p2.pos_id  "
                    + "  and per_status=1         and p3.pl_code=p2.pl_code  and p3.pl_code    "
                    + "  in ('511612','511104','520423','520412') and p1.per_cardno=? ";  //'3920100266971'
            //  sql="select 'xyz' as pl_name from per_personal where per_personal.per_cardno='3920100266971'    ";
            //     sql = "select  pn_shortname, per_name,per_surname  from per_personal@dpis  p ,per_prename@dpis  n  where   p.pn_code= n.pn_code   and  per_cardno = ? and     to_char(per_birthdate,'yyyy-mm-dd')= ?    ";
            ps = con.prepareStatement(sql);
            ps.setString(1, Utils.getInstance().getUserName());

            ResultSet result = ps.executeQuery();
            while (result.next()) {
                pl_name = result.getString("pl_name");
                //mem_type = result.getInt("member_type");
                pass = true;
            }

            if (pass) {
                this.create_permission("2");
                Utils u = Utils.getInstance();
                //this.setPermission_title( u.getPermission());
                this.setPermission_title(Utils.permission_list().get(u.getPermission()));
            }

        } //try
        catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {

            if (ps != null) {
                ps.close();
            }

            if (con != null) {
                con.close();
            }

        }
        return pass;
    }
     */
    private void validate_upload_files() throws SQLException, NoSuchAlgorithmException, NoSuchProviderException {
        boolean pass = false;
        String sql;
        PreparedStatement ps = null;
        Connection con = null;
        try {
            con = sal.getConnection();
            String idcard = Utils.getEncryptPassword(this.getUserName());
            this.msg_login = "xxxxxxxxxxyyyyyyy";
            //   this.username_all=idcard;
            sql = "select count(*) as cnt  from funeral_idcard  where idcard=  '" + idcard + "'    ";
            //   sql="select count(*) as cnt  from funeral_idcard  where idcard='200a13774ca1009df7839256feefd883'    ";
            this.msg_login = sql;
            //     sql = "select  pn_shortname, per_name,per_surname  from per_personal@dpis  p ,per_prename@dpis  n  where   p.pn_code= n.pn_code   and  per_cardno = ? and     to_char(per_birthdate,'yyyy-mm-dd')= ?    ";
            ps = con.prepareStatement(sql);

            //  System.out.print(idcard);
            //          ps.setString(1, idcard);
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                int cnt = result.getInt("cnt");
                //mem_type = result.getInt("member_type");
                if (cnt == 1) {
                    pass = true;
                }
            }

            if (pass) {
                this.create_permission("2");
//                Utils u = Utils.getInstance();
                //this.setPermission_title( u.getPermission());
                this.setPermission_title(Utils.permission_list().get(u.getPermission()));
                this.setShowLogin(false);
            } else {
                this.time_not_login++;
            }
        } //try //try
        catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {

            if (ps != null) {
                ps.close();
            }

            if (con != null) {
                con.close();
            }

        }
        //     return pass;
    }

    /**
     * @return the msg_login
     */
    public String getMsg_login() {
        return msg_login;
    }

    /**
     * @return the oldPassword
     */
    public String getOldPassword() {
        return oldPassword;
    }

    /**
     * @param oldPassword the oldPassword to set
     */
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    /**
     * @return the newPassword1
     */
    public String getNewPassword1() {
        return newPassword1;
    }

    /**
     * @param newPassword1 the newPassword1 to set
     */
    public void setNewPassword1(String newPassword1) {
        this.newPassword1 = newPassword1;
    }

    /**
     * @return the newPassword2
     */
    public String getNewPassword2() {
        return newPassword2;
    }

    /**
     * @param newPassword2 the newPassword2 to set
     */
    public void setNewPassword2(String newPassword2) {
        this.newPassword2 = newPassword2;
    }
}
