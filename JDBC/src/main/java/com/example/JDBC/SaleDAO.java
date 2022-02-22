package com.example.JDBC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SaleDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public SaleDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Sale> list(){
        String sql = "SELECT * FROM SALES";
        List<Sale> result = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Sale.class));
        return result;
    }

    public void save(Sale sale){
        SimpleJdbcInsert insertAction = new SimpleJdbcInsert(jdbcTemplate);
        insertAction.withTableName("Sales").usingColumns("item","quantity","amount");
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(sale);
        insertAction.execute(param);
    }

    public Sale get(int id){
        return null;
    }

    public void delete(int id){

    }

    public void update(Sale sale){

    }
}
