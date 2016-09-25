package ua.nure.bycheva.SummaryTask4.db.entity;

import java.util.Arrays;

/**
 * Created by yulia on 13.08.16.
 */
public class Certificate extends Entity{

    private Long entrantId;

    private String fileName;

    private byte[] fileContent;

    public Certificate() {
    }

    public Certificate(Long entrantId, String fileName, byte[] fileContent) {
        this(null,entrantId,fileName, fileContent);
    }

    public Certificate(Long id, Long entrantId, String fileName, byte[] fileContent) {
        super(id);
        this.entrantId = entrantId;
        this.fileName = fileName;
        this.fileContent = fileContent;
    }

    public Long getEntrantId() {
        return entrantId;
    }

    public void setEntrantId(Long entrantId) {
        this.entrantId = entrantId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Certificate that = (Certificate) o;
        if(Long.compare(getId(), that.getId()) != 0){
            return false;
        }
        if (entrantId != null ? !entrantId.equals(that.entrantId) : that.entrantId != null) {
            return false;
        }
        if (fileName != null ? !fileName.equals(that.fileName) : that.fileName != null) {
            return false;
        }
        return Arrays.equals(fileContent, that.fileContent);

    }

    @Override
    public int hashCode() {
        int result = entrantId != null ? entrantId.hashCode() : 0;
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(fileContent);
        return result;
    }

    @Override
    public String toString() {
        return "Certificate{" +
                "entrantId =" + entrantId +
                ", fileName='" + fileName + '\'' +
                ", fileContent=" + Arrays.toString(fileContent) +
                ", getId()=" + getId() +
                "} " + super.toString();
    }
}
