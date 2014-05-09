package com.example.webecho

class Student implements Serializable {

	Integer studentNumber
	Integer grade
	
    static constraints = {
		studentNumber()
		grade(nullable:true)
	}
	
	String toString() {
		"$studentNumber has grade $grade"
	}
}
