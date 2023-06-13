package utils;

import models.Number;

import java.util.List;

import static org.jdbi.v3.core.Jdbi.*;

public class JDBIReader {
    private final static String URL = "jdbc:postgresql://localhost:4567/postgres?user=postgres&password=Qre@1657koa";

    private final static String QUERY_SELECT_ALL = "select * from numbers";
    private final static String QUERY_SELECT_BY_ID = "select * from numbers where id=?";
    private final static String QUERY_INSERT = "insert into numbers (num1, num2, num3, num4, num5, num6, num7, num8, num9, num10) values(:num1, :num2, :num3, :num4, :num5, :num6, :num7, :num8, :num9, :num10)";
    private final static String QUERY_UPDATE = "update numbers set num1=:num1 where id=:id";
    private final static String QUERY_DELETE = "delete from numbers where id=:id";

    public static List<Number> getNumbersFromDB() {
        return create(URL).withHandle(hedler -> {
            return hedler.createQuery(QUERY_SELECT_ALL).mapToBean(Number.class).list();
        });
    }

    public static void main(String[] args) {
        insertNumbersFromDB(new Number(3, 1001,1002,1003,1004,1005,1006,1006,1008,1009,1010));
        updateNumbersFromDB(new Number(3,29,1002,1003,1004,1005,1006,1006,1008,1009,1010));
        deleteNumbersFromDB(new Number(3,29,1002,1003,1004,1005,1006,1006,1008,1009,1010));
        List<Number> numbers = getNumbersFromDB();
        System.out.println();
    }

    public static int insertNumbersFromDB(Number number) {
        return create(URL).withHandle(hedler -> {
            return hedler.createUpdate(QUERY_INSERT).bindBean(number).executeAndReturnGeneratedKeys()
                    .mapTo(Integer.class).one();
        });
    }

    public static int updateNumbersFromDB(Number number) {
        return create(URL).withHandle(hedler -> {
            return hedler.createUpdate(QUERY_UPDATE).bindBean(number).executeAndReturnGeneratedKeys()
                    .mapTo(Integer.class).one();
        });
    }

    public static int deleteNumbersFromDB(Number number) {
        return create(URL).withHandle(hedler -> {
            return hedler.createUpdate(QUERY_DELETE).bindBean(number).executeAndReturnGeneratedKeys()
                    .mapTo(Integer.class).one();
        });
    }
}


