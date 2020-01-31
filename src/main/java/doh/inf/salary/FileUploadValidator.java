/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doh.inf.salary;

/**
 *
 * @author suchat
 */
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

@FacesValidator(value = "fileUploadValidator")
public class FileUploadValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Part file = (Part) value;

        FacesMessage message = null;

        try {

//            if (file == null || file.getSize() <= 0 || file.getContentType().isEmpty()) {
//                message = new FacesMessage("ยังไม่เลือกไฟล์ต้องเลือกประเภทไฟล์เอกสาร(doc,docx,pdf)");
//            } else
        if (file.getSize() > (1024*1024*20) ) {
                message = new FacesMessage("ขนาดของไฟล์ต้องไม่เกิน 20 MB.");
            } else {

            /*    String[] ext = {"application/pdf", "application/msword","application/x-compressed","application/x-zip-compressed","application/zip","multipart/x-zip",
                    "application/excel","application/vnd.ms-excel","application/x-excel","application/x-msexcel","text/plain","application/mspowerpoint","application/powerpoint",
                    "application/vnd.ms-powerpoint","application/x-mspowerpoint","application/vnd.oasis.opendocument.text","application/vnd.oasis.opendocument.spreadsheet",
                    "application/vnd.oasis.opendocument.presentation","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                    "application/vnd.openxmlformats-officedocument.presentationml.presentation",
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.document"};
*/
                String[] ext={"application/x-zip-compressed","application/zip","multipart/x-zip"};
                
                String fType = file.getContentType();
                boolean found = false;
                for (String s : ext) {
                    if (s.equals(fType)) {
                        found = true;
                        break;
                    }

                }
                if (!found) {
                    String fName=Utils.getFileNameFromPart(file);
                    message = new FacesMessage(  fName +       " ไม่ตรงกับชนิดไฟล์ *.zip"     );
                    //   " ไม่ตรงกับชนิดไฟล์ *.doc,*.docx,*.xls,*.xlsx,*.ppt,*.pptx,*.txt,*.pdf,*.zip,*.odt,*.ods,*.odp" 
                }
            }
            if ((message != null && !message.getDetail().isEmpty())) {
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }

        } catch (ValidatorException ex) {
            throw new ValidatorException(new FacesMessage(ex.getMessage()));
        }

    }

}
