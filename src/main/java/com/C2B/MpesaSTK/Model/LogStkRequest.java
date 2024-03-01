package com.C2B.MpesaSTK.Model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Internal;
import org.hibernate.proxy.HibernateProxy;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name="stkrequest")
@AllArgsConstructor
@NoArgsConstructor
public class LogStkRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer     stkRequestId;


    private String businessShortCode;


    private String accountReference;


    private String phoneNumber;


    private String transactionType;


    private String partyA;


    private String partyB;


    private String callbackUrl;


    private String transactionDescription;


    private String merchantRequestId;


    private String checkoutRequestId;


    private String responseDescription;


    private String customerMessage;


    private Date dateCreated;


    private Date dateModified;

    private Integer amount;


    private String mpesaReceiptNumber;


    private String responseCode;


    private Date callbackResponseTime;


    private String cbsReference;


    private String transactionRef;


    private String resultCode;

    private String comment;


    private String branchCode;


    private String userCallbackResponseCode;


    private String  userCallbackResponseMsg ;


    private Date transactionDate;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        LogStkRequest that = (LogStkRequest) o;
        return getStkRequestId() != null && Objects.equals(getStkRequestId(), that.getStkRequestId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
