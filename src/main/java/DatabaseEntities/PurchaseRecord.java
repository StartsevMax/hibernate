package DatabaseEntities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "PurchaseList")
@IdClass(PurchaseRecord.PurchaseRecordId.class)
@Data
public class PurchaseRecord {
    @Id
    @Column(name = "student_name")
    private String studentName;

    @Id
    @Column(name = "course_name")
    private String courseName;

    private int price;

    @Column(name = "subscription_date")
    private Date subscriptionDate;

    @EqualsAndHashCode
    @ToString
    @Data
    public static class PurchaseRecordId implements Serializable {

        public PurchaseRecordId(){}

        public PurchaseRecordId(String studentName, String courseName) {
            this.studentName = studentName;
            this.courseName = courseName;
        }

        @Column(name = "student_name")
        private String studentName;

        @Column(name = "course_name")
        private String courseName;
    }
}
