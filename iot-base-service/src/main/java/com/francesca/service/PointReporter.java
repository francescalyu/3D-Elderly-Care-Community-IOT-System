package com.francesca.service;


/**
 * 点位上报器
 *
 /**
 * @Author francesca

 * 2025-05-16
 */

public interface PointReporter {


    /**
     * 上传点位到远端
     */
    void uploadPoints();

    boolean start();

    boolean stop();

    void ems(int ems);
}
