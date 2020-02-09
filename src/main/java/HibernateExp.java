import DatabaseEntities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.*;
import java.util.List;

public class HibernateExp {

    private static String url = "jdbc:mysql://localhost/skillbox?serverTimezone=UTC";
    private static String username = "root";
    private static String password = "58598532";

    private static int getStudentId(String studentName, Statement statement) throws SQLException {
        int studentId = -1;
        ResultSet resultSet = statement.executeQuery("SELECT id FROM Students WHERE name = '" +
                studentName + "'");
        while (resultSet.next()){
             studentId = Integer.parseInt(resultSet.getString("id"));
        }
        resultSet.close();
        return studentId;
    }

    private static int getCourseId(String courseName, Statement statement) throws SQLException {
        int courseId = -1;
        ResultSet resultSet = statement.executeQuery("SELECT id FROM Courses WHERE name = '" +
                courseName + "'");
        while (resultSet.next()){
            System.out.println("courseId before = " + courseId);
            courseId = Integer.parseInt(resultSet.getString("id"));
            System.out.println("courseId after = " + courseId);
        }
        resultSet.close();
        return courseId;
    }

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        Session session = sessionFactory.openSession();

        // Getting list of purchases
        String hql = "From " + PurchaseRecord.class.getSimpleName();
        List<PurchaseRecord> purchaseRecords = session.createQuery(hql).getResultList();

        for (PurchaseRecord purchaseRecord : purchaseRecords){

            Transaction transaction = session.beginTransaction();

            /*-----------Creating a record in linked purchase list-----------------------*/
            LinkedPurchaseRecord linkedPurchaseRecord = new LinkedPurchaseRecord();

            //getting studentId by executing sql query by student name
            String studentName = purchaseRecord.getStudentName();
            int studentId = getStudentId(studentName,statement);

            //getting courseId by executing sql query by course name
            String courseName = purchaseRecord.getCourseName();
            int courseId = getCourseId(courseName,statement);

            linkedPurchaseRecord.setStudentId(studentId);
            linkedPurchaseRecord.setCourseId(courseId);
            linkedPurchaseRecord.setPrice(purchaseRecord.getPrice());
            linkedPurchaseRecord.setSubscriptionDate(purchaseRecord.getSubscriptionDate());
            /*--------------------------------------------------------------------------*/

            session.save(linkedPurchaseRecord);
            transaction.commit();
        }
        session.close();
        sessionFactory.close();
        statement.close();
        connection.close();
    }
}
