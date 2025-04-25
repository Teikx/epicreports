package teik.ers.bukkit.managers.reportmg.helpers;

import teik.ers.bukkit.utilities.models.Comment;
import teik.ers.global.utils.querys.QuerysUT;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommentsListMG {
    private final Connection connection;

    private final QuerysUT querysUT;

    public CommentsListMG(QuerysUT querysUT, Connection connection) {
        this.connection = connection;

        this.querysUT = querysUT;

        updateCommentMap();
    }

    private final HashMap<Integer, Comment> commentMap = new HashMap<>();

    public void updateCommentMap(){
        String query = "SELECT * FROM CommentsTable";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            try (ResultSet resultSet = statement.executeQuery()) {
                commentMap.clear();
                while (resultSet.next()) {
                    Comment comment = querysUT.populateCommentData(resultSet);
                    commentMap.put(comment.getId(), comment);
                }
            }
        }
        catch(SQLException e){
            handleSQLException(e);
        }
    }

    //Getters

    public HashMap<Integer, Comment> getCommentMap() {
        return commentMap;
    }

    public List<Comment> getCommentList(){
        return new ArrayList<>(commentMap.values());
    }

    private void handleSQLException(SQLException e) {System.out.print("[EpicReports] Error with EpicReports: " + e.getMessage());
        e.printStackTrace();}
}
