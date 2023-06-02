package com.familybank.models;

public class Student {
    private long studentId;

    public Student(long studentId){
        this.studentId = studentId;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }
}
