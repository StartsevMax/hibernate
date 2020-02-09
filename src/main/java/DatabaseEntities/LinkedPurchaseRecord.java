package DatabaseEntities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "LinkedPurchaseList")
@IdClass(LinkedPurchaseRecord.LinkedPurchaseRecordId.class)
@Data
public class LinkedPurchaseRecord implements Serializable {
    @Id
    @Column(name = "student_id")
    private int studentId;

    @Id
    @Column(name = "course_id")
    private int courseId;

    private int price;

    @Column(name = "subscription_date")
    private Date subscriptionDate;

    @EqualsAndHashCode
    @ToString
    @Data
    public static class LinkedPurchaseRecordId implements Serializable{
        @Column(name = "student_id")
        private int studentId;

        @Column(name = "course_id")
        private int courseId;

        public LinkedPurchaseRecordId(){}

        public LinkedPurchaseRecordId(int studentId, int courseId){
            this.studentId = studentId;
            this.courseId = courseId;
        }
    }
}
