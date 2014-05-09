package com.example.webecho

import grails.converters.deep.JSON

class GradeController {
 	static final Integer incorrectNumber = -1
	
	def index = { 
		render "Methods available: grade/get?studentNumber=1234, grade/set?studentNumber=1234&grade=9"
	}
	
	def get = { 
		println params
		
		String jsonMessage = "Unknown"
		
		def studentNumber = Integer.parseInt(params.studentNumber ?: "$incorrectNumber")
		Student student = Student.findByStudentNumber(studentNumber)
		
		if(!student) {
			jsonMessage = "NOK: Could not find student"
		}
		else {
			jsonMessage = "OK"
		}
		
		def builder = new groovy.json.JsonBuilder()
		builder {
			result jsonMessage
			if(student) { 
				"grade" student.grade
			}
		}
		
		render builder.toString()
	}

    def set = { 
		println params
		println request.getHeader("user-agent")
		
		String jsonMessage = "Unknown"

		def studentNumber = Integer.parseInt(params.studentNumber ?: "$incorrectNumber")
		def grade = Integer.parseInt(params.grade ?: "$incorrectNumber" )

		if(studentNumber == incorrectNumber) {
			failed = true;
			jsonMessage = "NOK: incorrect studentNumber"
		}
		else if(grade == incorrectNumber || grade < 0 || grade > 10 ) {
			jsonMessage = "NOK: incorrect grade"
		}
		else {
			Student student = Student.findByStudentNumber(studentNumber)

			if(!student) {
				student = new Student(studentNumber:studentNumber)
			}

			student.grade = grade
			student.save()
			jsonMessage = "OK"
		}
		
		
		def builder = new groovy.json.JsonBuilder()
		builder {
			result jsonMessage
		}
		
		render builder.toString()
    }
	
	def test = { 
		println "Hello world..."
		println request
		println request.reader?.text
		//println request.JSON
		print params
		render "Check...";
	}
}
