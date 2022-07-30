package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class JdbcMemberRepository implements MemberRepository {

    // DB에 붙기 위해서는 Datasource가 필요하다.
    private final DataSource dataSource;

    // 그리고 스프링한테 주입도 받아야 한다.
    // application.properties에 기재한 것을 근거로 스프링부트가 DataSource를 만들어 둔다.
    public JdbcMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;

        // 아래 소스를 통해 데이터베이스 커넥션을 얻을 수 있다.
        // dataSource.getConnection()
    }


    @Override
    public Member save(Member member) {
        String sql = "insert into member(name) values(?)";

        /* 기본적인 소스
        // 데이터소스 커넥션을 가지고 온다. - 아래 소스를 통해 데이터베이스 커넥션을 얻을 수 있다.
        // Connection conn = dataSource.getConnection();

        // DB가 생성한 id 값이 필요하면 DB에서 새로 생성된 id 값을 읽어와야 한다.
        // 그 기능이 바로 RETURN_GENERATED_KEYS 이다.
        // PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        // pstmt.setString(1, member.getName());

        // 쿼리가 실행이 된다.
        // pstmt.executeUpdate();
         */

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null; // 결과를 받는 것

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, member.getName());
            pstmt.executeUpdate(); // 디비에 실제 쿼리가 이때 날라간다.
            rs = pstmt.getGeneratedKeys(); // 디비가 생성한 id 값을 return 해준다
            if (rs.next()) {
                member.setId(rs.getLong(1));
            } else {
                throw new SQLException("id 조회 실패");
            }
            return member;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs); // 리소스 반환. 반환해주지 않으면 리소스가 쌓여서 장애가 발생한다.
        }

    }

    @Override
    public Optional<Member> findById(Long id) {
        String sql = "select * from member where id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);

            rs = pstmt.executeQuery(); // 조회할 때는 executeQuery 사용

            if (rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }

    }

    @Override
    public Optional<Member> findByName(String name) {
        String sql = "select * from member where name = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<Member> findAll() {
        String sql = "select * from member";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Member> members = new ArrayList<>(); // 여러개의 return 값이 있을 수 있으므로
            while (rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                members.add(member);
            }
            return members;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource); // 꼭 이렇게 연결해야 한다.
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 에러의 발생근원지를 찾아서 단계별로 에러를 출력한다.
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource); // 꼭 이렇게 close 해야 한다. 
    }
}
