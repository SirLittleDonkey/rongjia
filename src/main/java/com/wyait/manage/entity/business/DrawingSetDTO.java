package com.wyait.manage.entity.business;
import com.wyait.manage.pojo.BasePojo;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public class DrawingSetDTO extends BasePojo implements Serializable {
    private Integer id;
    private String invCode;
    private String procedureCode;
    private Integer version;
    private String filePath;
    private MultipartFile file;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getInvCode() {
        return invCode;
    }

    public void setInvCode(String invCode) {
        this.invCode = invCode;
    }

    public String getProcedureCode() {
        return procedureCode;
    }

    public void setProcedureCode(String procedureCode) {
        this.procedureCode = procedureCode;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
