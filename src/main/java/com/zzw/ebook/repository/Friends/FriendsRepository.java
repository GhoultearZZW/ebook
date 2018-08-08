package com.zzw.ebook.repository.Friends;

import com.zzw.ebook.model.Node.Friends;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendsRepository extends GraphRepository<Friends> {

    Friends findByName(@Param("name")String name);

    @Query("match (user1:Friends),(user2:Friends) where user1.name={name1} and user2.name={name2} create (user1)-[:friend]->(user2)")
    void createRelation(@Param("name1")String name1,@Param("name2")String name2);

    @Query("match (a:Friends)-[:friend]->(b) where a.name ={name} return b")
    List<Friends> getFriends(@Param("name")String name);

}