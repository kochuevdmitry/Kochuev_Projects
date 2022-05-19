package com.example.test1.data;

import javax.persistence.*;

@Entity
@Table(name = "requests_history")
public class RequestsHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "request_value")
    private Double requestValue;

    @Column(name = "request_fx")
    private String requestFX;

    @Column(name = "output_value")
    private Double outputValue;

    @Column(name = "output_fx")
    private String outputFX;

    @Column(name = "success")
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public String getRequestFX() {
        return requestFX;
    }

    public void setRequestFX(String requestFX) {
        this.requestFX = requestFX;
    }

    public Double getRequestValue() {
        return requestValue;
    }

    public void setRequestValue(Double requestValue) {
        this.requestValue = requestValue;
    }

    public Double getOutputValue() {
        return outputValue;
    }

    public void setOutputValue(Double outputValue) {
        this.outputValue = outputValue;
    }

    public String getOutputFX() {
        return outputFX;
    }

    public void setOutputFX(String outputFX) {
        this.outputFX = outputFX;
    }
}
