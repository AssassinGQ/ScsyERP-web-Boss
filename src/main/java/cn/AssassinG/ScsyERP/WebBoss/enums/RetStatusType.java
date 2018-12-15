package cn.AssassinG.ScsyERP.WebBoss.enums;

public enum RetStatusType {
    StatusSuccess(0),StatusFailure(1);

    private int status;
    private RetStatusType(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
