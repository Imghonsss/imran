package ftmk.bits.autoremind;

public class Licensedetails {
    private String OwnerName;
    private String LicenseID;
    private String ClassType;
    private String ExpiryDate;

    public Licensedetails() {
    }

    public String getOwnerName() {
        return OwnerName;
    }

    public void setOwnerName(String ownerName) {
        OwnerName = ownerName;
    }

    public String getLicenseID() {
        return LicenseID;
    }

    public void setLicenseID(String licenseID) {
        LicenseID = licenseID;
    }

    public String getClassType() {
        return ClassType;
    }

    public void setClassType(String classType) {
        ClassType = classType;
    }

    public String getExpiryDate() {
        return ExpiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        ExpiryDate = expiryDate;
    }
}
