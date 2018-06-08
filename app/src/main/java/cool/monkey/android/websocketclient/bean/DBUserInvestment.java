package cool.monkey.android.websocketclient.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class DBUserInvestment {
    @Id(autoincrement = false)
    public long creatTimeAsId;  //把创建时间作为表的ID
    @Property(nameInDb = "DBUserInvestment")
    public String name;
    public String InvestmentCount;
    public String sign;

    @Generated(hash = 1349221561)
    public DBUserInvestment(long creatTimeAsId, String name, String InvestmentCount,
                            String sign) {
        this.creatTimeAsId = creatTimeAsId;
        this.name = name;
        this.InvestmentCount = InvestmentCount;
        this.sign = sign;
    }

    @Generated(hash = 1378341061)
    public DBUserInvestment() {
    }

    public long getCreatTimeAsId() {
        return this.creatTimeAsId;
    }

    public void setCreatTimeAsId(long creatTimeAsId) {
        this.creatTimeAsId = creatTimeAsId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInvestmentCount() {
        return this.InvestmentCount;
    }

    public void setInvestmentCount(String InvestmentCount) {
        this.InvestmentCount = InvestmentCount;
    }

    public String getSign() {
        return this.sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "DBUserInvestment{" +
                "creatTimeAsId=" + creatTimeAsId +
                ", name='" + name + '\'' +
                ", InvestmentCount='" + InvestmentCount + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
