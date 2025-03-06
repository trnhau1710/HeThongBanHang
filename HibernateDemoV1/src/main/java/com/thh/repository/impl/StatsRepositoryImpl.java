/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thh.repository.impl;

import com.thh.hibernatedemov1.HibernateUtils;
import com.thh.pojo.OrderDetail;
import com.thh.pojo.Product;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author admin
 */
public class StatsRepositoryImpl {
    public List<Object[]> statsRevenueByProduct(){
        try (Session s = HibernateUtils.getFACTORY().openSession()){
            CriteriaBuilder b = s.getCriteriaBuilder();
            CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
            Root root = q.from(OrderDetail.class);
            Join<OrderDetail, Product> join = root.join("productId",JoinType.RIGHT);
            
            q.multiselect(join.get("id"), join.get("name"), b.sum(b.prod(root.get("quantity"),root.get("unitPrice"))));
            q.groupBy(join.get("id"));
            
            Query query = s.createQuery(q);
            return query.getResultList();
        }
    }
}
